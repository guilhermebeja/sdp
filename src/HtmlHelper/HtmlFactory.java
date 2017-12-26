package HtmlHelper;

public class HtmlFactory {
    public static String bold(String tobold){
        return "<bold>"+tobold+"</bold>";
    }

    public static HtmlElement createATag(String link, String text){
        HtmlElement a = new HtmlElement("a", text);
        a.setAttribute("href", link);

        return a;
    }

    public static HtmlElement createATag(String link, HtmlElement element){
        HtmlElement a = new HtmlElement("a", element);
        a.setAttribute("href", link);
        return a;
    }

    /**
     * Creates an unordered list, given n list items.
     * This was created to reduce the lines of code in some
     * of the views that use lists.
     * A very important aspect of this method is that if
     * an HtmlElement in the parameters is not a list item,
     * it will be encapsulated in one.
     *
     * @param lis List Items to introduce to the list.
     * @return A list that contains all list items.
     */
    public static HtmlElement createUnorderedList(HtmlElement... lis){
        HtmlElement list = new HtmlElement("ul");
        for(HtmlElement element : lis){
            HtmlElement li = new HtmlElement("li");
            if(!element.getTag().equals("li")){
                li.appendElement(element);
            }
            else{
                li = element;
            }
            list.appendElement(li);
        }
        return list;
    }

    public static HtmlElement createBorderedTable(){
        HtmlElement table = new HtmlElement("table");

        return table;
    }
}
