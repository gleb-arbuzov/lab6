package dataClasses;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
/**
 * Используется в {@link Route Route}
 */
public class Location implements Serializable
{
    private int x;
    private int y;
    private double z;
    /**
     * Длина строки не должна быть больше 452, Поле не может быть null
     */
    private String name;

    public Location()
    {

    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y && Double.compare(z, location.z) == 0 && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y, z, name);
    }

    @Override
    public String toString()
    {
        return "Location{" + "x=" + x + ", y=" + y + ", z=" + z + ", name='" + name + '\'' + '}';
    }

    public Location(int x, int y, double z, String name)
    {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setName(name);
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    public void setName(String name)
    {
        if (name == null) throw new IllegalArgumentException("LocationFrom.name не может быть null");
        if (name.length() > 452) throw new IllegalArgumentException("Длина LocationFrom.name не должна быть больше 452");
        this.name = name;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public double getZ()
    {
        return z;
    }

    public String getName()
    {
        return name;
    }
    private void writeObject(ObjectOutputStream stream) throws IOException
    {
        stream.writeObject(x);
        stream.writeObject(y);
        stream.writeObject(z);
        stream.writeObject(name);
    }
    private void readObject(ObjectInputStream stream) throws IOException,ClassNotFoundException
    {
        x = (int) stream.readObject();
        y = (int) stream.readObject();
        z = (double) stream.readObject();
        name = (String) stream.readObject();
    }
}