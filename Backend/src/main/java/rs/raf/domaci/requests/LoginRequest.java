package rs.raf.domaci.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username is required")
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    private String lozinka;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
}