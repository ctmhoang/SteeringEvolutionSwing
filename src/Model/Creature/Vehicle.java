package Model.Creature;

import Model.DNA;
import Utils.Vector;

import java.awt.*;


public class Vehicle extends Animal
{

    private boolean isHealthiest;


    public Vehicle(Vector pos)
    {
        super();
        this.pos = pos;
        color = Color.GREEN;
        health = MAX_HEALTH;
        target = new Point(-1, -1);
        fleeingPoint = new Point(-1, -1);
        size = SIZE;


        maxSpeed = MAX_SPEED;
        maxForce = MAX_FORCE;
        currentVelocity = new Vector((r.nextDouble() * 10) - 5, (r.nextDouble() * 10) - 5);
        isHealthiest = false;

        dna = new DNA();

    }


    public Vehicle(Vector pos, DNA dna)
    {
        this(pos);
        this.dna = new DNA(dna);

    }

    public boolean isAlive()
    {
        return health <= 0 ? false : true;
    }

    public void reduceHealth()
    {
        health = health - AGING_RATE;
    }

    public void move(int width, int height)
    {
        behavior();
        boundaries(width, height);

        pos.setLocation(pos.getX() + currentVelocity.getX(), pos.getY() + currentVelocity.getY());
        currentVelocity.setVector(currentVelocity.add(acceleration));

        acceleration.mult(0);
    }

    private void behavior()
    {
        Vector desiredVelocity = new Vector(0, 0);

        // seek
        if (!(target.x == -1 && target.y == -1))
        {

            desiredVelocity.setVector(new Vector(target.getX() - pos.getX(), target.getY() - pos.getY()));
            desiredVelocity.setMag(maxSpeed);
            desiredVelocity.mult(dna.getEdibleAttraction());

            Vector steer = desiredVelocity.sub(currentVelocity);
            steer.limit(maxForce);
            applyForce(steer);
        }

        // flee
        if (!(fleeingPoint.x == -1 && fleeingPoint.y == -1))
        {

            desiredVelocity.setVector(new Vector(fleeingPoint.getX() - pos.getX(), fleeingPoint.getY() - pos.getY()));
            desiredVelocity.setMag(maxSpeed);
            desiredVelocity.mult(dna.getPoisonAttr());

            Vector steer = desiredVelocity.sub(currentVelocity);
            steer.limit(maxForce);
            applyForce(steer);
        }
    }

    private void boundaries(int width, int height)
    {
        Vector desiredVelocity = new Vector(0, 0);

        // stay in boundaries
        if (pos.getX() < 0)
        {
            desiredVelocity.setVector(new Vector(maxSpeed, this.currentVelocity.getY()));

            desiredVelocity.normalize();
            desiredVelocity.mult(maxSpeed);
            Vector steer = desiredVelocity.sub(currentVelocity);
            steer.limit(maxForce);
            applyForce(steer);
        }
        else if (pos.getX() > width)
        {
            desiredVelocity.setVector(new Vector(-maxSpeed, this.currentVelocity.getY()));

            desiredVelocity.normalize();
            desiredVelocity.mult(maxSpeed);
            Vector steer = desiredVelocity.sub(currentVelocity);
            steer.limit(maxForce);
            applyForce(steer);
        }

        if (pos.getY() < 0)
        {
            desiredVelocity.setVector(new Vector(currentVelocity.getX(), maxSpeed));
            desiredVelocity.normalize();
            desiredVelocity.mult(maxSpeed);
            Vector steer = desiredVelocity.sub(currentVelocity);
            steer.limit(maxForce);
            applyForce(steer);
        }
        else if (pos.getY() > height)
        {
            desiredVelocity.setVector(new Vector(currentVelocity.getX(), -maxSpeed));
            desiredVelocity.normalize();
            desiredVelocity.mult(maxSpeed);
            Vector steer = desiredVelocity.sub(currentVelocity);
            steer.limit(maxForce);
            applyForce(steer);
        }
    }

    private void applyForce(Vector v)
    {
        acceleration.add(v);
    }

    public void draw(Graphics g, boolean debug)
    {
        // lerpColor
        if (health >= 75)
        {
            g.setColor(Color.GREEN);
        }
        else
        {
            if (health >= 50)
            {
                g.setColor(Color.YELLOW);
            }
            else
            {
                if (health >= 25)
                {
                    g.setColor(Color.ORANGE);
                }
                else
                {
                    if (health >= 0)
                    {
                        g.setColor(Color.RED);
                    }
                }
            }
        }

        Vector v1 = new Vector(currentVelocity.getX(), currentVelocity.getY());

        v1.normalize();
        v1.mult(size);

        Point p1 = new Point((int) Math.round(v1.getX() + pos.getX()), (int) Math.round(v1.getY() + pos.getY()));

        v1.mult(-1);

        Vector v2 = new Vector(v1.getY() * -1, v1.getX());
        Vector v3 = new Vector(v1.getY(), v1.getX() * -1);

        v2.normalize();
        v3.normalize();

        v2.mult(size / 2);
        v3.mult(size / 2);

        Point p2 = new Point((int) Math.round(v2.getX() + pos.getX() + v1.getX()),
                (int) Math.round(v2.getY() + pos.getY() + v1.getY()));
        Point p3 = new Point((int) Math.round(v3.getX() + pos.getX() + v1.getX()),
                (int) Math.round(v3.getY() + pos.getY() + v1.getY()));

        Color c = g.getColor();

        if (isHealthiest)
        {
            g.setColor(c);
        }
        else
        {
            g.setColor(new Color(255, 255, 255, 80));
        }
        g.fillPolygon(new int[]{p2.x, p1.x, p3.x}, new int[]{p2.y, p1.y, p3.y}, 3);

        g.setColor(c);

        g.drawPolygon(new int[]{p2.x, p1.x, p3.x}, new int[]{p2.y, p1.y, p3.y}, 3);

        if (debug)
        {
            // Food perception and attraction
            g.setColor(Color.GREEN);
            int r = dna.getEdiblePerception();
            double d = dna.getEdibleAttraction();
            g.drawOval((int) Math.round(pos.getX() - r / 2), (int) Math.round(pos.getY() - r / 2), r, r);


//            Vector relative = new Vector((p2.x + p3.x) / 2, (p2.y + p3.y) / 2),
//                    top = new Vector(p1.x, p1.y);
//            relative.sub(top);
//
//            int cVar = (int) (- (-1) * relative.getY() * top.getX() - relative.getX() * top.getY());
//
//
//            g.drawLine(p1.x, p1.y, (int) (p1.x + d * 2),
//                    getRotateY(p1.x + d * 2, cVar, relative));

            g.drawLine((int) Math.round(pos.getX()), (int) Math.round(pos.getY()), (int) Math.round(pos.getX()),
                    (int) Math.round(pos.getY() + d * 10));

            // Poison perception and attraction
            g.setColor(Color.RED);
            r = dna.getPoisonPerception();
            d = dna.getPoisonAttr();
            g.drawOval((int) Math.round(pos.getX() - r / 2), (int) Math.round(pos.getY() - r / 2), r, r);

            g.drawLine((int) Math.round(pos.getX()), (int) Math.round(pos.getY()), (int) Math.round(pos.getX()),
                    (int) Math.round(pos.getY() + d * 10));

        }
    }


    public double getHealth()
    {
        return health;
    }

    public boolean isHealthiest()
    {
        return isHealthiest;
    }

    public void setHealthiest(boolean isHealthiest)
    {
        this.isHealthiest = isHealthiest;
    }

    public double getReproductionProbability()
    {
        return (0.001 * getHealth()) / 40; // between 1 and 0.1 percent chance of reproducing (depending on health)
    }

}
