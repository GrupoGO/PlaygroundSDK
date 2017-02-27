package es.grupogo.playgroundsdk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos Olmedo on 2/2/17.
 */

public class Action {

    @SerializedName("id") String id;
    @SerializedName("title") String title;
    @SerializedName("description") String description;
    @SerializedName("website") String website;
    @SerializedName("url") String url;
    @SerializedName("image") String image;
    @SerializedName("category") String category;
    @SerializedName("time") float time;
    @SerializedName("metric") String metric;
    @SerializedName("metric_quantity") float metricQuantity;
    @SerializedName("pledges") int pledges;
    String actionType;

    public Action(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return actionType;
    }

    public void setType(String type) {
        this.actionType = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public float getMetricQuantity() {
        return metricQuantity;
    }

    public void setMetricQuantity(float metricQuantity) {
        this.metricQuantity = metricQuantity;
    }

    public int getPledges() {
        return pledges;
    }

    public void setPledges(int pledges) {
        this.pledges = pledges;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Action)obj).getId().equals(id);
    }
}
