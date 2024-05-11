import view.ConsoleView;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client
{

    public static void main(String[] args)
    {
        ConsoleView view = new ConsoleView();
        view.setScanner(new Scanner(System.in));
        view.execute();
    }

}
