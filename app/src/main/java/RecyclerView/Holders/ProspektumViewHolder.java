package RecyclerView.Holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.osmdroidofflinedemo.R;

/**
 * Created by UnderSen on 29-03-16.
 */
public class ProspektumViewHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public ImageView imageView;
    public TextView textView_name;
    public TextView textView_other_info;
    public Button button;


    public ProspektumViewHolder(View v) {
        super(v);
        cardView = (CardView) v.findViewById(R.id.prospektum_view_card);
        imageView = (ImageView) v.findViewById(R.id.prospektum_view_imageview);
        textView_name = (TextView) v.findViewById(R.id.prospektum_view_text_name);
        textView_other_info = (TextView) v.findViewById(R.id.prospektum_view_text_description);
        button = (Button) v.findViewById(R.id.prospektum_view_button);
    }

    public CardView getCardView() {
        return cardView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView_name() {
        return textView_name;
    }

    public TextView getTextView_other_info() {
        return textView_other_info;
    }

}
