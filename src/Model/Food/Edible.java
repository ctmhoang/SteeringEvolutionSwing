package Model.Food;

import Utils.Vector;

import java.awt.*;

public class Edible extends Food
{
    public Edible(Vector pos){
        super();
        nutrition = 15;
        color = Color.GREEN;
        this.pos = pos;
        size = 5;
        eaten = false;
    }
}
