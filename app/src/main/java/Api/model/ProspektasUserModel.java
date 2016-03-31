package Api.model;

import com.google.gson.annotations.Expose;

/**
 * Created by fabian on 20-12-15.
 */
public class ProspektasUserModel {
    @Expose
    private int id;

    @Expose
    private String email;

    @Expose
    private String first_name;

    @Expose
    private String last_name;

    @Expose
    private String authentication_token;

    @Expose
    private String actable_type;

    @Expose
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAuthentication_token() {
        return authentication_token;
    }

    public void setAuthentication_token(String authentication_token) {
        this.authentication_token = authentication_token;
    }

    public String getActable_type() {
        return actable_type;
    }

    public void setActable_type(String actable_type) {
        this.actable_type = actable_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
