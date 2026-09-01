package simulation;

public class SharkConfig {
    private int number = 1;
    private double size = 10.0;
    private double speed = 3.0;
    private double maxForce = 0.15;
    private double detectRadius = 250;
    private double eatRadius = 12;

    public SharkConfig () {}

    public int numberShark() {return number;}    
    public void addShark() {number += 1;}

    public double size() { return size; }
    public void setSize(double value) { size = value; }

    public double speed() { return speed; }
    public void setSpeed(double value) { speed = value; }

    public double maxForce() { return maxForce; }
    public double detectRadius() { return detectRadius; }
    public double eatRadius() { return eatRadius; }
}
