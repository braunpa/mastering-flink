package de.vicio.VisitedPlaces;

import org.apache.flink.shaded.com.google.common.base.Predicates;
import org.apache.flink.shaded.com.google.common.collect.Collections2;

import java.util.Collection;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventsGenerator {

    public EventsGenerator() {
        //Init all Variables
        StaticVariables.init();
    }

    public ParentEvent next() throws Exception {
        double probability = StaticVariables.rand.nextDouble();

        if(probability<0.1){
            try{
                return this.emitRiderGetsIntoTheCarEvent();
            }catch(Exception e){
                try{
                    return this.emitRiderGetsOutOfTheCarEvent();
                }catch(Exception ex){
                    return this.emitRiderGetsIntoTheCarEvent();
                }
            }
        }else if(probability>0.2 && probability<0.4){
            try{
                return this.emitRiderGetsOutOfTheCarEvent();
            }catch(Exception e){
                try{
                    return this.emitRiderGetsIntoTheCarEvent();
                }catch(Exception ex){
                    return this.emitRiderGetsOutOfTheCarEvent();
                }
            }
        }else{
            return this.emitPlaceEvent();
        }


    }

    private PlaceEvent emitPlaceEvent(){
        return new PlaceEvent(StaticVariables.getRandomCarId(), new PlaceEntity());
    }

    private RiderGetsIntoTheCarEvent emitRiderGetsIntoTheCarEvent()throws Exception {
        UserEntity rider = StaticVariables.getUserWhichIsNotRiding();
        CarEntity car = StaticVariables.getCarWithAvailableSeats();
        if(rider!=null && car!=null)
            return new RiderGetsIntoTheCarEvent(car, rider);

        throw new Exception();
    }

    private RiderGetsOutOfTheCarEvent emitRiderGetsOutOfTheCarEvent()throws Exception {
        UserEntity rider = StaticVariables.getUserWhichIsRiding();
        if(rider!=null )
            return new RiderGetsOutOfTheCarEvent(rider);

        throw new Exception();
    }

}
