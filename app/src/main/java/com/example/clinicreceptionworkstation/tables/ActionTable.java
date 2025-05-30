package com.example.clinicreceptionworkstation.tables;

public class ActionTable {
    public static final String TABLE_NAME = "action";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";

    public static final String createTable = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MESSAGE + " TEXT, "
            + COLUMN_DATE + " TEXT, "
            + COLUMN_TIME + " TEXT)";
    public static final String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
