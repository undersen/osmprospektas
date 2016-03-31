package Api.service;

import android.content.Context;

import Api.ProspektasAPI;
import Api.ServiceGenerator;
import Api.model.ProspektumPostModel;
import retrofit.Call;

/**
 * Created by UnderSen on 29-03-16.
 */
public class ProspektumPostService extends BaseAuthenticatedService<ProspektumPostModel> {

    private ProspektumPostModel prospektumPostModel;

    public ProspektumPostService(Context context, ProspektumPostModel prospektumPostModel) {
        super(context);
        this.prospektumPostModel = prospektumPostModel;
    }

    @Override
    public void loadService(BaseCallback<ProspektumPostModel> callback) {
        ProspektasAPI prospektasAPI = ServiceGenerator.createService(ProspektasAPI.class,getmAuthToken(),getmEmail());
        Call<ProspektumPostModel> prospektumPostModelCall = prospektasAPI.createprospektum(prospektumPostModel);
        prospektumPostModelCall.enqueue(callback);

    }
}
