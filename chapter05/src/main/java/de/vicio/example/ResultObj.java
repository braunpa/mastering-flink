package de.vicio.example;

import java.util.ArrayList;

public class ResultObj {
    private ArrayList<Ort> visitedPlaces = new ArrayList<Ort>();
    private Fahrgast fahrgast;
    private FahrgastAusstieg fahrgastAusstieg;
    private FahrgastEinstieg fahrgastEinstieg;

    private String test;

    public ResultObj(Fahrgast fahrgast){
        this.fahrgast = fahrgast;
    }

    @Override
    public String toString(){
        return ""+this.fahrgast.getID() + "" + this.fahrgast.toString();
    }

    public void setFahrgast(Fahrgast fahrgast) {
        this.fahrgast = fahrgast;
    }

    public void setFahrgastAusstieg(FahrgastAusstieg fahrgastAusstieg) {
        this.fahrgastAusstieg = fahrgastAusstieg;
    }

    public void setFahrgastEinstieg(FahrgastEinstieg fahrgastEinstieg) {
        this.fahrgastEinstieg = fahrgastEinstieg;
    }

    public void setVisitedPlaces(Ort place) {
        this.visitedPlaces.add(place);
    }

    public Fahrgast getFahrgast() {
        return fahrgast;
    }

    public FahrgastAusstieg getFahrgastAusstieg() {
        return fahrgastAusstieg;
    }

    public FahrgastEinstieg getFahrgastEinstieg() {
        return fahrgastEinstieg;
    }

    public String getVisitedPlaces() {
        String vistedPlaces = "";
        for (Ort place: this.visitedPlaces) {
            vistedPlaces += ""+place.getOrt()+", ";
        }
        return vistedPlaces.substring(0,vistedPlaces.length()-1);
    }
}
