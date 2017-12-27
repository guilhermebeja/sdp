package Server;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Headers implements Serializable{
    HashMap<String, ArrayList<String>> headers;

    public Headers() {
        headers = new HashMap<>();
    }

    public void setHeaders() {
        headers = new HashMap<>();
    }

    public void addHeader(String header, String value){
        ArrayList<String> values = new ArrayList<>();
        if(headers.containsKey(header)){
           values = headers.get(header);
        }
        else{
            headers.put(header, values);
        }
        values.add(value);
    }

    public ArrayList<String> getHeader(String header){
        if(headers.containsKey(header)){
            return headers.get(header);
        }
        else{
            return new ArrayList<>();
        }
    }

    public boolean containsHeader(String header){
        return headers.containsKey(header);
    }
}
