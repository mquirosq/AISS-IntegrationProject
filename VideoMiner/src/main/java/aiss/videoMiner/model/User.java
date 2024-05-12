package aiss.videoMiner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

/**
 * @author Juan C. Alonso
 */
@Entity
@Table(name = "VMUser")
public class User {

    /*
    * In order to avoid making the model unnecessarily complex, we establish a one-to-one relationship between Comment and
    * User (instead of many-to-one). This causes an exception if we try to add a Comment to the DataBase that has been
    * created by a User that already has a Comment in a previously stored Video. To avoid this exception, we automatically
    * assign an id to each new User with AutoIncrement.
     */
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("userLink")
    private String userLink;

    @JsonProperty("pictureLink")
    private String pictureLink;

    public User(String name, String user_link, String picture_link) {
        this.name = name;
        this.userLink = user_link;
        this.pictureLink = picture_link;
    }

    public User(){
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserLink() {
        return userLink;
    }

    public void setUserLink(String userLink) {
        this.userLink = userLink;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userLink='" + userLink + '\'' +
                ", pictureLink='" + pictureLink + '\'' +
                '}';
    }

}
