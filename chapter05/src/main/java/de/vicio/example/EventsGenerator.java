package de.vicio.example;

import java.util.ArrayList;
import java.util.Random;

public class EventsGenerator {
    int counter = 0;
    ArrayList<Fahrgast> listeMitfahrer = new ArrayList<Fahrgast>();
    ArrayList<Fahrgast> Mitfahrer = new ArrayList<Fahrgast>();




    public EventsGenerator() {
        this.Mitfahrer.add(new Fahrgast("Braun","Patrick", 1));
        this.Mitfahrer.add(new Fahrgast("Braun","Christoph", 2));
        this.Mitfahrer.add(new Fahrgast("Hedtstück","Ulrich", 3));
        this.Mitfahrer.add(new Fahrgast("Markus","Scholtz", 4));
        this.Mitfahrer.add(new Fahrgast("Tobias","Steinel", 5));
        this.Mitfahrer.add(new Fahrgast("Yannick","Kenck", 6));
        this.Mitfahrer.add(new Fahrgast("Florian","Baier", 7));
        this.Mitfahrer.add(new Fahrgast("The","Jocker", 8));
        this.Mitfahrer.add(new Fahrgast("Amanda ","Bolton", 9));
        this.Mitfahrer.add(new Fahrgast("Summer","Sloan", 10));
        this.Mitfahrer.add(new Fahrgast("Super","Girl", 11));
        this.Mitfahrer.add(new Fahrgast("Super","man", 12));
        this.Mitfahrer.add(new Fahrgast("Spider","man", 13));
        this.Mitfahrer.add(new Fahrgast("Baby","Driver", 14));
        this.Mitfahrer.add(new Fahrgast("Henry","Cavill", 15));
        this.Mitfahrer.add(new Fahrgast("Bat","man", 16));


    }

    // ------------------------------------------------------------------------

    /**
     *
     */
    public ParentEvent next() throws InterruptedException {
        Random rand = new Random();
        double wahr = rand.nextDouble();
        try{
            if(wahr < 0.1){
                try{
                    return this.emitFahrgastEinstiegEvent();
                }catch(Exception eb){
                    return this.emitFahrgastAustieg();
                }
            }else if(wahr > 0.1 && wahr < 0.4){
                return this.emitFahrgastAustieg();
            }else{
                return this.emitOrt();
            }
        }catch(Exception e){
            try{
                return this.emitFahrgastEinstiegEvent();
            }catch(Exception eb){
                return this.emitFahrgastAustieg();
            }
        }


    }

    private Ort emitOrt(){
        return new Ort();
    }

    private FahrgastAusstieg emitFahrgastAustieg(){
        Fahrgast rider = this.getMitfahrerFromList();

        return new FahrgastAusstieg(this.getMitfahrerFromList());
    }

    private FahrgastEinstieg emitFahrgastEinstiegEvent()throws Exception{
        int counter = 0;
        Fahrgast mitfahrer = this.getRandomMitfahrer();
        //Check if Fahrgast is not in the list of Mitfahrer
        while(this.listeMitfahrer.contains(mitfahrer)){
            mitfahrer = this.getRandomMitfahrer();
            if(counter > 20){
                throw new Exception();
            }
        }
        this.listeMitfahrer.add(mitfahrer);
        return new FahrgastEinstieg(mitfahrer);

    }

    private Fahrgast getRandomMitfahrer(){
        int pick = new Random().nextInt(this.Mitfahrer.size());
        return this.Mitfahrer.get(pick);
    }

    private Fahrgast getMitfahrerFromList(){
        int pick = new Random().nextInt(this.listeMitfahrer.size());
        return this.listeMitfahrer.remove(pick);
    }
}
