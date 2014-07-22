package Test;

public class Intersect
{
    private Point Intercec=null;
    private float x;
    private float y;
    private boolean rezult;
    private float a1;
    private float a2;
    private float b1;
    private float b2;
    private float c1;
    private float c2;
    private float xDen;

    public Point findPointIntersection (Point P1, Point P2, Point P3, Point P4){
        a1=P1.getY()-P2.getY();
        a2=P3.getY()-P4.getY();
        b1=P2.getX()-P1.getX();
        b2=P4.getX()-P3.getX();
        c1=P1.getX()*P2.getY()-P2.getX()*P1.getY();
        c2=P3.getX()*P4.getY()-P4.getX()*P3.getY();
        xDen=(b2*a1-a2*b1);
        if (xDen==0){
            //проверка на коллинеарность
            if (c1==c2) System.out.println("Линии совпадают");
            //if (c1!=c2) System.out.println("Линии паралельные");
        }
        else {
            y=(a2*c1-c2*a1)/xDen;
            x=-(c1*b2-c2*b1)/xDen;
            rezult= (x<=Math.max(P1.getX(), P2.getX()) && x>=Math.min(P1.getX(), P2.getX())
                  && x<=Math.max(P3.getX(), P4.getX()) && x>=Math.min(P3.getX(), P4.getX())
                  && y<=Math.max(P1.getY(), P2.getY()) && y>=Math.min(P1.getY(), P2.getY())
                  && y<=Math.max(P3.getY(), P4.getY()) && y>=Math.min(P3.getY(), P4.getY()));
            Intercec=new Point(x, y, rezult);
        }
        return Intercec;
    }
}
