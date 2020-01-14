package de.vicio.VisitedPlaces;

public class UserEntity {
    private String firstName;
    private String lastName;
    private final int USERID;
    private boolean isRiding;
    private final int NOTRIDINGCARID = 0;
    private int carID = NOTRIDINGCARID;

    public UserEntity(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.USERID = StaticVariables.userIDCounter++;
        this.isRiding = false;
    }

    public void getsInTheCar(CarEntity car){
        this.isRiding = true;
        this.carID = car.getCARID();
    }

    public void getsOutOfTheCar(){
        this.isRiding = false;
        this.carID = NOTRIDINGCARID;
    }

    public boolean isRiding(){
        return this.isRiding;
    }

    public int getUSERID(){
        return this.USERID;
    }

    public int getCarId(){
        return this.carID;
    }

}
