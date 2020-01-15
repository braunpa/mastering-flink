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

    @Override
    public String toString(){
        return "---------------------------------------------\n" + this.RIDERGETSINTOTHECAREVENT.toString() + "\n"
                + this.interatePlaces() + "\n"
                + this.RIDERGETSOUTOFTHECAREVENT.toString() + "\n"
                + "---------------------------------------------\n";
    }

    private String interatePlaces(){
        String str="";
        if(this.VISITEDPLACES.size()>1){
            for(PlaceEvent place : this.VISITEDPLACES){
                str += ""+place.getPlace()+", \n";
            }
            return str.substring(0,str.length()-2);
        }else{
            str = this.VISITEDPLACES.get(0).getPlace();
        }
        return str;
    }

}
