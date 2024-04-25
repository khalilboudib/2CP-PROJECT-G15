CREATE DATABASE health_care_system;
USE health_care_system;

-- ALL SENSITIVE DATA IN ENCRYPTED USING AES 128


-- THE ADMINS TABLE
CREATE TABLE ADMINS (
    ADMIN_ID BIGINT PRIMARY KEY,
    FIRST_NAME VARBINARY(32),
    LAST_NAME VARBINARY(32),
    EMAIL VARBINARY(336) UNIQUE,
    PHONE_NUMBER VARBINARY(16), -- FOR BACK UP
    ADDRESS VARBINARY(256),
    HASHED_CODEPIN VARBINARY(72), -- BCrypt
    DATE_OF_BIRTH VARBINARY(16),
    NATIONAL_ID VARBINARY(16),
    GENDER TINYINT,
    EMERGENCY_CONTACT_NAME VARBINARY(64),
    EMERGENCY_CONTACT_PHONE VARBINARY(16),
    CARD_EXPIRING_DATE VARBINARY(16),
    USER_PUBLIC_KEY VARBINARY(272),
    ADMIN_STATUS VARBINARY(16),
    SESSION_KEY varbinary(256),
    INDEX IDX_ADMINS (ADMIN_ID , NATIONAL_ID)
);


-- THE CLINIQUES TABLE
CREATE TABLE CLINIQUES (
    CLINIQUE_ID INT AUTO_INCREMENT PRIMARY KEY,
    CLINIQUE_NAME VARCHAR(255),
    WILAYA INT,
    LOCATION VARCHAR(255),
    CONTACT_PERSON VARCHAR(50),
    CONTACT_PERSON_EMAIL VARCHAR(320) UNIQUE,
    CONTACT_PERSON_PHONE VARCHAR(15),
    INDEX IDX_CLINIQUES (CLINIQUE_ID, CLINIQUE_NAME, WILAYA)
);


-- THE DOCTORS TABLE
CREATE TABLE DOCTORS (
    DOCTOR_ID BIGINT PRIMARY KEY,
    FIRST_NAME VARBINARY(32),
    LAST_NAME VARBINARY(32),
    GENDER TINYINT,
    PICTURE VARBINARY(32),
    DATE_OF_BIRTH VARBINARY(32),
    NATIONAL_ID VARBINARY(16),
    ABOUT VARBINARY(1216),
    EMAIL VARBINARY(336) UNIQUE,
    ADDRESS VARBINARY(256),
    PHONE_NUMBER VARBINARY(16), -- FOR BACK UP
    HASHED_CODEPIN VARCHAR(72), -- BCrypt
    CLINIQUE_ID INT,
    CARD_EXPIRING_DATE VARBINARY(16),
    USER_PUBLIC_KEY VARBINARY(272),
    DOCTOR_STATUS VARBINARY(16),
    SESSION_KEY varbinary(256),
    INDEX IDX_DOCTORS (DOCTOR_ID, CLINIQUE_ID,NATIONAL_ID),
    FOREIGN KEY (CLINIQUE_ID) REFERENCES CLINIQUES(CLINIQUE_ID)
);



-- THE PATIENTS INFOS TABLE
CREATE TABLE patients_personal_info (
    PATIENT_ID BIGINT PRIMARY KEY,
    FIRST_NAME VARBINARY(32),
    LAST_NAME VARBINARY(32),
    GENDER TINYINT,
    PICTURE VARBINARY(32), 
    DATE_OF_BIRTH VARBINARY(16),
    NATIONAL_ID VARBINARY(16),
    EMAIL VARBINARY(336),
    ADDRESS VARBINARY(256),
    PHONE_NUMBER VARBINARY(16),
    HASHED_PASSWORD VARBINARY(72),
    CARD_EXPIRING_DATE VARBINARY(16),
    USER_PUBLIC_KEY VARBINARY(272),
    EMERGENCY_CONTACT_NAME VARBINARY(64),
    EMERGENCY_CONTACT_PHONE VARBINARY(64),
    OCCUPATION VARBINARY(32),
    MARITAL_STATUS VARBINARY(16),
    BLOOD_TYPE VARBINARY(16),
    HEIGHT VARBINARY(16),
    WEIGHT VARBINARY(16),
    PATIENT_STATUS VARBINARY(16),
    SESSION_KEY varbinary(256),
	INDEX IDX_PATIENTS_INFOS (PATIENT_ID, NATIONAL_ID)
);

-- THE PATIENTS MEDICAL RECORDS
CREATE TABLE patients_medical_records (
    PATIENT_ID BIGINT PRIMARY KEY,
    CHRONIC_DISEASES VARBINARY(528),
    ALLERGIES VARBINARY(256),
    PREVIOUS_SURGERIES VARBINARY(528),
    FAMILY_MEDICAL_HISTORY VARBINARY(528),
    DISABILITIES VARBINARY(528),
    SOCIAL_SECURITY_NUMBER VARBINARY(16),
    INDEX IDX_PATIENTS_MR (PATIENT_ID)
);


-- THE SESSIONS TABLE
CREATE TABLE SESSIONS (
    SESSION_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    PATIENT_ID BIGINT,
    DOCTOR_ID BIGINT,
    SESSION_DATE VARBINARY(16),
    SESSION_DURATION VARBINARY(4),
    SESSION_NOTES VARBINARY(2056),
    DIAGNOSIS VARBINARY(2056), 
    TREATMENT_PLAN VARBINARY(2056),
    IS_THERE_SCANS TINYINT,
    SESSION_FEE VARBINARY(16),
    INDEX IDX_SESSION (SESSION_ID , PATIENT_ID , DOCTOR_ID),
    FOREIGN KEY (PATIENT_ID) REFERENCES patients_personal_info(PATIENT_ID),
    FOREIGN KEY (DOCTOR_ID) REFERENCES DOCTORS(DOCTOR_ID)
);


-- THE SCANS TABLE
CREATE TABLE SCANS (
    SCAN_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    PATIENT_ID BIGINT, 
    SESSION_ID BIGINT, 
    SCAN_DESCRIPTION VARBINARY(256),
    DATE_OF_SCAN VARBINARY(16), 
    SCAN_RESULTS VARBINARY(2048), 
    DOCTORS_COMMENTS VARBINARY(2048), 
    RADIOLOGIST_NAME VARBINARY(64), 
    RADIOLOGIST_CONTACT_INFO VARBINARY(256), 
    INDEX IDX_SCAN (SCAN_ID , PATIENT_ID , SESSION_ID),
    FOREIGN KEY (PATIENT_ID) REFERENCES patients_personal_info(PATIENT_ID),
    FOREIGN KEY (SESSION_ID) REFERENCES SESSIONS(SESSION_ID)
);


-- THE ACCESSES HISTORY TO PATIENTS TABLE
CREATE TABLE ACCESSES_TO_PATIENT (
    ACCESS_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    PATIENT_ID BIGINT,
    DOCTOR_ID BIGINT,
    ACCESS_DATE DATETIME,
    ACCESS_TYPE ENUM('QUICK CHECK', 'NEW SESSION', 'UPDATE'),
    ACCESS_DURATION INT,
    INDEX IDX_ACCESS_TP (ACCESS_ID , PATIENT_ID , DOCTOR_ID),
    FOREIGN KEY (PATIENT_ID) REFERENCES patients_personal_info(PATIENT_ID),
    FOREIGN KEY (DOCTOR_ID) REFERENCES DOCTORS(DOCTOR_ID)
);


-- THE ACCESSES HISTORY TO DOCTORS TABLE
CREATE TABLE ACCESSES_TO_DOCOTR (
    ACCESS_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    DOCTOR_ID BIGINT,
    ACCESS_DATE DATETIME,
    ACCESS_DURATION INT,
    INDEX IDX_ACCESS_TP (ACCESS_ID , DOCTOR_ID),
    FOREIGN KEY (DOCTOR_ID) REFERENCES DOCTORS(DOCTOR_ID)
);



