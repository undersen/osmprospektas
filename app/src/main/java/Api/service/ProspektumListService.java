package Api.service;

import android.content.Context;

import Api.model.ProspektumGetModel;
import Api.model.ProspektumList;
import retrofit.Call;

/**
 * Created by UnderSen on 30-03-16.
 */
public class ProspektumListService extends BaseAuthenticatedService<ProspektumList> {
    private int employeeId =0;
    Call<ProspektumList> prospektumListCall;
    ;


    public ProspektumListService(Context context, int employeeId) {
        super(context);
        this.employeeId = employeeId;
    }

    @Override
    public void loadService(BaseCallback<ProspektumList> callback) {
        prospektumListCall = getmApiService().getProspektum(employeeId);
        prospektumListCall.enqueue(callback);
    }
}
