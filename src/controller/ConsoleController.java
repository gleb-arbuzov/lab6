package controller;

import dataClasses.Route;
import model.RouteModel;

import java.util.*;

/**
 * Класс выполняет вывод информации на терминал, чтение и исполнение команд из него.
 */
public class ConsoleController implements Controller
{
    private RouteModel model;

    /**
     * @param model на которую делегирована работа с коллекцией.
     */
    public ConsoleController(RouteModel model)
    {
        this.model = model;
    }

    public String help()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Справка по доступным командам\n");
        for (Command command : Command.values())
        {
            sb.append(command + " : " + command.description + "\n");
        }
        return sb.toString();
    }

    public String info()
    {
        return model.getCollectionInfo();
    }


    public String show()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Элементы коллекции:\n");
        List<String> strings = model.getAllElementsToString();
        Collections.sort(model.getAllElementsToString());
        for (String s : strings)
        {
            sb.append(s + "\n");
        }
        return sb.toString();
    }

    public boolean insert(Route route)
    {
        if (model.hasId(route.getId())) route.setId(model.generateId());
        model.insert(route);
        return true;
    }


    public boolean update(Route route)
    {
        if (!model.hasId(route.getId()))
        {
            return false;
        }
        model.update(route);
        return true;
    }

    public boolean save()
    {
        model.save();
        return true;
    }


    public boolean removeKey(int key)
    {
        if (!model.hasId(key))
        {
            return false;
        }
        model.remove(key);
        return true;
    }

    public boolean clear()
    {
        model.clear();
        return true;
    }

//    public boolean executeScript(String fileName)
//    {
//        Path path = Paths.get(fileName);
//        if (!Files.isRegularFile(path))
//        {
//            return false;
//        }
//        try (Scanner scanner = new Scanner(path))
//        {
//            if (scriptList.contains(fileName))
//            {
//                return false;
//            }
//            scriptList.add(fileName);
//            ConsoleView consoleView = new ConsoleView();
//            consoleView.setScanner(scanner);
//            consoleView.setController(this);
//            consoleView.setScript(true);
////            View oldView = view;
////            this.view = consoleView;
//            consoleView.execute();
//            scriptList.remove(fileName);
//            this.view = oldView;
//        } catch (IOException e)
//        {
//            System.out.println("Не удалось открыть файл.");
//        }
//        return true;
//    }

    public boolean exit()
    {
        Thread.currentThread().interrupt();
        return true;
    }

    public boolean replaceIfGreater(Route route)
    {
        if (!model.hasId(route.getId()))
        {
            return false;
        }
        model.replaceIfGreater(route);
        return true;
    }

    public boolean removeGreaterKey(int key)
    {
        model.removeGreaterKey(key);
        return true;
    }

    public boolean removeLowerKey(int key)
    {
        model.removeLowerKey(key);
        return true;
    }

    public boolean removeAnyByDistance(Long distance)
    {
        model.removeByDistance(distance);
        return true;
    }

    public String filterByDistance(Long distance)
    {
        StringBuilder sb = new StringBuilder();
        for (Route route : model.getRoutesByDistance(distance))
        {
            sb.append(route.toString()+ "\n");
        }
        return sb.toString();
    }

    public String printFieldAscendingDistance()
    {
        return (Arrays.toString(model.getSortedDistances().toArray(new Long[0])));
    }


}
