
package aiss.vimeoMiner.vimeoModel.modelChannel;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

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
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
