package Levels;

import Bodies.Blockade;
import Bodies.OffRoadCar;
import Collisions.DriverCollision;
import Collisions.OffRoadCarCollision;
import city.cs.engine.*;
import game.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Level3 extends GameLevel {

    // Creates an array for with the x locations for each lane
    private float xPositions[] = new float[] {
            -10.1f, -4.8f, 0.3f, 5.3f, 10.2f,
    };

    // A getter to return the random location for one of the roads
    public float getRoadPosition() {
        return xPositions[random.nextInt(xPositions.length)];
    }

    private Random random = new Random();

    public Level3(Game game){

        super(game);

        try {
            gameMusic = new SoundClip("data/Running in the 90's.wav");
            gameMusic.loop();
            gameMusic.setVolume(0.05);
            //System.out.println("Playing Running in the 90's");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }

        GameLevel world = this;

        addStepListener(new StepListener(){
            double previousTime = new Date().getTime() / 1000.;
            public void preStep(StepEvent e) {
                double currentTime = new Date().getTime() / 1000.;
                if (currentTime - previousTime > 5.f) {
                    new Blockade(world);
                    previousTime = currentTime;
                }
            }
            public void postStep(StepEvent e) { }
        });

        // make the ground
        Shape shape = new BoxShape(14.5f, 0.5f);
        StaticBody ground = new StaticBody(this, shape);
        ground.setPosition(new Vec2(0, -20.5f));

        // make the left wall
        Shape wall1Shape = new BoxShape(0.5f, 20);
        StaticBody wall1 = new StaticBody(this, wall1Shape);
        wall1.setPosition(new Vec2(-13, 0));

        // make the right wall
        Shape wall2Shape = new BoxShape(0.5f, 20);
        StaticBody wall2 = new StaticBody(this, wall2Shape);
        wall2.setPosition(new Vec2(13, 0));

        // sets the drivers spawn position and allows it to have collisions
        getDriver().setPosition(new Vec2(0.3f, -7));
        DriverCollision driverCollision = new DriverCollision(getDriver());
        getDriver().addCollisionListener(driverCollision);

        // this loop is used to spawn the off-road cars in one of the 5 lanes, each off-road car with a collision listener
        // sets a negative linear velocity in y direction, so the off-road cars move downwards after spawning in
        for (int i = 0; i < 3; i++) {
            float randPosition = xPositions[random.nextInt(xPositions.length)];
            OffRoadCar offroadcar = new OffRoadCar(this);
            offroadcar.setPosition(new Vec2(randPosition, 30));
            offroadcar.setLinearVelocity(new Vec2(0, -10));
            OffRoadCarCollision offroadcarCollision = new OffRoadCarCollision(offroadcar);
            offroadcar.addCollisionListener(offroadcarCollision);
        }

        // adds a step listener which executes before the physics engine
        addStepListener(new StepListener() {

            // sets the angle degrees to 0 so the objects no longer turn when colliding with each other
            public void preStep(StepEvent e) {
                List<DynamicBody> list = world.getDynamicBodies();
                for (int i=0; i<list.size(); i++){
                    list.get(i).setAngleDegrees(0);
                }
            }

            public void postStep(StepEvent e) { }
        });

    }

    // Override used to go to next level when the boolean returns true
    @Override
    public boolean isComplete() {
        if (getDriver().getKills() >= 3)
            return true;
        else
            return false;
    }

    @Override
    public String getLevelName() {
        return "Level3";
    }
}
