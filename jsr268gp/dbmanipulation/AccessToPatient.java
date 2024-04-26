package jsr268gp.dbmanipulation;

import java.time.LocalDateTime;

public class AccessToPatient {
    private long accessId;
    private long patientId;
    private long doctorId;
    private LocalDateTime accessDate;
    private AccessType accessType;
    private int accessDuration;

    // Constructor
    public AccessToPatient(long accessId, long patientId, long doctorId, LocalDateTime accessDate,
                           AccessType accessType, int accessDuration) {
        this.accessId = accessId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.accessDate = accessDate;
        this.accessType = accessType;
        this.accessDuration = accessDuration;
    }

    // Getters and setters
    public long getAccessId() {
        return accessId;
    }

    public void setAccessId(long accessId) {
        this.accessId = accessId;
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

    public LocalDateTime getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(LocalDateTime accessDate) {
        this.accessDate = accessDate;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public int getAccessDuration() {
        return accessDuration;
    }

    public void setAccessDuration(int accessDuration) {
        this.accessDuration = accessDuration;
    }
}
