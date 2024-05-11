package dataClasses;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * Используется в {@link Route Route}
 */
public class Coordinates implements Serializable
{
    public Coordinates()
    {
    }

    /**
     * Максимальное значение поля: 510, Поле не может быть null
     */
    private Long x;
    /**
     * Поле не может быть null
     */
    private Double y;

    @Override
    public String toString()
    {
        return "Coordinates{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    public Coordinates(Long x, Double y)
    {
        this.setX(x);
        this.setY(y);
    }

    public void setX(Long x)
    {
        if (x == null) throw new IllegalArgumentException("Coordinates.x не может быть null");
        if (x > 510) throw new IllegalArgumentException("Coordinates.x не может быть больше 510");
        this.x = x;
    }

    public void setY(Double y)
    {
        if (y == null) throw new IllegalArgumentException("Coordinates.y не может быть null");
        this.y = y;
    }

    public Long getX()
    {
        return x;
    }

    public Double getY()
    {
        return y;
    }
    private void writeObject(ObjectOutputStream stream) throws IOException
    {
        stream.writeObject(x);
        stream.writeObject(y);
    }
    private void readObject(ObjectInputStream stream) throws IOException,ClassNotFoundException
    {
        x = (Long) stream.readObject();
        y = (Double) stream.readObject();
    }
}



