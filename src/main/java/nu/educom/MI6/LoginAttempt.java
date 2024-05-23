package nu.educom.MI6;

import java.time.LocalDateTime;

public class LoginAttempt {
    public int id;
    public int serviceId;
    public LocalDateTime loginStamp;
    public boolean loginSuccess;

    public LoginAttempt(int id, int serviceId, LocalDateTime loginStamp, boolean loginSuccess) {
        this.id = id;
        this.serviceId = serviceId;
        this.loginStamp = loginStamp;
        this.loginSuccess = loginSuccess;
    }

    public int getServiceId() {
        return serviceId;
    }

    public LocalDateTime getLoginStamp() {
        return this.loginStamp;
    }

    public boolean getLoginSuccess() {
        return this.loginSuccess;
    }

    @Override
    public String toString() {
        return "LoginAttempt{" +
                "serviceId=" + serviceId +
                ", loginStamp=" + loginStamp +
                ", loginSuccess=" + loginSuccess + '\'' +
                '}';
    }
}