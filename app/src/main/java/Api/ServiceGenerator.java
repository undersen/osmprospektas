package Api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.lang.reflect.Type;

import Realm.RealmString;
import io.realm.RealmList;
import io.realm.RealmObject;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
/**
 * Created by fabian on 17-12-15.
 */
public class ServiceGenerator {
        public static final String API_BASE_URL = "http://stage.prospektas.cl/";

    private static OkHttpClient httpClient  = new OkHttpClient();

    private static Type token = new TypeToken<RealmList<RealmString>>(){}.getType();
    private static Gson gson                = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .registerTypeAdapter(token, new TypeAdapter<RealmList<RealmString>>() {
                @Override
                public void write(JsonWriter out, RealmList<RealmString> value) throws IOException {
                    out.beginArray();
                    for( RealmString newString : value ){
                        out.value( newString.getValue() );
                    }
                    out.endArray();
                }

                @Override
                public RealmList<RealmString> read(JsonReader in) throws IOException {
                    RealmList<RealmString>  list    = new RealmList<RealmString>();
                    in.beginArray();
                    while(in.hasNext()){
                        list.add(new RealmString((in.nextString())));
                    }
                    in.endArray();
                    return list;
                }
            })
            .create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));


    public static <S> S createService(Class<S> serviceClass, final String authToken, final String userEmail ) {
        if (authToken != null && userEmail !=null) {
            httpClient.interceptors().clear();
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("X-User-Email", userEmail)
                            .header("X-User-Token", authToken)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}