package Test;

public class Point
{
    private float x;
    private float y;
    private boolean inter;

    public Point(float x, float y){
        this.x=x;
        this.y=y;
    }

    public Point(float x, float y, boolean inter){
        this.x=x;
        this.y=y;
        this.inter=inter;
    }
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isInter() {
        return inter;
    }

    public void setInter(boolean inter) {
        this.inter = inter;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        if (x!=point.getX()||y!=point.getY()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (int)(((x+y)+(x-y)+(y-x))*x*y);
    }*/
}
