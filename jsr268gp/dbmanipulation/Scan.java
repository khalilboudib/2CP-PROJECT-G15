package jsr268gp.dbmanipulation;

import java.sql.Date;

public class Scan {
    private long scanId;
    private long patientId;
    private long sessionId;
    private String scanDescription;
    private Date dateOfScan;
    private String scanResults;
    private String doctorsComments;
    private String radiologistName;
    private String radiologistContactInfo;

    // Constructor
    public Scan(long patientId, long sessionId, String scanDescription, Date dateOfScan,
                String scanResults, String doctorsComments, String radiologistName,
                String radiologistContactInfo) {
        this.patientId = patientId;
        this.sessionId = sessionId;
        this.scanDescription = scanDescription;
        this.dateOfScan = dateOfScan;
        this.scanResults = scanResults;
        this.doctorsComments = doctorsComments;
        this.radiologistName = radiologistName;
        this.radiologistContactInfo = radiologistContactInfo;
    }

    // Getters and Setters
    public long getScanId() {
        return scanId;
    }

    public void setScanId(long scanId) {
        this.scanId = scanId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getScanDescription() {
        return scanDescription;
    }

    public void setScanDescription(String scanDescription) {
        this.scanDescription = scanDescription;
    }

    public Date getDateOfScan() {
        return dateOfScan;
    }

    public void setDateOfScan(Date dateOfScan) {
        this.dateOfScan = dateOfScan;
    }

    public String getScanResults() {
        return scanResults;
    }

    public void setScanResults(String scanResults) {
        this.scanResults = scanResults;
    }

    public String getDoctorsComments() {
        return doctorsComments;
    }

    public void setDoctorsComments(String doctorsComments) {
        this.doctorsComments = doctorsComments;
    }

    public String getRadiologistName() {
        return radiologistName;
    }

    public void setRadiologistName(String radiologistName) {
        this.radiologistName = radiologistName;
    }

    public String getRadiologistContactInfo() {
        return radiologistContactInfo;
    }

    public void setRadiologistContactInfo(String radiologistContactInfo) {
        this.radiologistContactInfo = radiologistContactInfo;
    }

    
}

