package core;
public class calc {
    public static vector sum(vector a, vector b) {
        return new vector(a.getX() + b.getX(), a.getY() + b.getY());
    }
    public static vector dif(vector a, vector b) {
        return new vector(a.getX() - b.getX(), a.getY() - b.getY());
    }
    public static double dot(vector a, vector b) {
        return a.getX() * b.getX() + a.getY() * b.getY();
    }
    public static double distance(vector a, vector b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
