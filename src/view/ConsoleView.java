package view;

import Request.*;
import Request.RequestFactory;
import Response.*;
import dataClasses.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleView implements AutoCloseable, View
{

    public static final int PORT = 1795;
    private Scanner scanner;
    private boolean isScript = false;
    private String inputBuffer;
    private ArrayList<String> scriptList = new ArrayList<>();

    public void setInputBuffer(String inputBuffer)
    {
        this.inputBuffer = inputBuffer;
    }

    public void setScript(boolean script)
    {
        isScript = script;
    }


    public void writeMessage(String message)
    {
        System.out.println(message);
    }

    public String readMessage()
    {
        return scanner.nextLine();
    }

    public Route getRoute()
    {
        Route route = new Route();

        // Ввод name
        writeMessage("Введите name:");
        String name = readMessage();
        while (name.trim().isEmpty())
        {
            writeMessage("Строка name не может быть пустой. Повторите ввод:");
            name = scanner.nextLine();
        }
        route.setName(name);

        // Ввод coordinates
        Coordinates coordinates = createCoordinates();
        route.setCoordinates(coordinates);

        // Ввод creationDate
        route.setCreationDate(new Date());

        // Ввод from
        writeMessage("Введите данные для поля from (или оставьте пустым для значения null):");
        Location from = createLocationFrom();
        route.setFrom(from);

        // Ввод to
        writeMessage("Введите данные для поля to:");
        while (true)
        {
            Location to = createLocationFrom();
            try
            {
                route.setTo(to);
                break;
            } catch (IllegalArgumentException e)
            {
                writeMessage("Поле Location.name не может быть пустым, повторите ввод.");
            }
        }

        // Ввод distance
        writeMessage("Введите distance или оставьте поле пустым для значения null:");
        Long distance = null;
        boolean validDistance = false;
        while (!validDistance)
        {
            String input = readMessage();
            if (input.equals("")) break;
            try
            {
                distance = Long.parseLong(input);
                if (distance > 1)
                {
                    validDistance = true;
                } else
                {
                    writeMessage("Значение distance должно быть больше 1. Повторите ввод:");
                }
            } catch (NumberFormatException e)
            {
                writeMessage("Некорректный ввод. Введите целое число:");
            }
        }
        route.setDistance(distance);

        return route;
    }

    @Override
    public int getKey()
    {
        int result = 0;
        while (true)
        {
            try
            {
                result = Integer.parseInt(inputBuffer);
                if (result <= 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e)
            {
                writeMessage("Полученный аргумент не является ключом. Введите целое число больше 0");
                inputBuffer = readMessage();
            }
        }
        return result;
    }

    @Override
    public Long getDistance()
    {
        Long result = 0L;
        while (true)
        {
            try
            {
                if (inputBuffer != null && inputBuffer.equals("")) return null;
                result = Long.parseLong(inputBuffer);
                if (result <= 1) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e)
            {
                writeMessage("Введите целое число больше 1");
                inputBuffer = readMessage();
            }
        }
        return result;
    }

    @Override
    public Path getFileName()
    {
        Path result = null;
        while (true)
        {
            try
            {
                result = Paths.get(inputBuffer);
                if (!Files.isRegularFile(result)) throw new RuntimeException();
                break;
            } catch (InvalidPathException e)
            {
                writeMessage("Введенный путь к файлу невалиден. Повторите ввод");
                inputBuffer = readMessage();
            } catch (RuntimeException e)
            {
                writeMessage("По введенному пути нет файла. Повторите ввод");
            }
        }
        return result;
    }

    private Coordinates createCoordinates()
    {
        Coordinates coordinates = new Coordinates();

        // Ввод x
        writeMessage("Введите значение x для coordinates:");
        Long x = null;
        boolean validX = false;
        while (!validX)
        {
            String input = readMessage();
            try
            {
                x = Long.parseLong(input);
                if (x <= 510)
                {
                    validX = true;
                } else
                {
                    writeMessage("Максимальное значение x для coordinates: 510. Повторите ввод:");
                }
            } catch (NumberFormatException e)
            {
                writeMessage("Некорректный ввод. Введите целое число:");
            }
        }
        coordinates.setX(x);

        // Ввод y
        writeMessage("Введите значение y для coordinates:");
        Double y = null;
        boolean validY = false;
        while (!validY)
        {
            String input = readMessage();
            try
            {
                y = Double.parseDouble(input);
                validY = true;
            } catch (NumberFormatException e)
            {
                writeMessage("Некорректный ввод. Введите число:");
            }
        }
        coordinates.setY(y);

        return coordinates;
    }

    private Location createLocationFrom()
    {
        Location locationFrom = new Location();
        writeMessage("Введите значение name: ");
        String name = readMessage();
        if (name.isEmpty())
        {
            return null;
        } else
        {
            locationFrom.setName(name);
        }

        writeMessage("Введите значение x: ");
        // Ввод x
        Integer x = null;
        while (true)
        {
            String input = readMessage();
            try
            {
                x = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e)
            {
                writeMessage("Некорректный ввод. Введите целое число:");
            }
        }
        locationFrom.setX(x);

        writeMessage("Введите значение y: ");
        Integer y = null;
        while (true)
        {
            String input = readMessage();
            try
            {
                y = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e)
            {
                writeMessage("Некорректный ввод. Введите целое число:");
            }
        }
        locationFrom.setY(y);

        writeMessage("Введите значение z: ");
        Double z = null;
        while (true)
        {
            String input = readMessage();
            try
            {
                z = Double.parseDouble(input);
                break;
            } catch (NumberFormatException e)
            {
                writeMessage("Некорректный ввод. Введите число:");
            }
        }
        locationFrom.setZ(z);


        return locationFrom;
    }

    @Override
    public void close() throws Exception
    {
        scanner.close();
    }


    public void execute()
    {
        RequestFactory requestFactory = new RequestFactory(this);
        while (!Thread.currentThread().isInterrupted() && !isScript)
        {
            String command = scanner.nextLine();
            String[] commands = command.split(" ");
            if (command.equals("exit"))
            {
                Thread.currentThread().interrupt();
                break;
            }
            if (commands[0].equals("execute_script"))
            {
                Scanner oldScanner = this.scanner;
                if (commands.length > 1) inputBuffer = commands[1];
                while (true)
                {
                    Path script = null;
                    try
                    {
                        script = Paths.get(inputBuffer);
                        this.scanner = new Scanner(script);
                        if (scriptList.contains(script.toAbsolutePath().toString()))
                        {
                            System.out.println("Ошибка рекурсивно повторяющихся скриптов");
                            break;
                        }
                        scriptList.add(script.toAbsolutePath().toString());
                        this.execute();
                    } catch (IOException e)
                    {
                        System.out.println("Не удалось открыть файл.");
                        break;
                    } catch (IndexOutOfBoundsException | NullPointerException e)
                    {
                        writeMessage("Введите путь к скрипту");
                        inputBuffer = scanner.nextLine();
                    } catch (NoSuchElementException e)
                    {
                        writeMessage("Скрипт выполнен");
                        scriptList.remove(script.toAbsolutePath().toString());
                        break;
                    }
                }
                this.scanner = oldScanner;
            } else try
            {
                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress("localhost", PORT));

                Request request = requestFactory.createRequest(command);
                ByteBuffer requestBuffer = RequestWriter.writeRequest(request);
                socketChannel.write(requestBuffer);

                ByteBuffer responseBuffer = ByteBuffer.allocate(1024*100);
                socketChannel.read(responseBuffer);
                responseBuffer.flip();

                Response response = ResponceReader.readResponse(responseBuffer);
                System.out.println("Получен ответ от сервера\n");
                printResponse(response);
                socketChannel.close();
            } catch (UnknownHostException e)
            {
                System.out.println("Неизвестная ошибка на сервере");
            } catch (IOException e)
            {
                System.out.println("Сервер не отвечает");
            } catch (IllegalArgumentException e)
            {
                System.out.println("Такой команды не существует. Введите help для получения справки по доступным командам");
            }

//                try
//            {
//                Request request = requestFactory.createRequest(command);
//                RequestWriter.writeRequest(request);
//                Response response = ResponceReader.getResponse();
//                printResponse(response);
//            } catch (IllegalArgumentException e)
//            {
//                System.out.println("Такой команды не существует. Введите help для получения справки по доступным командам");
//            }
        }
    }

    public void setScanner(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public void printResponse(Response response)
    {
        System.out.println(response.getInfo());
    }
}
