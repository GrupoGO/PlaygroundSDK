package es.grupogo.playgroundsdk;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carlos Olmedo on 2/2/17.
 */

public class Action implements Parcelable{

    @SerializedName("id") String id;
    @SerializedName("title") String title;
    @SerializedName("description") String description;
    @SerializedName("website") String website;
    @SerializedName("url") String url;
    @SerializedName("image") String image;
    @SerializedName("time") int time;
    @SerializedName("metric") String metric;
    @SerializedName("metric_quantity") int metricQuantity;
    @SerializedName("pledges") int pledges;
    @SerializedName("review") int review;
    @SerializedName("type") String type;
    @SerializedName("cms_url") String cmsUrl;
    @SerializedName("category")
    List<String> categories;

    public Action(String id) {
        this.id = id;
    }


    protected Action(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        website = in.readString();
        url = in.readString();
        image = in.readString();
        time = in.readInt();
        metric = in.readString();
        metricQuantity = in.readInt();
        pledges = in.readInt();
        review = in.readInt();
        type = in.readString();
        cmsUrl = in.readString();
        categories = in.createStringArrayList();
    }

    public static final Creator<Action> CREATOR = new Creator<Action>() {
        @Override
        public Action createFromParcel(Parcel in) {
            return new Action(in);
        }

        @Override
        public Action[] newArray(int size) {
            return new Action[size];
        }
    };

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
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
        return type;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public int getMetricQuantity() {
        return metricQuantity;
    }

    public void setMetricQuantity(int metricQuantity) {
        this.metricQuantity = metricQuantity;
    }

    public int getPledges() {
        return pledges;
    }

    public void setPledges(int pledges) {
        this.pledges = pledges;
    }

    public String getCmsUrl() {
        return cmsUrl;
    }

    public void setCmsUrl(String cmsUrl) {
        this.cmsUrl = cmsUrl;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Action)obj).getId().equals(id);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(website);
        dest.writeString(url);
        dest.writeString(image);
        dest.writeInt(time);
        dest.writeString(metric);
        dest.writeInt(metricQuantity);
        dest.writeInt(pledges);
        dest.writeInt(review);
        dest.writeString(type);
        dest.writeString(cmsUrl);
        dest.writeStringList(categories);
    }
}
