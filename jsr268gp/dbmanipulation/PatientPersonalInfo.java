package jsr268gp.dbmanipulation;

public class PatientPersonalInfo {
	
    private long patientId;
    private byte[] firstName;
    private byte[] lastName;
    private byte gender;
    private byte[] picture;
    private byte[] dateOfBirth;
    private long nationalId;
    private byte[] email;
    private byte[] address;
    private byte[] phoneNumber;
    private byte[] hashedPassword;
    private byte[] cardExpiringDate;
    private byte[] userPublicKey;
    private byte[] emergencyContactName;
    private byte[] emergencyContactPhone;
    private byte[] occupation;
    private byte[] maritalStatus;
    private byte[] bloodType;
    private byte[] height;
    private byte[] weight;
    private byte[] patientStatus;
    private byte[] sessionKey;
    
    
    // Constructor
    public PatientPersonalInfo(long patientId, byte[] firstName, byte[] lastName, byte gender, byte[] picture, byte[] dateOfBirth, long nationalId, byte[] email, byte[] address, byte[] phoneNumber, byte[] hashedPassword, byte[] cardExpiringDate, byte[] userPublicKey, byte[] emergencyContactName, byte[] emergencyContactPhone, byte[] occupation, byte[] maritalStatus, byte[] bloodType, byte[] height, byte[] weight, byte[] patientStatus, byte[] sessionKey) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.picture = picture;
        this.dateOfBirth = dateOfBirth;
        this.nationalId = nationalId;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.hashedPassword = hashedPassword;
        this.cardExpiringDate = cardExpiringDate;
        this.userPublicKey = userPublicKey;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.occupation = occupation;
        this.maritalStatus = maritalStatus;
        this.bloodType = bloodType;
        this.height = height;
        this.weight = weight;
        this.patientStatus = patientStatus;
        this.sessionKey = sessionKey;
    }
    
    // No Argument Constructor
    public PatientPersonalInfo () {
    	return ; 
    }
    

    // Getters & Setters
    public void setPatientId(long patientId){
    	this.patientId = patientId;
    }
    
    public long getPatientId() {
        return patientId;
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

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
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

    public byte[] getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(byte[] emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public byte[] getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(byte[] emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public byte[] getOccupation() {
        return occupation;
    }

    public void setOccupation(byte[] occupation) {
        this.occupation = occupation;
    }

    public byte[] getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(byte[] maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public byte[] getBloodType() {
        return bloodType;
    }

    public void setBloodType(byte[] bloodType) {
        this.bloodType = bloodType;
    }

    public byte[] getHeight() {
        return height;
    }

    public void setHeight(byte[] height) {
        this.height = height;
    }

    public byte[] getWeight() {
        return weight;
    }

    public void setWeight(byte[] weight) {
        this.weight = weight;
    }

    public byte[] getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(byte[] patientStatus) {
        this.patientStatus = patientStatus;
    }

    public byte[] getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(byte[] sessionKey) {
        this.sessionKey = sessionKey;
    }
}
