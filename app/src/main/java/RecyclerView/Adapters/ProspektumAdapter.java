package RecyclerView.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.osmdroidofflinedemo.MapActivity;
import com.example.android.osmdroidofflinedemo.ProspektumActivity;
import com.example.android.osmdroidofflinedemo.R;

import java.util.List;

import Api.model.ProspektumGetModel;
import RecyclerView.Holders.ProspektumViewHolder;

/**
 * Created by UnderSen on 30-03-16.
 */
public class ProspektumAdapter extends RecyclerView.Adapter<ProspektumViewHolder> {

    private List<ProspektumGetModel> prospektumModelList;
    private Activity activity;

    public ProspektumAdapter(@NonNull List<ProspektumGetModel> prospektumModelList, Activity activity) {
        this.prospektumModelList = prospektumModelList;
        this.activity = activity;
    }

    @Override
    public ProspektumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prospektum_views, null);
        ProspektumViewHolder prospektumViewHolder = new ProspektumViewHolder(view);
        return prospektumViewHolder;
    }

    @Override
    public void onBindViewHolder(ProspektumViewHolder holder, int position) {
        final ProspektumGetModel prospektumModel =  prospektumModelList.get(position);
        holder.textView_name.setText("Holaaa");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProspektumActivity prospektumActivity = (ProspektumActivity) activity ;
                prospektumActivity.initMap(prospektumModel.getId());
            }
        });


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Edit Prospektum
            }
        });
    }

    @Override
    public int getItemCount() {
        return prospektumModelList.size();
    }
}
