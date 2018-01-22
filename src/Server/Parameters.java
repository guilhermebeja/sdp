package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Parameters implements Serializable{
    HashMap<String, ArrayList<String>> parameters;

    public Parameters() {
        parameters = new HashMap<>();
    }

    public void setParameters() {
        parameters = new HashMap<>();
    }

    public void addParameter(String header, String value){
        ArrayList<String> values = new ArrayList<>();
        if(parameters.containsKey(header)){
           values = parameters.get(header);
        }
        else{
            parameters.put(header, values);
        }
        values.add(value);
    }

    public ArrayList<String> getParameter(String parameter){
        if(parameters.containsKey(parameter)){
            return parameters.get(parameter);
        }
        else{
            return new ArrayList<>();
        }
    }

    public boolean containsParameter(String param){
        return parameters.containsKey(param) && ((ArrayList)parameters.get(param)).size() > 0;
    }
}
