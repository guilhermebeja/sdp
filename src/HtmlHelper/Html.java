package HtmlHelper;

public class Html {
    public static HtmlElement text(String text){
        return new Text(text);
    }

    public static HtmlElement h(int size, String text){
        return new HtmlElement("h"+size, text);
    }

    public static HtmlElement h(int size, HtmlElement... nodes){
        return new HtmlElement("h"+size, nodes);
    }

    public static HtmlElement a(String link, String text){
        HtmlElement a = new HtmlElement("a", text);
        a.setAttribute("href", link);
        return a;
    }

    public static HtmlElement a(String link, HtmlElement... nodes){
        HtmlElement a = new HtmlElement("a", nodes);
        a.setAttribute("href", link);
        return a;
    }

    public static HtmlElement hr(){
        return new HtmlUnpairedElement("hr");
    }

    public static HtmlElement p(String text){
        return new HtmlElement("p", text);
    }

    public static HtmlElement p(HtmlElement... nodes){
        return new HtmlElement("p", nodes);
    }

    public static HtmlElement html(HtmlElement... nodes){
        return new HtmlElement("html", nodes);
    }

    public static HtmlElement head(HtmlElement... nodes){
        return new HtmlElement("head", nodes);
    }

    public static HtmlElement style(String text){
        return new HtmlElement("style", text);
    }

    public static HtmlElement body(HtmlElement... nodes){
        return new HtmlElement("body", nodes);
    }

    public static HtmlElement table(HtmlElement... nodes){
        return new HtmlElement("table", nodes);
    }

    public static HtmlElement tr(HtmlElement... nodes){
        return new HtmlElement("tr", nodes);
    }

    public static HtmlElement td(HtmlElement... nodes){
        return new HtmlElement("td", nodes);
    }

    public static HtmlElement td(String text){
        return new HtmlElement("td", text);
    }

    public static HtmlElement th(HtmlElement... nodes){
        return new HtmlElement("th", nodes);
    }

    public static HtmlElement th(String text){
        return new HtmlElement("th", text);
    }

    public static HtmlElement ul(HtmlElement... nodes){
        return new HtmlElement("ul", nodes);
    }

    public static HtmlElement li(HtmlElement... nodes){
        return new HtmlElement("li", nodes);
    }

    public static HtmlElement li(String text){
        return new HtmlElement("li", text);
    }

    public static HtmlElement strong(String text){
        return new HtmlElement("strong", text);
    }

    public static HtmlElement strong(HtmlElement... nodes){
        return  new HtmlElement("strong", nodes);
    }

    public static HtmlElement breadcrumbs(HtmlElement... nodes){
        HtmlElement ul = ul(nodes);
        ul.setAttribute("class", "breadcrumb");
        return ul;
    }

    public static HtmlElement form(HtmlElement... nodes){
        HtmlElement form = new HtmlElement("form", nodes);

        return form;
    }

    public static HtmlElement form(String action, HtmlElement... nodes){
        HtmlElement form = form(nodes);
        form.setAttribute("action", action);

        return form;
    }

    public static HtmlElement form(String action, String method, HtmlElement... nodes){
        HtmlElement form = form(action, nodes);
        form.setAttribute("method", method);
        return form;
    }

    public static HtmlElement input(String type, String name){
        HtmlElement input = new HtmlElement("input");
        input.setAttribute("type", type);
        input.setAttribute("name", name);

        return input;
    }

    public static HtmlElement button(String txt){
        return new HtmlElement("button", txt);
    }

    public static HtmlElement br(){
        return new HtmlUnpairedElement("br");
    }

    public static HtmlElement option(String value, String text){
        return new HtmlElement("option", text).setAttribute("value", value);
    }

    public static HtmlElement select(boolean multiple, HtmlElement... options){
        if(multiple){
            return (new HtmlElement("select", options)).setAttribute("multiple");
        }
        else{
            return (new HtmlElement("select", options));

        }
    }
}
