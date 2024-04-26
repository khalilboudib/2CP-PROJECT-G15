package jsr268gp.dbmanipulation;
public class Session {
    private long sessionId;
    private long patientId;
    private long doctorId;
    private byte[] sessionDate;
    private byte[] sessionDuration;
    private byte[] sessionNotes;
    private byte[] diagnosis;
    private byte[] treatmentPlan;
    private byte isThereScans;
    private byte[] sessionFee;

    // Constructor
    public Session(long sessionId, long patientId, long doctorId, byte[] sessionDate, byte[] sessionDuration,
                   byte[] sessionNotes, byte[] diagnosis, byte[] treatmentPlan, byte isThereScans, byte[] sessionFee) {
        this.sessionId = sessionId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.sessionDate = sessionDate;
        this.sessionDuration = sessionDuration;
        this.sessionNotes = sessionNotes;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.isThereScans = isThereScans;
        this.sessionFee = sessionFee;
    }

    // Getters and setters
    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public byte[] getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(byte[] sessionDate) {
        this.sessionDate = sessionDate;
    }

    public byte[] getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(byte[] sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public byte[] getSessionNotes() {
        return sessionNotes;
    }

    public void setSessionNotes(byte[] sessionNotes) {
        this.sessionNotes = sessionNotes;
    }

    public byte[] getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(byte[] diagnosis) {
        this.diagnosis = diagnosis;
    }

    public byte[] getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(byte[] treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public byte getIsThereScans() {
        return isThereScans;
    }

    public void setIsThereScans(byte isThereScans) {
        this.isThereScans = isThereScans;
    }

    public byte[] getSessionFee() {
        return sessionFee;
    }

    public void setSessionFee(byte[] sessionFee) {
        this.sessionFee = sessionFee;
    }
}

