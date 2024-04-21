package jsr268gp.dbmanipulation;

public class Clinique {
    private int cliniqueId;
    private String cliniqueName;
    private String location;
    private String contactPerson;
    private String contactPersonEmail;
    private String contactPersonPhone;

    // Constructor
    public Clinique(String cliniqueName, String location, String contactPerson,
                    String contactPersonEmail, String contactPersonPhone) {
        this.cliniqueName = cliniqueName;
        this.location = location;
        this.contactPerson = contactPerson;
        this.contactPersonEmail = contactPersonEmail;
        this.contactPersonPhone = contactPersonPhone;
    }

    // Getters
    public int getCliniqueId() {
        return cliniqueId;
    }

    public String getCliniqueName() {
        return cliniqueName;
    }

    public String getLocation() {
        return location;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    // Setters
    public void setCliniqueId(int cliniqueId) {
        this.cliniqueId = cliniqueId;
    }

    public void setCliniqueName(String cliniqueName) {
        this.cliniqueName = cliniqueName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }
}

