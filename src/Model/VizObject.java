package Model;

import Utils.Vector;

import java.awt.*;

public abstract class VizObject
{

    protected Vector pos;
    protected Color color;
    protected int size;

    public abstract void draw(Graphics2D g);

    public Vector getPosition(){
        return pos;
    }

}
