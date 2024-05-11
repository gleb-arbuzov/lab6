package model;

import dataClasses.Route;
import model.strategy.RouteUDstrategy;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Хранит в {@link LinkedHashMap} объекты {@link Route} и содержит методы для работы с коллекцией.
 * Читает и сохраняет коллекцию при помощи объекта {@link RouteUDstrategy}.
 */
public class RouteModel
{
    private RouteUDstrategy routeUDstrategy;
    private LinkedHashMap<Integer, Route> collection = new LinkedHashMap<>();
    private Date initializationDate;
    private int maxId;
    private List<Integer> deletedIds = new ArrayList<>();

    /**
     * Сохраняет полученные из аргумента объекты {@link Route} в коллекцию и считает текущий максимальный id
     */
    public RouteModel(RouteUDstrategy routeUDstrategy)
    {
        this.routeUDstrategy = routeUDstrategy;
        routeUDstrategy.download(collection);
        for (Route route : collection.values())
        {
            maxId = collection.values().stream().mapToInt(Route::getId).max().orElse(0);
        }
        initializationDate = new Date();
        refreshDeletedIds();
    }

    public LinkedHashMap<Integer, Route> getCollection()
    {
        return collection;
    }

    public String initialize()
    {
        String result = routeUDstrategy.download(collection);
        for (Route route : collection.values())
        {
            maxId = Math.max(maxId, route.getId());
        }
        initializationDate = new Date();
        refreshDeletedIds();
        return result;
    }

    private void refreshDeletedIds()
    {
        List<Integer> ids = new ArrayList<>();
        for (Route route : collection.values())
        {
            ids.add(route.getId());
        }
        for (int i = 1; i < maxId; i++)
        {
            if (!ids.contains(i)) deletedIds.add(i);
        }
    }

    /**
     * @return Новое уникальное значение id, полученное инкрементацией максимального текущего id
     */
    public int generateId()
    {
        if (deletedIds.size() == 0) return ++maxId;
        return deletedIds.remove(0);
    }

    /**
     * Добавляет новый элемент в коллекцию
     *
     * @throws IllegalArgumentException Если элемент с таким id уже существует
     */
    public void insert(Route route)
    {
        if (collection.get(route.getId()) != null)
            throw new IllegalArgumentException("Элемент с таким ID уже существует");
        collection.put(route.getId(), route);
    }

    /**
     * Обновляет значение элемента коллекции.
     *
     * @throws IllegalArgumentException Если элемента с таким ID не существует
     */
    public void update(Route route)
    {
        if (collection.get(route.getId()) == null)
            throw new IllegalArgumentException("Элемента с таким ID не существует");
        collection.replace(route.getId(), route);
    }

    /**
     * Удаляет элемент коллекции с заданным ключом
     *
     * @return удаленный элемент или null если элемента по данному ключу не существует
     */
    public Route remove(int id)
    {
        return collection.remove(id);
    }

    /**
     * Удаляет все элементы коллекции
     */
    public void clear()
    {
        collection.clear();
    }

    /**
     * Сохраняет коллекцию при помощи объекта {@link RouteUDstrategy}
     */
    public void save()
    {
        routeUDstrategy.upload(collection);
    }

    public Route getById(int id)
    {
        return collection.get(id);
    }

    /**
     * @return список полей distance элементов коллекции, отсортированных по возрастанию
     */
    public List<Long> getSortedDistances()
    {
        return collection.values().stream()
                .map(Route::getDistance)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * @return список элементов, значение поля distance которых равно заданному
     */
    public List<Route> getRoutesByDistance(Long distance)
    {
        return collection.values().stream()
                .filter(route -> Objects.equals(route.getDistance(), distance))
                .collect(Collectors.toList());
    }

    /**
     * Удаляет один элемент коллекции, значение поля distance которого равно заданному
     *
     * @return удаленный элемент или null если элемента с таким значением distance нет
     */
    public Route removeByDistance(Long distance)
    {
        for (Route route : collection.values())
        {
            if (route.getDistance() == distance)
            {
                return collection.remove(route.getId());
            }
        }
        return null;
    }

    /**
     * Удаляет все элементы коллекции, ключ которых меньше чем заданный
     */
    public void removeLowerKey(int id)
    {
        collection.entrySet().removeIf(entry -> entry.getKey() < id);
    }

    /**
     * Удаляет все элементы коллекции, ключ которых больше чем заданный
     */
    public void removeGreaterKey(int id)
    {
        collection.entrySet().removeIf(entry -> entry.getKey() > id);
    }

    /**
     * Заменяет значение по ключу, если новое значение больше старого.
     * Использует сравнение по умолчанию
     *
     * @return старое значение или null если значение не было изменено
     */
    public Route replaceIfGreater(Route route)
    {
        Route oldValue = collection.get(route.getId());
        if (oldValue == null) throw new IllegalArgumentException("Элемента с таким ID не существует");
        if (oldValue.compareTo(route) < 0)
        {
            return collection.replace(route.getId(), route);
        }
        return null;
    }

    /**
     * @return список строковых представлений элементов коллекции
     */
    public List<String> getAllElementsToString()
    {
        List<String> result = new ArrayList<>();
        for (Route route : collection.values())
        {
            result.add(route.toString());
        }
        return result;
    }

    public String getCollectionInfo()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("Тип коллекции: LinkedHashMap\n");
        sb.append("Дата инициализации: " + dateFormat.format(initializationDate) + "\n");
        sb.append("Количество элементов: " + collection.size());
        return sb.toString();
    }

    public boolean hasId(int id)
    {
        return collection.containsKey(id);
    }
}
