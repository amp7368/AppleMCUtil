package apple.mc.utilities.data.region;

public class XYZ<T> {

    private T x;
    private T y;
    private T z;

    public XYZ(T x, T y, T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    public T getZ() {
        return z;
    }

    public void setZ(T z) {
        this.z = z;
    }


    @Override
    public String toString() {
        return String.format("<%s,%s,%s>",
            x == null ? "null" : x.toString(),
            y == null ? "null" : y.toString(),
            z == null ? "null" : z.toString()
        );
    }

    @Override
    public int hashCode() {
        long hash = 0;
        hash += ((long) x.hashCode()) >> 8;
        hash += y.hashCode();
        hash += ((long) z.hashCode()) << 8;
        return (int) (hash % Integer.MAX_VALUE);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof XYZ<?> t) {
            return this.x.equals(t.x) && this.y.equals(t.y) && this.z.equals(t.z);
        }
        return false;
    }

}
