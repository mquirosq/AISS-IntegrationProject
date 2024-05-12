
package aiss.vimeoMiner.vimeoModel.modelUser;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Capabilities {

    @JsonProperty("hasLiveSubscription")
    private Boolean hasLiveSubscription;
    @JsonProperty("hasEnterpriseLihp")
    private Boolean hasEnterpriseLihp;
    @JsonProperty("hasSvvTimecodedComments")
    private Boolean hasSvvTimecodedComments;
    @JsonProperty("hasSimplifiedEnterpriseAccount")
    private Boolean hasSimplifiedEnterpriseAccount;

    @JsonProperty("hasLiveSubscription")
    public Boolean getHasLiveSubscription() {
        return hasLiveSubscription;
    }

    @JsonProperty("hasLiveSubscription")
    public void setHasLiveSubscription(Boolean hasLiveSubscription) {
        this.hasLiveSubscription = hasLiveSubscription;
    }

    @JsonProperty("hasEnterpriseLihp")
    public Boolean getHasEnterpriseLihp() {
        return hasEnterpriseLihp;
    }

    @JsonProperty("hasEnterpriseLihp")
    public void setHasEnterpriseLihp(Boolean hasEnterpriseLihp) {
        this.hasEnterpriseLihp = hasEnterpriseLihp;
    }

    @JsonProperty("hasSvvTimecodedComments")
    public Boolean getHasSvvTimecodedComments() {
        return hasSvvTimecodedComments;
    }

    @JsonProperty("hasSvvTimecodedComments")
    public void setHasSvvTimecodedComments(Boolean hasSvvTimecodedComments) {
        this.hasSvvTimecodedComments = hasSvvTimecodedComments;
    }

    @JsonProperty("hasSimplifiedEnterpriseAccount")
    public Boolean getHasSimplifiedEnterpriseAccount() {
        return hasSimplifiedEnterpriseAccount;
    }

    @JsonProperty("hasSimplifiedEnterpriseAccount")
    public void setHasSimplifiedEnterpriseAccount(Boolean hasSimplifiedEnterpriseAccount) {
        this.hasSimplifiedEnterpriseAccount = hasSimplifiedEnterpriseAccount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Capabilities.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("hasLiveSubscription");
        sb.append('=');
        sb.append(((this.hasLiveSubscription == null)?"<null>":this.hasLiveSubscription));
        sb.append(',');
        sb.append("hasEnterpriseLihp");
        sb.append('=');
        sb.append(((this.hasEnterpriseLihp == null)?"<null>":this.hasEnterpriseLihp));
        sb.append(',');
        sb.append("hasSvvTimecodedComments");
        sb.append('=');
        sb.append(((this.hasSvvTimecodedComments == null)?"<null>":this.hasSvvTimecodedComments));
        sb.append(',');
        sb.append("hasSimplifiedEnterpriseAccount");
        sb.append('=');
        sb.append(((this.hasSimplifiedEnterpriseAccount == null)?"<null>":this.hasSimplifiedEnterpriseAccount));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
