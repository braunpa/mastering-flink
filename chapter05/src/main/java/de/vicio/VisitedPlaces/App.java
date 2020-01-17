package de.vicio.VisitedPlaces;

import javafx.scene.Parent;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.*;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.nfa.aftermatch.AfterMatchSkipStrategy;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args)throws Exception {
        initExampleOne(AfterMatchSkipStrategy.skipPastLastEvent());

    }

    private static void initExampleOne(AfterMatchSkipStrategy skipStrategy)throws Exception{
        StaticVariables.skipStrategy = skipStrategy;

        final SourceFunction<ParentEvent> source;
        source = new EventsGeneratorSource(StaticVariables.GENERATERRORPROBABILITY, StaticVariables.GENERATORDELY);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<ParentEvent> inputEventStreamFromGenerator = env.addSource(source);

        Pattern<ParentEvent, ?> visitedPlacesPattern = getPattern_ONE();
        PatternStream<ParentEvent> patternStreamCEP = getPatterStream_ONE(inputEventStreamFromGenerator, visitedPlacesPattern);

        inputEventStreamFromGenerator.print();
        getResultOfPatter_ONE(patternStreamCEP).print();
        env.execute("CEP UBER Use Case");
    }

    private static Pattern<ParentEvent, ?> getPattern_ONE(){
        return Pattern.<ParentEvent>
                begin("start", StaticVariables.skipStrategy)
                .subtype(RiderGetsIntoTheCarEvent.class)
                .followedBy("middle")
                .subtype(PlaceEvent.class)
                .oneOrMore().allowCombinations()
                .next("end")
                .subtype(RiderGetsOutOfTheCarEvent.class)
                .where(
                        new SimpleCondition<RiderGetsOutOfTheCarEvent>() {
                            @Override
                            public boolean filter(RiderGetsOutOfTheCarEvent value) throws Exception {
                                return value.getCAR().checkIfRiderIsInTheCar(value.getRIDER());
                            }
                        });
    }

    private static PatternStream<ParentEvent> getPatterStream_ONE(DataStream<ParentEvent> inputEventStreamFromGenerator, Pattern<ParentEvent, ?> visitedPlacesPattern ){
        return CEP.pattern(inputEventStreamFromGenerator
                        .keyBy(
                                new KeySelector<ParentEvent, Integer>() {
                                    @Override
                                    public Integer getKey(ParentEvent parentEvent) throws Exception {
                                        return parentEvent.carId;
                                    }
                                })
                , visitedPlacesPattern);
    }
    private static DataStream<VisitedPlacesOfRiderEvent> getResultOfPatter_ONE(PatternStream<ParentEvent> patternStreamCEP){
        return patternStreamCEP.process(new PatternProcessFunction<ParentEvent, VisitedPlacesOfRiderEvent>() {
            @Override
            public void processMatch(Map<String, List<ParentEvent>> map, Context context, Collector<VisitedPlacesOfRiderEvent> collector) throws Exception {
                RiderGetsIntoTheCarEvent start = (RiderGetsIntoTheCarEvent) map.get("start").get(0);
                RiderGetsOutOfTheCarEvent end = (RiderGetsOutOfTheCarEvent) map.get("end").get(0);
                ArrayList<PlaceEvent> middle = new ArrayList<PlaceEvent>();
                if (start.getRiderId() == end.getRiderId()) {
                    if(map.get("middle").size()>1){
                        for (Object place: map.get("middle").toArray()) {
                            middle.add((PlaceEvent) place);
                        }
                    }else{
                        middle.add((PlaceEvent) map.get("middle").get(0));
                    }
                    collector.collect(new VisitedPlacesOfRiderEvent(start, end, middle));
                }
            }
        });
    }
}
