package jsr268gp.dbmanipulation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * MyJDBC class provides methods for database connection, closing resources, and database operations related to Admin entities.
 */
public class MyJDBC {
    // JDBC URL for MySQL database connection
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/health_care_system";

    // Username for database connection
    private static final String USERNAME = "root";

    // Password for database connection
    private static final String PASSWORD = "cp2_project";

    /**
     * Establishes a connection to the database.
     * @return Connection object representing the established database connection
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static Connection connectToDataBase() throws SQLException, ClassNotFoundException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Get a connection to the database using JDBC URL, username, and password
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            // Rethrow the exception to notify the caller about the driver loading or connection failure
            throw e;
        }
    }

    /**
     * Closes a database connection.
     * @param connection Connection object to be closed
     * @throws SQLException if a database access error occurs
     */
    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            try {
                // Close the connection if it's not null
                connection.close();
            } catch (SQLException e) {
                // Rethrow the exception to notify the caller about the closing failure
                throw e;
            }
        }
    }

    /**
     * Closes database resources in a specific order (statement, then connection).
     * @param connection Connection object to be closed
     * @param statement PreparedStatement object to be closed
     * @throws SQLException if a database access error occurs
     */
    public static void closeResources(Connection connection, PreparedStatement statement) throws SQLException {
        SQLException exception = null;
        if (statement != null) {
            try {
                // Close the statement if it's not null
                statement.close();
            } catch (SQLException e) {
                // Capture the exception if there's one while closing the statement
                exception = e;
            }
        }
        if (connection != null) {
            try {
                // Close the connection if it's not null, using the closeConnection method
                closeConnection(connection);
            } catch (SQLException e) {
                // Capture any exception from closeConnection and potentially override the previous one
                if (exception == null) {
                    exception = e;
                }
            }
        }
        // If any exception was captured during closing, re-throw it
        if (exception != null) {
            throw exception;
        }
    }

    /**
     * Closes database resources in a specific order (result set, statement, then connection).
     * @param connection Connection object to be closed
     * @param statement PreparedStatement object to be closed
     * @param resultSet ResultSet object to be closed
     * @throws SQLException if a database access error occurs
     */
    public static void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            try {
                // Close the result set if it's not null
                resultSet.close();
            } catch (SQLException e) {
                // Capture the exception if there's one while closing the result set
                throw e;
            }
        }
        // Close statement and connection using the overloaded closeResources method
        closeResources(connection, statement);
    }

    /**
     * Adds an Admin object to the database.
     * @param admin Admin object to be added
     * @return true if the admin was added successfully, false if the admin already exists
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static Boolean addAdmin(Admin admin) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // Check if admin ID and National ID don't exist before attempting to add
            if ((!doesAdminIdExist(admin.getAdminId())) && (!doesAdminNationalIdExist(admin.getNationalId()))) {
                connection = connectToDataBase();
                statement = connection.prepareStatement(Maps.INSERT_ADMIN_SQL);
                setAdminStatement(statement, admin);
                statement.executeUpdate();
                closeResources(connection, statement);
                return true;
            } else {
                // Close resources even if admin already exists
                closeResources(connection, statement);
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Rethrow any exceptions caught during the process
            throw e;
        } finally {
            // Ensure resources are closed even if there are exceptions
            closeResources(connection, statement);
        }
    }

    /**
     * Sets the parameters of a PreparedStatement for adding an Admin object.
     * @param statement PreparedStatement object to set parameters for
     * @param admin Admin object containing values for parameters
     * @throws SQLException if a database access error occurs
     */
    private static void setAdminStatement(PreparedStatement statement, Admin admin) throws SQLException {
        try {
            // Set the values for each parameter in the prepared statement according to the Admin object's properties
            statement.setLong(1, admin.getAdminId());
            statement.setBytes(2, admin.getFirstName()); // Assuming String getters return String objects
            statement.setBytes(3, admin.getLastName());
            statement.setBytes(4, admin.getEmail());
            statement.setBytes(5, admin.getPhoneNumber());
            statement.setBytes(6, admin.getAddress());
            statement.setBytes(7, admin.getHashedCodePin());
            statement.setBytes(8, admin.getDateOfBirth()); // Assuming date of birth is stored as a byte array
            statement.setLong(9, admin.getNationalId());
            statement.setByte(10, admin.getGender());
            statement.setBytes(11, admin.getEmergencyContactName());
            statement.setBytes(12, admin.getEmergencyContactPhone());
            statement.setBytes(13, admin.getCardExpiringDate()); // Assuming card expiring date is stored as a byte array
            statement.setBytes(14, admin.getUserPublicKey());
            statement.setBytes(15, admin.getAdminStatus());
            statement.setBytes(16, admin.getSessionKey());
        } catch (SQLException e) {
            throw e; // Rethrow any SQLException caught while setting parameters
        }
    }

    /**
     * Searches for an admin in the database by admin_id.
     * @param adminId Admin ID to search for
     * @return Admin object if found, null otherwise
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static Admin searchAdminById(long adminId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.SEARCH_ADMIN_BY_ID_SQL);
            statement.setLong(1, adminId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractAdminFromResultSet(resultSet);
            }
            return null; // Admin not found
        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Rethrow any SQLException or ClassNotFoundException caught during search
        } finally {
            closeResources(connection, statement, resultSet); // Ensure resources are closed
        }
    }

    /**
     * Checks if an admin with a specific ID exists.
     * @param adminId Admin ID to check for existence
     * @return true if the admin exists, false otherwise
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     * @throws SQLException if a database access error occurs
     */
    public static Boolean doesAdminIdExist(long adminId) throws ClassNotFoundException, SQLException {
        return (searchAdminById(adminId) != null); // Leverage searchAdminById to check existence
    }

    /**
     * Searches for an admin in the database by national ID.
     * @param nationalId National ID of the admin to search for
     * @return Admin object if found, null otherwise
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static Admin searchAdminByNationalId(long nationalId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.SEARCH_ADMIN_BY_NATIONAL_CARD_SQL);
            statement.setLong(1, nationalId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractAdminFromResultSet(resultSet);
            }
            return null; // Admin not found
        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Rethrow any SQLException or ClassNotFoundException caught during search
        } finally {
            closeResources(connection, statement, resultSet); // Ensure resources are closed
        }
    }

    /**
     * Helper method to check if an admin with a specific national ID exists.
     * @param nationalId National ID to check for existence
     * @return true if an admin with the specified national ID exists, false otherwise
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     * @throws SQLException if a database access error occurs
     */
    public static Boolean doesAdminNationalIdExist(long nationalId) throws ClassNotFoundException, SQLException {
        return (searchAdminByNationalId(nationalId) != null); // Leverage searchAdminByNationalId to check existence
    }

    /**
     * Extracts an Admin object from a ResultSet.
     * @param resultSet ResultSet containing data from a database query
     * @return Admin object extracted from the ResultSet
     * @throws SQLException if a database access error occurs
     */
    private static Admin extractAdminFromResultSet(ResultSet resultSet) throws SQLException {
        try {
            return new Admin(
                    resultSet.getLong("admin_id"),
                    resultSet.getBytes("first_name"),
                    resultSet.getBytes("last_name"),
                    resultSet.getBytes("email"),
                    resultSet.getBytes("phone_number"),
                    resultSet.getBytes("address"),
                    resultSet.getBytes("hashed_codepin"),
                    resultSet.getBytes("date_of_birth"),
                    resultSet.getLong("national_id"),
                    resultSet.getByte("gender"),
                    resultSet.getBytes("emergency_contact_name"),
                    resultSet.getBytes("emergency_contact_phone"),
                    resultSet.getBytes("card_expiring_date"),
                    resultSet.getBytes("user_public_key"),
                    resultSet.getBytes("admin_status"),
                    resultSet.getBytes("session_key")
            );
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Updates an admin in the database.
     * @param admin Admin object with updated values
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static void updateAdmin(Admin admin) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.UPDATE_ADMIN_FIELDS); // Assuming Maps holds SQL queries
            setAdminStatementForUpdate(statement, admin); // Set update parameters using helper method
            statement.executeUpdate(); // Execute the update statement
        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Rethrow any exceptions caught during update
        } finally {
            closeResources(connection, statement); // Ensure resources are closed
        }
    }

    /**
     * Sets the parameters of a PreparedStatement for updating an Admin object.
     * @param statement PreparedStatement object to set parameters for
     * @param admin Admin object containing values for parameters
     * @throws SQLException if a database access error occurs
     */
    private static void setAdminStatementForUpdate(PreparedStatement statement, Admin admin) throws SQLException {
        try {
            // Set the values for each parameter in the prepared statement according to the Admin object's properties
            statement.setLong(15, admin.getAdminId()); // Update statement might set admin ID last
            statement.setBytes(1, admin.getFirstName()); // Assuming String getters return String objects
            statement.setBytes(2, admin.getLastName());
            statement.setBytes(3, admin.getEmail());
            statement.setBytes(4, admin.getPhoneNumber());
            statement.setBytes(5, admin.getAddress());
            statement.setBytes(6, admin.getHashedCodePin());
            statement.setBytes(7, admin.getDateOfBirth()); // Assuming date of birth is stored as a byte array
            statement.setByte(8, admin.getGender());
            statement.setBytes(9, admin.getEmergencyContactName());
            statement.setBytes(10, admin.getEmergencyContactPhone());
            statement.setBytes(11, admin.getCardExpiringDate()); // Assuming card expiring date is stored as a byte array
            statement.setBytes(12, admin.getUserPublicKey());
            statement.setBytes(13, admin.getAdminStatus());
            statement.setBytes(14, admin.getSessionKey());
        } catch (SQLException e) {
            throw e; // Rethrow any SQLException caught while setting parameters
        }
    }

    /**
     * Updates the admin status in the database.
     * @param status Byte array representing the new admin status
     * @param admin Admin object to update
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static void updateAdminStatus(byte[] status, Admin admin) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.UPDATE_ADMIN_STATUS_SQL);
            statement.setBytes(1, status); // Set the new admin status
            statement.setLong(2, admin.getAdminId()); // Specify the admin to update based on ID
            statement.executeUpdate(); // Execute the update statement
        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Rethrow any exceptions caught during update
        } finally {
            closeResources(connection, statement); // Ensure resources are closed
        }
    }

    /**
     * Adds a Doctor object to the database.
     * @param doctor Doctor object to be added
     * @return true if the doctor was added successfully, false if the doctor already exists
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static Boolean addDoctor(Doctor doctor) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            if ((!doesDoctorIdExist(doctor.getDoctorId())) && (!doesDoctorNationalIdExist(doctor.getNationalId()))) {
                connection = connectToDataBase();
                statement = connection.prepareStatement(Maps.INSERT_DOCTOR_SQL);
                setDoctorStatement(statement, doctor);
                statement.executeUpdate();
                closeResources(connection, statement);
                return true;
            } else {
                closeResources(connection, statement);
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement);
        }
    }

    /**
     * Sets the parameters of a PreparedStatement for adding a Doctor object.
     * @param statement PreparedStatement object to set parameters for
     * @param doctor Doctor object containing values for parameters
     * @throws SQLException if a database access error occurs
     */
    private static void setDoctorStatement(PreparedStatement statement, Doctor doctor) throws SQLException {
        try {
            statement.setLong(1, doctor.getDoctorId());
            statement.setBytes(2, doctor.getFirstName());
            statement.setBytes(3, doctor.getLastName());
            statement.setByte(4, doctor.getGender());
            statement.setBytes(5, doctor.getPicture());
            statement.setBytes(6, doctor.getDateOfBirth());
            statement.setLong(7, doctor.getNationalId());
            statement.setBytes(8, doctor.getAbout());
            statement.setBytes(9, doctor.getEmail());
            statement.setBytes(10, doctor.getAddress());
            statement.setBytes(11, doctor.getPhoneNumber());
            statement.setString(12, doctor.getHashedCodePin());
            statement.setInt(13, doctor.getCliniqueId());
            statement.setBytes(14, doctor.getCardExpiringDate());
            statement.setBytes(15, doctor.getUserPublicKey());
            statement.setBytes(16, doctor.getDoctorStatus());
            statement.setBytes(17, doctor.getSessionKey());
        } catch (SQLException e) {
            throw e;
        }
    }

    
    
    /**
     * Updates a Doctor object in the database.
     * @param doctor Doctor object with updated values
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static void updateDoctor(Doctor doctor) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.UPDATE_DOCTOR_FIELDS);
            setDoctorStatementForUpdate(statement, doctor);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement);
        }
    }

    /**
     * Sets the parameters of a PreparedStatement for updating a Doctor object.
     * @param statement PreparedStatement object to set parameters for
     * @param doctor Doctor object containing values for parameters
     * @throws SQLException if a database access error occurs
     */
    private static void setDoctorStatementForUpdate(PreparedStatement statement, Doctor doctor) throws SQLException {
        try {
            statement.setBytes(1, doctor.getFirstName());
            statement.setBytes(2, doctor.getLastName());
            statement.setByte(3, doctor.getGender());
            statement.setBytes(4, doctor.getPicture());
            statement.setBytes(5, doctor.getDateOfBirth());
            statement.setBytes(6, doctor.getAbout());
            statement.setBytes(7, doctor.getEmail());
            statement.setBytes(8, doctor.getAddress());
            statement.setBytes(9, doctor.getPhoneNumber());
            statement.setString(10, doctor.getHashedCodePin());
            statement.setInt(11, doctor.getCliniqueId());
            statement.setBytes(12, doctor.getCardExpiringDate());
            statement.setBytes(13, doctor.getUserPublicKey());
            statement.setBytes(14, doctor.getDoctorStatus());
            statement.setBytes(15, doctor.getSessionKey());
            statement.setLong(16, doctor.getDoctorId()); // Where clause
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Searches for a doctor in the database by doctor ID.
     * @param doctorId Doctor ID to search for
     * @return Doctor object if found, null otherwise
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static Doctor searchDoctorById(long doctorId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.SEARCH_DOCTOR_BY_ID_SQL);
            statement.setLong(1, doctorId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractDoctorFromResultSet(resultSet);
            }
            return null; // Doctor not found
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }

    /**
     * Checks if a doctor with a specific ID exists in the database.
     * @param doctorId Doctor ID to check for existence
     * @return true if the doctor exists, false otherwise
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     * @throws SQLException if a database access error occurs
     */
    public static Boolean doesDoctorIdExist(long doctorId) throws ClassNotFoundException, SQLException{
        return (searchDoctorById(doctorId)!= null);
    }

    /**
     * Searches for a doctor in the database by national ID.
     * @param nationalId National ID of the doctor to search for
     * @return Doctor object if found, null otherwise
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static Doctor searchDoctorByNationalId(long nationalId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.SEARCH_DOCTOR_BY_NATIONAL_ID_SQL);
            statement.setLong(1, nationalId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractDoctorFromResultSet(resultSet);
            }
            return null; // Doctor not found
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }

    /**
     * Checks if a doctor with a specific national ID exists in the database.
     * @param nationalId National ID to check for existence
     * @return true if the doctor exists, false otherwise
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     * @throws SQLException if a database access error occurs
     */
    public static Boolean doesDoctorNationalIdExist(long nationalId) throws ClassNotFoundException, SQLException{
        return (searchDoctorByNationalId(nationalId)!= null);
    }

    /**
     * Extracts a Doctor object from a ResultSet.
     * @param resultSet ResultSet containing data from a database query
     * @return Doctor object extracted from the ResultSet
     * @throws SQLException if a database access error occurs
     */
    private static Doctor extractDoctorFromResultSet(ResultSet resultSet) throws SQLException {
        try {
            return new Doctor(
                    resultSet.getLong("doctor_id"),
                    resultSet.getBytes("first_name"),
                    resultSet.getBytes("last_name"),
                    resultSet.getByte("gender"),
                    resultSet.getBytes("picture"),
                    resultSet.getBytes("date_of_birth"),
                    resultSet.getLong("national_id"),
                    resultSet.getBytes("about"),
                    resultSet.getBytes("email"),
                    resultSet.getBytes("address"),
                    resultSet.getBytes("phone_number"),
                    resultSet.getString("hashed_codepin"),
                    resultSet.getInt("clinique_id"),
                    resultSet.getBytes("card_expiring_date"),
                    resultSet.getBytes("user_public_key"),
                    resultSet.getBytes("doctor_status"),
                    resultSet.getBytes("session_key")
            );
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Updates the status of a doctor in the database.
     * @param status Byte array representing the new status of the doctor
     * @param doctor Doctor object to update
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static void updateDoctorStatus(byte[] status, Doctor doctor) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.UPDATE_DOCTOR_STATUS_SQL);
            statement.setBytes(1, status);
            statement.setLong(2, doctor.getDoctorId());
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement);
        }
    }

    
    /**
     * Adds a new patient to the database.
     * @param patient PatientPersonalInfo object representing the new patient
     * @return true if the patient is added successfully, false if the patient ID or national ID already exists
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static Boolean addPatient(PatientPersonalInfo patient) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            if (!doesPatientIdExist(patient.getPatientId()) && !doesPatientNationalIdExist(patient.getNationalId())) {
                connection = connectToDataBase();
                statement = connection.prepareStatement(Maps.INSERT_PATIENT_SQL);
                setPatientStatement(statement, patient);
                statement.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement);
        }
    }

    /**
     * Sets the parameters of a PreparedStatement for adding a new patient.
     * @param statement PreparedStatement object to set parameters for
     * @param patient PatientPersonalInfo object representing the new patient
     * @throws SQLException if a database access error occurs
     */
    private static void setPatientStatement(PreparedStatement statement, PatientPersonalInfo patient) throws SQLException {
        try {
            statement.setLong(1, patient.getPatientId());
            statement.setBytes(2, patient.getFirstName());
            statement.setBytes(3, patient.getLastName());
            statement.setByte(4, patient.getGender());
            statement.setBytes(5, patient.getPicture());
            statement.setBytes(6, patient.getDateOfBirth());
            statement.setLong(7, patient.getNationalId());
            statement.setBytes(8, patient.getEmail());
            statement.setBytes(9, patient.getAddress());
            statement.setBytes(10, patient.getPhoneNumber());
            statement.setBytes(11, patient.getHashedPassword());
            statement.setBytes(12, patient.getCardExpiringDate());
            statement.setBytes(13, patient.getUserPublicKey());
            statement.setBytes(14, patient.getEmergencyContactName());
            statement.setBytes(15, patient.getEmergencyContactPhone());
            statement.setBytes(16, patient.getOccupation());
            statement.setBytes(17, patient.getMaritalStatus());
            statement.setBytes(18, patient.getBloodType());
            statement.setBytes(19, patient.getHeight());
            statement.setBytes(20, patient.getWeight());
            statement.setBytes(21, patient.getPatientStatus());
            statement.setBytes(22, patient.getSessionKey());
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Searches for a patient in the database by patient ID.
     * @param patientId Patient ID to search for
     * @return PatientPersonalInfo object if found, null otherwise
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static PatientPersonalInfo searchPatientById(long patientId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.SEARCH_PATIENT_BY_ID_SQL);
            statement.setLong(1, patientId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractPatientFromResultSet(resultSet);
            }
            return null; // Patient not found
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }

    /**
     * Searches for a patient in the database by national ID.
     * @param nationalId National ID of the patient to search for
     * @return PatientPersonalInfo object if found, null otherwise
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static PatientPersonalInfo searchPatientByNationalId(long nationalId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.SEARCH_PATIENT_BY_NATIONAL_ID_SQL);
            statement.setLong(1, nationalId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractPatientFromResultSet(resultSet);
            }
            return null; // Patient not found
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }

    /**
     * Extracts a PatientPersonalInfo object from a ResultSet.
     * @param resultSet ResultSet containing data from a database query
     * @return PatientPersonalInfo object extracted from the ResultSet
     * @throws SQLException if a database access error occurs
     */
    private static PatientPersonalInfo extractPatientFromResultSet(ResultSet resultSet) throws SQLException {
        try {
            return new PatientPersonalInfo(
                    resultSet.getLong("PATIENT_ID"),
                    resultSet.getBytes("FIRST_NAME"),
                    resultSet.getBytes("LAST_NAME"),
                    resultSet.getByte("GENDER"),
                    resultSet.getBytes("PICTURE"),
                    resultSet.getBytes("DATE_OF_BIRTH"),
                    resultSet.getLong("NATIONAL_ID"),
                    resultSet.getBytes("EMAIL"),
                    resultSet.getBytes("ADDRESS"),
                    resultSet.getBytes("PHONE_NUMBER"),
                    resultSet.getBytes("HASHED_PASSWORD"),
                    resultSet.getBytes("CARD_EXPIRING_DATE"),
                    resultSet.getBytes("USER_PUBLIC_KEY"),
                    resultSet.getBytes("EMERGENCY_CONTACT_NAME"),
                    resultSet.getBytes("EMERGENCY_CONTACT_PHONE"),
                    resultSet.getBytes("OCCUPATION"),
                    resultSet.getBytes("MARITAL_STATUS"),
                    resultSet.getBytes("BLOOD_TYPE"),
                    resultSet.getBytes("HEIGHT"),
                    resultSet.getBytes("WEIGHT"),
                    resultSet.getBytes("PATIENT_STATUS"),
                    resultSet.getBytes("SESSION_KEY")
            );
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Checks if a patient ID exists in the database.
     * @param patientId Patient ID to check for existence
     * @return true if the patient ID exists, false otherwise
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    private static boolean doesPatientIdExist(long patientId) throws SQLException, ClassNotFoundException {
        return (searchPatientById(patientId)!= null);
    }

    /**
     * Checks if a patient's national ID exists in the database.
     * @param nationalId National ID to check for existence
     * @return true if the national ID exists, false otherwise
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    private static boolean doesPatientNationalIdExist(long nationalId) throws SQLException, ClassNotFoundException {
        return (searchPatientByNationalId(nationalId)!= null);
    }

    /**
     * Updates the information of a patient in the database.
     * @param patient PatientPersonalInfo object representing the updated patient information
     * @return true

     if the patient information is updated successfully, false if the patient does not exist
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static boolean updatePatientInfo(PatientPersonalInfo patient) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            if (doesPatientIdExist(patient.getPatientId())) {
                connection = connectToDataBase();
                statement = connection.prepareStatement(Maps.UPDATE_PATIENT_FIELDS);
                setPatientStatementForUpdate(statement, patient);
                statement.executeUpdate();
                return true; // Updated successfully
            } else {
                return false; // Patient does not exist
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement);
        }
    }

    /**
     * Sets the parameters of a PreparedStatement for updating patient information.
     * @param statement PreparedStatement object to set parameters for
     * @param patient PatientPersonalInfo object representing the updated patient information
     * @throws SQLException if a database access error occurs
     */
    private static void setPatientStatementForUpdate(PreparedStatement statement, PatientPersonalInfo patient) throws SQLException {
        try {
            statement.setBytes(1, patient.getFirstName());
            statement.setBytes(2, patient.getLastName());
            statement.setByte(3, patient.getGender());
            statement.setBytes(4, patient.getPicture());
            statement.setBytes(5, patient.getDateOfBirth());
            statement.setBytes(6, patient.getEmail());
            statement.setBytes(7, patient.getAddress());
            statement.setBytes(8, patient.getPhoneNumber());
            statement.setBytes(9, patient.getHashedPassword());
            statement.setBytes(10, patient.getCardExpiringDate());
            statement.setBytes(11, patient.getUserPublicKey());
            statement.setBytes(12, patient.getEmergencyContactName());
            statement.setBytes(13, patient.getEmergencyContactPhone());
            statement.setBytes(14, patient.getOccupation());
            statement.setBytes(15, patient.getMaritalStatus());
            statement.setBytes(16, patient.getBloodType());
            statement.setBytes(17, patient.getHeight());
            statement.setBytes(18, patient.getWeight());
            statement.setBytes(19, patient.getPatientStatus());
            statement.setBytes(20, patient.getSessionKey());
            statement.setLong(21, patient.getPatientId());
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Updates the status of a patient in the database.
     * @param status Byte array representing the new status of the patient
     * @param patient PatientPersonalInfo object to update
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static void updatePatientStatus(byte[] status, PatientPersonalInfo patient ) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.UPDATE_PATIENT_STATUS_SQL);
            statement.setBytes(1, status);
            statement.setLong(2, patient.getPatientId());
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement);
        }
    }
    
    
    
    /**
     * Adds a new patient medical record to the database.
     * @param medicalRecord PatientMedicalRecord object representing the new medical record
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static void addPatientMedicalRecord(PatientMedicalRecord medicalRecord) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.INSERT_PATIENT_MEDICAL_RECORD_SQL);
            setPatientMedicalRecordStatement(statement, medicalRecord);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement);
        }
    }

    /**
     * Retrieves a patient medical record by patient ID.
     * @param patientId Patient ID to retrieve medical record for
     * @return PatientMedicalRecord object if found, null otherwise
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static PatientMedicalRecord getPatientMedicalRecordById(long patientId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.SEARCH_PATIENT_MEDICAL_RECORD_BY_ID_SQL);
            statement.setLong(1, patientId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractPatientMedicalRecordFromResultSet(resultSet);
            }
            return null; // Medical record not found
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }

    /**
     * Updates a patient medical record in the database.
     * @param medicalRecord PatientMedicalRecord object representing the updated medical record
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static void updatePatientMedicalRecord(PatientMedicalRecord medicalRecord) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.UPDATE_PATIENT_MEDICAL_RECORD_SQL);
            setPatientMedicalRecordStatementForUpdate(statement, medicalRecord);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement);
        }
    }

    /**
     * Extracts a PatientMedicalRecord object from a ResultSet.
     * @param resultSet ResultSet containing data from a database query
     * @return PatientMedicalRecord object extracted from the ResultSet
     * @throws SQLException if a database access error occurs
     */
    private static PatientMedicalRecord extractPatientMedicalRecordFromResultSet(ResultSet resultSet) throws SQLException {
        try {
            return new PatientMedicalRecord(
                resultSet.getLong("PATIENT_ID"),
                resultSet.getBytes("CHRONIC_DISEASES"),
                resultSet.getBytes("ALLERGIES"),
                resultSet.getBytes("PREVIOUS_SURGERIES"),
                resultSet.getBytes("FAMILY_MEDICAL_HISTORY"),
                resultSet.getBytes("DISABILITIES"),
                resultSet.getBytes("SOCIAL_SECURITY_NUMBER")
            );
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Sets the parameters of a PreparedStatement for adding a patient medical record.
     * @param statement PreparedStatement object to set parameters for
     * @param medicalRecord PatientMedicalRecord object representing the medical record to add
     * @throws SQLException if a database access error occurs
     */
    private static void setPatientMedicalRecordStatement(PreparedStatement statement, PatientMedicalRecord medicalRecord) throws SQLException {
        try {
            statement.setLong(1, medicalRecord.getPatientId());
            statement.setBytes(2, medicalRecord.getChronicDiseases());
            statement.setBytes(3, medicalRecord.getAllergies());
            statement.setBytes(4, medicalRecord.getPreviousSurgeries());
            statement.setBytes(5, medicalRecord.getFamilyMedicalHistory());
            statement.setBytes(6, medicalRecord.getDisabilities());
            statement.setBytes(7, medicalRecord.getSocialSecurityNumber());
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Sets the parameters of a PreparedStatement for updating a patient medical record.
     * @param statement PreparedStatement object to set parameters for
     * @param medicalRecord PatientMedicalRecord object representing the medical record to update
     * @throws SQLException if a database access error occurs
     */
    private static void setPatientMedicalRecordStatementForUpdate(PreparedStatement statement, PatientMedicalRecord medicalRecord) throws SQLException {
        try {
            statement.setBytes(1, medicalRecord.getChronicDiseases());
            statement.setBytes(2, medicalRecord.getAllergies());
            statement.setBytes(3, medicalRecord.getPreviousSurgeries());
            statement.setBytes(4, medicalRecord.getFamilyMedicalHistory());
            statement.setBytes(5, medicalRecord.getDisabilities());
            statement.setBytes(6, medicalRecord.getSocialSecurityNumber());
            statement.setLong(7, medicalRecord.getPatientId()); // Where clause
        } catch (SQLException e) {
            throw e;
        }
    }

    
    
    /**
     * Adds a new session to the database.
     * @param session Session object representing the new session
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public void addSession(Session session) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.INSERT_SESSION_SQL);

            statement.setLong(1, session.getPatientId());
            statement.setLong(2, session.getDoctorId());
            statement.setBytes(3, session.getSessionDate());
            statement.setBytes(4, session.getSessionDuration());
            statement.setBytes(5, session.getSessionNotes());
            statement.setBytes(6, session.getDiagnosis());
            statement.setBytes(7, session.getTreatmentPlan());
            statement.setByte(8, session.getIsThereScans());
            statement.setBytes(9, session.getSessionFee());

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            closeResources(connection, statement);
        }
    }
    
    
    public static List<Session> searchSessionByDoctorId(long doctorId) throws SQLException, ClassNotFoundException {
        List<Session> sessions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // Connect to the database
            connection = connectToDataBase();   
            statement = connection.prepareStatement(Maps.SEARCH_SESSIONS_BY_DOCTOR_ID_SQL);

            // Set the doctor ID parameter
            statement.setLong(1, doctorId);

            // Execute the query and get the results
            resultSet = statement.executeQuery();

            // Process results
            while (resultSet.next()) {
                long sessionId = resultSet.getLong("SESSION_ID");
                long patientId = resultSet.getLong("PATIENT_ID");
                byte[] sessionDate = resultSet.getBytes("SESSION_DATE");
                byte[] sessionDuration = resultSet.getBytes("SESSION_DURATION");
                byte[] sessionNotes = resultSet.getBytes("SESSION_NOTES");
                byte[] diagnosis = resultSet.getBytes("DIAGNOSIS");
                byte[] treatmentPlan = resultSet.getBytes("TREATMENT_PLAN");
                byte isThereScans = resultSet.getByte("IS_THERE_SCANS");
                byte[] sessionFee = resultSet.getBytes("SESSION_FEE");

                Session session = new Session(sessionId, patientId, doctorId, sessionDate, sessionDuration, sessionNotes, diagnosis, treatmentPlan, isThereScans, sessionFee);
                sessions.add(session);
            }

            // No sessions found
            if (sessions.isEmpty()) {
                return null;
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return sessions;
    }

    
    
    public static List<Session> searchSessionByPatientId(long patientId) throws SQLException, ClassNotFoundException {
        List<Session> sessions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // Connect to the database
            connection = connectToDataBase();

            statement = connection.prepareStatement(Maps.SEARCH_SESSIONS_BY_PATIENT_ID_SQL);

            // Set the patient ID parameter
            statement.setLong(1, patientId);

            // Execute the query and get the results
            resultSet = statement.executeQuery();

            // Process results
            while (resultSet.next()) {
                long sessionId = resultSet.getLong("SESSION_ID");
                long doctorId = resultSet.getLong("DOCTOR_ID");
                byte[] sessionDate = resultSet.getBytes("SESSION_DATE");
                byte[] sessionDuration = resultSet.getBytes("SESSION_DURATION");
                byte[] sessionNotes = resultSet.getBytes("SESSION_NOTES");
                byte[] diagnosis = resultSet.getBytes("DIAGNOSIS");
                byte[] treatmentPlan = resultSet.getBytes("TREATMENT_PLAN");
                byte isThereScans = resultSet.getByte("IS_THERE_SCANS");
                byte[] sessionFee = resultSet.getBytes("SESSION_FEE");

                Session session = new Session(sessionId, patientId, doctorId, sessionDate, sessionDuration, sessionNotes, diagnosis, treatmentPlan, isThereScans, sessionFee);
                sessions.add(session);
            }

            // No sessions found
            if (sessions.isEmpty()) {
                return null;
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return sessions;
    }
    
    
    
    public static List<Session> searchSessionById(long sessionId) throws SQLException, ClassNotFoundException {
        List<Session> sessions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // Connect to the database
            connection = connectToDataBase();

            statement = connection.prepareStatement(Maps.SEARCH_SESSION_BY_ID_SQL);

            // Set the patient ID parameter
            statement.setLong(1, sessionId);

            // Execute the query and get the results
            resultSet = statement.executeQuery();

            // Process results
            while (resultSet.next()) {
                long patientId = resultSet.getLong("PATIENT_ID");
                long doctorId = resultSet.getLong("DOCTOR_ID");
                byte[] sessionDate = resultSet.getBytes("SESSION_DATE");
                byte[] sessionDuration = resultSet.getBytes("SESSION_DURATION");
                byte[] sessionNotes = resultSet.getBytes("SESSION_NOTES");
                byte[] diagnosis = resultSet.getBytes("DIAGNOSIS");
                byte[] treatmentPlan = resultSet.getBytes("TREATMENT_PLAN");
                byte isThereScans = resultSet.getByte("IS_THERE_SCANS");
                byte[] sessionFee = resultSet.getBytes("SESSION_FEE");

                Session session = new Session(sessionId, patientId, doctorId, sessionDate, sessionDuration, sessionNotes, diagnosis, treatmentPlan, isThereScans, sessionFee);
                sessions.add(session);
            }

            // No sessions found
            if (sessions.isEmpty()) {
                return null;
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return sessions;
    }
    
    
    public static void updateSession(Session session) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // Connect to the database
            connection = connectToDataBase();

            // Prepare the update statement
            statement = connection.prepareStatement(Maps.UPDATE_SESSION_SQL);

            // Set update parameters
            statement.setBytes(1, session.getSessionNotes());
            statement.setBytes(2, session.getDiagnosis());
            statement.setBytes(3, session.getTreatmentPlan());
            statement.setByte(4, session.getIsThereScans());
            statement.setLong(5, session.getSessionId());
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement); // No result set used here
        }
    }
    
    
    public static Scan getScanById(long scanId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // Connect to the database
            connection = connectToDataBase();

            // Prepare the SQL statement with a placeholder for scan ID
            statement = connection.prepareStatement(Maps.SEARCH_SCAN_BY_ID_SQL);

            // Set the scan ID parameter
            statement.setLong(1, scanId);

            // Execute the query and get the results
            resultSet = statement.executeQuery();

            // Check if a scan was found
            if (resultSet.next()) {
                long patientId = resultSet.getLong("PATIENT_ID");
                long sessionId = resultSet.getLong("SESSION_ID");
                byte[] scanDescription = resultSet.getBytes("SCAN_DESCRIPTION");
                byte[] dateOfScan = resultSet.getBytes("DATE_OF_SCAN");
                byte[] scanResults = resultSet.getBytes("SCAN_RESULTS");
                byte[] doctorsComments = resultSet.getBytes("DOCTORS_COMMENTS");
                byte[] radiologistName = resultSet.getBytes("RADIOLOGIST_NAME");
                byte[] radiologistContactInfo = resultSet.getBytes("RADIOLOGIST_CONTACT_INFO");
                return new Scan(scanId, patientId, sessionId, scanDescription, dateOfScan, scanResults, doctorsComments, radiologistName, radiologistContactInfo);
            }

            // No scan found
            return null;

        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    
    public static List<Scan> getScansByPatientId(long patientId) throws SQLException, ClassNotFoundException {
        List<Scan> scans = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // Connect to the database
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.SEARCH_SCAN_BY_PATIENT_ID);

            // Set the patient ID parameter
            statement.setLong(1, patientId);

            // Execute the query and get the results
            resultSet = statement.executeQuery();

            // Process results
            while (resultSet.next()) {
                long scanId = resultSet.getLong("SCAN_ID");
                long sessionId = resultSet.getLong("SESSION_ID");
                byte[] scanDescription = resultSet.getBytes("SCAN_DESCRIPTION");
                byte[] dateOfScan = resultSet.getBytes("DATE_OF_SCAN");
                byte[] scanResults = resultSet.getBytes("SCAN_RESULTS");
                byte[] doctorsComments = resultSet.getBytes("DOCTORS_COMMENTS");
                byte[] radiologistName = resultSet.getBytes("RADIOLOGIST_NAME");
                byte[] radiologistContactInfo = resultSet.getBytes("RADIOLOGIST_CONTACT_INFO");
                Scan scan = new Scan(scanId, patientId, sessionId, scanDescription, dateOfScan, scanResults, doctorsComments, radiologistName, radiologistContactInfo);
                scans.add(scan);
            }

            return scans;

        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    
    
    
    public static List<Scan> getScansBySessionId(long patientId) throws SQLException, ClassNotFoundException {
        List<Scan> scans = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // Connect to the database
            connection = connectToDataBase();
            statement = connection.prepareStatement(Maps.SEARCH_SCAN_BY_SESSION_ID);

            // Set the patient ID parameter
            statement.setLong(1, patientId);

            // Execute the query and get the results
            resultSet = statement.executeQuery();

            // Process results
            while (resultSet.next()) {
                long scanId = resultSet.getLong("SCAN_ID");
                long sessionId = resultSet.getLong("PATIENT_ID");
                byte[] scanDescription = resultSet.getBytes("SCAN_DESCRIPTION");
                byte[] dateOfScan = resultSet.getBytes("DATE_OF_SCAN");
                byte[] scanResults = resultSet.getBytes("SCAN_RESULTS");
                byte[] doctorsComments = resultSet.getBytes("DOCTORS_COMMENTS");
                byte[] radiologistName = resultSet.getBytes("RADIOLOGIST_NAME");
                byte[] radiologistContactInfo = resultSet.getBytes("RADIOLOGIST_CONTACT_INFO");
                Scan scan = new Scan(scanId, patientId, sessionId, scanDescription, dateOfScan, scanResults, doctorsComments, radiologistName, radiologistContactInfo);
                scans.add(scan);
            }

            return scans;

        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    
    public static void addScan(Scan scan) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // Connect to the database
            connection = connectToDataBase();

            // Prepare the insert statement
            statement = connection.prepareStatement(Maps.INSERT_SCAN_SQL);

            // Set insert parameters
            statement.setLong(1, scan.getPatientId());
            statement.setLong(2, scan.getSessionId());
            statement.setBytes(3, scan.getScanDescription());
            statement.setBytes(4, scan.getDateOfScan());
            statement.setBytes(5, scan.getScanResults());
            statement.setBytes(6, scan.getDoctorsComments());
            statement.setBytes(7, scan.getRadiologistName());
            statement.setBytes(8, scan.getRadiologistContactInfo());

            // Execute the insert query
            statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement, null); // No result set used here
        }
    }
    
    
    public static void updateScan(Scan scan) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // Connect to the database
            connection = connectToDataBase();

            // Prepare the update statement
            statement = connection.prepareStatement(Maps.UPDATE_SCAN_SQL);

            // Set update parameters
            statement.setBytes(1, scan.getScanDescription());
            statement.setBytes(2, scan.getScanResults());
            statement.setBytes(3, scan.getDoctorsComments());
            statement.setBytes(4, scan.getRadiologistName());
            statement.setBytes(5, scan.getRadiologistContactInfo());
            statement.setLong(6, scan.getScanId());

            // Execute the update query
            statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement, null); // No result set used here
        }
    }
    
    
    public static Clinique getCliniqueById(int cliniqueId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // Connect to the database
            connection = connectToDataBase();

            // Prepare the SQL statement with a placeholder for clinique ID
            statement = connection.prepareStatement(Maps.SEARCH_CLINIQUE_BY_ID);

            // Set the clinique ID parameter
            statement.setInt(1, cliniqueId);

            // Execute the query and get the results
            resultSet = statement.executeQuery();

            // Check if a clinique was found
            if (resultSet.next()) {
                String cliniqueName = resultSet.getString("CLINIQUE_NAME");
                int wilaya = resultSet.getInt("WILAYA");
                String location = resultSet.getString("LOCATION");
                String contactPerson = resultSet.getString("CONTACT_PERSON");
                String contactPersonEmail = resultSet.getString("CONTACT_PERSON_EMAIL");
                String contactPersonPhone = resultSet.getString("CONTACT_PERSON_PHONE");

                return new Clinique(cliniqueId, cliniqueName, wilaya, location, contactPerson, contactPersonEmail, contactPersonPhone);
            }

            // No clinique found
            return null;

        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }

    
    
    public static List<Clinique> getCliniquesByWilaya(int wilaya) throws SQLException, ClassNotFoundException {
        List<Clinique> cliniques = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // Connect to the database
            connection = connectToDataBase();

            // Prepare the SQL statement with a placeholder for wilaya
            statement = connection.prepareStatement(Maps.SEARCH_CLINIQUE_BY_WILAYA);

            // Set the wilaya parameter
            statement.setInt(1, wilaya);

            // Execute the query and get the results
            resultSet = statement.executeQuery();

            // Process results
            while (resultSet.next()) {
                int cliniqueId = resultSet.getInt("CLINIQUE_ID");
                String cliniqueName = resultSet.getString("CLINIQUE_NAME");
                String location = resultSet.getString("LOCATION");
                String contactPerson = resultSet.getString("CONTACT_PERSON");
                String contactPersonEmail = resultSet.getString("CONTACT_PERSON_EMAIL");
                String contactPersonPhone = resultSet.getString("CONTACT_PERSON_PHONE");

                Clinique clinique = new Clinique(cliniqueId, cliniqueName, wilaya, location, contactPerson, contactPersonEmail, contactPersonPhone);
                cliniques.add(clinique);
            }

            return cliniques;

        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }

    
    
    public static void addClinique(Clinique clinique) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // Connect to the database
            connection = connectToDataBase();

            // Prepare the insert statement
            statement = connection.prepareStatement(Maps.INSERT_CLINIQUE_SQL);

            // Set insert parameters
            statement.setString(1, clinique.getCliniqueName());
            statement.setInt(2, clinique.getWilaya());
            statement.setString(3, clinique.getLocation());
            statement.setString(4, clinique.getContactPerson());
            statement.setString(5, clinique.getContactPersonEmail());
            statement.setString(6, clinique.getContactPersonPhone());

            // Execute the insert query
            statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw e; // Re-throw the exception
        } finally {
            closeResources(connection, statement, null); // No result set used here
        }
    }  
}

