package model.strategy;

import dataClasses.Coordinates;
import dataClasses.Location;
import dataClasses.Route;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Реализует стратегию по загрузке/выгрузке объектов {@link Route} из файла с расширением .csv
 * Работает с файлом, путь к которому передается в конструкторе
 */
public class CsvFileStrategy implements RouteUDstrategy
{
    private Path file;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MMM:yyyy hh:mm:ss", Locale.ENGLISH);

    /**
     * Принимает на вход путь к файлу, если такого не существует, создает его.
     *
     * @throws IllegalArgumentException если формат файла не scv
     */
    public CsvFileStrategy(String path)
    {
        if (!path.endsWith(".csv")) throw new IllegalArgumentException("Неверное расширение файла. Повторите ввод:");
        file = Paths.get(path).toAbsolutePath();
        if (Files.notExists(file))
        {
            try
            {
                Files.createFile(file);
            } catch (IOException e)
            {
                throw new RuntimeException("Файла по введенному адресу не существует. Произошла ошибка при его создании.");
            }
        }
    }

    /**
     * Загружает объекты из файла
     */
    @Override
    public String download(Map<Integer, Route> collection)
    {
        long stringNumber = 0;
        StringBuilder mistakes = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()));)
        {
            reader.readLine();
            while (reader.ready())
            {
                try
                {
                    stringNumber++;
                    String line = reader.readLine();
                    String[] fields = line.split(",");
                    Integer id = Integer.parseInt(fields[0]);
                    String name = fields[1];
                    Long x = Long.parseLong(fields[2]);
                    Double y = Double.parseDouble(fields[3]);
                    Date creationDate = dateFormat.parse(fields[4]);
                    Location from = null;
                    if (!fields[5].isEmpty())
                    {
                        int fromX = Integer.parseInt(fields[5]);
                        int fromY = Integer.parseInt(fields[6]);
                        double fromZ = Double.parseDouble(fields[7]);
                        String fromName = fields[8];
                        from = new Location(fromX, fromY, fromZ, fromName);
                    }
                    Location to = null;
                    int toX = Integer.parseInt(fields[9]);
                    int toY = Integer.parseInt(fields[10]);
                    double toZ = Double.parseDouble(fields[11]);
                    String toName = fields[12];
                    to = new Location(toX, toY, toZ, toName);
                    Long distance = null;
                    if (!fields[13].equals("null"))
                        distance = Long.parseLong(fields[13]);
                    collection.put(id, new Route(id, name, new Coordinates(x, y), creationDate, from, to, distance));
                } catch (ParseException e)
                {
                    mistakes.append("Ошибка при чтении даты. Строка " + stringNumber + "\n");
                } catch (Exception e)
                {
                    mistakes.append("Произошла ошибка при чтении данных из файла. Строка " + stringNumber + "\n");
                }
            }
        } catch (FileNotFoundException e)
        {
            throw new RuntimeException("Файл не найден.");
        } catch (IOException e)
        {
            throw new RuntimeException("Произошла ошибка при открытии файла.");
        } catch (Exception e)
        {
            throw new RuntimeException("Произошла ошибка при чтении данных из файла\n" + e.getMessage());
        }
        return mistakes.toString();
    }

    /**
     * Выгружает объекты в файл
     */
    @Override
    public void upload(Map<Integer, Route> collection)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()));)
        {
            writer.write("Id,Name,Coordinates x,Coordinates y,Creation Date,From x,From y,From z,From name,To x,To y,To z,To name,Distance\n");
            for (Integer id : collection.keySet())
            {
                Route route = collection.get(id);
                writer.write(route.getId() + ",");
                writer.write(route.getName() + ",");
                writer.write(route.getCoordinates().getX() + ",");
                writer.write(route.getCoordinates().getY() + ",");
                writer.write(dateFormat.format(route.getCreationDate()) + ",");
                writer.write((route.getFrom() != null ? route.getFrom().getX() + "," + route.getFrom().getY() + "," + route.getFrom().getZ() + "," + route.getFrom().getName() : ",,,") + ",");
                writer.write(route.getTo().getX() + "," + route.getTo().getY() + "," + route.getTo().getZ() + "," + route.getTo().getName() + ",");
                writer.write(route.getDistance() + "\n");
            }
        } catch (IOException e)
        {
            throw new RuntimeException("Произошла ошибка при открытии файла.");
        }

    }
}
