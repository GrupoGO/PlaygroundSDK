package es.grupogo.playgroundsdk;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by jorge_cmata on 10/2/17.
 */

public class JSONParserHelper {


    public static Action parseAction(JSONObject actionObject)throws JSONException{
        Gson gson = new GsonBuilder().create();
        Action action = gson.fromJson(actionObject.toString(), Action.class);
        if (action.getId()==null) {
            throw new JSONException("Invalid id in action");
        }
        action = JSONParserHelper.parseCategories(action, actionObject);
        return action;
    }



    public static Action parseCategories(Action action, JSONObject actionObject) throws JSONException {
        JSONArray categoriesArray = actionObject.getJSONArray("category");
        String categoriesString = "";
        for (int i=0; i<categoriesArray.length(); i++) {
            if(i!=categoriesArray.length()-1){
                categoriesString += categoriesArray.getString(i) + "---";
            }else {
                categoriesString += categoriesArray.getString(i);
            }
        }
        action.setCategories(categoriesString);
        return action;
    }

    public static LatLngBounds parseRegionBounds(JSONObject jsonObject) throws JSONException {
        JSONArray resultsArrayObject = jsonObject.getJSONArray("results");
        JSONObject resultsObject = resultsArrayObject.getJSONObject(0);
        JSONObject geometryObject = resultsObject.optJSONObject("geometry");
        JSONObject boundsObject = geometryObject.optJSONObject("bounds");
        //northeast
        JSONObject northeastObject = boundsObject.optJSONObject("northeast");
        LatLng latLngNortheast = new LatLng(Double.parseDouble(northeastObject.optString("lat")),
                Double.parseDouble(northeastObject.optString("lng")));
        //southwest
        JSONObject southwestObject = boundsObject.optJSONObject("southwest");
        LatLng latLngSouthwest = new LatLng(Double.parseDouble(southwestObject.optString("lat")),
                Double.parseDouble(southwestObject.optString("lng")));
        return new LatLngBounds(latLngSouthwest, latLngNortheast);

    }

}
