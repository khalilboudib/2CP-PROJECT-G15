package application.net;

import java.time.LocalDate;

public class Admin extends Person {

    private String emergencyContactName;
    private String emergencyContactPhone;
    private String adminStatus;
	public Admin(String firstName, String familyName, Gender gender,
			LocalDate dateOfBirth, long nationalId, String about, String email,
			String adresse, String phoneNumber, LocalDate cardExpiringDate,
			String emergencyContactName, String emergencyContactPhone,
			String adminStatus,String picture) {
		super(firstName, familyName, gender, dateOfBirth, nationalId, about,
				email, adresse, phoneNumber, cardExpiringDate,picture);
		this.emergencyContactName = emergencyContactName;
		this.emergencyContactPhone = emergencyContactPhone;
		this.adminStatus = adminStatus;
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
	public String getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}
    
    
    
}
