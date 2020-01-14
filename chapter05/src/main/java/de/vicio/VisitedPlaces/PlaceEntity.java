package de.vicio.VisitedPlaces;

import java.util.Random;

public class PlaceEntity {
    private String city;
    private Enum<PlaceEnum> place;
    private String country;


    public PlaceEntity(){
        int pick = StaticVariables.rand.nextInt(PlaceEnum.values().length);
        this.place = PlaceEnum.values()[pick];
    }

    public PlaceEntity getPlace(){
        return this;
    }

    @Override
    public String toString() {
        return this.place.toString();
    }
}
