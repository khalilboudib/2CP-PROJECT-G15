package application.net;

import java.time.LocalDate;

public class Patient extends Person {

	private String emergencyContactName;
    private String emergencyContactPhone;
    private String occupation;
    private String maritalStatus;
    private String bloodType;
    private String height;
    private String weight;
    private String patientStatus;
    
	public Patient(String firstName, String familyName, Gender gender,
			LocalDate dateOfBirth, long nationalId, String about, String email,
			String adresse, String phoneNumber, LocalDate cardExpiringDate,
			String emergencyContactName, String emergencyContactPhone,
			String occupation, String maritalStatus, String bloodType,
			String height, String weight, String patientStatus,String picture) {
		super(firstName, familyName, gender, dateOfBirth, nationalId, about,
				email, adresse, phoneNumber, cardExpiringDate,picture);
		this.emergencyContactName = emergencyContactName;
		this.emergencyContactPhone = emergencyContactPhone;
		this.occupation = occupation;
		this.maritalStatus = maritalStatus;
		this.bloodType = bloodType;
		this.height = height;
		this.weight = weight;
		this.patientStatus = patientStatus;
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

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPatientStatus() {
		return patientStatus;
	}

	public void setPatientStatus(String patientStatus) {
		this.patientStatus = patientStatus;
	}
    
	
    
}
