package jsr268gp.dbmanipulation;

public class PatientMedicalRecord {
    private long patientId;
    private byte[] chronicDiseases;
    private byte[] allergies;
    private byte[] previousSurgeries;
    private byte[] familyMedicalHistory;
    private byte[] disabilities;
    private byte[] socialSecurityNumber;

    // Constructor
    public PatientMedicalRecord(long patientId, byte[] chronicDiseases, byte[] allergies, byte[] previousSurgeries,
                                byte[] familyMedicalHistory, byte[] disabilities, byte[] socialSecurityNumber) {
        this.patientId = patientId;
        this.chronicDiseases = chronicDiseases;
        this.allergies = allergies;
        this.previousSurgeries = previousSurgeries;
        this.familyMedicalHistory = familyMedicalHistory;
        this.disabilities = disabilities;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    // Getters and setters
    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public byte[] getChronicDiseases() {
        return chronicDiseases;
    }

    public void setChronicDiseases(byte[] chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public byte[] getAllergies() {
        return allergies;
    }

    public void setAllergies(byte[] allergies) {
        this.allergies = allergies;
    }

    public byte[] getPreviousSurgeries() {
        return previousSurgeries;
    }

    public void setPreviousSurgeries(byte[] previousSurgeries) {
        this.previousSurgeries = previousSurgeries;
    }

    public byte[] getFamilyMedicalHistory() {
        return familyMedicalHistory;
    }

    public void setFamilyMedicalHistory(byte[] familyMedicalHistory) {
        this.familyMedicalHistory = familyMedicalHistory;
    }

    public byte[] getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(byte[] disabilities) {
        this.disabilities = disabilities;
    }

    public byte[] getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(byte[] socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }
}
