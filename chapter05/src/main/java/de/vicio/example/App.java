package de.vicio.example;


import java.util.Map;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.nfa.aftermatch.AfterMatchSkipStrategy;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.time.Time;


import javax.xml.transform.Result;


public class App {
    public static void main(String[] args)throws Exception {
        final SourceFunction<ParentEvent> source;
        source = new EventsGeneratorSource(0, 1);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<ParentEvent> inputEventStream = env.addSource(source);



        Pattern<ParentEvent, ?> warningPattern = Pattern.<ParentEvent>
                begin("start")
                    .subtype(FahrgastEinstieg.class)
                .followedBy("middle")
                    .subtype(Ort.class)
                .next("end")
                    .subtype(FahrgastAusstieg.class);

        AfterMatchSkipStrategy.noSkip();

        PatternStream<ParentEvent> patternStream = CEP.pattern(inputEventStream, warningPattern);

        DataStream<ResultObj> alarms = patternStream.select(new PatternSelectFunction<ParentEvent, ResultObj>() {
            @Override
            public ResultObj select(Map<String, ParentEvent> map) throws Exception {
                FahrgastEinstieg fahrgastEinstieg = (FahrgastEinstieg) map.get("start");
                Ort ort = (Ort) map.get("middle");
                FahrgastAusstieg fahrgastAusstieg = (FahrgastAusstieg) map.get("end");
                return new ResultObj(new Fahrgast(fahrgastEinstieg.getName(),"", (int) Integer.parseInt(fahrgastEinstieg.getFahrgast())));
            }
        });

        //inputEventStream.print();
        alarms.map(v -> v.toString()).print();

        env.execute("CEP on Temperature Sensor");
    }
}
