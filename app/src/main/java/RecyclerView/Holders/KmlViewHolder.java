package RecyclerView.Holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.android.osmdroidofflinedemo.R;

/**
 * Created by UnderSen on 29-03-16.
 */
public class KmlViewHolder extends RecyclerView.ViewHolder {


    public TextView textView_name;
    public Switch aSwitch;


    public KmlViewHolder(View v) {
        super(v);
        textView_name = (TextView) v.findViewById(R.id.kml_views_name);
        aSwitch = (Switch) v.findViewById(R.id.kml_views_switch);
    }

    public TextView getTextView_name() {
        return textView_name;
    }

    public void setTextView_name(TextView textView_name) {
        this.textView_name = textView_name;
    }

    public Switch getaSwitch() {
        return aSwitch;
    }

    public void setaSwitch(Switch aSwitch) {
        this.aSwitch = aSwitch;
    }
}
