package es.grupogo.playgroundsdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by jorge_cmata on 10/2/17.
 */
public class RequestManager {


    public interface ApiService {

        @Headers({
                "Token: 59baef879d68f4af3c97c0269ed46200",
                "Secret: b6cccc4e45422e84143cd6a8fa589eb4"
        })
        @GET("search")
        Call<String> getActions(@Query("text") String text,
                                @Query("size") int size,
                                @Query("ids") String ids,
                                @Query("type") String type,
                                @Query("website") String website,
                                @Query("categories") String categories);

    }

    private static RequestManager mInstance = null;
    private static ApiService apiService;
    private static final String API_URL = "https://webintra.net/api/Playground/";

    private ConnectivityManager cm;

    public class RequestException extends Exception {
        public RequestException(String detailMessage) {
            super(detailMessage);
        }
    }

    public class ConnectionException extends Exception {

        public ConnectionException(String detailMessage) {
            super(detailMessage);
        }
    }

    public interface RequestCallback<T> {
        void onResponse(T response);
        void onFailure(Throwable t);
    }

    public static RequestManager getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new RequestManager(context);
        }
        return mInstance;
    }

    private RequestManager(Context context) {

        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(defaultOkHttpClient())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private static OkHttpClient defaultOkHttpClient() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    public void checkConnection() throws ConnectionException {

        if (cm!=null && cm.getActiveNetworkInfo()!=null) {

            if(!cm.getActiveNetworkInfo().isConnected()) throw new ConnectionException("There is no Internet connection");
        } else {
            throw new ConnectionException("There is no Internet connection");
        }

    }


    //region SEARCH
    //---------------------------------------------------------------------------------------

    public void getActions(@Nullable String text,
                           int quantity,
                           @Nullable List<Integer> ids,
                           @Nullable String type,
                           @Nullable String website,
                           @Nullable String category,
                           final RequestManager.RequestCallback<List<Action>> callback) {

        try {
            checkConnection();
        } catch (ConnectionException e) {
            e.printStackTrace();
            callback.onFailure(e);
            return;
        }

        String idsString = "";

        if (ids!=null) {
            for (int i = 0; i < ids.size(); i++) {
                if (i == 0) {
                    idsString += ids.get(i);
                } else {
                    idsString += "," + ids.get(i);
                }
            }
        }

        Call<String> historyCall;
        historyCall = apiService.getActions(text, quantity, idsString, type, website, category);



        historyCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    String responseString = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseString);
                        JSONArray items = jsonObject.getJSONObject("info").getJSONArray("items");
                        List<Action> actions = new ArrayList<>();
                        for (int i = 0; i < items.length(); i++) {
                            try {
                                Action action = JSONParserHelper.parseAction(items.getJSONObject(i));
                                actions.add(action);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("SDK", items.getJSONObject(i).toString());
                            }
                        }
                        callback.onResponse(actions);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFailure(new RequestManager.RequestException("An error has occurred"));
                    }

                } else {
                    Log.e("request", response.errorBody().toString());
                    callback.onFailure(new RequestManager.RequestException("An error has occurred"));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure(new RequestManager.RequestException("An error has occurred"));

            }
        });
    }


    //---------------------------------------------------------------------------------------
    //endregion






}

