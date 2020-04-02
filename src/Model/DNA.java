package Model;

import java.util.Random;

public class DNA
{
    Random r = new Random();

    private double edibleAttraction;
    private double poisonAttraction;
    private int ediblePerception;
    private int poisonPerception;

    public DNA()
    {
        edibleAttraction = (r.nextDouble() * 10) - 5;
        poisonAttraction = (r.nextDouble() * 10) - 5;
        ediblePerception = r.nextInt(150) + 1;
        poisonPerception = r.nextInt(150) + 1;
//		attractionFood = 5;
//		attractionPoison = -5;
//		perceptionFood = 150;
//		perceptionPoison = 150;
    }

    public DNA(DNA dna)
    {
        if (r.nextDouble() <= 0.1)
        { //5 per cent chance of drastic mutation
            edibleAttraction = (r.nextDouble() * 10) - 5;
            poisonAttraction = (r.nextDouble() * 10) - 5;
            ediblePerception = r.nextInt(150) + 1;
            poisonPerception = r.nextInt(150) + 1;
        }
        else
        {
            //regular mutation
            this.edibleAttraction = dna.edibleAttraction + (r.nextDouble() * 4) - 2;
            this.poisonAttraction = dna.poisonAttraction + (r.nextDouble() * 4) - 2;
            this.ediblePerception = dna.ediblePerception + r.nextInt(60) - 30;
            this.poisonPerception = dna.poisonPerception + r.nextInt(60) - 30;

            if (ediblePerception <= 0)
            {
                ediblePerception = 1;
            }

            if (poisonPerception <= 0)
            {
                poisonPerception = 1;
            }
        }
    }

    public double getEdibleAttraction()
    {
        return edibleAttraction;
    }

    public double getPoisonAttr()
    {
        return poisonAttraction;
    }

    public int getEdiblePerception()
    {
        return ediblePerception;
    }

    public int getPoisonPerception()
    {
        return poisonPerception;
    }
}
