package simulation;
import entity.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Environment {
    
    private ArrayList <Entity> entities; 
    private WorldConfig worldConfig;
    private BoidConfig boidConfig;
    private SharkConfig sharkConfig;
    private final Set<Entity> pendingRemoval = new HashSet<>();
    
    public Environment (WorldConfig w, BoidConfig b, SharkConfig s) {
        this.entities = new ArrayList<Entity>();
        this.worldConfig = w;
        this.boidConfig = b;
        this.sharkConfig = s;

        for (int i = 0; i < b.numberBoid(); ++i) {
            double x = Math.random() * w.getWidth();
            double y = Math.random() * w.getHeight();
            entities.add(new Boid(x, y));
        }

        for (int i = 0; i < s.numberShark(); ++i) {
            double x = Math.random() * w.getWidth();
            double y = Math.random() * w.getHeight();
            entities.add(new Shark(x, y));
        }
    }

    public ArrayList<Entity> getEntities() { return entities; }
    public WorldConfig getWorldConfig() { return worldConfig; }
    public BoidConfig getBoidConfig() { return boidConfig; }
    public SharkConfig getSharkConfig() { return sharkConfig; }
    
    public void markForRemoval(Entity e) {
       pendingRemoval.add(e); 
    }

    public void update() {
        for (Entity e: this.entities) {
            e.act(this);
        }
        if (!pendingRemoval.isEmpty()) {
            entities.removeAll(pendingRemoval);
            pendingRemoval.clear();
        }
    }
}
