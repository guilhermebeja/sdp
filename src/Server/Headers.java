package Server;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Headers {
    HashMap<String, ArrayList<String>> headers;

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
