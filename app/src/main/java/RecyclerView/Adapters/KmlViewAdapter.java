package RecyclerView.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.android.osmdroidofflinedemo.MapActivity;
import com.example.android.osmdroidofflinedemo.ProspektumActivity;
import com.example.android.osmdroidofflinedemo.R;

import java.io.File;
import java.util.List;

import Api.model.ProspektumGetModel;
import Api.model.ProspektumKmlFile;
import RecyclerView.Holders.KmlViewHolder;
import RecyclerView.Holders.ProspektumViewHolder;

/**
 * Created by UnderSen on 30-03-16.
 */
public class KmlViewAdapter extends RecyclerView.Adapter<KmlViewHolder> {


    private List<ProspektumKmlFile> files;
    private Activity activity;

    public KmlViewAdapter(List<ProspektumKmlFile> files, Activity activity) {
        this.files = files;
        this.activity = activity;
    }

    @Override
    public KmlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kml_views,null);
        KmlViewHolder kmlViewHolder = new KmlViewHolder(view);
        return kmlViewHolder;
    }

    @Override
    public void onBindViewHolder(KmlViewHolder holder, int position) {
        final ProspektumKmlFile file =  files.get(position);
        holder.textView_name.setText(file.getName());
        holder.aSwitch.setChecked(file.isLoaded());
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    MapActivity mapActivity = (MapActivity) activity;
                    mapActivity.loadKmlFromRoute(file.getName());

                }else
                {

                }
            }
        });
    }

    @Override
    public int getItemCount() {return files.size();}
}

