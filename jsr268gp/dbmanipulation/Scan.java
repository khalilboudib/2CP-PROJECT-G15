package jsr268gp.dbmanipulation;

public class Scan {
    private long scanId;
    private long patientId;
    private long sessionId;
    private byte[] scanDescription;
    private byte[] dateOfScan;
    private byte[] scanResults;
    private byte[] doctorsComments;
    private byte[] radiologistName;
    private byte[] radiologistContactInfo;

    // Constructor
    public Scan(long scanId, long patientId, long sessionId, byte[] scanDescription, byte[] dateOfScan,
                byte[] scanResults, byte[] doctorsComments, byte[] radiologistName, byte[] radiologistContactInfo) {
        this.scanId = scanId;
        this.patientId = patientId;
        this.sessionId = sessionId;
        this.scanDescription = scanDescription;
        this.dateOfScan = dateOfScan;
        this.scanResults = scanResults;
        this.doctorsComments = doctorsComments;
        this.radiologistName = radiologistName;
        this.radiologistContactInfo = radiologistContactInfo;
    }

    // Getters and setters
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

    public byte[] getScanDescription() {
        return scanDescription;
    }

    public void setScanDescription(byte[] scanDescription) {
        this.scanDescription = scanDescription;
    }

    public byte[] getDateOfScan() {
        return dateOfScan;
    }

    public void setDateOfScan(byte[] dateOfScan) {
        this.dateOfScan = dateOfScan;
    }

    public byte[] getScanResults() {
        return scanResults;
    }

    public void setScanResults(byte[] scanResults) {
        this.scanResults = scanResults;
    }

    public byte[] getDoctorsComments() {
        return doctorsComments;
    }

    public void setDoctorsComments(byte[] doctorsComments) {
        this.doctorsComments = doctorsComments;
    }

    public byte[] getRadiologistName() {
        return radiologistName;
    }

    public void setRadiologistName(byte[] radiologistName) {
        this.radiologistName = radiologistName;
    }

    public byte[] getRadiologistContactInfo() {
        return radiologistContactInfo;
    }

    public void setRadiologistContactInfo(byte[] radiologistContactInfo) {
        this.radiologistContactInfo = radiologistContactInfo;
    }
}


