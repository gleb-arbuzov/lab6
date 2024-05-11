package controller;


import dataClasses.Route;

public interface Controller
{
    public String help();
    public String info();
    public String show();
    public boolean insert(Route route);
    public boolean update(Route route);
    public boolean save();
    public boolean removeKey(int key);
    public boolean clear();
    //public boolean executeScript(String fileName);
    public boolean exit();
    public boolean replaceIfGreater(Route route);
    public boolean removeGreaterKey(int key);
    public boolean removeLowerKey(int key);
    public boolean removeAnyByDistance(Long distance);
    public String filterByDistance(Long distance);
    public String printFieldAscendingDistance();
}
