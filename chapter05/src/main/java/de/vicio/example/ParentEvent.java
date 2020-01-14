package de.vicio.example;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParentEvent {
    private final String NAME;
    private final String DATE;
    public static int counter = 1;
    private final int ID;

    public ParentEvent(String name){
        this.NAME = name;
        this.DATE = this.getCurrentDate();
        this.ID = counter++;

    }

    public int getID(){
        return this.ID;
    }

    public String getCurrentDate(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return date.toString();
    }

    public String getName(){
        return this.NAME;
    }

    public String getDate(){
        return this.DATE;
    }
}
