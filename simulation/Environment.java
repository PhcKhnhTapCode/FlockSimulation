package simulation;
import entity.*;
import java.util.ArrayList;


public class Environment {
    
    private ArrayList <Entity> entities; 
    private WorldConfig worldConfig;
    private BoidConfig boidConfig;
    
    public Environment (WorldConfig w, BoidConfig b) {
        this.entities = new ArrayList<Entity>();
        this.worldConfig = w;
        this.boidConfig = b;

        for (int i = 0; i < b.numberBoid(); ++i) {
            double x = Math.random() * w.getWidth();
            double y = Math.random() * w.getHeight();
            entities.add(new Boid(x, y));
        }
    }

    public ArrayList<Entity> getEntities() { return entities; }
    public WorldConfig getWorldConfig() { return worldConfig; }
    public BoidConfig getBoidConfig() { return boidConfig; }
    
    public void update() {
        for (Entity e: this.entities) {
            e.act(this);
        }
    }
}