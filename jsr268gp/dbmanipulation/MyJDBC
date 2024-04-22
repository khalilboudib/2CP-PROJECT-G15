package jsr268gp.dbmanipulation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyJDBC {
    // JDBC URL for MySQL database connection
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/health_care_system";
    private static final String USERNAME = "root"; // Username for database connection
    private static final String PASSWORD = "cp2_project"; // Password for database connection

    // Establish a connection to the database
    public static Connection connectToDataBase() throws SQLException, ClassNotFoundException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Rethrow the exception to notify the caller about the failure
            throw e;
        } catch (SQLException e) {
            // Rethrow the exception to notify the caller about the failure
            throw e;
        }
    }
    // Close database connection
    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw e;
            }
        }
    }
    
 // Method to close database resources
    private static void closeResources(Connection connection, PreparedStatement statement) throws SQLException {
        SQLException exception = null;
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                exception = e;
            }
        }
        if (connection != null) {
            try {
                closeConnection(connection);
            } catch (SQLException e) {
                exception = e;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }
    
    
 // Method to close database resources
    private static void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        SQLException exception = null;
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                exception = e;
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                exception = e;
            }
        }
        if (connection != null) {
            try {
                closeConnection(connection);
            } catch (SQLException e) {
                exception = e;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }


    
    
 // Add a patient to the database
    public static void addPatient(Patient patient) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Get connection to the database
            connection = connectToDataBase();

            String sqlQuery = "INSERT INTO PATIENTS (FIRST_NAME, LAST_NAME, GENDER, PICTURE, DATE_OF_BIRTH, NATIONAL_ID, EMAIL, ADDRESS, PHONE_NUMBER, CHRONIC_DISEASES, HASHED_PASSWORD, CARD_EXPIRING_DATE, USER_PUBLIC_KEY, SERVER_PRIVATE_KEY, EMERGENCY_CONTACT_NAME, EMERGENCY_CONTACT_PHONE, OCCUPATION, MARITAL_STATUS, ALLERGIES, PREVIOUS_SURGERIES, FAMILY_MEDICAL_HISTORY, BLOOD_TYPE, HEIGHT, WEIGHT, DISABILITIES, SOCIAL_SECURITY_NUMBER, PATIENT_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sqlQuery);

            // Set the statement parameters for the patient data
            setPatientStatement(statement, patient);

            // Execute the update to add the patient to the database
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in the finally block
            closeResources(connection, statement);
        }
    }


    // Set the statement parameters for adding a patient
    private static void setPatientStatement(PreparedStatement statement, Patient patient) throws SQLException {
        try {
            // Set each parameter of the prepared statement with patient data
        	statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getLastName());
            statement.setByte(3, (byte)(patient.getGender() ? 1 : 0));
            statement.setString(4, patient.getPicture());
            statement.setDate(5, patient.getDateOfBirth());
            statement.setString(6, patient.getNationalId());
            statement.setString(7, patient.getEmail());
            statement.setString(8, patient.getAddress());
            statement.setString(9, patient.getPhoneNumber());
            statement.setString(10, patient.getChronicDiseases());
            statement.setString(11, patient.getHashedPassword());
            statement.setDate(12, patient.getCardExpiringDate());
            statement.setBytes(13, patient.getUserPublicKey());
            statement.setBytes(14, patient.getServerPrivateKey());
            statement.setString(15, patient.getEmergencyContactName());
            statement.setString(16, patient.getEmergencyContactPhone());
            statement.setString(17, patient.getOccupation());
            statement.setString(18, patient.getMaritalStatus());
            statement.setString(19, patient.getAllergies());
            statement.setString(20, patient.getPreviousSurgeries());
            statement.setString(21, patient.getFamilyMedicalHistory());
            statement.setString(22, patient.getBloodType());
            statement.setBigDecimal(23, patient.getHeight());
            statement.setBigDecimal(24, patient.getWeight());
            statement.setString(25, patient.getDisabilities());
            statement.setString(26, patient.getSocialSecurityNumber());
            statement.setLong(27, patient.getPatientId());
        } catch (SQLException e) {
            // Rethrow the exception to propagate it
            throw e;
        }
    }
    
    
 // Add a doctor to the database
    public static void addDoctor(Doctor doctor) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Get connection to the database
            connection = connectToDataBase();

            String sqlQuery = "INSERT INTO DOCTORS (DOCTOR_ID, FIRST_NAME, LAST_NAME, GENDER, PICTURE, DATE_OF_BIRTH, NATIONAL_ID, ABOUT, EMAIL, ADDRESS, PHONE_NUMBER, HASHED_PASSWORD, CLINIQUE_ID, CARD_EXPIRING_DATE, USER_PUBLIC_KEY, SERVER_PRIVATE_KEY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sqlQuery);

            // Set the statement parameters for the doctor data
            setDoctorStatement(statement, doctor);

            // Execute the update to add the doctor to the database
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in the finally block
            closeResources(connection, statement);
        }
    }


    // Set the statement parameters for adding a doctor
    private static void setDoctorStatement(PreparedStatement statement, Doctor doctor) throws SQLException {
        try {
            // Set each parameter of the prepared statement with doctor data
            statement.setLong(1, doctor.getDoctorId());
            statement.setString(2, doctor.getFirstName());
            statement.setString(3, doctor.getLastName());
            statement.setBoolean(4, doctor.getGender());
            statement.setString(5, doctor.getPicture());
            statement.setDate(6, doctor.getDateOfBirth());
            statement.setString(7, doctor.getNationalId());
            statement.setString(8, doctor.getAbout());
            statement.setString(9, doctor.getEmail());
            statement.setString(10, doctor.getAddress());
            statement.setString(11, doctor.getPhoneNumber());
            statement.setString(12, doctor.getHashedPassword());
            statement.setInt(13, doctor.getCliniqueId());
            statement.setDate(14, doctor.getCardExpiringDate());
            statement.setBytes(15, doctor.getUserPublicKey());
            statement.setBytes(16, doctor.getServerPrivateKey());
        } catch (SQLException e) {
            // Rethrow the exception to propagate it
            throw e;
        }
    }
    
 // Add an admin to the database
    public static void addAdmin(Admin admin) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Get connection to the database
            connection = connectToDataBase();

            String sqlQuery = "INSERT INTO ADMINS (ADMIN_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, ADDRESS, HASHED_PASSWORD, DATE_OF_BIRTH, NATIONAL_ID, GENDER, EMERGENCY_CONTACT_NAME, EMERGENCY_CONTACT_PHONE, CARD_EXPIRING_DATE, USER_PUBLIC_KEY, SERVER_PRIVATE_KEY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sqlQuery);

            // Set the statement parameters for the admin data
            setAdminStatement(statement, admin);

            // Execute the update to add the admin to the database
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in the finally block
            closeResources(connection, statement);
        }
    }


    // Set the statement parameters for adding an admin
    private static void setAdminStatement(PreparedStatement statement, Admin admin) throws SQLException {
        try {
            // Set each parameter of the prepared statement with admin data
            statement.setLong(1, admin.getAdminId());
            statement.setString(2, admin.getFirstName());
            statement.setString(3, admin.getLastName());
            statement.setString(4, admin.getEmail());
            statement.setString(5, admin.getPhoneNumber());
            statement.setString(6, admin.getAddress());
            statement.setString(7, admin.getHashedPassword());
            statement.setDate(8, admin.getDateOfBirth());
            statement.setString(9, admin.getNationalId());
            statement.setBoolean(10, admin.getGender());
            statement.setString(11, admin.getEmergencyContactName());
            statement.setString(12, admin.getEmergencyContactPhone());
            statement.setDate(13, admin.getCardExpiringDate());
            statement.setBytes(14, admin.getUserPublicKey());
            statement.setBytes(15, admin.getServerPrivateKey());
        } catch (SQLException e) {
            // Rethrow the exception to propagate it
            throw e;
        }
    }
    
    
 // Add a patient to the recycle bin in the database
    public static void addPatientToRecycleBin(Patient patient) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Get connection to the database
            connection = connectToDataBase();

            String sqlQuery = "INSERT INTO PATIENTS_DELETED (FIRST_NAME, LAST_NAME, GENDER, PICTURE, DATE_OF_BIRTH, NATIONAL_ID, EMAIL, ADDRESS, PHONE_NUMBER, CHRONIC_DISEASES, HASHED_PASSWORD, CARD_EXPIRING_DATE, USER_PUBLIC_KEY, SERVER_PRIVATE_KEY, EMERGENCY_CONTACT_NAME, EMERGENCY_CONTACT_PHONE, OCCUPATION, MARITAL_STATUS, ALLERGIES, PREVIOUS_SURGERIES, FAMILY_MEDICAL_HISTORY, BLOOD_TYPE, HEIGHT, WEIGHT, DISABILITIES, SOCIAL_SECURITY_NUMBER, PATIENT_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sqlQuery);

            // Set the statement parameters for the patient data
            setPatientStatement(statement, patient);

            // Execute the update to add the patient to the recycle bin
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in the finally block
            closeResources(connection, statement);
        }
    }

 // Add a doctor to the recycle bin in the database
    public static void addDoctorToRecycleBin(Doctor doctor) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Get connection to the database
            connection = connectToDataBase();

            String sqlQuery = "INSERT INTO DOCTORS_DELETED (DOCTOR_ID, FIRST_NAME, LAST_NAME, GENDER, PICTURE, DATE_OF_BIRTH, NATIONAL_ID, ABOUT, EMAIL, ADDRESS, PHONE_NUMBER, HASHED_PASSWORD, CLINIQUE_ID, CARD_EXPIRING_DATE, USER_PUBLIC_KEY, SERVER_PRIVATE_KEY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sqlQuery);

            // Set the statement parameters for the doctor data
            setDoctorStatement(statement, doctor);

            // Execute the update to add the doctor to the recycle bin
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in the finally block
            closeResources(connection, statement);
        }
    }

    
 // Add an admin to the recycle bin in the database
    public static void addAdminToRecyleBin(Admin admin) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Get connection to the database
            connection = connectToDataBase();

            String sqlQuery = "INSERT INTO ADMINS_DELETED (ADMIN_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, ADDRESS, HASHED_PASSWORD, DATE_OF_BIRTH, NATIONAL_ID, GENDER, EMERGENCY_CONTACT_NAME, EMERGENCY_CONTACT_PHONE, CARD_EXPIRING_DATE, USER_PUBLIC_KEY, SERVER_PRIVATE_KEY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sqlQuery);

            // Set the statement parameters for the admin data
            setAdminStatement(statement, admin);

            // Execute the update to add the admin to the recycle bin
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in the finally block
            closeResources(connection, statement);
        }
    }

    
    
 // Delete a patient from the database
    public static void deletePatient(Patient patient) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Get connection to the database
            connection = connectToDataBase();

            String sqlQuery = "DELETE FROM PATIENTS WHERE PATIENT_ID = ?";
            statement = connection.prepareStatement(sqlQuery);

            // Set the placeholder to patient ID
            statement.setLong(1, patient.getPatientId());

            // Execute the update to delete the patient from the database
            statement.executeUpdate();

            // Add patient to the table of deleted patients
            addPatientToRecycleBin(patient);
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in the finally block
            closeResources(connection, statement);
        }
    }

    
 // Delete a doctor from the database
    public static void deleteDoctor(Doctor doctor) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Get connection to the database
            connection = connectToDataBase();

            String sqlQuery = "DELETE FROM DOCTORS WHERE DOCTOR_ID = ?";
            statement = connection.prepareStatement(sqlQuery);

            // Set the placeholder to doctor ID
            statement.setLong(1, doctor.getDoctorId());

            // Execute the update to delete the doctor from the database
            statement.executeUpdate();

            // Add doctor to the table of deleted doctors
            addDoctorToRecycleBin(doctor);
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in the finally block
            closeResources(connection, statement);
        }
    }



 // Delete an admin from the database
    public static void deleteAdmin(Admin admin) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Get connection to the database
            connection = connectToDataBase();

            String sqlQuery = "DELETE FROM ADMINS WHERE ADMIN_ID = ?";
            statement = connection.prepareStatement(sqlQuery);

            // Set the placeholder to admin ID
            statement.setLong(1, admin.getAdminId());

            // Execute the update to delete the admin from the database
            statement.executeUpdate();

            // Add admin to the table of deleted admins
            addAdminToRecyleBin(admin);
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in the finally block
            closeResources(connection, statement);
        }
    }

    
    
    public static Patient searchPatientById(long patientId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;

        try {
            connection = connectToDataBase();
            statement1 = connection.prepareStatement("SELECT * FROM PATIENTS WHERE PATIENT_ID = ?");
            statement2 = connection.prepareStatement("SELECT * FROM PATIENTS_DELETED WHERE PATIENT_ID = ?");

            statement1.setLong(1, patientId);
            resultSet1 = statement1.executeQuery();
            if (resultSet1.next()) {
                return extractPatientFromResultSet(resultSet1);
            }

            // If not found in active patients table, check in deleted patients table
            statement2.setLong(1, patientId);
            resultSet2 = statement2.executeQuery();
            if (resultSet2.next()) {
                return extractPatientFromResultSet(resultSet2);
            }

            // If not found in deleted patients table as well, return null
            return null;
        } catch (SQLException e) {
            throw new SQLException("Error searching for patient with ID " + patientId, e);
        } finally {
            // Close resources using the closeResources method
            closeResources(connection, statement1, resultSet1);
            closeResources(null, statement2, resultSet2); // Pass null for connection as it's already closed
        }
    }



    
    
    public static Boolean DoesPatientExist(long patientId) throws ClassNotFoundException, SQLException{
    	return (searchPatientById(patientId)!=null);
    }
    
    
    public static List<Patient> searchPatientByFirstName(String firstName) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase();
            String sqlQuery = "SELECT * FROM PATIENTS WHERE FIRST_NAME = ?";
            List<Patient> patients = new ArrayList<>();

            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, firstName);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                patients.add(extractPatientFromResultSet(resultSet));
            }

            return patients;
        } catch (SQLException e) {
            throw new SQLException("Error searching for patients by first name: " + firstName, e);
        } finally {
            // Close resources using the closeResources method
            closeResources(connection, statement, resultSet);
        }
    }

    
    
    public static List<Patient> searchPatientByLastName(String lastName) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase();
            String sqlQuery = "SELECT * FROM PATIENTS WHERE LAST_NAME = ?";
            List<Patient> patients = new ArrayList<>();

            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, lastName);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                patients.add(extractPatientFromResultSet(resultSet));
            }

            return patients;
        } catch (SQLException e) {
            throw new SQLException("Error searching for patients by last name: " + lastName, e);
        } finally {
            // Close resources using the closeResources method
            closeResources(connection, statement, resultSet);
        }
    }

    
    
    public static List<Patient> searchPatientByFullName(String firstName, String lastName) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase();
            String sqlQuery = "SELECT * FROM PATIENTS WHERE FIRST_NAME = ? AND LAST_NAME = ?";
            List<Patient> patients = new ArrayList<>();

            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                patients.add(extractPatientFromResultSet(resultSet));
            }

            return patients;
        } catch (SQLException e) {
            throw new SQLException("Error searching for patients by full name: " + firstName + " " + lastName, e);
        } finally {
            // Close resources using the closeResources method
            closeResources(connection, statement, resultSet);
        }
    }

    
    private static Patient extractPatientFromResultSet(ResultSet resultSet) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(resultSet.getLong("PATIENT_ID"));
        patient.setFirstName(resultSet.getString("FIRST_NAME"));
        patient.setLastName(resultSet.getString("LAST_NAME"));
        patient.setGender(resultSet.getBoolean("GENDER"));
        patient.setPicture(resultSet.getString("PICTURE"));
        patient.setDateOfBirth(resultSet.getDate("DATE_OF_BIRTH"));
        patient.setNationalId(resultSet.getString("NATIONAL_ID"));
        patient.setEmail(resultSet.getString("EMAIL"));
        patient.setAddress(resultSet.getString("ADDRESS"));
        patient.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
        patient.setChronicDiseases(resultSet.getString("CHRONIC_DISEASES"));
        patient.setHashedPassword(resultSet.getString("HASHED_PASSWORD"));
        patient.setCardExpiringDate(resultSet.getDate("CARD_EXPIRING_DATE"));
        patient.setUserPublicKey(resultSet.getBytes("USER_PUBLIC_KEY"));
        patient.setServerPrivateKey(resultSet.getBytes("SERVER_PRIVATE_KEY"));
        patient.setEmergencyContactName(resultSet.getString("EMERGENCY_CONTACT_NAME"));
        patient.setEmergencyContactPhone(resultSet.getString("EMERGENCY_CONTACT_PHONE"));
        patient.setOccupation(resultSet.getString("OCCUPATION"));
        patient.setMaritalStatus(resultSet.getString("MARITAL_STATUS"));
        patient.setAllergies(resultSet.getString("ALLERGIES"));
        patient.setPreviousSurgeries(resultSet.getString("PREVIOUS_SURGERIES"));
        patient.setFamilyMedicalHistory(resultSet.getString("FAMILY_MEDICAL_HISTORY"));
        patient.setBloodType(resultSet.getString("BLOOD_TYPE"));
        patient.setHeight(resultSet.getBigDecimal("HEIGHT"));
        patient.setWeight(resultSet.getBigDecimal("WEIGHT"));
        patient.setDisabilities(resultSet.getString("DISABILITIES"));
        patient.setSocialSecurityNumber(resultSet.getString("SOCIAL_SECURITY_NUMBER"));
        return patient;
    }
    
    
    public static Doctor searchDoctorById(long doctorId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;

        try {
            connection = connectToDataBase();
            statement1 = connection.prepareStatement("SELECT * FROM DOCTORS WHERE DOCTOR_ID = ?");
            statement2 = connection.prepareStatement("SELECT * FROM DOCTORS_DELETED WHERE DOCTOR_ID = ?");

            statement1.setLong(1, doctorId);
            resultSet1 = statement1.executeQuery();
            if (resultSet1.next()) {
                return extractDoctorFromResultSet(resultSet1);
            }

            // If not found in active doctors table, check in deleted doctors table
            statement2.setLong(1, doctorId);
            resultSet2 = statement2.executeQuery();
            if (resultSet2.next()) {
                return extractDoctorFromResultSet(resultSet2);
            }

            // If not found in deleted doctors table as well, return null
            return null;
        } catch (SQLException e) {
            throw new SQLException("Error searching for doctor with ID " + doctorId, e);
        } finally {
            // Close resources using the closeResources method
            closeResources(connection, statement1, resultSet1);
            closeResources(null, statement2, resultSet2); // Pass null for connection as it's already closed
        }
    }

    
    public static Boolean DoesDoctorExist(long doctorId) throws ClassNotFoundException, SQLException{
    	return (searchDoctorById(doctorId)!=null);
    }


    
    public static List<Doctor> searchDoctorByFirstName(String firstName) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Doctor> doctors = new ArrayList<>();

        try {
            connection = connectToDataBase();
            String sqlQuery = "SELECT * FROM DOCTORS WHERE FIRST_NAME = ?";
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, firstName);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Extract doctor data from the result set and add to the list
                doctors.add(extractDoctorFromResultSet(resultSet));
            }

            return doctors;
        } catch (SQLException e) {
            // Rethrow the exception to propagate it
            throw new SQLException("Error searching doctors by first name: " + e.getMessage(), e);
        } finally {
            // Close resources using the closeResources method
            closeResources(connection, statement, resultSet);
        }
    }


    
    public static List<Doctor> searchDoctorByLastName(String lastName) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Doctor> doctors = new ArrayList<>();

        try {
            connection = connectToDataBase();
            String sqlQuery = "SELECT * FROM DOCTORS WHERE LAST_NAME = ?";
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, lastName);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Extract doctor data from the result set and add to the list
                doctors.add(extractDoctorFromResultSet(resultSet));
            }

            return doctors;
        } catch (SQLException e) {
            // Rethrow the exception to propagate it
            throw new SQLException("Error searching doctors by last name: " + e.getMessage(), e);
        } finally {
            // Close resources using the closeResources method
            closeResources(connection, statement, resultSet);
        }
    }


    
    public static List<Doctor> searchDoctorByFullName(String firstName, String lastName) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Doctor> doctors = new ArrayList<>();

        try {
            connection = connectToDataBase();
            String sqlQuery = "SELECT * FROM DOCTORS WHERE FIRST_NAME = ? AND LAST_NAME = ?";
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Extract doctor data from the result set and add to the list
                doctors.add(extractDoctorFromResultSet(resultSet));
            }

            return doctors;
        } catch (SQLException e) {
            // Rethrow the exception to propagate it
            throw new SQLException("Error searching doctors by full name: " + e.getMessage(), e);
        } finally {
            // Close resources using the closeResources method
            closeResources(connection, statement, resultSet);
        }
    }


    
    
    private static Doctor extractDoctorFromResultSet(ResultSet resultSet) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(resultSet.getLong("DOCTOR_ID"));
        doctor.setFirstName(resultSet.getString("FIRST_NAME"));
        doctor.setLastName(resultSet.getString("LAST_NAME"));
        doctor.setGender(resultSet.getBoolean("GENDER"));
        doctor.setPicture(resultSet.getString("PICTURE"));
        doctor.setDateOfBirth(resultSet.getDate("DATE_OF_BIRTH"));
        doctor.setNationalId(resultSet.getString("NATIONAL_ID"));
        doctor.setAbout(resultSet.getString("ABOUT"));
        doctor.setEmail(resultSet.getString("EMAIL"));
        doctor.setAddress(resultSet.getString("ADDRESS"));
        doctor.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
        doctor.setHashedPassword(resultSet.getString("HASHED_PASSWORD"));
        doctor.setCliniqueId(resultSet.getInt("CLINIQUE_ID"));
        doctor.setCardExpiringDate(resultSet.getDate("CARD_EXPIRING_DATE"));
        doctor.setUserPublicKey(resultSet.getBytes("USER_PUBLIC_KEY"));
        doctor.setServerPrivateKey(resultSet.getBytes("SERVER_PRIVATE_KEY"));
        return doctor;
    }
    
    
    public static Admin searchAdminById(long adminId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;

        try {
            connection = connectToDataBase();
            statement1 = connection.prepareStatement("SELECT * FROM ADMINS WHERE ADMIN_ID = ?");
            statement2 = connection.prepareStatement("SELECT * FROM ADMINS_DELETED WHERE ADMIN_ID = ?");

            statement1.setLong(1, adminId);
            resultSet1 = statement1.executeQuery();
            if (resultSet1.next()) {
                return extractAdminFromResultSet(resultSet1);
            }

            // If not found in active admins table, check in deleted admins table
            statement2.setLong(1, adminId);
            resultSet2 = statement2.executeQuery();
            if (resultSet2.next()) {
                return extractAdminFromResultSet(resultSet2);
            }

            // If not found in deleted admins table as well, return null
            return null;
        } catch (SQLException e) {
            throw new SQLException("Error searching for admin with ID " + adminId, e);
        } finally {
            // Close resources using the closeResources method
            closeResources(connection, statement1, resultSet1);
            closeResources(null, statement2, resultSet2); // Pass null for connection as it's already closed
        }
    }

    
    public static Boolean DoesAdminExist(long adminId) throws ClassNotFoundException, SQLException{
    	return (searchAdminById(adminId)!=null);
    }


    
    
    public static List<Admin> searchAdminByFirstName(String firstName) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase();
            
            // Create SQL query to search for admins by first name
            String sqlQuery = "SELECT * FROM ADMINS WHERE FIRST_NAME = ?";
            List<Admin> admins = new ArrayList<>();
            
            // Prepare statement and execute query
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, firstName);
            resultSet = statement.executeQuery();
            
            // Iterate over the result set and add admins to the list
            while (resultSet.next()) {
                Admin admin = extractAdminFromResultSet(resultSet);
                admins.add(admin);
            }
            
            // Close connection and return the list of admins
            closeConnection(connection);
            return admins;
        } catch (SQLException e) {
            // If a SQL exception occurs during search, throw it
            throw e;
        } finally {
            // Close resources in finally block
            closeResources(connection, statement, resultSet);
        }
    }


    
    public static List<Admin> searchAdminByLastName(String lastName) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase();
            
            // Create SQL query to search for admins by last name
            String sqlQuery = "SELECT * FROM ADMINS WHERE LAST_NAME = ?";
            List<Admin> admins = new ArrayList<>();
            
            // Prepare statement and execute query
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, lastName);
            resultSet = statement.executeQuery();
            
            // Iterate over the result set and add admins to the list
            while (resultSet.next()) {
                Admin admin = extractAdminFromResultSet(resultSet);
                admins.add(admin);
            }
            
            // Close connection and return the list of admins
            closeConnection(connection);
            return admins;
        } catch (SQLException e) {
            // If a SQL exception occurs during search, throw it
            throw e;
        } finally {
            // Close resources in finally block
            closeResources(connection, statement, resultSet);
        }
    }


    
    public static List<Admin> searchAdminByFullName(String firstName, String lastName) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase();
            
            // Create SQL query to search for admins by full name
            String sqlQuery = "SELECT * FROM ADMINS WHERE FIRST_NAME = ? AND LAST_NAME = ?";
            List<Admin> admins = new ArrayList<>();
            
            // Prepare statement and execute query
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();
            
            // Iterate over the result set and add admins to the list
            while (resultSet.next()) {
                Admin admin = extractAdminFromResultSet(resultSet);
                admins.add(admin);
            }
            
            // Close connection and return the list of admins
            closeConnection(connection);
            return admins;
        } catch (SQLException e) {
            // If a SQL exception occurs during search, throw it
            throw e;
        } finally {
            // Close resources in finally block
            closeResources(connection, statement, resultSet);
        }
    }


    
    private static Admin extractAdminFromResultSet(ResultSet resultSet) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(resultSet.getLong("ADMIN_ID"));
        admin.setFirstName(resultSet.getString("FIRST_NAME"));
        admin.setLastName(resultSet.getString("LAST_NAME"));
        admin.setEmail(resultSet.getString("EMAIL"));
        admin.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
        admin.setAddress(resultSet.getString("ADDRESS"));
        admin.setHashedPassword(resultSet.getString("HASHED_PASSWORD"));
        admin.setDateOfBirth(resultSet.getDate("DATE_OF_BIRTH"));
        admin.setNationalId(resultSet.getString("NATIONAL_ID"));
        admin.setGender(resultSet.getBoolean("GENDER"));
        admin.setEmergencyContactName(resultSet.getString("EMERGENCY_CONTACT_NAME"));
        admin.setEmergencyContactPhone(resultSet.getString("EMERGENCY_CONTACT_PHONE"));
        admin.setCardExpiringDate(resultSet.getDate("CARD_EXPIRING_DATE"));
        admin.setUserPublicKey(resultSet.getBytes("USER_PUBLIC_KEY"));
        admin.setServerPrivateKey(resultSet.getBytes("SERVER_PRIVATE_KEY"));
        return admin;
    }
    
    
    public static Patient[] getPatients(int numberUsersPerPage, long pageNumber) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase();

            // Calculate the offset based on the page number and number of users per page
            long offset = (pageNumber - 1) * numberUsersPerPage;

            // SQL query to retrieve patients with pagination
            String sqlQuery = "SELECT * FROM PATIENTS LIMIT ? OFFSET ?";
            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, numberUsersPerPage);
            statement.setLong(2, offset);
            resultSet = statement.executeQuery();

            // Create an array to store patients
            Patient[] patients = new Patient[numberUsersPerPage];

            // Iterate over the result set and populate the array with patients
            int index = 0;
            while (resultSet.next()) {
                patients[index++] = extractPatientFromResultSet(resultSet);
            }

            return patients;
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in finally block
            closeResources(connection, statement, resultSet);
        }
    }
    
    
    public static Doctor[] getDoctors(int numberUsersPerPage, long pageNumber) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase();

            // Calculate the offset based on the page number and number of users per page
            long offset = (pageNumber - 1) * numberUsersPerPage;

            // SQL query to retrieve doctors with pagination
            String sqlQuery = "SELECT * FROM DOCTORS LIMIT ? OFFSET ?";
            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, numberUsersPerPage);
            statement.setLong(2, offset);
            resultSet = statement.executeQuery();

            // Create an array to store doctors
            Doctor[] doctors = new Doctor[numberUsersPerPage];

            // Iterate over the result set and populate the array with doctors
            int index = 0;
            while (resultSet.next()) {
                doctors[index++] = extractDoctorFromResultSet(resultSet);
            }

            return doctors;
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in finally block
            closeResources(connection, statement, resultSet);
        }
    }

    
    public static Admin[] getAdmins(int numberUsersPerPage, long pageNumber) throws SQLException, ClassNotFoundException {
        
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase();

            // Calculate the offset based on the page number and number of users per page
            long offset = (pageNumber - 1) * numberUsersPerPage;

            // SQL query to retrieve admins with pagination
            String sqlQuery = "SELECT * FROM ADMINS LIMIT ? OFFSET ?";
            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, numberUsersPerPage);
            statement.setLong(2, offset);
            resultSet = statement.executeQuery();

            // Create an array to store admins
            Admin[] admins = new Admin[numberUsersPerPage];

            // Iterate over the result set and populate the array with admins
            int index = 0;
            while (resultSet.next()) {
                admins[index++] = extractAdminFromResultSet(resultSet);
            }

            return admins;
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in finally block
            closeResources(connection, statement, resultSet);
        }
    }

    
 // Helper method to get the total count of patients
 
	private static int getTotalPatientsCount(Connection connection) throws SQLException {
        PreparedStatement countStatement = null;
        ResultSet countResultSet = null;
        try {
            String countQuery = "SELECT COUNT(*) AS total FROM PATIENTS";
            countStatement = connection.prepareStatement(countQuery);
            countResultSet = countStatement.executeQuery();
            if (countResultSet.next()) {
                return countResultSet.getInt("total");
            }
            return 0;
        } finally {
            closeResources(null, countStatement, countResultSet); // Connection is already closed
        }
    }
    
    public static int getNumberOfPatients() throws SQLException, ClassNotFoundException {
    	Connection connection = null ;
    	try {
    		connection = connectToDataBase();
    		return getTotalPatientsCount(connection);
    	} catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in finally block
            closeConnection(connection);
        }
    }
    
    
	private static int getTotalDoctorsCount(Connection connection) throws SQLException {
        PreparedStatement countStatement = null;
        ResultSet countResultSet = null;
        try {
            String countQuery = "SELECT COUNT(*) AS total FROM DOCTORS";
            countStatement = connection.prepareStatement(countQuery);
            countResultSet = countStatement.executeQuery();
            if (countResultSet.next()) {
                return countResultSet.getInt("total");
            }
            return 0;
        } finally {
            closeResources(null, countStatement, countResultSet); // Connection is already closed
        }
    }
	
	
	public static int getNumberOfDoctors() throws SQLException, ClassNotFoundException {
    	Connection connection = null ;
    	try {
    		connection = connectToDataBase();
    		return getTotalDoctorsCount(connection);
    	} catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in finally block
            closeConnection(connection);
        }
    }

    
	private static int getTotalAdminsCount(Connection connection) throws SQLException {
        PreparedStatement countStatement = null;
        ResultSet countResultSet = null;
        try {
            String countQuery = "SELECT COUNT(*) AS total FROM ADMINS";
            countStatement = connection.prepareStatement(countQuery);
            countResultSet = countStatement.executeQuery();
            if (countResultSet.next()) {
                return countResultSet.getInt("total");
            }
            return 0;
        } finally {
            closeResources(null, countStatement, countResultSet); // Connection is already closed
        }
    }
	
	
	public static int getNumberOfAdmins() throws SQLException, ClassNotFoundException {
    	Connection connection = null ;
    	try {
    		connection = connectToDataBase();
    		return getTotalAdminsCount(connection);
    	} catch (SQLException | ClassNotFoundException e) {
            // Rethrow the exception to propagate it
            throw e;
        } finally {
            // Close resources in finally block
            closeConnection(connection);
        }
    }
    
    
   
}

