package Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.Date;

public class RequestReader
{

    private RequestReader()
    {
    }
    public static Request getRequest(ByteBuffer buffer)
    {
        try
        {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            return (Request) inputStream.readObject();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
