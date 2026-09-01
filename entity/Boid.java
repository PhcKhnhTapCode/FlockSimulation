package entity;
import simulation.Environment;
import simulation.BoidConfig;
import java.util.ArrayList;
import core.*;

public class Boid extends DynamicEntity {

    public Boid(double x, double y) {
        super(x, y);
        this.maxForce = 0.2;
        this.maxSpeed = 4.0;
    }


    public vector separation(ArrayList<Entity> entities, BoidConfig config) {
        vector steer = new vector(0, 0);
        int cnt = 0;
        for (Entity e: entities) {
            if (e instanceof Boid other && e != this) {
                double d = calc.distance(this.getPosition(), other.getPosition());
                if (d > config.sepRadius()) continue;
                
                vector diff = calc.dif(this.getPosition(), other.getPosition());
                diff.normalize(); if (d > 0) diff.div(d);
                
                steer.add(diff);
                ++cnt;
            }
        }

        if (cnt > 0) {
            steer.div((double) cnt);
            // Steering = Desired - Velocity
            steer.normalize();
            steer.mul(this.maxSpeed);
            steer.sub(this.velocity);
            steer.limit(this.maxForce);
        }
        
        steer.mul(config.sepWeight());
        return steer; 
    }

    public vector flee(ArrayList<Entity> entities, BoidConfig config) {
        vector steer = new vector(0, 0);
        int cnt = 0;
        for (Entity e: entities) {
            if (e instanceof Shark other) {
                double d = calc.distance(this.getPosition(), other.getPosition());
                if (d > config.fleeRadius()) continue;
                
                vector diff = calc.dif(this.getPosition(), other.getPosition());
                diff.normalize(); if (d > 0) diff.div(d);
                
                steer.add(diff);
                ++cnt;
            }
        }

        if (cnt > 0) {
            steer.div((double) cnt);
            steer.normalize();
            steer.mul(this.maxSpeed);
            steer.sub(this.velocity);
            steer.limit(this.maxForce);
        }
        
        steer.mul(config.fleeWeight());
        return steer; 
    }

    public vector alignment(ArrayList<Entity> entities, BoidConfig config) {
        vector steer = new vector(0, 0);
        int cnt = 0;
        
        for (Entity e: entities) {
            if (e instanceof Boid other && e != this) {
                double d = calc.distance(this.getPosition(), other.getPosition());
                if (d < config.sepRadius() / 2 || d > config.aliRadius()) continue;
                
                // Add velocity of neighbors
                steer.add(other.velocity);
                ++cnt;
            }
        }

        if (cnt > 0) {
            // Get average velocity
            steer.div((double) cnt);
            
            // Steering = Desired - Velocity
            steer.normalize();
            steer.mul(this.maxSpeed);
            steer.sub(this.velocity);
            steer.limit(this.maxForce);
        }
        steer.mul(config.aliWeight());
        return steer;
    }
    public vector cohesion(ArrayList<Entity> entities, BoidConfig config) {
        vector steer = new vector(0, 0);
        int cnt = 0;
        
        for (Entity e: entities) {
            if (e instanceof Boid other && e != this) {
                double d = calc.distance(this.getPosition(), other.getPosition());
                if (d > config.cohRadius() || d == 0) continue;
                
                // Add position of neighbors
                steer.add(other.getPosition());
                ++cnt;
            }
        }

        if (cnt > 0) {
            // Get average position (Center of Mass)
            steer.div((double) cnt);
            
            // Get directional vector from current pos to Center of Mass
            steer.sub(this.position);
            
            // Steering = Desired - Velocity
            steer.normalize();
            steer.mul(this.maxSpeed);
            steer.sub(this.velocity);
            steer.limit(this.maxForce);
        }
        steer.mul(config.cohWeight());
        return steer;
    }
    @Override
    public void act(Environment env) {
        this.applyForce(this.avoidBorder(env.getWorldConfig()));
        this.applyForce(separation(env.getEntities(), env.getBoidConfig()));
        this.applyForce(alignment(env.getEntities(), env.getBoidConfig()));
        this.applyForce(cohesion(env.getEntities(), env.getBoidConfig()));
        this.applyForce(flee(env.getEntities(), env.getBoidConfig()));
        this.move();
    }
}
