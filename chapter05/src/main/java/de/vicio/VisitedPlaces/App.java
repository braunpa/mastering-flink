package de.vicio.VisitedPlaces;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.nfa.aftermatch.AfterMatchSkipStrategy;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args)throws Exception {
        final SourceFunction<ParentEvent> source;
        source = new EventsGeneratorSource(0, 1000);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<ParentEvent> inputEventStreamFromGenerator = env.addSource(source);

        DataStream<ParentEvent> partitionedInput = inputEventStreamFromGenerator;

        /**
             .keyBy(
             new KeySelector<ParentEvent, String>() {
            @Override
            public String getKey(ParentEvent parentEvent) throws Exception {
            return ""+parentEvent.getCARID();
            }
            });
         */

        Pattern<ParentEvent, ?> visitedPlacesPattern = Pattern.<ParentEvent>
                begin("start")
                    .subtype(RiderGetsIntoTheCarEvent.class)
                .followedBy("middle")
                    .subtype(PlaceEvent.class)
                .followedBy("end")
                    .subtype(RiderGetsOutOfTheCarEvent.class);
        AfterMatchSkipStrategy.noSkip();

        PatternStream<ParentEvent> visitedPlacesPatternStream = CEP.pattern(inputEventStreamFromGenerator
                .keyBy(
                        new KeySelector<ParentEvent, Integer>() {
                            @Override
                            public Integer getKey(ParentEvent parentEvent) throws Exception {
                                return parentEvent.carId;
                            }
                        })
                /**.keyBy(
                        new KeySelector<ParentEvent, Integer>() {
                            @Override
                            public Integer getKey(ParentEvent parentEvent) throws Exception {
                                if(parentEvent instanceof RiderGetsOutOfTheCarEvent ){
                                    return ((RiderGetsOutOfTheCarEvent) parentEvent).getRiderId();
                                }else if(parentEvent instanceof RiderGetsIntoTheCarEvent){
                                    return ((RiderGetsIntoTheCarEvent) parentEvent).getRiderId();
                                }
                                return 0;
                            }
                        }
                )*/
                , visitedPlacesPattern);

        DataStream<VisitedPlacesOfRiderEvent> visitedPlacesOfRiderEventDataStream = visitedPlacesPatternStream
                .select(new PatternSelectFunction<ParentEvent, VisitedPlacesOfRiderEvent>() {
                      @Override
                      public VisitedPlacesOfRiderEvent select(Map<String, ParentEvent> map) throws Exception {
                          RiderGetsIntoTheCarEvent start = (RiderGetsIntoTheCarEvent) map.get("start");
                          PlaceEvent middle = (PlaceEvent) map.get("middle");
                          RiderGetsOutOfTheCarEvent end = (RiderGetsOutOfTheCarEvent) map.get("end");
                          return new VisitedPlacesOfRiderEvent(start, end, middle);
                      }
                  });


        inputEventStreamFromGenerator.print();
        visitedPlacesOfRiderEventDataStream.print();
        env.execute("CEP on Temperature Sensor");
    }
}
