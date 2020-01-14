package de.vicio.VisitedPlaces;

public class RiderGetsOutOfTheCarEvent extends ParentEvent{
    private final UserEntity RIDER;
    private final CarEntity CAR;

    public RiderGetsOutOfTheCarEvent(UserEntity rider){
        super(rider.getCarId());
        RIDER = rider;
        CAR = StaticVariables.getCarById(rider.getCarId());

        //Passenger Variables are set by the CAR Method
        CAR.passengerGetsOutOfTheCar(rider);
    }

    public int getRiderId(){
        return this.RIDER.getUSERID();
    }

    public UserEntity getRIDER(){
        return this.RIDER;
    }

    @Override
    public String toString(){
        return "Rider " + this.RIDER.getUSERID() + " ist aus Auto " + this.carId + " ausgestiegen.";
    }
}
