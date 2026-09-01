package entity;
import core.*;
import simulation.WorldConfig;

public abstract class DynamicEntity extends Entity {
    protected vector velocity, acceleration;
    protected double maxForce;
    protected double maxSpeed;

    public DynamicEntity(double x, double y) {
        super(x, y);
        double angle = Math.random() * Math.PI * 2;
        this.velocity = new vector(Math.cos(angle), Math.sin(angle));
        this.acceleration = new vector(0, 0);
    }
    public vector getVelocity() {return velocity;}
    public vector getAcceleration() {return acceleration;}

    public void applyForce(vector force) {
        acceleration.add(force);
    }
    
    public void move() {
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        acceleration.mul(0);
        position.add(velocity);
    } 

    protected vector seek(vector target) {
        vector steer = calc.dif(target, this.position);
        steer.normalize();
        steer.mul(maxSpeed);
        steer.sub(this.velocity);
        steer.limit(maxForce);
        return steer;
    }

    public vector avoidBorder(WorldConfig config) {
        vector steer = new vector(0, 0);
        if (!config.isAvoidBorder()) return steer;
        int width = config.getWidth();
        int margin = config.getMargin();
        int height = config.getHeight();

        boolean hitEdge = false;

        if (this.position.getX() < margin) {
            steer.setX(maxSpeed * 2); 
            hitEdge = true;
        } else if (this.position.getX() > width - margin) {
            steer.setX(-maxSpeed * 2);
            hitEdge = true;
        }

        if (this.position.getY() < margin) {
            steer.setY(maxSpeed * 2); 
            hitEdge = true;
        } else if (this.position.getY() > height - margin) {
            steer.setY(-maxSpeed * 2); 
            hitEdge = true;
        }

        if (hitEdge) {
            steer.normalize();
            steer.mul(maxSpeed);
            steer.sub(this.velocity);
            // Can increase wall repulsion force if needed (e.g., maxForce * 3.0)
            steer.limit(maxForce * 5.0); 
        }

        return steer;
    }
}
