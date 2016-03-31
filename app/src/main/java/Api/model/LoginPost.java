package Api.model;

import com.google.gson.annotations.Expose;

/**
 * Created by UnderSen on 28-03-16.
 */
public class LoginPost {

    @Expose
    private String email;

    @Expose
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
