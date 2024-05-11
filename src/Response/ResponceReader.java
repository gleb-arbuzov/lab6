package Response;

import Request.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.Date;

public class ResponceReader
{

    private ResponceReader()
    {
    }
    public static Response readResponse(ByteBuffer buffer)
    {
        try
        {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            return (Response) inputStream.readObject();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
