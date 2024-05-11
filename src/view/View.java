package view;

import dataClasses.Route;

import java.nio.file.Path;

public interface View
{
    public String readMessage();

    public void writeMessage(String message);

    public Route getRoute();

    public int getKey();

    public Long getDistance();

    public Path getFileName();
    void setInputBuffer(String message);
}
