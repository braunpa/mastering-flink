package de.vicio.VisitedPlaces;

import javafx.scene.Parent;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.*;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.nfa.aftermatch.AfterMatchSkipStrategy;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args)throws Exception {
        final SourceFunction<ParentEvent> source;
        source = new EventsGeneratorSource(0, 10);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<ParentEvent> inputEventStreamFromGenerator = env.addSource(source);

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
                , visitedPlacesPattern);


        DataStream<VisitedPlacesOfRiderEvent> result = visitedPlacesPatternStream.select(new PatternSelectFunction<ParentEvent, VisitedPlacesOfRiderEvent>() {
            @Override
            public VisitedPlacesOfRiderEvent select(Map<String, List<ParentEvent>> map) throws Exception {
                RiderGetsIntoTheCarEvent start = (RiderGetsIntoTheCarEvent) map.get("start").get(0);
                PlaceEvent middle = (PlaceEvent) map.get("middle").stream().collect(Collectors.toList());
                RiderGetsOutOfTheCarEvent end = (RiderGetsOutOfTheCarEvent) map.get("end").get(0);
                if (start.getRiderId() == end.getRiderId()) {
                    return new VisitedPlacesOfRiderEvent(start, end, middle);
                }else{
                    return new VisitedPlacesOfRiderEvent();
                }
            }
        });




        /*patternStreamCEP.process(new PatternProcessFunction<ParentEvent, VisitedPlacesOfRiderEvent>() {
                 @Override
                 public void processMatch(Map<String, List<ParentEvent>> map, Context context, Collector<VisitedPlacesOfRiderEvent> collector) throws Exception {
                     RiderGetsIntoTheCarEvent start = (RiderGetsIntoTheCarEvent) map.get("start");
                     PlaceEvent middle = (PlaceEvent) map.get("middle");
                     RiderGetsOutOfTheCarEvent end = (RiderGetsOutOfTheCarEvent) map.get("end");
                     if (start.getRiderId() == end.getRiderId()) {
                         collector.collect(new VisitedPlacesOfRiderEvent(start, end, middle));
                     }
                 }
             }).setParallelism(1).print();*/

        inputEventStreamFromGenerator.print();
        //visitedPlacesOfRiderEventDataStream.print();
        env.execute("CEP on Temperature Sensor");
    }
}
