package de.vicio.VisitedPlaces;

import org.apache.flink.cep.nfa.aftermatch.AfterMatchSkipStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StaticVariables {
    protected static Random rand = new Random();

    protected static int carIDCounter;
    protected static int userIDCounter;
    protected static ArrayList<UserEntity> USERS= new ArrayList<UserEntity>();
    protected static ArrayList<CarEntity> CARS= new ArrayList<CarEntity>();

    protected final static int GENERATORDELY = 1000;
    protected final static int GENERATERRORPROBABILITY = 0;

    protected final static AfterMatchSkipStrategy SKIP_STRATEGY = AfterMatchSkipStrategy.skipPastLastEvent();

    protected static final double GENERATORPROBABILITY_RIDERINTOCAR = 0.1;
    protected static final double GENERATORPROBABILITY_RIDEROUTOFCAR = 0.1;



    public static void init(){
        initCounters();
        initUsers();
        initCars();
    }

    private static void initCounters(){
        carIDCounter = 1;
        userIDCounter = 1;
    }

    private static void initCars(){
        CARS.add(new CarEntity(4));
        CARS.add(new CarEntity(2));
        CARS.add(new CarEntity(6));
        CARS.add(new CarEntity(8));
        CARS.add(new CarEntity(4));
        CARS.add(new CarEntity(5));
        CARS.add(new CarEntity(6));
        CARS.add(new CarEntity(3));
        CARS.add(new CarEntity(2));
        CARS.add(new CarEntity(1));
    }

    public static UserEntity getUserWhichIsNotRiding(){
        try{
            ArrayList<UserEntity> list = new ArrayList(StaticVariables.USERS.stream().filter(u -> u.isRiding()==false).collect(Collectors.toList()));
            int randomnumb = new Random().nextInt(list.size());
            return list.get(randomnumb);
        }catch(Exception e){
            return null;
        }
    }

    public static int getRandomCarId(){
        int id = rand.nextInt(CARS.size());
        return id==0?++id:id;
    }

    public static CarEntity getCarWithAvailableSeats(){
        try{
            ArrayList<CarEntity> list = new ArrayList(StaticVariables.CARS.stream().filter(c -> c.hasSeats()==true).collect(Collectors.toList()));
            int randomnumb = rand.nextInt(list.size());
            return list.get(randomnumb);
        }catch(Exception e){
            return null;
        }
    }

    public static UserEntity getUserWhichIsRiding(){
        try{
            ArrayList<UserEntity> list = new ArrayList(StaticVariables.USERS.stream().filter(u -> u.isRiding()==true).collect(Collectors.toList()));
            int randomnumb = rand.nextInt(list.size());
            return list.get(randomnumb);
        }catch(Exception e){
            return null;
        }
    }

    public static CarEntity getCarById(int id){
        return CARS.stream().filter(c -> c.getCARID()==id).collect(Collectors.toList()).get(0);
    }

    private static void initUsers(){
        USERS.add(new UserEntity("Braun","Patrick"));
        USERS.add(new UserEntity("Braun","Christoph"));
        USERS.add(new UserEntity("Hedtstück","Ulrich"));
        USERS.add(new UserEntity("Markus","Scholtz"));
        USERS.add(new UserEntity("Tobias","Steinel"));
        USERS.add(new UserEntity("Yannick","Kenck"));
        USERS.add(new UserEntity("Florian","Baier"));
        USERS.add(new UserEntity("The","Jocker"));
        USERS.add(new UserEntity("Amanda ","Bolton"));
        USERS.add(new UserEntity("Summer","Sloan"));
        USERS.add(new UserEntity("Super","Girl"));
        USERS.add(new UserEntity("Super","man"));
        USERS.add(new UserEntity("Spider","man"));
        USERS.add(new UserEntity("Baby","Driver"));
        USERS.add(new UserEntity("Henry","Cavill"));
        USERS.add(new UserEntity("Bat","man"));
    }









}
