package Test;

import Utils.Vector;

public class Main
{
    private static int getRotateY(double x, double c, Vector relative)
    {
        return (int) (-(x * relative.getY() * (-1) + c) / relative.getX());
    }

    public static void main(String[] args)
    {
        Vector a = new Vector(1,2);
        Vector b = new Vector(2,5);
        a.sub(b);
        System.out.println(a);
        int c = (int) (- (-1) * a.getY() * b.getX() - a.getX() * b.getY()); // a is relative vectors
        System.out.println();
        System.out.println(getRotateY(3,c,a));
    }
}
