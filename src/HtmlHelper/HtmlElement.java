package HtmlHelper;

import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An html element represents an html node. This means it can have child nodes, parent node, tags, attributes, etc.
 */
public class HtmlElement implements Serializable {
    //region Variables
    private ArrayList<HtmlElement> children;
    private HtmlElement parent;
    private HashMap<String, String> attributes;
    private String tag;
    private String text; // text in this component
    //endregion

    //region Getters and Setters

    public HtmlElement getParent() {
        return parent;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public void setInnerText(String text){
        this.text = text;
    }

    public String getInnerText(){
        return text;
    }

    //endregion

    //region Constructors

    public HtmlElement(String tag) {
        this.tag = tag;
        children = new ArrayList<>();
        attributes = new HashMap<>();
        parent = null;
        text = "";
    }

    public HtmlElement(String tag, String text){
        this(tag);
        this.text = text;
    }

    public HtmlElement(String tag, HtmlElement element){
        this(tag);
        appendElement(element);
    }

    public HtmlElement(String tag, HtmlElement... nodes){
        this(tag);
        for(HtmlElement node : nodes){
            appendElement(node);
        }
    }
    //endregion

    //region Element Specific Methods

    /**
     * Sets or updates an attibute to be included in the html <attrs></>
     * @param attr Attribute key
     * @param value Attribute value
     */
    public HtmlElement setAttribute(String attr, String value){
        attributes.put(attr, value);
        return this;
    }

    /**
     * Places the attribute without parameters
     * @param attribute
     * @return
     */
    public HtmlElement setAttribute(String attribute){
        attributes.put(attribute, null);
        return this;
    }

    /**
     * Searches for an attribute and returns it's value.
     * @param attr Attribute to search for.
     * @return String: value if attr found and null if no attr was found.
     */
    public String getAttribute(String attr){
        if(attributes.containsKey(attr)){
            return attributes.get(attr);
        }
        return null;
    }

    /**
     * Checks if there is an attribute declarated.
     * @param attr Attribute to check.
     * @return True or False.
     */
    public boolean hasAttribute(String attr){
        return attributes.containsKey(attr);
    }

    //endregion

    //region Relation Methods

    private int calculateTreeLevel(int val){
        if(parent != null){
            val++;
            return parent.calculateTreeLevel(val);
        }
        return val;
    }

    public int getTreeLevel(){
        return calculateTreeLevel(0);
    }

    /**
     * Appends an html element at an indicated position.
     * @param element Element to insert
     * @param index Index to insert the element to
     * @return Success of the operation
     */
    public boolean appendElement(HtmlElement element, int index){
        if(!children.contains(element)){
            if(index >= 0 && index < children.size()){
                children.add(index, element);
                element.parent = this;
                return true;
            }
        }
        return false;
    }

    public HtmlElement with(HtmlElement... nodes){
        for(HtmlElement node : nodes){
            appendElement(node);
        }
        return this;
    }

    /**
     * Appends an element at the last position
     * @param element Html element to append.
     */
    public boolean appendElement(HtmlElement element){
        if(!children.contains(element)){
            children.add(element);
            element.parent = this;
            return true;
        }
        return false;
    }

    /**
     * Removes an element if found in the list
     * @param element Element to remove.
     * @return True or False
     */
    public boolean removeElement(HtmlElement element){
        if(children.contains(element)){
            children.remove(element);
            element.parent = null;
            return true;
        }
        return false;
    }

    /**
     * Removes an element at an indicated position given that it exists.
     * @param index Position of the element to remove.
     * @return True or False.
     */
    public boolean removeElement(int index){
        if(index >= 0 && index <= children.size()){

            children.remove(index);
            return true;
        }
        return false;
    }

    /**
     * This removes all children nodes.
     * IMPORTANT: This is a dangerous method due to node dependencies. Use with caution.
     */
    public void removeAllElements(){
        children = new ArrayList<>();
    }
    //endregion

    //region Search Methods

    /**
     * Searches and returns an element given it's id.
     * @param id ID of the element to get.
     * @return HtmlElement if id found, null if not.
     */
    public HtmlElement getElementById(String id){
        return children.stream().filter(
                c -> c.hasAttribute("id") && c.getAttribute("id").equals(id)
        ).findFirst().get();
    }

    /**
     * Finds and returns n elements that match the class descriptor.
     * @param classDscrptr Html class descriptor
     * @return An arraylist of found elements. If none were found, returns an empty list.
     */
    public List<HtmlElement> getElementsByClass(String classDscrptr){
        return children.stream().filter(c -> c.getAttribute("class").equals(classDscrptr)).collect(Collectors.toList());
    }
    //endregion

    //region Extracting and Parsing Methods

    /**
     * Renders this element as a string (html representation).
     * @return
     */
    public String render(){
        String idents = "";
        for(int i = 0; i < getTreeLevel(); i++){
            idents+="   ";
        }
        StringBuilder sb = new StringBuilder("\n"+idents + openTag());

        sb.append(idents + "    " + text);

        children.forEach(c -> sb.append(c.render()));

        sb.append("\n"+idents+ closeTag());
        return sb.toString();
    }

    protected String openTag(){
        StringBuilder sb = new StringBuilder("<"+tag);
        for(Map.Entry<String, String> attr : attributes.entrySet()){
            if(attr.getValue() != null && attr.getValue() != ""){
                sb.append(" " + attr.getKey() + "=" + "\"" + attr.getValue() + "\""); // < id="1"> example
            }
            else{
                sb.append(" " + attr.getKey()); // < id > example
            }
        }
        sb.append(">\n");
        return sb.toString();
    }

    protected String closeTag(){
        return "</"+tag+">";
    }
    //endregion

    public HtmlElement createCopy(){
        return (HtmlElement) SerializationUtils.clone(this);
    }
}
