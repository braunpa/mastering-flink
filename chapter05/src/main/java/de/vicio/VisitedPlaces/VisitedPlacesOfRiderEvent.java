package de.vicio.VisitedPlaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisitedPlacesOfRiderEvent {
    private final RiderGetsIntoTheCarEvent RIDERGETSINTOTHECAREVENT;
    private final RiderGetsOutOfTheCarEvent RIDERGETSOUTOFTHECAREVENT;
    private final List<PlaceEvent> VISITEDPLACES;

    public VisitedPlacesOfRiderEvent(RiderGetsIntoTheCarEvent riderGetsInto, RiderGetsOutOfTheCarEvent riderGetsOutof, List<PlaceEvent> places){
        this.RIDERGETSINTOTHECAREVENT = riderGetsInto;
        this.RIDERGETSOUTOFTHECAREVENT = riderGetsOutof;
        this.VISITEDPLACES = places;
    }

    public VisitedPlacesOfRiderEvent(RiderGetsIntoTheCarEvent riderGetsInto, RiderGetsOutOfTheCarEvent riderGetsOutof, PlaceEvent places){
        this.RIDERGETSINTOTHECAREVENT = riderGetsInto;
        this.RIDERGETSOUTOFTHECAREVENT = riderGetsOutof;
        this.VISITEDPLACES = Arrays.asList(places);
    }

    public VisitedPlacesOfRiderEvent(){
        this.RIDERGETSOUTOFTHECAREVENT = null;
        this.RIDERGETSINTOTHECAREVENT = null;
        this.VISITEDPLACES = null;
    }

    @Override
    public String toString(){
        if(this.RIDERGETSOUTOFTHECAREVENT!=null){
            return "---------------------------------------------\n" + this.RIDERGETSINTOTHECAREVENT.toString() + "\n"
                    + this.VISITEDPLACES.get(0).toString() + "\n"
                    + this.RIDERGETSOUTOFTHECAREVENT.toString() + "\n"
                    + "---------------------------------------------\n";
        }
        return "";
    }
}
