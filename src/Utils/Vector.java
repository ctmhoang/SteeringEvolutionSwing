package Utils;

import java.awt.geom.Point2D;

public class Vector extends Point2D
{

    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }


    public Vector add(Vector v) {
        Vector result = new Vector(x + v.getX(), y + v.getY());
        this.setVector(result);
        return result;
    }

    public Vector sub(Vector v) {
        Vector result = new Vector(x - v.getX(), y - v.getY());
        this.setVector(result);
        return result;
    }

    public double getMag() {
        double result = Math.sqrt(this.getMagSq());
        return result;
    }

    public double getMagSq(){
        double result = (x * x) + (y * y);
        return result;
    }

    public Vector setMag(double magnitude) {
        Vector unitVector = normalize();
        Vector result = new Vector(unitVector.x * magnitude, unitVector.y * magnitude);
        this.setVector(result);
        return result;
    }

    public Vector mult(double d) {
        Vector result = new Vector(x * d, y * d);
        this.setVector(result);
        return result;
    }

    public void limit(double max) {
        if (getMagSq() > max * max) {
            normalize();
            mult(max);
        }
    }

    public Vector normalize() {
        double magnitude = getMag();
        if (magnitude == 0) {
            return new Vector(0, 0);
        }
        Vector result = new Vector(x / magnitude, y / magnitude);
        this.setVector(result);
        return result;
    }

    @Override
    public String toString() {
        String s = "x: " + x + "\ny: " + y;
        return s;
    }

    public double distance(Vector v){
        double result;
        Vector w = new Vector(v.x - this.x, v.y - this.y);
        result = w.getMag();
        return result;
    }

}
