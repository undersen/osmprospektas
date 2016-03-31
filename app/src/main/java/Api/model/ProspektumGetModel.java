package Api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UnderSen on 30-03-16.
 */
public class ProspektumGetModel {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("employee_id")
    @Expose
    public Integer employeeId;
    @SerializedName("company_id")
    @Expose
    public Integer companyId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("region")
    @Expose
    public String region;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("finded_minerals")
    @Expose
    public List<String> findedMinerals = new ArrayList<String>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getFindedMinerals() {
        return findedMinerals;
    }

    public void setFindedMinerals(List<String> findedMinerals) {
        this.findedMinerals = findedMinerals;
    }
}
