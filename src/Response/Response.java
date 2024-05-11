package Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Response implements Serializable
{
    private String name;
    private boolean isSuccess;
    private String info;

    public Response(String name, boolean isSuccess, String info)
    {
        this.name = name;
        this.isSuccess = isSuccess;
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "Response{" + "name='" + name + '\'' + ", isSuccess=" + isSuccess + ", info='" + info + '\'' + '}';
    }

    public boolean isSuccess()
    {
        return isSuccess;
    }

    public String getInfo()
    {
        return info;
    }

    public String getName()
    {
        return name;
    }
    private void writeObject(ObjectOutputStream stream) throws IOException
    {
        stream.writeObject(name);
        stream.writeBoolean(isSuccess);
        stream.writeObject(info);
    }
    private void readObject(ObjectInputStream stream) throws IOException,ClassNotFoundException
    {
        name = (String) stream.readObject();
        isSuccess = stream.readBoolean();
        info = (String) stream.readObject();
    }
}
