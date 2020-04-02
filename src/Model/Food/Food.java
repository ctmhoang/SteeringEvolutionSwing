package Model.Food;

import Model.VizObject;

import java.awt.*;


public abstract class Food extends VizObject
{
    protected int nutrition;
    protected boolean eaten;

    public Food(){
        size = 10;
    }

    public int getNutrition(){
        return nutrition;
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillOval((int) Math.round(pos.getX() - size / 2), (int) Math.round(pos.getY() - size / 2), size, size);
    }
}
