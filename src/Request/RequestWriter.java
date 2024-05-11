package Request;

import Response.ResponceReader;
import Response.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class RequestWriter
{

    private RequestWriter()
    {
    }

    public static ByteBuffer writeRequest(Request request)
    {
        try
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(request);
            return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

}
