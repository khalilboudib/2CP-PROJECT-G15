package jsr268gp.dbmanipulation;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;


public class Patient {
    private long patientId;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private String picture;
    private Date dateOfBirth;
    private String nationalId;
    private String email;
    private String address;
    private String phoneNumber;
    private String chronicDiseases;
    private String hashedPassword;
    private Date cardExpiringDate;
    private byte[] userPublicKey;
    private byte[] serverPrivateKey;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String occupation;
    private String maritalStatus;
    private String allergies;
    private String previousSurgeries;
    private String familyMedicalHistory;
    private String bloodType;
    private BigDecimal height;
    private BigDecimal weight;
    private String disabilities;
    private String socialSecurityNumber;
    
    
    public Patient() {
		return;
	}

    // Constructor
    public Patient(long patientId, String firstName, String lastName, Boolean gender, String picture,
                   Date dateOfBirth, String nationalId, String email, String address, String phoneNumber,
                   String chronicDiseases, String hashedPassword, Date cardExpiringDate,
                   byte[] userPublicKey, byte[] serverPrivateKey, String emergencyContactName,
                   String emergencyContactPhone, String occupation, String maritalStatus,
                   String allergies, String previousSurgeries, String familyMedicalHistory,
                   String bloodType, BigDecimal height, BigDecimal weight, String disabilities,
                   String socialSecurityNumber) {
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
        this.chronicDiseases = chronicDiseases;
        this.hashedPassword = hashedPassword;
        LocalDate today = LocalDate.now();
        LocalDate twoYearsLater = today.plusYears(2);
        this.cardExpiringDate = Date.valueOf(twoYearsLater);
        this.userPublicKey = userPublicKey;
        this.serverPrivateKey = serverPrivateKey;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.occupation = occupation;
        this.maritalStatus = maritalStatus;
        this.allergies = allergies;
        this.previousSurgeries = previousSurgeries;
        this.familyMedicalHistory = familyMedicalHistory;
        this.bloodType = bloodType;
        this.height = height;
        this.weight = weight;
        this.disabilities = disabilities;
        this.socialSecurityNumber = socialSecurityNumber;
    }
    
    public void printPatient() {
    	System.out.println(firstName + " " + lastName);
    }
    
    // Getters and Setters
    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
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

    public String getChronicDiseases() {
        return chronicDiseases;
    }

    public void setChronicDiseases(String chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getPreviousSurgeries() {
        return previousSurgeries;
    }

    public void setPreviousSurgeries(String previousSurgeries) {
        this.previousSurgeries = previousSurgeries;
    }

    public String getFamilyMedicalHistory() {
        return familyMedicalHistory;
    }

    public void setFamilyMedicalHistory(String familyMedicalHistory) {
        this.familyMedicalHistory = familyMedicalHistory;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(String disabilities) {
        this.disabilities = disabilities;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }
}

