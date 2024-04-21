package jsr268gp.dbmanipulation;

import java.math.BigDecimal;
import java.sql.Date;

public class Session {
    private long sessionId;
    private long patientId;
    private long doctorId;
    private Date sessionDate;
    private int sessionDuration;
    private String sessionNotes;
    private String diagnosis;
    private String treatmentPlan;
    private Boolean isThereScans;
    private BigDecimal sessionFee;

    // Constructor
    public Session(long patientId, long doctorId, Date sessionDate, int sessionDuration,
                   String sessionNotes, String diagnosis, String treatmentPlan,
                   Boolean isThereScans, BigDecimal sessionFee) {
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

    // Getters and Setters
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

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public int getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(int sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public String getSessionNotes() {
        return sessionNotes;
    }

    public void setSessionNotes(String sessionNotes) {
        this.sessionNotes = sessionNotes;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public Boolean getIsThereScans() {
        return isThereScans;
    }

    public void setIsThereScans(Boolean isThereScans) {
        this.isThereScans = isThereScans;
    }

    public BigDecimal getSessionFee() {
        return sessionFee;
    }

    public void setSessionFee(BigDecimal sessionFee) {
        this.sessionFee = sessionFee;
    }
}

