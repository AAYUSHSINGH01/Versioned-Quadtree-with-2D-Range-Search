public class Point {
    private float x;
    private float y;
    private long time;

    public Point(float x, float y,long time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + x + " , " + y + "]";
    }
}