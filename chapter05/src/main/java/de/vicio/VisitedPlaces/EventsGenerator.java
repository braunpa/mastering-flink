package de.vicio.VisitedPlaces;

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

        if(probability<StaticVariables.GENERATORPROBABILITY_RIDERINTOCAR){
            try{
                return this.emitRiderGetsIntoTheCarEvent();
            }catch(Exception e){
                try{
                    return this.emitRiderGetsOutOfTheCarEvent();
                }catch(Exception ex){
                    return this.emitRiderGetsIntoTheCarEvent();
                }
            }
        }else if(probability>StaticVariables.GENERATORPROBABILITY_RIDERINTOCAR &&
                 probability<StaticVariables.GENERATORPROBABILITY_RIDERINTOCAR+StaticVariables.GENERATORPROBABILITY_RIDEROUTOFCAR){
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
