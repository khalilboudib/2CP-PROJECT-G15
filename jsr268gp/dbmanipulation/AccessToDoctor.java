package jsr268gp.dbmanipulation;

import java.time.LocalDateTime;

public class AccessToDoctor {
    private long accessId;
    private long doctorId;
    private LocalDateTime accessDate;
    private int accessDuration;

    // Constructor
    public AccessToDoctor(long accessId, long doctorId, LocalDateTime accessDate, int accessDuration) {
        this.accessId = accessId;
        this.doctorId = doctorId;
        this.accessDate = accessDate;
        this.accessDuration = accessDuration;
    }

    // Getters and setters
    public long getAccessId() {
        return accessId;
    }

    public void setAccessId(long accessId) {
        this.accessId = accessId;
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

    public int getAccessDuration() {
        return accessDuration;
    }

    public void setAccessDuration(int accessDuration) {
        this.accessDuration = accessDuration;
    }
}
