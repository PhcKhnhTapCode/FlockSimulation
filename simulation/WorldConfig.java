package simulation;


public class WorldConfig {
    /*
                          (0, 0) ------------- (X, 0)
                            |                    |
                            |                    |
    Border of the world: (0, Y) ------------- (X, Y)
    */
    private int X, Y, margin;  
    private boolean avoidBorder;
        
    public WorldConfig (int _X, int _Y, int margin, boolean _avoidBorder) {
        this.X = _X;
        this.Y = _Y;
        this.margin = margin;
        this.avoidBorder = _avoidBorder;
    }

    public int getWidth() {return this.X;}
    public int getHeight() {return this.Y;}
    public int getMargin() {return this.margin;}
    public boolean isAvoidBorder() {return avoidBorder;}
    public void switchBorder() {
        this.avoidBorder = !this.avoidBorder;
    }
}