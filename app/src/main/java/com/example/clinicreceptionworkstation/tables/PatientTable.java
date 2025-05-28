package com.example.clinicreceptionworkstation.tables;

public class PatientTable {
    public static final String TABLE_NAME = "patient";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECORD = "record";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_PATRONYMIC = "patronymic";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_BIRTH_DATE = "birth_date";
    public static final String COLUMN_INSURANCE = "insurance";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_REGISTRATION_DATE = "registration_date";
    public static final String COLUMN_REGISTRATION_TIME = "registration_time";

    public static final String createTable = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RECORD + " TEXT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_SURNAME + " TEXT, "
            + COLUMN_PATRONYMIC + " TEXT, "
            + COLUMN_GENDER + " TEXT, "
            + COLUMN_BIRTH_DATE + " TEXT, "
            + COLUMN_INSURANCE + " TEXT, "
            + COLUMN_PHONE + " TEXT, "
            + COLUMN_REGISTRATION_DATE + " TEXT, "
            + COLUMN_REGISTRATION_TIME + " TEXT)";
    public static final String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
