package application.net;

import java.time.LocalDate;

public class Doctor extends Person {

	private Clinique clinique;
	private String doctorStatus;
	
	public Doctor(String firstName, String familyName, Gender gender,
			LocalDate dateOfBirth, long nationalId, String about, String email,
			String adresse, String phoneNumber, LocalDate cardExpiringDate,
			Clinique clinique, String doctorStatus,String picture) {
		super(firstName, familyName, gender, dateOfBirth, nationalId, about,
				email, adresse, phoneNumber, cardExpiringDate,picture);
		this.clinique = clinique;
		this.doctorStatus = doctorStatus;
	}

	public Clinique getClinique() {
		return clinique;
	}

	public void setClinique(Clinique clinique) {
		this.clinique = clinique;
	}

	public String getDoctorStatus() {
		return doctorStatus;
	}

	public void setDoctorStatus(String doctorStatus) {
		this.doctorStatus = doctorStatus;
	}
	
	
	
}
