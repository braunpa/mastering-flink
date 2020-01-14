package de.vicio.VisitedPlaces;

public class PlaceEvent extends ParentEvent {

    private final PlaceEntity PLACE;

    public PlaceEvent(int carId, PlaceEntity place){
        super(carId);
        this.PLACE = place;
    }

    public String toString(){
        return "Car " + this.carId + " -- " + this.PLACE.toString();
    }

    public String getPlace(){
        return this.PLACE.toString();
    }
}
