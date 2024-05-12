
package aiss.vimeoMiner.vimeoModel.modelComment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Paging {

    @JsonProperty("next")
    private Object next;
    @JsonProperty("previous")
    private Object previous;
    @JsonProperty("first")
    private String first;
    @JsonProperty("last")
    private String last;

    @JsonProperty("next")
    public Object getNext() {
        return next;
    }

    @JsonProperty("next")
    public void setNext(Object next) {
        this.next = next;
    }

    @JsonProperty("previous")
    public Object getPrevious() {
        return previous;
    }

    @JsonProperty("previous")
    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    @JsonProperty("first")
    public String getFirst() {
        return first;
    }

    @JsonProperty("first")
    public void setFirst(String first) {
        this.first = first;
    }

    @JsonProperty("last")
    public String getLast() {
        return last;
    }

    @JsonProperty("last")
    public void setLast(String last) {
        this.last = last;
    }

}
