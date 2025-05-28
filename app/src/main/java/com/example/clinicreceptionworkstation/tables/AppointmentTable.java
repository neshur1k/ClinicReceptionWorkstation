package com.example.clinicreceptionworkstation.tables;

public class AppointmentTable {
    public static final String TABLE_NAME = "appointment";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PATIENT_ID = "patient_id";
    public static final String COLUMN_DOCTOR_ID = "doctor_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_SCHEDULING_DATE = "scheduling_date";
    public static final String COLUMN_SCHEDULING_TIME = "scheduling_time";

    public static final String createTable = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PATIENT_ID + " INTEGER, "
            + COLUMN_DOCTOR_ID + " INTEGER, "
            + COLUMN_DATE + " TEXT, "
            + COLUMN_TIME + " TEXT, "
            + COLUMN_NOTES + " TEXT, "
            + COLUMN_SCHEDULING_DATE + " TEXT, "
            + COLUMN_SCHEDULING_TIME + " TEXT)";
    public static final String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
