package Api;


import Api.model.LoginPost;
import Api.model.ProspektasUserModel;
import Api.model.ProspektumList;
import Api.model.ProspektumPostModel;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by fabian on 17-12-15.
 */
public interface ProspektasAPI {

    @POST("api/sessions")
    Call<ProspektasUserModel> createSession(
            @Body LoginPost prospektasUser
    );

    @POST("api/prospektas")
    Call<ProspektumPostModel> createprospektum(
            @Body ProspektumPostModel prospektasUserModel
    );

    @GET("api/prospektas/")
    Call<ProspektumList> getProspektum(@Query("prospektum_id") long employee_id
    );

    @POST("api/prospektas_images/")
    Call<ProspektumPostModel> createProspektumImages(
            @Body ProspektumPostModel prospektasUserModel
    );

    @GET("api/prospektas/")
    Call<ProspektumList> getProspektumImages(@Query("prospektum_id") long employee_id
    );



}

