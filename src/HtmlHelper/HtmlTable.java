package HtmlHelper;

public class HtmlTable extends HtmlElement{
    private HtmlElement thead, tbody;

    public HtmlTable() {
        super("table");
        thead = new HtmlElement("thead");
        tbody = new HtmlElement("tbody");
        appendElement(thead);
        appendElement(tbody);
    }

    /**
     * Replaces headers in a table
     * @param strs Names of headers : String[]
     */
    public void setHeaders(String[] strs){
        thead.removeAllElements();
        HtmlElement tr = new HtmlElement("tr");
        for(int i = 0; i < strs.length; i++){
            tr.appendElement(new HtmlElement("th", strs[i]));
        }
        thead.appendElement(tr);
    }

    public void addRow(String[] row){
        HtmlElement tr = new HtmlElement("tr");
        for(int i=0; i < row.length; i++){
            tr.appendElement(new HtmlElement("td", row[i]));
        }
        tbody.appendElement(tr);
    }
}
