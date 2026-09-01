package simulation;
import entity.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Environment {

    private ArrayList<Boid> boids;
    private ArrayList<Shark> sharks;
    private WorldConfig worldConfig;
    private BoidConfig boidConfig;
    private SharkConfig sharkConfig;
    private final Set<Boid> pendingRemoval = new HashSet<>();

    public Environment (WorldConfig w, BoidConfig b, SharkConfig s) {
        this.boids = new ArrayList<Boid>();
        this.sharks = new ArrayList<Shark>();
        this.worldConfig = w;
        this.boidConfig = b;
        this.sharkConfig = s;

        for (int i = 0; i < b.numberBoid(); ++i) {
            double x = Math.random() * w.getWidth();
            double y = Math.random() * w.getHeight();
            boids.add(new Boid(x, y));
        }

        for (int i = 0; i < s.numberShark(); ++i) {
            double x = Math.random() * w.getWidth();
            double y = Math.random() * w.getHeight();
            sharks.add(new Shark(x, y));
        }
    }

    public ArrayList<Boid> getBoids() { return boids; }
    public ArrayList<Shark> getSharks() { return sharks; }
    public WorldConfig getWorldConfig() { return worldConfig; }
    public BoidConfig getBoidConfig() { return boidConfig; }
    public SharkConfig getSharkConfig() { return sharkConfig; }

    public void markForRemoval(Boid b) {
       pendingRemoval.add(b);
    }

    public void update() {
        for (Boid b : boids) {
            b.act(this);
        }
        for (Shark s : sharks) {
            s.act(this);
        }
        if (!pendingRemoval.isEmpty()) {
            boids.removeAll(pendingRemoval);
            pendingRemoval.clear();
        }
    }
}
