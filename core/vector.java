package core;

public class vector {
    private double x;
    private double y;

    public double getX() {return x;}
    public double getY() {return y;}
    public void setX(double _x) {this.x = _x;}
    public void setY(double _y) {this.y = _y;}
    public vector copy() {return new vector(x, y);}

    public vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add (vector obj) { this.x += obj.x; this.y += obj.y;}
    public void sub (vector obj) { this.x -= obj.x; this.y -= obj.y;}
    public void mul (double a) { this.x *= a; this.y *= a;}
    public void div(double scalar) { this.x /= scalar; this.y /= scalar;}
    public double magnitude() { return Math.sqrt(this.x * this.x + this.y * this.y);}
    
    public void normalize() {
        double mag = magnitude();
        if (mag == 0) return ;
        this.x /= mag;
        this.y /= mag;
    }


    public void limit(double max) {
        if (magnitude() > max) {
            normalize();
            mul(max);
        }
    }
}