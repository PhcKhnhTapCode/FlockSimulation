package entity;
import simulation.Environment;
import simulation.SharkConfig;
import java.util.ArrayList;
import core.*;

public class Shark extends DynamicEntity {

    private boolean preyDetected = false;

    public Shark(double x, double y) {
        super(x, y);
        this.maxForce = 0.15;
        this.maxSpeed = 3.0;
    }

    public boolean isPreyDetected() { return preyDetected; }

    private Boid findPreyInView(ArrayList<Boid> boids, SharkConfig config) {
        double heading = Math.atan2(this.velocity.getY(), this.velocity.getX());
        double halfView = config.viewAngle() / 2;
        double radiusSq = config.detectRadius() * config.detectRadius();

        Boid nearest = null;
        double bestSq = Double.MAX_VALUE;

        for (Boid b : boids) {
            double dSq = calc.distanceSq(this.position, b.getPosition());
            if (dSq > radiusSq) continue;

            vector diffVec = calc.dif(b.getPosition(), this.position);
            double angleToBoid = Math.atan2(diffVec.getY(), diffVec.getX());
            double angleDiff = normalizeAngle(angleToBoid - heading);
            if (Math.abs(angleDiff) > halfView) continue;

            if (dSq < bestSq) {
                bestSq = dSq;
                nearest = b;
            }
        }
        return nearest;
    }

    private double normalizeAngle(double angle) {
        while (angle > Math.PI) angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }

    @Override
    public void act(Environment env) {
        SharkConfig config = env.getSharkConfig();
        this.maxSpeed = config.speed();
        this.maxForce = config.maxForce();

        this.applyForce(this.avoidBorder(env.getWorldConfig()));

        ArrayList<Boid> boids = env.getBoids();
        double eatRadiusSq = config.eatRadius() * config.eatRadius();

        for (Boid b : boids) {
            if (calc.distanceSq(this.position, b.getPosition()) <= eatRadiusSq) {
                env.markForRemoval(b);
            }
        }

        Boid target = findPreyInView(boids, config);
        this.preyDetected = (target != null);
        if (target != null) {
            this.applyForce(this.seek(target.getPosition()));
        }

        this.move();
    }
}
