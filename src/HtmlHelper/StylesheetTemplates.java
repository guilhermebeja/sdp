package HtmlHelper;

import java.util.HashMap;

public class StylesheetTemplates {
    private static HashMap<String, Stylesheet> stylesheets;

    public static void addStyleSheet(String id, Stylesheet stylesheet){
        if(stylesheets == null){
            stylesheets = new HashMap<>();
            stylesheets.put(id, stylesheet);
        }
    }

    public static Stylesheet getStyleSheet(String id){
        if(stylesheets != null){
            return stylesheets.get(id);
        }
        return null;
    }
}
