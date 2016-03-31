package Api.model;

import com.google.gson.annotations.Expose;

/**
 * Created by UnderSen on 29-03-16.
 */
public class ProspektumPostModel {
    @Expose
    private
    int id;

    @Expose
    private
    int employee_id;

    @Expose
    private
    int company_id;

    @Expose
    private
    String name;

    @Expose
    private
    String region;

    @Expose
    private
    String city;

    @Expose
    private
    String found_minerals;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
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

    public String getFinded_minerals() {
        return found_minerals;
    }

    public void setFinded_minerals(String finded_minerals) {this.found_minerals = finded_minerals;}
}
