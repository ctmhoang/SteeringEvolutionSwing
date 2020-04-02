package Model.Creature;

import Model.DNA;
import Model.Food.Food;
import Model.VizObject;
import Utils.Vector;

import java.awt.*;
import java.util.Random;

public abstract class Animal extends VizObject
{

    //Health will be reduced every frame by this amount
    protected static final double AGING_RATE = 0.25;
    protected static final int SIZE = 10;
    protected static final int MAX_HEALTH = 100;
    protected static final int MAX_SPEED = 5;
    protected static final double MAX_FORCE = 0.7;

    Random r = new Random();
    DNA dna;

    protected double health;
    protected Point target;
    protected Point fleeingPoint;
    protected int maxSpeed;
    protected double maxForce;
    protected Vector currentVelocity;

    protected Vector acceleration = new Vector(0, 0);

    public void setTarget(Point p) {
        target.setLocation(p.x, p.y);
    }

    public void setFleeingPoint(Point p) {
        fleeingPoint.setLocation(p.x, p.y);
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillOval((int) Math.round(pos.getX() - size / 2), (int) Math.round(pos.getY() - size / 2), size, size);
    }

    public void eat(Food i){
        health += i.getNutrition();
        health = health > MAX_HEALTH ? MAX_HEALTH : health;
    }

    public DNA getDna()
    {
        return dna;
    }
}
