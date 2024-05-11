package Response;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class ResponceWriter
{

    private ResponceWriter()
    {
    }

    public static ByteBuffer writeResponse(Response response)
    {
        try
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(response);
            return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

}
