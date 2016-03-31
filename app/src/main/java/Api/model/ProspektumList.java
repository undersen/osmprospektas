package Api.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import Api.model.ProspektumGetModel;

/**
 * Created by UnderSen on 30-03-16.
 */
public class ProspektumList {

    @Expose
    private List<ProspektumGetModel> prospektas = new ArrayList<ProspektumGetModel>();

    public ProspektumList() {
    }

    public ProspektumList(List<ProspektumGetModel> prospektas) {
        this.prospektas = prospektas;
    }

    public List<ProspektumGetModel> getProspektas() {
        return prospektas;
    }

    public void setProspektas(List<ProspektumGetModel> prospektas) {
        this.prospektas = prospektas;
    }

}
