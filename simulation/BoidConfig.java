package simulation;



public class BoidConfig {
    
    // Separation, Agliment, Cohension
    private double[] radius = {30, 40, 50}; 
    private double[] weight = {0.0, 0.0, 0.0};  
    private int number = 30;
    public BoidConfig () {}

    public int numberBoid() {return number;}    
    public void addBoid() {number += 1;}

    public double sepRadius() {return radius[0];}
    public double aliRadius() {return radius[1];}
    public double cohRadius() {return radius[2];}

    public double sepWeight() {return weight[0];}
    public double aliWeight() {return weight[1];}
    public double cohWeight() {return weight[2];}
    
    public void setSepWeight (double value) {weight[0] = value;}
    public void setAliWeight (double value) {weight[1] = value;}
    public void setCohWeight (double value) {weight[2] = value;}

    // public static void main(String[] args) {
    //     BoidConfig tmp = new BoidConfig();
    //     System.out.println(tmp.number);
    // }
}