package de.vicio.VisitedPlaces;

import java.util.ArrayList;

public class CarEntity {
    private final int CARID;
    private final int CARSEATS;
    private boolean isFull;
    private ArrayList<Object> passengers = new ArrayList<Object>();

    public CarEntity(int carSeats){
        CARSEATS = carSeats;
        CARID = this.createCARID();
    }

    public boolean passengerGetsIntoTheCar(UserEntity passenger){
        if(!isFull){
            passengers.add(passenger);
            passenger.getsInTheCar(this);
            isFull = !this.hasSeats();
        }else{
            return false;
        }
        return true;
    }

    public boolean passengerGetsOutOfTheCar(UserEntity passenger){
        passengers.remove(passenger);
        passenger.getsOutOfTheCar();
        isFull=!this.hasSeats();
        return isFull;
    }

    public boolean hasSeats(){
        return passengers.size()<CARSEATS;
    }

    private int createCARID(){
        return StaticVariables.carIDCounter++;
    }


    public int getCARID(){
        return CARID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CarEntity) {
            CarEntity carEntity = (CarEntity) obj;
            return carEntity.equals(this) && CARID == carEntity.CARID;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return CARID;
    }




}
