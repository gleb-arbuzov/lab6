package controller;

import Request.Request;
import Response.Response;
import dataClasses.Route;

public class RequestHandler
{
    private Controller controller;

    public RequestHandler(Controller controller)
    {
        this.controller = controller;
    }

    public Response handleRequest(Request request)
    {
        switch (request.getName())
        {
            case "help": return new Response("help",true, controller.help());
            case "info": return new Response("info",true, controller.info());
            case "show": return new Response("show",true, controller.show());
            case "insert":
            {
                boolean isSuccess = controller.insert((Route) request.getRequestData());
                return new Response("insert",isSuccess,"Элемент добавлен.");
            }
            case "update":
            {
                boolean isSuccess = controller.update((Route) request.getRequestData());
                return new Response("update",isSuccess,isSuccess ? "Элемент добавлен." : "Элемента по такому ключу не существует.");
            }
            case "remove_key":
            {
                boolean isSuccess = controller.removeKey((int)request.getRequestData());
                return new Response("remove_key",isSuccess,isSuccess ? "Элемент удален." : "Элемента по такому ключу не существует.");
            }
            case "clear": return new Response("clear", controller.clear(), "Коллекция очищена.");
            case "save": return new Response("save", controller.save(), "Коллекция сохранена.");
            case "exit": return new Response("exit", controller.exit(), "До свидания!");
            case "replace_if_greater":
            {
                boolean isSuccess = controller.replaceIfGreater((Route) request.getRequestData());
                return new Response("replace_if_greater", isSuccess,isSuccess ? "Элемент заменен." : "Элемента по такому ключу не существует.");
            }
            case "remove_greater_key": return new Response("remove_greater_key", controller.removeGreaterKey((int)request.getRequestData()), "Элементы удалены.");
            case "remove_lower_key": return new Response("remove_greater_key", controller.removeLowerKey((int)request.getRequestData()),"Элементы удалены." );
            case "remove_any_by_distance": return new Response("remove_any_by_distance", controller.removeAnyByDistance((Long) request.getRequestData()),"Элемент удален." );
            case "filter_by_distance": return new Response("filter_by_distance",true, controller.filterByDistance((Long) request.getRequestData()));
            case "print_field_ascending_distance": return new Response("print_field_ascending_distance",true, controller.printFieldAscendingDistance());
            default: return new Response("error",false,"Получены некорректные данные.");
        }
    }
}
