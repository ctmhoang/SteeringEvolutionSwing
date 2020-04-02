package Utils;


import Model.Creature.Vehicle;
import Model.Food.Edible;
import Model.Food.Poison;
import Model.VizObject;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;


public class Controller
{
    private static final int EDIBLE_QUANTITY = 220,
            POISON_QUANTITY = 50,
            VEHICLE_QUANTITY = 30;

    private boolean debug = false;

    Random r = new Random();

    List<Edible> edibles;
    List<Poison> poison;
    List<Vehicle> vehicles;

    private final int WIDTH, HEIGHT;

    public Controller(int width, int height)
    {

        WIDTH = width - 10;
        HEIGHT = height - 30;

        edibles = new ArrayList<>();
        poison = new ArrayList<>();
        vehicles = new ArrayList<>();

        IntStream.range(0, EDIBLE_QUANTITY).forEach(j -> edibles.add(new Edible(new Vector(r.nextInt(WIDTH), r.nextInt(HEIGHT)))));

        IntStream.range(0, POISON_QUANTITY).forEach(i -> poison.add(new Poison(new Vector(r.nextInt(WIDTH), r.nextInt(HEIGHT)))));

        for (int i = 0; i < VEHICLE_QUANTITY; i++)
        {
            vehicles.add(new Vehicle(new Vector(r.nextInt(WIDTH), r.nextInt(HEIGHT))));
        }

    }

    public void show(Graphics2D g)
    {
        IntStream.range(0, edibles.size()).forEach(i -> edibles.get(i).draw(g));

        IntStream.range(0, poison.size()).forEach(i -> poison.get(i).draw(g));

        IntStream.range(0, vehicles.size()).forEach(i -> vehicles.get(i).draw(g, debug));

    }

    private void setTarget()
    {
        double distance = -1;
        Edible foo = null;
        Vehicle ant = null;
        Poison pois = null;

        //Set Food as Target for Ants
        for (int i = 0; i < vehicles.size(); i++)
        {
            // Food
            for (int j = 0; j < edibles.size(); j++)
            {
                double d = getDistance(edibles.get(j), vehicles.get(i));
                if ((distance == -1 && d <= vehicles.get(i).getDna().getEdiblePerception() / 2) || (d < distance && d <= vehicles.get(i).getDna().getEdiblePerception() / 2))
                {
                    distance = d;
                    foo = edibles.get(j);
                }
            }
            if (foo == null)
            {
                vehicles.get(i).setTarget(new Point(-1, -1));
            }
            else
            {
                vehicles.get(i).setTarget(new Point((int) Math.round(foo.getPosition().getX()),
                        (int) Math.round(foo.getPosition().getY())));
            }
            foo = null;
            distance = -1;
        }

        // Set ants or poison as targets for predators

    }

    private void setFleeingPoint()
    {
        double distance = -1;
        Poison pois = null;

        for (int i = 0; i < vehicles.size(); i++)
        {
            // Poison
            for (int j = 0; j < poison.size(); j++)
            {
                double d = getDistance(poison.get(j), vehicles.get(i));
                if (distance == -1 && d <= vehicles.get(i).getDna().getPoisonPerception() / 2 || (d < distance && d <= vehicles.get(i).getDna().getPoisonPerception() / 2))
                {
                    distance = d;
                    pois = poison.get(j);
                }
            }
            if (pois == null)
            {
                vehicles.get(i).setFleeingPoint(new Point(-1, -1));
            }
            else
            {
                vehicles.get(i).setFleeingPoint(new Point((int) Math.round(pois.getPosition().getX()),
                        (int) Math.round(pois.getPosition().getY())));
            }
            pois = null;
            distance = -1;
        }
    }

    public void computeNextFrame()
    {
        setTarget();
        setFleeingPoint();
        findHealthiest();
        updatePosVehicle();
        eat();
        addFood();
        addPoison();
    }

    private void updatePosVehicle()
    {
        for (int i = 0; i < vehicles.size(); i++)
        {
            reproduce(vehicles.get(i));
            vehicles.get(i).move(WIDTH ,HEIGHT);
            vehicles.get(i).reduceHealth();
        }
    }

    private void reproduce(Vehicle ant)
    {
        if (r.nextDouble() <= ant.getReproductionProbability())
            vehicles.add(new Vehicle(new Vector(ant.getPosition().getX(), ant.getPosition().getY()), ant.getDna()));
    }

    private void eat()
    {
        // Ants eating food
        for (int i = 0; i < vehicles.size(); i++)
        {
            for (int j = 0; j < edibles.size(); j++)
            {
                if (vehicles.get(i).getPosition().distance(edibles.get(j).getPosition()) < 7)
                {
                    vehicles.get(i).eat(edibles.get(j));
                    edibles.remove(j);
                }
            }
        }

        // Ants eating poison
        for (int i = 0; i < vehicles.size(); i++)
        {
            for (int j = 0; j < poison.size(); j++)
            {
                if (vehicles.get(i).getPosition().distance(poison.get(j).getPosition()) < 7)
                {
                    vehicles.get(i).eat(poison.get(j));
                    poison.remove(j);
                }
            }
        }

        checkHealth();
    }

    private void checkHealth()
    {
        for (int i = 0; i < vehicles.size(); i++)
        {
            if (!vehicles.get(i).isAlive())
            {
                // ant is dead
                Vector v = new Vector(vehicles.get(i).getPosition().getX(), vehicles.get(i).getPosition().getY());
                vehicles.remove(i);
                edibles.add(new Edible(v));
            }
        }
    }

    private void findHealthiest()
    {
        if (vehicles.size() >= 1)
        {
            for (int i = 0; i < vehicles.size(); i++)
            {
                vehicles.get(i).setHealthiest(false);
            }
            vehicles.get(0).setHealthiest(true);
            double healthiestHealth = vehicles.get(0).getHealth();
            int healthiestAntNo = 0;
            for (int i = 0; i < vehicles.size(); i++)
            {
                if (vehicles.get(i).getHealth() > healthiestHealth)
                {
                    vehicles.get(healthiestAntNo).setHealthiest(false);
                    healthiestAntNo = i;
                    vehicles.get(i).setHealthiest(true);
                    healthiestHealth = vehicles.get(i).getHealth();
                }
            }
        }
    }

    private void addFood()
    {
        if (r.nextInt(100) <= 6)
        { // 6 per cent chance of adding new food in this frame
            edibles.add(new Edible(new Vector(r.nextInt(WIDTH), r.nextInt(HEIGHT))));
        }
    }

    private void addPoison()
    {
        if (r.nextInt(100) <= 2)
        { // 2 per cent chance of adding new food in this frame
            poison.add(new Poison(new Vector(r.nextInt(WIDTH), r.nextInt(HEIGHT))));
        }
    }

    public boolean isDebug()
    {
        return debug;
    }

    public void setDebug(boolean debug)
    {
        this.debug = debug;
    }

    public void addVehicle()
    {
        vehicles.add(new Vehicle(new Vector(r.nextInt(WIDTH), r.nextInt(HEIGHT))));
    }

    public void addPoisonAt(int x, int y)
    {
        poison.add(new Poison(new Vector(x, y)));
    }

    public void addFoodAt(int x, int y)
    {
        edibles.add(new Edible(new Vector(x, y)));
    }

    // returns distance between two Particles (animals or items)
    private double getDistance(VizObject p1, VizObject p2)
    {
        Vector v = new Vector(p1.getPosition().getX() - p2.getPosition().getX(), p1.getPosition().getY() - p2.getPosition().getY());
        return v.getMag();
    }

}
