package de.vicio.example;

import java.util.Random;

public class Ort extends ParentEvent {
    private String stadt;
    private Enum<OrtEnum> ort;
    private String land;


    public Ort(){
        super("Ort");
        int pick = new Random().nextInt(OrtEnum.values().length);
        this.ort = OrtEnum.values()[pick];
    }

    public String getOrt(){
        return ""+this.ort.toString()+"(" + this.stadt + ")";
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("PlaceEvent ")
                .append(this.ort.toString());

        return builder.toString();

        //return "Ort " + this.ort.toString() +" wurde gesehen";
    }
}
