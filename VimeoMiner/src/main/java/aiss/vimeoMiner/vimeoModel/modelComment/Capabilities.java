
package aiss.vimeoMiner.vimeoModel.modelComment;

import com.fasterxml.jackson.annotation.JsonProperty;

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

}
