package entity;
import simulation.Environment;
import simulation.SharkConfig;
import java.util.ArrayList;
import core.*;

public class Shark extends DynamicEntity {
    public Shark(double x, double y) {
        super(x, y);
        this.maxForce = 0.15;
        this.maxSpeed = 3.0;
    }

    public Entity findNearestBoid(ArrayList<Entity> entities) {
        Entity nearest = null;
        double best = Double.MAX_VALUE;

        for (Entity e : entities) {
            if (e instanceof Boid) {
                double d = calc.distance(this.getPosition(), e.getPosition());
                if (d < best) {
                    best = d;
                    nearest = e;
                }
            }
        }
        return nearest;
    }

    @Override
    public void act(Environment env) {
        SharkConfig config = env.getSharkConfig();
        this.maxSpeed = config.speed();
        this.maxForce = config.maxForce();

        this.applyForce(this.avoidBorder(env.getWorldConfig()));

        Entity target = findNearestBoid(env.getEntities());
        if (target != null) {
            double d = calc.distance(this.getPosition(), target.getPosition());

            if (d <= config.detectRadius()) {
                this.applyForce(this.seek(target.getPosition()));
            }
            if (d <= config.eatRadius()) {
                env.markForRemoval(target);
            }
        }

        this.move();
    }
}
