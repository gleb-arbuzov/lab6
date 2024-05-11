package Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Request implements Serializable
{
    private String name;

    public void setRequestData(Object requestData)
    {
        this.requestData = requestData;
    }

    public Object getRequestData()
    {
        return requestData;
    }

    private Object requestData;

    public Request(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    private void writeObject(ObjectOutputStream stream) throws IOException
    {
        stream.writeObject(name);
        stream.writeObject(requestData);
    }
    private void readObject(ObjectInputStream stream) throws IOException,ClassNotFoundException
    {
        name = (String) stream.readObject();
        requestData = stream.readObject();
    }

    @Override
    public String toString()
    {
        return "Request{" + "name='" + name + '\'' + ", requestData=" + requestData + '}';
    }
}
