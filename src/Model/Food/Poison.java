package Model.Food;

import Utils.Vector;

import java.awt.*;

public class Poison extends Food
{
    public Poison(Vector pos){
        super();
        nutrition = -30;
        color = Color.RED;
        this.pos = pos;
        size = 5;
        eaten = false;
    }
}
