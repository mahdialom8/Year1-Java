package Levels;

import Bodies.*;
import Collisions.*;
import city.cs.engine.SoundClip;
import city.cs.engine.World;
import game.*;

// Creates an abstract class called GameLevel which extends World
public abstract class GameLevel extends World {

    // Creating private variables for the driver and vehicles
    private Driver driver;
    private FireTruck fireTruck;
    private OffRoadCar offRoadCar;
    private PoliceCar policeCar;

    public void setTurretCar(TurretCar turretCar) {
        this.turretCar = turretCar;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    protected TurretCar turretCar;
    protected Tank tank;

    private Game game;
    public static SoundClip gameMusic;
    private GameLevel level;

    /**
     * Returns the collisions for all type of vehicles
     * Allows the driver to interact with each level
     *
     * @param game The location of game, being used from Game class
     */
    // Main GameLevel which holds the driver and its related collisions
    public GameLevel(Game game){
        // sets the world gravity to 0
        setGravity(0);
        driver = new Driver(this);
        this.game = game;
        FireTruckCollision fireTruckEncounter = new FireTruckCollision(this, game);
        driver.addCollisionListener(fireTruckEncounter);
        OffRoadCarCollision offRoadCarEncounter = new OffRoadCarCollision(this, game);
        driver.addCollisionListener(offRoadCarEncounter);
        PoliceCarCollision policeCarEncounter = new PoliceCarCollision(this, game);
        driver.addCollisionListener(policeCarEncounter);
        TurretCarCollision turretCarEncounter = new TurretCarCollision(this, game);
        driver.addCollisionListener(turretCarEncounter);
        TankCollision tankEncounter = new TankCollision(this, game);
        driver.addCollisionListener(tankEncounter);
    }

    // Getters used to return the private variables
    public Driver getDriver(){ return driver; }
    public FireTruck getFireTruck(){ return fireTruck; }
    public OffRoadCar getOffRoadCar() { return offRoadCar; }
    public PoliceCar getPoliceCar() { return policeCar; }
    public TurretCar getTurretCar() { return turretCar; }
    public Tank getTank() { return tank; }

    public Game getGame() { return game; }
    public static SoundClip getGameMusic(){return gameMusic;}
    // Getter used to retrieve the position of my 5 lanes
    public abstract float getRoadPosition();
    // Getter used to check if IsComplete has been met, to go to next level
    public abstract boolean isComplete();

    public abstract String getLevelName();

}