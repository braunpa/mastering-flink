package de.vicio.example;

import javafx.scene.Parent;

public class FahrgastEinstieg extends ParentEvent {
    private Fahrgast fahrGast;


    public FahrgastEinstieg(Fahrgast fahrGast) {
        super("FahrerEinstieg");
        this.fahrGast = fahrGast;
    }

    public String getFahrgast(){
        return this.fahrGast.getId();
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("FahrgastEinstieg ")
                .append(this.fahrGast.toString())
                .append(" ist um "+this.getDate())
                .append(" eingestiegen");

        return builder.toString();


        //return this.getName() + this.fahrGast.toString() + " ist um " + this.getDate() + " eingestiegen.";
    }
}
