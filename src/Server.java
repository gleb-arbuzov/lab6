import Request.*;
import controller.ConsoleController;
import controller.RequestHandler;
import model.RouteModel;
import Response.*;
import model.strategy.CsvFileStrategy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Server
{
    public static final int PORT = 1795;

    public static void main(String[] args)
    {
        CsvFileStrategy csvFileStrategy = null;
        Scanner scanner = new Scanner(System.in);
        if (args.length == 0)
        {
            System.out.println("Не получен путь к файлу, введите его:");
            args = new String[]{scanner.nextLine()};
        }
        while (true)
        {
            try
            {
                csvFileStrategy = new CsvFileStrategy(args[0]);
                break;
            } catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
                args = new String[]{scanner.nextLine()};

            }
        }
        RouteModel model = new RouteModel(csvFileStrategy);
        System.out.println(model.initialize());
        ConsoleController controller = new ConsoleController(model);
        RequestHandler requestHandler = new RequestHandler(controller);
        try
        {
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(PORT));
            serverSocket.configureBlocking(false);

            while (!Thread.currentThread().isInterrupted()) {
                if (System.in.available() > 0)
                {
                    String command = scanner.nextLine();
                    switch (command)
                    {
                        case "exit": Thread.currentThread().interrupt();
                        case "save": controller.save();

                    }
                }
                SocketChannel client = serverSocket.accept();
                if (client != null) {
                    ByteBuffer buffer = ByteBuffer.allocate(1024*100);
                    client.read(buffer);
                    buffer.flip();

                    Request request = RequestReader.getRequest(buffer);

                    // Логика обработки запроса
                    System.out.println("Получен запрос от клиента: " + request);

                    Response response = requestHandler.handleRequest(request);
                    ByteBuffer responseBuffer = ResponceWriter.writeResponse(response);
                    client.write(responseBuffer);
                    System.out.println("Клиенту отправлен ответ: " + response);
                    client.close();
                }
            }
        } catch (IOException e)
        {
            System.out.println("Произошла ошибка на клиенте");
        }
    }
}
