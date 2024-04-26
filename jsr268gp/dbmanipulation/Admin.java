package jsr268gp.dbmanipulation;

public class Admin {
    private long adminId;
    private byte[] firstName;
    private byte[] lastName;
    private byte[] email;
    private byte[] phoneNumber;
    private byte[] address;
    private byte[] hashedCodePin;
    private byte[] dateOfBirth;
    private long nationalId;
    private byte gender;
    private byte[] emergencyContactName;
    private byte[] emergencyContactPhone;
    private byte[] cardExpiringDate;
    private byte[] userPublicKey;
    private byte[] adminStatus;
    private byte[] sessionKey;

    
    // Constructor
    public Admin(long adminId, byte[] firstName, byte[] lastName, byte[] email, byte[] phoneNumber, byte[] address,
                 byte[] hashedCodePin, byte[] dateOfBirth, long nationalId, byte gender, byte[] emergencyContactName,
                 byte[] emergencyContactPhone, byte[] cardExpiringDate, byte[] userPublicKey, byte[] adminStatus,
                 byte[] sessionKey) {
        this.adminId = adminId;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setAddress(address);
        this.setHashedCodePin(hashedCodePin);
        this.setDateOfBirth(dateOfBirth);
        this.setNationalId(nationalId);
        this.setGender(gender);
        this.setEmergencyContactName(emergencyContactName);
        this.setEmergencyContactPhone(emergencyContactPhone);
        this.setCardExpiringDate(cardExpiringDate);
        this.setUserPublicKey(userPublicKey);
        this.setAdminStatus(adminStatus);
        this.setSessionKey(sessionKey);
    }

	// Getters and setters
    
    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
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

	public byte[] getEmail() {
		return email;
	}

	public void setEmail(byte[] email) {
		this.email = email;
	}

	public byte[] getHashedCodePin() {
		return hashedCodePin;
	}

	public void setHashedCodePin(byte[] hashedCodePin) {
		this.hashedCodePin = hashedCodePin;
	}

	public byte[] getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(byte[] dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public byte[] getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(byte[] phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
		this.address = address;
	}

	public long getNationalId() {
		return nationalId;
	}

	public void setNationalId(long nationalId) {
		this.nationalId = nationalId;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public byte[] getEmergencyContactPhone() {
		return emergencyContactPhone;
	}

	public void setEmergencyContactPhone(byte[] emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}

	public byte[] getEmergencyContactName() {
		return emergencyContactName;
	}

	public void setEmergencyContactName(byte[] emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
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

	public byte[] getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(byte[] sessionKey) {
		this.sessionKey = sessionKey;
	}

	public byte[] getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(byte[] adminStatus) {
		this.adminStatus = adminStatus;
	}
	
	@Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", firstName=" + new String(firstName) +
                ", lastName=" + new String(lastName) +
                ", email=" + new String(email) +
                ", phoneNumber=" + new String(phoneNumber) +
                ", address=" + new String(address) +
                ", hashedCodePin=" + new String(hashedCodePin) +
                ", dateOfBirth=" + new String(dateOfBirth) +
                ", nationalId=" + nationalId +
                ", gender=" + gender +
                ", emergencyContactName=" + new String(emergencyContactName) +
                ", emergencyContactPhone=" + new String(emergencyContactPhone) +
                ", cardExpiringDate=" + new String(cardExpiringDate) +
                ", userPublicKey=" + new String(userPublicKey) +
                ", adminStatus=" + new String(adminStatus) +
                ", sessionKey=" + new String(sessionKey) +
                '}';
    }
	
}
