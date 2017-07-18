package es.grupogo.playgroundsdk;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.grupogo.playgroundsdk2.R;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by Carlos Olmedo on 2/2/17.
 */

public class Action extends RealmObject implements Parcelable{

    @PrimaryKey
    @SerializedName("id") String id;
    @SerializedName("title") String title;
    @SerializedName("description") String description;
    @SerializedName("website") String website;
    @SerializedName("url") String url;
    @SerializedName("image") String image;
    @SerializedName("time") int time;
    @SerializedName("metric") String metric;
    @SerializedName("longitude") String longitude;
    @SerializedName("latitude") String latitude;
    @SerializedName("metric_quantity") int metricQuantity;
    @SerializedName("plegdes") int pledges;
    @SerializedName("review") float review;
    @SerializedName("type") String type;
    @SerializedName("cms_url") String cmsUrl;
    //@SerializedName("poll") Poll poll;
    boolean done;
    String categories;


    public Action() {
    }

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
        review = in.readFloat();
        type = in.readString();
        cmsUrl = in.readString();
        categories = in.readString();
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<String> getCategories() {
        if(categories!=null){
            List<String> categoriesList = Arrays.asList(categories.split("--- "));
            return categoriesList;
        } else {
            return new ArrayList<>();
        }

    }

    public void setCategories(List<String> categories) {
        if(categories!=null){
            String categoriesString = "";
            for(int i = 0; i<categories.size();i++){
                if(i!=categories.size()-1){
                    categoriesString += categories.get(i) + "--- ";
                }else {
                    categoriesString += categories.get(i);
                }
            }
            this.categories = categoriesString;
        } else {
            this.categories=null;
        }
    }

    public void setCategories(String categories) {
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

    public float getReview() {
        return review;
    }

    public void setReview(float review) {
        this.review = review;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public String getMetric() {
        return metric;
    }

    public void setTime(int time) {
        this.time = time;
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


    public int getShortType() {


        switch (type) {
            case "Free training":
                return R.string.type_learn;
            case "Ideation":
                return R.string.type_think;
            case "Micro-Volunteering":
            case "NGOs and volunteer work":
                return R.string.type_participate;
            case "Events":
                return R.string.type_assist;
            case "Crowdfunding positive projects":
            case "Donate Processing Power":
                return R.string.type_contribute;
            case "Signatures for change":
                return R.string.type_sign;
            case "Donations":
                return R.string.type_donate;
            case "Individual Actions":
            case "Special":
            case "Help us prioritize":
                return R.string.type_do;
            case "Adoptions":
                return R.string.type_adopt;
            case "Ethical Consumer":
            case "Personal Habits":
                return R.string.type_pledge;
            case "Messages Worth Spreading":
                return R.string.type_share;
            default:
                return R.string.type_default;
        }
    }

    public int getShortTypeColor() {

        switch (type) {
            case "Free training":
            case "Ideation":
            case "Micro-Volunteering":
            case "Help us prioritize":
            case "NGOs and volunteer work":
            case "Events":
            case "Special":
                return R.color.colorPG1;
            case "Crowdfunding positive projects":
                return R.color.colorPG2;
            case "Signatures for change":
                return R.color.colorPG3;
            case "Donate Processing Power":
            case "Donations":
                return R.color.colorPG4;
            case "Individual Actions":
            case "Personal Habits":
            case "Adoptions":
            case "Ethical Consumer":
                return R.color.colorPG5;
            case "Messages Worth Spreading":
                return R.color.colorPG6;
            default:
                return R.color.colorPG1;
        }
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
        dest.writeFloat(review);
        dest.writeString(type);
        dest.writeString(cmsUrl);
        dest.writeString(categories);
    }


}
