package HtmlHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Stylesheet {
    private String name;
    private HashMap<String, ArrayList<String>> parameters;

    public Stylesheet(String name){
        parameters = new HashMap<>();
    }

    public void addParameter(String element, String... parameter){
        ArrayList<String> params = new ArrayList<>();

        if(parameters.containsKey(element)){
            params = parameters.get(element);
        }

        for(String p : parameter){
            params.add(p);
        }
        parameters.put(element, params);
    }

    public String render(){
        StringBuilder sb =  new StringBuilder("\n");
        for(HashMap.Entry entry : parameters.entrySet()){
            sb.append(entry.getKey() + " {\n");

            for(String s : (ArrayList<String>)entry.getValue()){
                sb.append(s + ";\n");
            }

            sb.append("}\n");
        }

        return sb.toString();
    }
}
