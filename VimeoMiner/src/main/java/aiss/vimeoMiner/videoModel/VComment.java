package aiss.vimeoMiner.videoModel;

public class VComment {
    private String id;
    private String text;
    private String createdOn;
    private VUser author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public VUser getAuthor() {
        return author;
    }

    public void setAuthor(VUser author) {
        this.author = author;
    }
}
