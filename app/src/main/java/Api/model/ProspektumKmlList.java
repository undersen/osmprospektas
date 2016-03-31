package Api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UnderSen on 31-03-16.
 */
public class ProspektumKmlList {

    @SerializedName("kml_sernageomin")
    @Expose
    public List<ProspektumKml> kmlSernageomin = new ArrayList<ProspektumKml>();

    public List<ProspektumKml> getKmlSernageomin() {
        return kmlSernageomin;
    }

    public void setKmlSernageomin(List<ProspektumKml> kmlSernageomin) {
        this.kmlSernageomin = kmlSernageomin;
    }


}
