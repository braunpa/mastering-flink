package de.vicio.VisitedPlaces;

import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import static org.apache.flink.util.Preconditions.checkArgument;

public class EventsGeneratorSource extends RichParallelSourceFunction<ParentEvent>{
    private final double errorProbability;

    private final int delayPerRecordMillis;

    private volatile boolean running = true;

    public EventsGeneratorSource(double errorProbability, int delayPerRecordMillis) {
        checkArgument(errorProbability >= 0.0 && errorProbability <= 1.0, "error probability must be in [0.0, 1.0]");
        checkArgument(delayPerRecordMillis >= 0, "deplay must be >= 0");

        this.errorProbability = errorProbability;
        this.delayPerRecordMillis = delayPerRecordMillis;
    }

    @Override
    public void run(SourceContext<ParentEvent> sourceContext) throws Exception {
        final EventsGenerator generator = new EventsGenerator();

        while (running) {
            sourceContext.collect(generator.next());

            if (delayPerRecordMillis > 0) {
                Thread.sleep(delayPerRecordMillis);
            }
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
