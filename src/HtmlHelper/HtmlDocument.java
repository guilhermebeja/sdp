package HtmlHelper;

/**
 * An html document is the top object of an html representation. It holds sections and has some parsing abilities.
 * Can be compared to a top of a tree. This class was only created for helping and unifying the use of html pages.
 *
 * This also implements non required functionality such as the possibility of creating a stylesheet.
 */
public class HtmlDocument extends HtmlElement{

    private HtmlElement head, body, style;

    public HtmlDocument(Stylesheet stylesheet) {
        super("html");
        head = new HtmlElement("head");
        style = new HtmlElement("style", stylesheet.render());
        body = new HtmlElement("body");

        // create and insert a head and a body element
        appendElement(head);
        appendElement(style);
        appendElement(body);
    }

    public HtmlDocument(){
        this(new Stylesheet("stylesheet"));
    }

    private void configureHead(){

    }

    public void addElementToBody(HtmlElement element){
        body.appendElement(element);
    }

}
