package es.grupogo.playgroundsdk;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

/**
 * Created by jorge_cmata on 18/7/17.
 */

public class ApiManager {

    private static ApiManager mInstance = null;
    private static RequestManager requestManager = null;

    public interface ApiCallback<T> {
        void onResponse(T response);
        void onFailure(Throwable t);
    }

    public static ApiManager getInstance(Context context) {

        if (mInstance==null) {
            mInstance = new ApiManager(context);
        }

        return mInstance;
    }


    private ApiManager(Context context) {
        requestManager = RequestManager.getInstance(context);
    }

    public void getActions(@Nullable String text, int quantity, List<Integer> ids, @Nullable  String type, @Nullable  String website, @Nullable  String category, @Nullable LatLng northeast,
                           @Nullable LatLng southwest, final ApiCallback<List<Action>> callback){

        requestManager.getActions(text, quantity, ids, type, website, category, northeast, southwest, new RequestManager.RequestCallback<List<Action>>(){
            @Override
            public void onResponse(List<Action> actions) {
                callback.onResponse(actions);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                callback.onFailure(t);
            }
        });
    }

    public void getActionsInBounds(@Nullable final String text, final int quantity, final List<Integer> ids, @Nullable final String type, @Nullable final String website, @Nullable final String category,
                                   double latitude, double longitude, final ApiCallback<List<Action>> callback){

        requestManager.getRegionBounds(latitude, longitude, new RequestManager.RequestCallback<LatLngBounds>() {
            @Override
            public void onResponse(LatLngBounds latLngBounds) {
                LatLng northeast = latLngBounds.northeast;
                LatLng southwest = latLngBounds.southwest;

                requestManager.getActions(text, quantity, ids, type, website, category, northeast, southwest, new RequestManager.RequestCallback<List<Action>>(){
                    @Override
                    public void onResponse(List<Action> actions) {
                        callback.onResponse(actions);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        callback.onFailure(t);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
