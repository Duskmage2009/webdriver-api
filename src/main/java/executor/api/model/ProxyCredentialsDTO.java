package executor.api.model;

import java.util.Objects;

public class ProxyCredentialsDTO {
    private String user;
    private String password;

    public ProxyCredentialsDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public ProxyCredentialsDTO(){}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProxyCredentialsDTO that = (ProxyCredentialsDTO) o;

        if (!Objects.equals(user, that.user)) return false;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
