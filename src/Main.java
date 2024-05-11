//import Request.RequestWriter;
//import Response.ResponceReader;
//import view.ConsoleView;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Scanner;
//
//public class Main
//{
//    public static void main(String[] args) throws IOException
//    {
//        Path requests = Paths.get("/Users/glebarbuzov/labs/lab6client1/requestFile");
//        Path responces = Paths.get("/Users/glebarbuzov/labs/lab6client1/responceFile");
//        RequestWriter.setOutputStream(new ObjectOutputStream(Files.newOutputStream(requests)));
//        ObjectInputStream inputStream = null;
//        InputStream inputStream1 = Files.newInputStream(requests);
//        while (true)
//        {
//            try
//            {
//                inputStream = new ObjectInputStream(inputStream1);
//                break;
//            } catch (EOFException e)
//            {
//            }
//        }
//        ResponceReader.setInputStream(inputStream);
//        ConsoleView view = new ConsoleView();
//        view.setScanner(new Scanner(System.in));
//        view.execute();
//    }
//}
