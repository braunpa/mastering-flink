package de.vicio.VisitedPlaces;

public class ParentEvent {
    public int carId;

    public ParentEvent(int carId){
        if(carId <0)
            this.carId = 0;
        else
            this.carId = carId;
    }
}
