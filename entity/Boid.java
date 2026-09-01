package entity;
import simulation.Environment;
import simulation.BoidConfig;
import core.*;

public class Boid extends DynamicEntity {

    public Boid(double x, double y) {
        super(x, y);
        this.maxForce = 0.2;
        this.maxSpeed = 4.0;
    }

    @Override
    public void act(Environment env) {
        BoidConfig config = env.getBoidConfig();

        double sepRadius = config.sepRadius();
        double aliRadiusMin = sepRadius / 2;
        double aliRadius = config.aliRadius();
        double cohRadius = config.cohRadius();
        double fleeRadius = config.fleeRadius();

        double sepRadiusSq = sepRadius * sepRadius;
        double aliRadiusMinSq = aliRadiusMin * aliRadiusMin;
        double aliRadiusSq = aliRadius * aliRadius;
        double cohRadiusSq = cohRadius * cohRadius;
        double fleeRadiusSq = fleeRadius * fleeRadius;

        vector sepSum = new vector(0, 0);
        int sepCount = 0;
        vector aliSum = new vector(0, 0);
        int aliCount = 0;
        vector cohSum = new vector(0, 0);
        int cohCount = 0;
        vector fleeSum = new vector(0, 0);
        int fleeCount = 0;

        for (Boid other : env.getBoids()) {
            if (other == this) continue;

            double dSq = calc.distanceSq(this.position, other.getPosition());

            if (dSq <= sepRadiusSq) {
                double d = Math.sqrt(dSq);
                vector diff = calc.dif(this.getPosition(), other.getPosition());
                diff.normalize(); if (d > 0) diff.div(d);
                sepSum.add(diff);
                ++sepCount;
            }

            if (dSq >= aliRadiusMinSq && dSq <= aliRadiusSq) {
                aliSum.add(other.velocity);
                ++aliCount;
            }

            if (dSq <= cohRadiusSq && dSq != 0) {
                cohSum.add(other.getPosition());
                ++cohCount;
            }
        }

        for (Shark other : env.getSharks()) {
            double dSq = calc.distanceSq(this.position, other.getPosition());

            if (dSq <= fleeRadiusSq) {
                double d = Math.sqrt(dSq);
                vector diff = calc.dif(this.getPosition(), other.getPosition());
                diff.normalize(); if (d > 0) diff.div(d);
                fleeSum.add(diff);
                ++fleeCount;
            }
        }

        this.applyForce(this.avoidBorder(env.getWorldConfig()));
        this.applyForce(finishSteer(sepSum, sepCount, config.sepWeight()));
        this.applyForce(finishSteer(aliSum, aliCount, config.aliWeight()));
        this.applyForce(finishCohesionSteer(cohSum, cohCount, config.cohWeight()));
        this.applyForce(finishSteer(fleeSum, fleeCount, config.fleeWeight()));
        this.move();
    }

    private vector finishSteer(vector sum, int count, double weight) {
        vector steer = sum.copy();
        if (count > 0) {
            steer.div((double) count);
            steer.normalize();
            steer.mul(this.maxSpeed);
            steer.sub(this.velocity);
            steer.limit(this.maxForce);
        }
        steer.mul(weight);
        return steer;
    }

    private vector finishCohesionSteer(vector sum, int count, double weight) {
        vector steer = sum.copy();
        if (count > 0) {
            steer.div((double) count);
            steer.sub(this.position);
            steer.normalize();
            steer.mul(this.maxSpeed);
            steer.sub(this.velocity);
            steer.limit(this.maxForce);
        }
        steer.mul(weight);
        return steer;
    }
}
