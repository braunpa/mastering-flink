package de.vicio.example;

public class FahrgastAusstieg extends ParentEvent {
    private Fahrgast fahrGast;

    public FahrgastAusstieg(Fahrgast fahrGast) {
        super("FahrerAusstieg");
        this.fahrGast = fahrGast;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("FahrgastAusstieg ")
                .append(this.fahrGast.toString())
                .append(" ist um ")
                .append(this.getDate())
                .append(" ausgestiegen.");

        return builder.toString();

        //return this.getName() + this.fahrGast.toString() + " ist um " + this.getDate() + " ausgestiegen.";
    }
}
