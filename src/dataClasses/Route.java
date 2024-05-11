package dataClasses;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * бин, объекты которого хранятся в коллекции
 */
public class Route implements Comparable<Route>, Serializable
{
    /**
     * Поле не может быть null, Значение поля больше 0, Значение этого поля уникально
     */
    private Integer id;
    /**
     * Поле не null, Строка не пустая
     */
    private String name;
    /**
     * Поле не null
     */
    private Coordinates coordinates;
    /**
     * Поле не null, генерируется автоматически
     */
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**
     * Поле может быть null
     */
    private Location from;
    /**
     * Поле не может быть null
     */
    private Location to;
    /**
     * Значение поля должно быть больше 1
     */
    private Long distance;

    public Route(Integer id, String name, Coordinates coordinates, Location from, Location to, Long distance)
    {
        this.setId(id);
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setFrom(from);
        this.setTo(to);
        this.setDistance(distance);
        creationDate = new Date();
    }
    public Route(Integer id, String name, Coordinates coordinates, Date creationDate, Location from, Location to, Long distance)
    {
        this.setId(id);
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setFrom(from);
        this.setTo(to);
        this.setDistance(distance);
        this.creationDate = creationDate;
    }
    public Route() {}

    /**
     * @param id
     * @throws IllegalArgumentException если переданное id меньше нуля или null
     */
    public void setId(Integer id)
    {
        if (id == null || id <= 0) throw new IllegalArgumentException("Route.id должно быть больше 0 и не может быть null");
        this.id = id;
    }

    /**
     * @throws IllegalArgumentException если переданное name null или пустая строка
     * @param name
     */
    public void setName(String name)
    {
        if (name == null || name.equals("")) throw new IllegalArgumentException("Route.name не может быть null или пустой строкой");
        this.name = name;
    }

    /**
     * @throws IllegalArgumentException Если переданное coordinates null
     * @param coordinates
     */
    public void setCoordinates(Coordinates coordinates)
    {
        if (coordinates == null) throw new IllegalArgumentException("Route.coordinates не может быть null");
        this.coordinates = coordinates;
    }

    public void setFrom(Location from)
    {
        this.from = from;
    }

    /**
     * @throws IllegalArgumentException Если аргумент null
     * @param to
     */
    public void setTo(Location to)
    {
        if (to == null) throw new IllegalArgumentException("Route.to не может быть null");
        this.to = to;
    }

    /**
     * @throws IllegalArgumentException если distance не больше 1
     * @param distance
     */
    public void setDistance(Long distance)
    {
        if (distance != null && distance <= 1) throw new IllegalArgumentException("Route.distance должно быть больше 1");
        this.distance = distance;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Coordinates getCoordinates()
    {
        return coordinates;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public Location getFrom()
    {
        return from;
    }

    public Location getTo()
    {
        return to;
    }

    public Long getDistance()
    {
        return distance;
    }

    /**
     * Сравнивает по distance
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Route o)
    {
        if (this.distance == null && o.distance != null) return -1;
        else if (this.distance != null && o.distance == null) return 1;
        else if (this.distance == null && o.distance == null)  return 0;
        else return Long.compare(this.distance,o.distance);
    }

    @Override
    public String toString()
    {
        return "Route{" + "id=" + id + ", name='" + name + '\'' + ", coordinates=" + coordinates + ", creationDate=" + creationDate + ", from=" + from + ", to=" + to + ", distance=" + distance + '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return distance == route.distance && Objects.equals(id, route.id) && Objects.equals(name, route.name) && Objects.equals(coordinates, route.coordinates) && Objects.equals(creationDate, route.creationDate) && Objects.equals(from, route.from) && Objects.equals(to, route.to);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, coordinates, creationDate, from, to, distance);
    }

    private void writeObject(ObjectOutputStream stream) throws IOException
    {
        stream.writeObject(id);
        stream.writeObject(name);
        stream.writeObject(coordinates);
        stream.writeObject(creationDate);
        stream.writeObject(from);
        stream.writeObject(to);
        stream.writeObject(distance);
    }
    private void readObject(ObjectInputStream stream) throws IOException,ClassNotFoundException
    {
        id = (Integer) stream.readObject();
        name = (String) stream.readObject();
        coordinates = (Coordinates) stream.readObject();
        creationDate = (Date) stream.readObject();
        from = (Location) stream.readObject();
        to = (Location) stream.readObject();
        distance = (Long) stream.readObject();
    }
}