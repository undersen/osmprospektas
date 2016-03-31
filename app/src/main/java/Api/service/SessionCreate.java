package Api.service;

import Api.ProspektasAPI;
import Api.ServiceGenerator;
import Api.model.LoginPost;

import Api.model.ProspektasUserModel;
import retrofit.Call;

/**
 * Created by fabian on 20-12-15.
 */
public class SessionCreate {
    private LoginPost loginPost;


    public SessionCreate(LoginPost loginPost){
        this.loginPost      = loginPost;
    }



    //Async call
    public void loadService(BaseCallback<ProspektasUserModel> callback){
        ProspektasAPI apiService    = ServiceGenerator.createService(ProspektasAPI.class, null, null);
         Call<ProspektasUserModel> prospektasUserCall     = apiService.createSession( loginPost );
        prospektasUserCall.enqueue(callback);
    }






}
