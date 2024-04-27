package application.net;

import java.time.LocalDate;

public abstract class Person {
	
	private String firstName;
	private String familyName;
	private Gender gender;
	private LocalDate dateOfBirth;
	private long nationalId;
	private String about;
	private String email;
	private String adresse;
	private String phoneNumber;
	private LocalDate cardExpiringDate;
	private String picture;
	
	
	public Person(String firstName, String familyName, Gender gender,
			LocalDate dateOfBirth, long nationalId, String about, String email,
			String adresse, String phoneNumber, LocalDate cardExpiringDate,String picture) {
		super();
		this.firstName = firstName;
		this.familyName = familyName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.nationalId = nationalId;
		this.about = about;
		this.email = email;
		this.adresse = adresse;
		this.phoneNumber = phoneNumber;
		this.cardExpiringDate = cardExpiringDate;
		this.setPicture(picture);
	}
	
	public Person(){
		
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public long getNationalId() {
		return nationalId;
	}
	public void setNationalId(long nationalId) {
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
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public LocalDate getCardExpiringDate() {
		return cardExpiringDate;
	}
	public void setCardExpiringDate(LocalDate cardExpiringDate) {
		this.cardExpiringDate = cardExpiringDate;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	
}
