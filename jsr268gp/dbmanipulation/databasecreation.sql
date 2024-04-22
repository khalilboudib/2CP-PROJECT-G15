CREATE DATABASE health_care_system;
USE health_care_system;

-- THE ADMINS TABLE
CREATE TABLE ADMINS (
    ADMIN_ID BIGINT PRIMARY KEY,
    FIRST_NAME VARCHAR(20),
    LAST_NAME VARCHAR(20),
    EMAIL VARCHAR(320) UNIQUE,
    PHONE_NUMBER VARCHAR(15), -- FOR BACK UP
    ADDRESS VARCHAR(255),
    HASHED_PASSWORD VARCHAR(72), -- BCrypt
    DATE_OF_BIRTH DATE,
    NATIONAL_ID VARCHAR(20),
    GENDER TINYINT,
    EMERGENCY_CONTACT_NAME VARCHAR(50),
    EMERGENCY_CONTACT_PHONE VARCHAR(15),
    CARD_EXPIRING_DATE DATE,
    USER_PUBLIC_KEY VARBINARY(256),
    SERVER_PRIVATE_KEY VARBINARY(256),
    INDEX IDX_ADMINS (FIRST_NAME, LAST_NAME, ADMIN_ID)
);


-- THE ADMINS TABLE
CREATE TABLE ADMINS_DELETED (
    ADMIN_ID BIGINT PRIMARY KEY,
    FIRST_NAME VARCHAR(20),
    LAST_NAME VARCHAR(20),
    EMAIL VARCHAR(320) UNIQUE,
    PHONE_NUMBER VARCHAR(15), -- FOR BACK UP
    ADDRESS VARCHAR(255),
    HASHED_PASSWORD VARCHAR(72), -- BCrypt
    DATE_OF_BIRTH DATE,
    NATIONAL_ID VARCHAR(20),
    GENDER TINYINT,
    EMERGENCY_CONTACT_NAME VARCHAR(50),
    EMERGENCY_CONTACT_PHONE VARCHAR(15),
    CARD_EXPIRING_DATE DATE,
    USER_PUBLIC_KEY VARBINARY(256),
    SERVER_PRIVATE_KEY VARBINARY(256),
    INDEX IDX_ADMINSD (EMAIL, PHONE_NUMBER, ADMIN_ID)
);


-- THE CLINIQUES TABLE
CREATE TABLE CLINIQUES (
    CLINIQUE_ID INT AUTO_INCREMENT PRIMARY KEY,
    CLINIQUE_NAME VARCHAR(255),
    WILAYA INT,
    LOCATION VARCHAR(255),
    CONTACT_PERSON VARCHAR(50),
    CONTACT_PERSON_EMAIL VARCHAR(320),
    CONTACT_PERSON_PHONE VARCHAR(15),
    INDEX IDX_CLINIQUES (CLINIQUE_ID, CLINIQUE_NAME, WILAYA)
);


-- THE DOCTORS TABLE
CREATE TABLE DOCTORS (
    DOCTOR_ID BIGINT PRIMARY KEY,
    FIRST_NAME VARCHAR(20),
    LAST_NAME VARCHAR(20),
    GENDER TINYINT,
    PICTURE VARCHAR(30),
    DATE_OF_BIRTH DATE,
    NATIONAL_ID VARCHAR(20),
    ABOUT VARCHAR(1200),
    EMAIL VARCHAR(320),
    ADDRESS VARCHAR(255),
    PHONE_NUMBER VARCHAR(15), -- FOR BACK UP
    HASHED_PASSWORD VARCHAR(72), -- BCrypt
    CLINIQUE_ID INT,
    CARD_EXPIRING_DATE DATE,
    USER_PUBLIC_KEY VARBINARY(256),
    SERVER_PRIVATE_KEY VARBINARY(256),
    INDEX IDX_DOCTORS (DOCTOR_ID, CLINIQUE_ID, FIRST_NAME, LAST_NAME),
    FOREIGN KEY (CLINIQUE_ID) REFERENCES CLINIQUES(CLINIQUE_ID)
);


CREATE TABLE DOCTORS_DELETED (
    DOCTOR_ID BIGINT PRIMARY KEY,
    FIRST_NAME VARCHAR(20),
    LAST_NAME VARCHAR(20),
    GENDER TINYINT,
    PICTURE VARCHAR(30),
    DATE_OF_BIRTH DATE,
    NATIONAL_ID VARCHAR(20),
    ABOUT VARCHAR(1200),
    EMAIL VARCHAR(320),
    ADDRESS VARCHAR(255),
    PHONE_NUMBER VARCHAR(15), -- FOR BACK UP
    HASHED_PASSWORD VARCHAR(72), -- BCrypt
    CLINIQUE_ID INT,
    CARD_EXPIRING_DATE DATE,
    USER_PUBLIC_KEY VARBINARY(256),
    SERVER_PRIVATE_KEY VARBINARY(256),
    INDEX IDX_DOCTORSD (DOCTOR_ID, CLINIQUE_ID, FIRST_NAME, LAST_NAME),
    FOREIGN KEY (CLINIQUE_ID) REFERENCES CLINIQUES(CLINIQUE_ID)
);


-- THE PATIENTS TABLE
CREATE TABLE PATIENTS (
    PATIENT_ID BIGINT PRIMARY KEY,
    FIRST_NAME VARCHAR(20),
    LAST_NAME VARCHAR(20),
    GENDER TINYINT,
    PICTURE VARCHAR(30), 
    DATE_OF_BIRTH DATE,
    NATIONAL_ID VARCHAR(20),
    EMAIL VARCHAR(320),
    ADDRESS VARCHAR(255),
    PHONE_NUMBER VARCHAR(15), -- FOR BACK UP
    CHRONIC_DISEASES VARCHAR(512),
    HASHED_PASSWORD VARCHAR(72), -- BCrypt
    CARD_EXPIRING_DATE DATE,
    USER_PUBLIC_KEY VARBINARY(256),
    SERVER_PRIVATE_KEY VARBINARY(256),
    EMERGENCY_CONTACT_NAME VARCHAR(50),
    EMERGENCY_CONTACT_PHONE VARCHAR(15),
    OCCUPATION VARCHAR(100),
    MARITAL_STATUS VARCHAR(20),
    ALLERGIES VARCHAR(255),
    PREVIOUS_SURGERIES TEXT,
    FAMILY_MEDICAL_HISTORY TEXT,
    BLOOD_TYPE VARCHAR(4),
    HEIGHT DECIMAL(5, 2),
    WEIGHT DECIMAL(5, 2),
    DISABILITIES TEXT,
    SOCIAL_SECURITY_NUMBER VARCHAR(20),
    INDEX IDX_PATIENTS (PATIENT_ID, FIRST_NAME, LAST_NAME)
);


CREATE TABLE PATIENTS_DELETED (
    PATIENT_ID BIGINT PRIMARY KEY,
    FIRST_NAME VARCHAR(20),
    LAST_NAME VARCHAR(20),
    GENDER TINYINT,
    PICTURE VARCHAR(30), 
    DATE_OF_BIRTH DATE,
    NATIONAL_ID VARCHAR(20),
    EMAIL VARCHAR(320),
    ADDRESS VARCHAR(255),
    PHONE_NUMBER VARCHAR(15), -- FOR BACK UP
    CHRONIC_DISEASES VARCHAR(512),
    HASHED_PASSWORD VARCHAR(72), -- BCrypt
    CARD_EXPIRING_DATE DATE,
    USER_PUBLIC_KEY VARBINARY(256),
    SERVER_PRIVATE_KEY VARBINARY(256),
    EMERGENCY_CONTACT_NAME VARCHAR(50),
    EMERGENCY_CONTACT_PHONE VARCHAR(15),
    OCCUPATION VARCHAR(100),
    MARITAL_STATUS VARCHAR(20),
    ALLERGIES VARCHAR(255),
    PREVIOUS_SURGERIES TEXT,
    FAMILY_MEDICAL_HISTORY TEXT,
    BLOOD_TYPE VARCHAR(4),
    HEIGHT DECIMAL(5, 2),
    WEIGHT DECIMAL(5, 2),
    DISABILITIES TEXT,
    SOCIAL_SECURITY_NUMBER VARCHAR(20),
    INDEX IDX_PATIENTSD (PATIENT_ID, FIRST_NAME, LAST_NAME)
);

-- THE SESSIONS TABLE
CREATE TABLE SESSIONS (
    SESSION_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    PATIENT_ID BIGINT,
    DOCTOR_ID BIGINT,
    SESSION_DATE DATE,
    SESSION_DURATION INT,
    SESSION_NOTES TEXT,
    DIAGNOSIS TEXT,
    TREATMENT_PLAN TEXT,
    IS_THERE_SCANS TINYINT,
    SESSION_FEE DECIMAL(10, 2),
    INDEX IDX_PATIENT_ID (PATIENT_ID, DOCTOR_ID),
    FOREIGN KEY (PATIENT_ID) REFERENCES PATIENTS(PATIENT_ID),
    FOREIGN KEY (DOCTOR_ID) REFERENCES DOCTORS(DOCTOR_ID)
);


-- THE SCANS TABLE
CREATE TABLE SCANS (
    SCAN_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    PATIENT_ID BIGINT,
    SESSION_ID BIGINT,
    SCAN_DESCRIPTION VARCHAR(255),
    DATE_OF_SCAN DATE,
    SCAN_RESULTS TEXT,
    DOCTORS_COMMENTS TEXT,
    RADIOLOGIST_NAME VARCHAR(50),
    RADIOLOGIST_CONTACT_INFO VARCHAR(255),
    INDEX IDX_PATIENT_ID (PATIENT_ID, SESSION_ID),
    FOREIGN KEY (PATIENT_ID) REFERENCES PATIENTS(PATIENT_ID),
    FOREIGN KEY (SESSION_ID) REFERENCES SESSIONS(SESSION_ID)
);


-- THE ACCESSES TABLE
CREATE TABLE ACCESSES (
    ACCESS_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    PATIENT_ID BIGINT,
    DOCTOR_ID BIGINT,
    ACCESS_DATE DATETIME,
    ACCESS_TYPE ENUM('QUICK CHECK', 'NEW SESSION', 'UPDATE'),
    ACCESS_DURATION INT,
    INDEX IDX_PATIENT_ID (PATIENT_ID, DOCTOR_ID),
    FOREIGN KEY (PATIENT_ID) REFERENCES PATIENTS(PATIENT_ID),
    FOREIGN KEY (DOCTOR_ID) REFERENCES DOCTORS(DOCTOR_ID)
);
