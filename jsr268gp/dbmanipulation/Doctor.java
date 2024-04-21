package jsr268gp.dbmanipulation;

import java.sql.Date;
import java.time.LocalDate;

public class Doctor {
    private long doctorId;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private String picture;
    private Date dateOfBirth;
    private String nationalId;
    private String about;
    private String email;
    private String address;
    private String phoneNumber;
    private String hashedPassword;
    private int cliniqueId;
    private Date cardExpiringDate;
    private byte[] userPublicKey;
    private byte[] serverPrivateKey;
    
    
    
    public Doctor () {
    	return;
    }

    // Constructor
    public Doctor(long doctorId, String firstName, String lastName, Boolean gender, String picture,
                  Date dateOfBirth, String nationalId, String about, String email, String address,
                  String phoneNumber, String hashedPassword, int cliniqueId, Date cardExpiringDate,
                  byte[] userPublicKey, byte[] serverPrivateKey) {
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.picture = picture;
        this.dateOfBirth = dateOfBirth;
        this.nationalId = nationalId;
        this.about = about;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.hashedPassword = hashedPassword;
        this.cliniqueId = cliniqueId;
        LocalDate today = LocalDate.now();
        LocalDate twoYearsLater = today.plusYears(2);
        this.cardExpiringDate = Date.valueOf(twoYearsLater);
        this.userPublicKey = userPublicKey;
        this.serverPrivateKey = serverPrivateKey;
    }

    // Getters and Setters
    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public int getCliniqueId() {
        return cliniqueId;
    }

    public void setCliniqueId(int cliniqueId) {
        this.cliniqueId = cliniqueId;
    }

    public Date getCardExpiringDate() {
        return cardExpiringDate;
    }

    public void setCardExpiringDate(Date cardExpiringDate) {
        this.cardExpiringDate = cardExpiringDate;
    }

    public byte[] getUserPublicKey() {
        return userPublicKey;
    }

    public void setUserPublicKey(byte[] userPublicKey) {
        this.userPublicKey = userPublicKey;
    }

    public byte[] getServerPrivateKey() {
        return serverPrivateKey;
    }

    public void setServerPrivateKey(byte[] serverPrivateKey) {
        this.serverPrivateKey = serverPrivateKey;
    }
    
}

