package jsr268gp.dbmanipulation;

public class Doctor {
	
	
	private long doctorId;
    private byte[] firstName;
    private byte[] lastName;
    private byte gender;
    private byte[] picture;
    private byte[] dateOfBirth;
    private long nationalId;
    private byte[] about;
    private byte[] email;
    private byte[] address;
    private byte[] phoneNumber;
    private String hashedCodePin;
    private int cliniqueId;
    private byte[] cardExpiringDate;
    private byte[] userPublicKey;
    private byte[] doctorStatus;
    private byte[] sessionKey;

    // Constructor
    public Doctor(long doctorId, byte[] firstName, byte[] lastName, byte gender, byte[] picture, byte[] dateOfBirth,
                  long nationalId, byte[] about, byte[] email, byte[] address, byte[] phoneNumber, String hashedCodePin,
                  int cliniqueId, byte[] cardExpiringDate, byte[] userPublicKey, byte[] doctorStatus, byte[] sessionKey) {
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
        this.hashedCodePin = hashedCodePin;
        this.cliniqueId = cliniqueId;
        this.cardExpiringDate = cardExpiringDate;
        this.userPublicKey = userPublicKey;
        this.doctorStatus = doctorStatus;
        this.sessionKey = sessionKey;
    }

    public Doctor() {
		return ;
	}

	// Getters and setters
    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public byte[] getFirstName() {
        return firstName;
    }

    public void setFirstName(byte[] firstName) {
        this.firstName = firstName;
    }

    public byte[] getLastName() {
        return lastName;
    }

    public void setLastName(byte[] lastName) {
        this.lastName = lastName;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public byte[] getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(byte[] dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getNationalId() {
        return nationalId;
    }

    public void setNationalId(long nationalId) {
        this.nationalId = nationalId;
    }

    public byte[] getAbout() {
        return about;
    }

    public void setAbout(byte[] about) {
        this.about = about;
    }

    public byte[] getEmail() {
        return email;
    }

    public void setEmail(byte[] email) {
        this.email = email;
    }

    public byte[] getAddress() {
        return address;
    }

    public void setAddress(byte[] address) {
        this.address = address;
    }

    public byte[] getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(byte[] phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHashedCodePin() {
        return hashedCodePin;
    }

    public void setHashedCodePin(String hashedCodePin) {
        this.hashedCodePin = hashedCodePin;
    }

    public int getCliniqueId() {
        return cliniqueId;
    }

    public void setCliniqueId(int cliniqueId) {
        this.cliniqueId = cliniqueId;
    }

    public byte[] getCardExpiringDate() {
        return cardExpiringDate;
    }

    public void setCardExpiringDate(byte[] cardExpiringDate) {
        this.cardExpiringDate = cardExpiringDate;
    }

    public byte[] getUserPublicKey() {
        return userPublicKey;
    }

    public void setUserPublicKey(byte[] userPublicKey) {
        this.userPublicKey = userPublicKey;
    }

    public byte[] getDoctorStatus() {
        return doctorStatus;
    }

    public void setDoctorStatus(byte[] doctorStatus) {
        this.doctorStatus = doctorStatus;
    }

    public byte[] getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(byte[] sessionKey) {
        this.sessionKey = sessionKey;
    }
    
}

