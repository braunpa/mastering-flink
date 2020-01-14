package de.vicio.example;

public class Fahrgast extends ParentEvent{
    private String nachName;
    private String vorName;
    private int id;

    public Fahrgast(String nachname, String vorname, int id){
        super("Fahrgast");
        this.nachName = nachname;
        this.vorName = vorname;
        this.id = id;
    }

    public String getId(){
        return ""+this.id;
    }

    public String getFullName(){
        return this.id + " " + this.nachName + " " + this.vorName;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("Fahrgast: ");
        builder.append(this.getFullName());

        return builder.toString();
        //return this.getName() + this.id + ":" + this.nachName + " " + this.vorName;
    }
}
