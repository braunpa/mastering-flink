package de.vicio.VisitedPlaces;

public class RiderGetsIntoTheCarEvent extends ParentEvent {
    private final UserEntity RIDER;
    private final CarEntity CAR;


    public RiderGetsIntoTheCarEvent(CarEntity car, UserEntity rider){
        super(car.getCARID());
        RIDER = rider;
        CAR = car;

        //Passenger Variables are set by the CAR Method
        CAR.passengerGetsIntoTheCar(RIDER);
    }

    public int getRiderId(){
        return this.RIDER.getUSERID();
    }

    @Override
    public String toString(){
        return "Rider " + this.RIDER.getUSERID() + " ist in Auto " + this.carId + " eingestiegen.";
    }

}
