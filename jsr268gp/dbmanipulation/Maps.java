package jsr268gp.dbmanipulation;

public class Maps {
	private static final String[] wilayas = {"Adrar","Chlef","Laghouat","Oum El Bouaghi","Batna","Bejaia","Biskra","Bechar","Blida","Bouira","Tamanrasset","Tebessa","Tlemcen","Tiaret","Tizi Ouzou","Alger","Djelfa","Jijel","Setif","Saida","Skikda","Sidi Bel Abbes","Annaba","Guelma","Constantine","Medea","Mostaganem","M'Sila","Mascara","Ouargla","Oran","El Bayadh","Illizi","Bordj Bou Arreridj","Boumerdès","El Tarf","Tindouf","Tissemsilt","El Oued","Khenchela","Souk Ahras","Tipaza","Mila","Ain Defla","Naama","Ain Temouchent","Ghardaia","Relizane","Timimoun","Bordj Badji Mokhtar","Beni Abbes","Ouled Djellal","In Salah","In Guezzam","Touggourt","Djanet","El M'Ghair","El Menia"};

	// SQL query to insert an admin into the database
    public static final String INSERT_ADMIN_SQL = "INSERT INTO ADMINS (ADMIN_ID, FIRST_NAME, LAST_NAME, " +
            "EMAIL, PHONE_NUMBER, ADDRESS, HASHED_CODEPIN, DATE_OF_BIRTH, NATIONAL_ID, GENDER, " +
            "EMERGENCY_CONTACT_NAME, EMERGENCY_CONTACT_PHONE, CARD_EXPIRING_DATE, USER_PUBLIC_KEY, " +
            "ADMIN_STATUS, SESSION_KEY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    // SQL query to update admin status in the database
    public static final String UPDATE_ADMIN_STATUS_SQL = "UPDATE ADMINS SET ADMIN_STATUS = ? WHERE ADMIN_ID = ?";
    
    // SQL query to search admin by ID
    public static final String SEARCH_ADMIN_BY_ID_SQL = "SELECT * FROM ADMINS WHERE ADMIN_ID = ?";

    // SQL query to search admin by national card number
    public static final String SEARCH_ADMIN_BY_NATIONAL_CARD_SQL = "SELECT * FROM ADMINS WHERE NATIONAL_ID = ?";
	
	// SQL query to update admin
    public static final String UPDATE_ADMIN_FIELDS = "UPDATE admins SET first_name = ?, last_name = ?, email = ?, phone_number = ?, address = ?, " +
            "hashed_codepin = ?, date_of_birth = ? , gender = ?, emergency_contact_name = ?, " +
            "emergency_contact_phone = ?, card_expiring_date = ?, user_public_key = ?, admin_status = ?, session_key = ? " +
            "WHERE admin_id = ?";
    
    
    public static final String INSERT_DOCTOR_SQL = "INSERT INTO DOCTORS (DOCTOR_ID, FIRST_NAME, LAST_NAME, GENDER, PICTURE, " +
            "DATE_OF_BIRTH, NATIONAL_ID, ABOUT, EMAIL, ADDRESS, PHONE_NUMBER, HASHED_CODEPIN, CLINIQUE_ID, CARD_EXPIRING_DATE, " +
            "USER_PUBLIC_KEY, DOCTOR_STATUS, SESSION_KEY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_DOCTOR_FIELDS = "UPDATE DOCTORS SET FIRST_NAME = ?, LAST_NAME = ?, GENDER = ?, PICTURE = ?, " +
            "DATE_OF_BIRTH = ?, ABOUT = ?, EMAIL = ?, ADDRESS = ?, PHONE_NUMBER = ?, HASHED_CODEPIN = ?, " +
            "CLINIQUE_ID = ?, CARD_EXPIRING_DATE = ?, USER_PUBLIC_KEY = ?, DOCTOR_STATUS = ?, SESSION_KEY = ? WHERE DOCTOR_ID = ?";

    public static final String SEARCH_DOCTOR_BY_ID_SQL = "SELECT * FROM DOCTORS WHERE DOCTOR_ID = ?";

    public static final String SEARCH_DOCTOR_BY_NATIONAL_ID_SQL = "SELECT * FROM DOCTORS WHERE NATIONAL_ID = ?";

    public static final String UPDATE_DOCTOR_STATUS_SQL = "UPDATE DOCTORS SET DOCTOR_STATUS = ? WHERE DOCTOR_ID = ?";
    
 // INSERT statement for inserting a new patient
    public static final String INSERT_PATIENT_SQL = "INSERT INTO patients_personal_info " +
            "(PATIENT_ID, FIRST_NAME, LAST_NAME, GENDER, PICTURE, DATE_OF_BIRTH, NATIONAL_ID, EMAIL, " +
            "ADDRESS, PHONE_NUMBER, HASHED_PASSWORD, CARD_EXPIRING_DATE, USER_PUBLIC_KEY, EMERGENCY_CONTACT_NAME, " +
            "EMERGENCY_CONTACT_PHONE, OCCUPATION, MARITAL_STATUS, BLOOD_TYPE, HEIGHT, WEIGHT, PATIENT_STATUS, SESSION_KEY) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // UPDATE statement for updating patient fields
    public static final String UPDATE_PATIENT_FIELDS = "UPDATE patients_personal_info SET " +
            "FIRST_NAME = ?, LAST_NAME = ?, GENDER = ?, PICTURE = ?, DATE_OF_BIRTH = ?, EMAIL = ?, " +
            "ADDRESS = ?, PHONE_NUMBER = ?, HASHED_PASSWORD = ?, CARD_EXPIRING_DATE = ?, USER_PUBLIC_KEY = ?, " +
            "EMERGENCY_CONTACT_NAME = ?, EMERGENCY_CONTACT_PHONE = ?, OCCUPATION = ?, MARITAL_STATUS = ?, " +
            "BLOOD_TYPE = ?, HEIGHT = ?, WEIGHT = ?, PATIENT_STATUS = ?, SESSION_KEY = ? WHERE PATIENT_ID = ?";

    // SELECT statement for searching a patient by their ID
    public static final String SEARCH_PATIENT_BY_ID_SQL = "SELECT * FROM patients_personal_info WHERE PATIENT_ID = ?";

    // SELECT statement for searching a patient by their national ID
    public static final String SEARCH_PATIENT_BY_NATIONAL_ID_SQL = "SELECT * FROM patients_personal_info WHERE NATIONAL_ID = ?";

    // UPDATE statement for updating patient status
    public static final String UPDATE_PATIENT_STATUS_SQL = "UPDATE patients_personal_info SET " +
            "PATIENT_STATUS = ? WHERE PATIENT_ID = ?";
    
    
    public static final String INSERT_PATIENT_MEDICAL_RECORD_SQL = "INSERT INTO patients_medical_records " +
            "(PATIENT_ID, CHRONIC_DISEASES, ALLERGIES, PREVIOUS_SURGERIES, FAMILY_MEDICAL_HISTORY, DISABILITIES, SOCIAL_SECURITY_NUMBER) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String SEARCH_PATIENT_MEDICAL_RECORD_BY_ID_SQL = "SELECT * FROM patients_medical_records " +
            "WHERE PATIENT_ID = ?";

    public static final String UPDATE_PATIENT_MEDICAL_RECORD_SQL = "UPDATE patients_medical_records SET " +
            "CHRONIC_DISEASES = ?, ALLERGIES = ?, PREVIOUS_SURGERIES = ?, FAMILY_MEDICAL_HISTORY = ?, " +
            "DISABILITIES = ?, SOCIAL_SECURITY_NUMBER = ? " +
            "WHERE PATIENT_ID = ?";

    
    
 // SQL statement to insert a session into the database
    public static final String INSERT_SESSION_SQL = "INSERT INTO SESSIONS (PATIENT_ID, DOCTOR_ID, SESSION_DATE, " +
            "SESSION_DURATION, SESSION_NOTES, DIAGNOSIS, TREATMENT_PLAN, IS_THERE_SCANS, SESSION_FEE) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // SQL statement to search for a session by session ID
    public static final String SEARCH_SESSION_BY_ID_SQL = "SELECT * FROM SESSIONS WHERE SESSION_ID = ?";
    
    
 // SQL statement to search for sessions by patient ID
    public static final String SEARCH_SESSIONS_BY_PATIENT_ID_SQL = "SELECT * FROM SESSIONS WHERE PATIENT_ID = ?";

    // SQL statement to search for sessions by doctor ID
    public static final String SEARCH_SESSIONS_BY_DOCTOR_ID_SQL = "SELECT * FROM SESSIONS WHERE DOCTOR_ID = ?";


    // SQL statement to update a session in the database
    public static final String UPDATE_SESSION_SQL = "UPDATE SESSIONS SET " +
            "SESSION_NOTES = ?, DIAGNOSIS = ?, TREATMENT_PLAN = ?, " +
            "IS_THERE_SCANS = ? WHERE SESSION_ID = ?";

    
    public static final String SEARCH_SCAN_BY_ID_SQL = "SELECT * FROM SCANS WHERE SCAN_ID = ?" ;
    
    
    public static final String SEARCH_SCAN_BY_PATIENT_ID = "SELECT * FROM SCANS WHERE PATIENT_ID = ?";
    
    public static final String SEARCH_SCAN_BY_SESSION_ID = "SELECT * FROM SCANS WHERE SESSION_ID = ?";

	public static final String INSERT_SCAN_SQL = "INSERT INTO SCANS (PATIENT_ID, SESSION_ID, SCAN_DESCRIPTION, DATE_OF_SCAN, SCAN_RESULTS, DOCTORS_COMMENTS, RADIOLOGIST_NAME, RADIOLOGIST_CONTACT_INFO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String UPDATE_SCAN_SQL = "UPDATE SCANS SET " +
                    "SCAN_DESCRIPTION = ?" +
                    "SCAN_RESULTS = ?, DOCTORS_COMMENTS = ?, RADIOLOGIST_NAME = ?, RADIOLOGIST_CONTACT_INFO = ? " +
                    "WHERE SCAN_ID = ?";

	public static final String SEARCH_CLINIQUE_BY_ID = "SELECT * FROM CLINIQUES WHERE CLINIQUE_ID = ?";

	public static final String SEARCH_CLINIQUE_BY_WILAYA = "SELECT * FROM CLINIQUES WHERE WILAYA = ?";

	public static final String INSERT_CLINIQUE_SQL = "INSERT INTO CLINIQUES (CLINIQUE_NAME, WILAYA, LOCATION, CONTACT_PERSON, CONTACT_PERSON_EMAIL, CONTACT_PERSON_PHONE) VALUES (?, ?, ?, ?, ?, ?)";

    public static String getwilaya(int wilayaNumber) {
		if ((wilayaNumber <= 58) && (wilayaNumber >=0)){
			return wilayas[wilayaNumber-1];
		}
		return null;
	}
	

}
