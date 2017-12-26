package HtmlHelper;

public class Text extends HtmlElement{
    public Text(String text) {
        super("text");
        setInnerText(text);
    }

    @Override
    public String render() {
        return getInnerText();
    }
}
