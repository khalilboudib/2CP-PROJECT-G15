package jsr268gp.dbmanipulation;

import java.sql.Date;
import java.time.LocalDate;

public class Admin {
    private long adminId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String hashedPassword;
    private Date dateOfBirth;
    private String nationalId;
    private Boolean gender;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private Date cardExpiringDate;
    private byte[] userPublicKey;
    private byte[] serverPrivateKey;

    
    public Admin() {
    	return;
    }
    
    
    // Constructor
    public Admin(long adminId, String firstName, String lastName, String email, String phoneNumber,
                 String address, String hashedPassword, Date dateOfBirth, String nationalId, Boolean gender,
                 String emergencyContactName, String emergencyContactPhone,
                 byte[] userPublicKey, byte[] serverPrivateKey) {
        this.adminId = adminId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.hashedPassword = hashedPassword;
        this.dateOfBirth = dateOfBirth;
        this.nationalId = nationalId;
        this.gender = gender;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        LocalDate today = LocalDate.now();
        LocalDate twoYearsLater = today.plusYears(2);
        this.cardExpiringDate = Date.valueOf(twoYearsLater);
        this.userPublicKey = userPublicKey;
        this.serverPrivateKey = serverPrivateKey;
        }
    
    
    public Admin(long adminId, String firstName, String lastName, String email, String phoneNumber,
            String address, String hashedPassword, Date dateOfBirth, String nationalId, Boolean gender,
            byte[] userPublicKey, byte[] serverPrivateKey) {
    	this.adminId = adminId;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.email = email;
    	this.phoneNumber = phoneNumber;
    	this.address = address;
    	this.hashedPassword = hashedPassword;
    	this.dateOfBirth = dateOfBirth;
    	this.nationalId = nationalId;
    	this.gender = gender;
    	LocalDate today = LocalDate.now();
    	LocalDate twoYearsLater = today.plusYears(2);
    	this.cardExpiringDate = Date.valueOf(twoYearsLater);
    	this.userPublicKey = userPublicKey;
    	this.serverPrivateKey = serverPrivateKey;
    	}

    // Getters
    public long getAdminId() {
        return adminId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNationalId() {
        return nationalId;
    }

    public Boolean getGender() {
        return gender;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public Date getCardExpiringDate() {
        return cardExpiringDate;
    }

    public byte[] getUserPublicKey() {
        return userPublicKey;
    }

    public byte[] getServerPrivateKey() {
        return serverPrivateKey;
    }

    // Setters
    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public void setCardExpiringDate(Date cardExpiringDate) {
        this.cardExpiringDate = cardExpiringDate;
    }

    public void setUserPublicKey(byte[] userPublicKey) {
        this.userPublicKey = userPublicKey;
    }

    public void setServerPrivateKey(byte[] serverPrivateKey) {
        this.serverPrivateKey = serverPrivateKey;
    }
}

