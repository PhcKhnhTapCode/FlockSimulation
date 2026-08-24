package entity;
import core.vector;
import simulation.Environment;

public abstract class Entity {
    protected vector position;
    public Entity(double x, double y) {
        this.position = new vector(x, y);
    }

    public vector getPosition() {return position;}

    public abstract void act(Environment env);
}