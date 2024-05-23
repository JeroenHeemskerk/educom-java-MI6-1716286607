package nu.educom.MI6;

import java.time.LocalDate;

public class Agent {
    public int id;
    public int serviceId;
    public boolean retired;
    public boolean licence;
    public LocalDate licenceValid;
    public String passphrase;

    public Agent(int id, int serviceId, boolean retired, boolean licence, LocalDate licenceValid, String passphrase) {
        this.id = id;
        this.serviceId = serviceId;
        this.retired = retired;
        this.licence = licence;
        this.licenceValid = licenceValid;
        this.passphrase = passphrase;
    }

    public int getServiceId() {
        return serviceId;
    }

    public boolean getRetired() {
        return this.retired;
    }

    public boolean getLicence() {
        return this.licence;
    }

    public LocalDate getLicenceValid() {
        return this.licenceValid;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "serviceId=" + serviceId +
                ", licenceToKill=" + licence +
                ", licenceValid=" + licenceValid +
                ", retired=" + retired + '\'' +
                '}';
    }
}
