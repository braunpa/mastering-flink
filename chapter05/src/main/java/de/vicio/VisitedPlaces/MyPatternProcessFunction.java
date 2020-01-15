package de.vicio.VisitedPlaces;

import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyPatternProcessFunction<IN, OUT> extends PatternProcessFunction<IN, OUT> {

    @Override
    public void processMatch(Map<String, List<IN>> map, Context context, Collector<OUT> collector) throws Exception {

    }
}
