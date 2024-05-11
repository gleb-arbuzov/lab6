package Request;

import dataClasses.Route;
import view.View;

public class RequestFactory
{
    private View view;

    public RequestFactory(View view)
    {
        this.view = view;
    }

    public Request createRequest(String message)
    {
        String[] words = message.split(" ");
        String name = message.split(" ")[0];
        if (words.length > 1) view.setInputBuffer(words[1]);
        switch (name)
        {
            case "help": return new Request("help");
            case "info": return new Request("info");
            case "show": return new Request("show");
            case "clear": return new Request("clear");
            case "exit": return new Request("exit");
            case "print_field_ascending_distance": return new Request("print_field_ascending_distance");
            case "insert":
            {
                int id = view.getKey();
                Route route = view.getRoute();
                route.setId(id);
                Request request = new Request("insert");
                request.setRequestData(route);
                return request;
            }
            case "update":
            {
                int id = view.getKey();
                Route route = view.getRoute();
                route.setId(id);
                Request request =  new Request("update");
                request.setRequestData("update");
            }
            case "remove_key":
            {
                Request request = new Request("remove_key");
                request.setRequestData(view.getKey());
                return  request;
            }
            case "execute_script":
            {
                Request request = new Request("execute_script");
                request.setRequestData(view.getFileName());
                return  request;
            }
            case "replace_if_greater":
            {
                int id = view.getKey();
                Route route = view.getRoute();
                route.setId(id);
                Request request =  new Request("replace_if_greater");
                request.setRequestData(route);
            }
            case "remove_greater_key":
            {
                Request request = new Request("remove_greater_key");
                request.setRequestData(view.getKey());
                return  request;
            }
            case "remove_lower_key":
            {
                Request request = new Request("remove_lower_key");
                request.setRequestData(view.getKey());
                return  request;
            }
            case "remove_any_by_distance":
            {
                Request request = new Request("remove_any_by_distance");
                request.setRequestData(view.getDistance());
                return  request;
            }
            case "filter_by_distance":
            {
                Request request = new Request("filter_by_distance");
                request.setRequestData(view.getDistance());
                return  request;
            }
            default: throw new IllegalArgumentException();
        }
    }
}
