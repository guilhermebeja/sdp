package HtmlHelper;

import java.util.HashMap;

public class HtmlTemplates {
    private static HashMap<String, HtmlDocument> documents;

    public static HtmlDocument getTemplate(String tempID){
        if(documents != null && documents.containsKey(tempID)){
            return (HtmlDocument)(documents.get(tempID)).createCopy();
        }
        return null;
    }
    public static void addTemplate(String tempID, HtmlDocument template){
        if(documents == null){
            documents = new HashMap<>();
        }
        documents.put(tempID, template);
    }
}
