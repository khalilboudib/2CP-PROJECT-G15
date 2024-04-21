package jsr268gp.dbmanipulation;

import java.sql.Timestamp;

public class Access {
    private long accessId;
    private long patientId;
    private long doctorId;
    private Timestamp accessDate;
    private AccessType accessType;
    private int accessDuration;

    // Constructor
    public Access(long patientId, long doctorId, Timestamp accessDate,
                  AccessType accessType, int accessDuration) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.accessDate = accessDate;
        this.accessType = accessType;
        this.accessDuration = accessDuration;
    }

    // Getters and Setters
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

    public Timestamp getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Timestamp accessDate) {
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

