package es.grupogo.playgroundsdk;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import retrofit2.http.Url;

/**
 * Created by Carlos Olmedo on 2/2/17.
 */

public class RequestHelper {

    private interface DoService {

        @Headers({
                "Token: 59baef879d68f4af3c97c0269ed46200",
                "Secret: b6cccc4e45422e84143cd6a8fa589eb4"
        })
        @GET("api/Playground/search")
        Call<String> getActions(@Query("text") String text,
                                @Query("size") int size,
                                @Query("type") String type,
                                @Query("website") String website,
                                @Query("categories") String categories);

    }

    public interface RequestCallback<T> {
        void onResponse(T response);
        void onFailure(Throwable t);
    }


    private static RequestHelper mInstance = null;
    private static DoService apiService;


    public class RequestException extends Exception {
        RequestException(String detailMessage) {
            super(detailMessage);
        }
    }

    class DateTypeDeserializer implements JsonDeserializer<Date> {
        private final String[] DATE_FORMATS = new String[]{
                "dd-MM-yyyy",
                "yyyy-MM-dd HH:mm:ss.0",
                "-yyyy-MM-dd",
                "yyyy-MM-dd",
                "yyyy/MM/dd"

        };

        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context) throws JsonParseException {
            for (String format : DATE_FORMATS) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
                    formatter.setLenient(false);
                    return formatter.parse(jsonElement.getAsString());
                } catch (ParseException e) {
                    //e.printStackTrace();
                }
            }
            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                    + "\". Supported formats: \n" + Arrays.toString(DATE_FORMATS));
        }
    }

    class ActionTypeDeserializer implements JsonDeserializer<Action> {

        @Override
        public Action deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return null;
        }
    }


    public static RequestHelper getInstance(Context context) {

        if (mInstance==null) {
            mInstance = new RequestHelper();
        }
        return mInstance;
    }

    private static OkHttpClient defaultOkHttpClient() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    private RequestHelper() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeDeserializer())
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://webintra.net/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(defaultOkHttpClient())
                .build();

        apiService = retrofit.create(DoService.class);



    }

    public String getActions(String text, int quantity) throws RequestException {

        Call<String> historyCall = apiService.getActions(text, quantity, null, null, null);

        try {
            Response<String> response = historyCall.execute();
            if (response.isSuccessful()) {

                String responseString = response.body();
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    String info = jsonObject.getString("info");
                    return info;
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new RequestException("No se ha podido recuperar el historial");
                }

            } else {
                throw new RequestException("No se ha podido recuperar el historial");
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RequestException("No se ha podido establecer conexión con el servidor");
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new RequestException("Fallo en la respuesta del servidor");
        }

    }



    public void getActionsAsync(@Nullable  String text, int quantity, @Nullable  String type, @Nullable  String website, @Nullable  String category, final RequestCallback<List<Action>> callback) {

        Call<String> historyCall = apiService.getActions(text, quantity, type, website, category);

        historyCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    String responseString = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseString);
                        JSONArray items = jsonObject.getJSONObject("info").getJSONArray("items");
                        Gson gson = new GsonBuilder().create();
                        List<Action> actions = new ArrayList<>();
                        for (int i = 0; i < items.length(); i++) {
                            try {
                                Action action = gson.fromJson(items.getJSONObject(i).toString(), Action.class);
                                actions.add(action);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("SDK", items.getJSONObject(i).toString());
                            }
                        }
                        callback.onResponse(actions);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFailure(new RequestException("No se han podido recuperar acciones"));
                    }

                } else {
                    Log.e("request", response.errorBody().toString());
                    callback.onFailure(new RequestException("No se han podido recuperar acciones"));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure(new RequestException("No se han podido recuperar acciones"));

            }
        });

    }

}
