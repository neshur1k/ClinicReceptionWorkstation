package com.example.clinicreceptionworkstation.tables;

public class DoctorTable {
    public static final String TABLE_NAME = "doctor";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_PATRONYMIC = "patronymic";
    public static final String COLUMN_SPECIALIZATION = "specialization";
    public static final String COLUMN_OFFICE = "office";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_NOTES = "notes";

    public static final String createTable = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_SURNAME + " TEXT, "
            + COLUMN_PATRONYMIC + " TEXT, "
            + COLUMN_SPECIALIZATION + " TEXT, "
            + COLUMN_OFFICE + " TEXT, "
            + COLUMN_PHONE + " TEXT, "
            + COLUMN_NOTES + " TEXT)";
    public static final String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
