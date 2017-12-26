package HtmlHelper;

/**
 * This element represents an unpaired tag html element.
 */
public class HtmlUnpairedElement extends HtmlElement{


    public HtmlUnpairedElement(String tag) {
        super(tag);
    }

    public HtmlUnpairedElement(String tag, String text) {
        super(tag, text);
    }

    @Override
    public String render() {
        return openTag();
    }

}
