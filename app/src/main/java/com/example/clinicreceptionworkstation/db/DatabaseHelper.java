package com.example.clinicreceptionworkstation.db;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.clinicreceptionworkstation.models.Action;
import com.example.clinicreceptionworkstation.models.Appointment;
import com.example.clinicreceptionworkstation.models.Doctor;
import com.example.clinicreceptionworkstation.models.Patient;
import com.example.clinicreceptionworkstation.tables.ActionTable;
import com.example.clinicreceptionworkstation.tables.AppointmentTable;
import com.example.clinicreceptionworkstation.tables.DoctorTable;
import com.example.clinicreceptionworkstation.tables.PatientTable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "clinic.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PatientTable.createTable);
        db.execSQL(DoctorTable.createTable);
        db.execSQL(AppointmentTable.createTable);
        db.execSQL(ActionTable.createTable);

        addDoctor(db, "Мария", "Ямпольская", "Георгиевна", "Терапевт", "101", "+79064567890", "Пятница-суббота 14:00-18:00");
        addDoctor(db, "Ксения", "Липасова", "Александровна", "Педиатр", "302", "+79059876543", "Понедельник-среда 10:00-16:00");
        addDoctor(db, "Екатерина", "Ермакова", "Максимовна", "Отоларинголог", "102", "+79076543210", "Вторник-четверг 11:00-17:00");
        addDoctor(db, "Илья", "Пахомов", "Андреевич", "Стоматолог", "221", "+79043456789", "Понедельник-пятница 09:00-15:00");
        addDoctor(db, "Григорий", "Озеров", "Алексеевич", "Офтальмолог", "121", "+79001234567", "Понедельник-среда 10:00-16:00");
        addDoctor(db, "Михаил", "Прокофьев", "Олегович", "Психиатр", "207", "+79085678901", "Воскресенье-вторник 16:00-20:00");
        addDoctor(db, "Василий", "Иванов", "Вячеславович", "Гинеколог", "109", "+79022345678", "Вторник-четверг 11:00-17:00");
        addDoctor(db, "Максим", "Русаков", "Юрьевич", "Уролог", "205", "+79017654321", "Пятница-суббота 14:00-18:00");
        addDoctor(db, "Анна", "Гурьянова", "Сергеевна", "Анестезиолог", "111", "+79038765432", "Воскресенье-вторник 16:00-20:00");
        addDoctor(db, "Артём", "Вакшин", "Игоревич", "Химиотерапевт", "209", "+79093210987", "Понедельник-пятница 09:00-15:00");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PatientTable.dropTable);
        db.execSQL(DoctorTable.dropTable);
        db.execSQL(AppointmentTable.dropTable);
        db.execSQL(ActionTable.dropTable);
        onCreate(db);
    }

    public boolean addPatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PatientTable.COLUMN_RECORD, patient.getRecord());
        cv.put(PatientTable.COLUMN_NAME, patient.getName());
        cv.put(PatientTable.COLUMN_SURNAME, patient.getSurname());
        cv.put(PatientTable.COLUMN_PATRONYMIC, patient.getPatronymic());
        cv.put(PatientTable.COLUMN_GENDER, patient.getGender());
        cv.put(PatientTable.COLUMN_BIRTH_DATE, patient.getBirthDate());
        cv.put(PatientTable.COLUMN_INSURANCE, patient.getInsurance());
        cv.put(PatientTable.COLUMN_PHONE, patient.getPhone());
        cv.put(PatientTable.COLUMN_REGISTRATION_DATE, patient.getRegistrationDate());
        cv.put(PatientTable.COLUMN_REGISTRATION_TIME, patient.getRegistrationTime());
        long result = db.insert(PatientTable.TABLE_NAME, null, cv);
        db.close();
        return result != -1;
    }

    private void addDoctor(SQLiteDatabase db, String name, String surname, String patronymic,
                             String specialization, String office, String phone, String notes) {
        ContentValues cv = new ContentValues();
        cv.put(DoctorTable.COLUMN_NAME, name);
        cv.put(DoctorTable.COLUMN_SURNAME, surname);
        cv.put(DoctorTable.COLUMN_PATRONYMIC, patronymic);
        cv.put(DoctorTable.COLUMN_SPECIALIZATION, specialization);
        cv.put(DoctorTable.COLUMN_OFFICE, office);
        cv.put(DoctorTable.COLUMN_PHONE, phone);
        cv.put(DoctorTable.COLUMN_NOTES, notes);
        db.insert(DoctorTable.TABLE_NAME, null, cv);
    }

    public boolean addAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AppointmentTable.COLUMN_PATIENT_ID, appointment.getPatientId());
        cv.put(AppointmentTable.COLUMN_DOCTOR_ID, appointment.getDoctorId());
        cv.put(AppointmentTable.COLUMN_DATE, appointment.getDate());
        cv.put(AppointmentTable.COLUMN_TIME, appointment.getTime());
        cv.put(AppointmentTable.COLUMN_NOTES, appointment.getNotes());
        cv.put(AppointmentTable.COLUMN_SCHEDULING_DATE, appointment.getSchedulingDate());
        cv.put(AppointmentTable.COLUMN_SCHEDULING_TIME, appointment.getSchedulingTime());
        long result = db.insert(AppointmentTable.TABLE_NAME, null, cv);
        db.close();
        return result != -1;
    }

    public void addAction(String message, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ActionTable.COLUMN_MESSAGE, message);
        cv.put(ActionTable.COLUMN_DATE, date);
        cv.put(ActionTable.COLUMN_TIME, time);
        db.insert(ActionTable.TABLE_NAME, null, cv);
        db.close();
    }

    public boolean deletePatient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(PatientTable.TABLE_NAME, PatientTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public boolean deleteAppointment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(AppointmentTable.TABLE_NAME, AppointmentTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public Patient findPatient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(PatientTable.TABLE_NAME, new String[]{
                PatientTable.COLUMN_ID,
                PatientTable.COLUMN_RECORD,
                PatientTable.COLUMN_NAME,
                PatientTable.COLUMN_SURNAME,
                PatientTable.COLUMN_PATRONYMIC,
                PatientTable.COLUMN_GENDER,
                PatientTable.COLUMN_BIRTH_DATE,
                PatientTable.COLUMN_INSURANCE,
                PatientTable.COLUMN_PHONE,
                PatientTable.COLUMN_REGISTRATION_DATE,
                PatientTable.COLUMN_REGISTRATION_TIME},
                PatientTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Patient patient = new Patient(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getString(7),
                    cursor.getString(8), cursor.getString(9), cursor.getString(10));
            cursor.close();
            db.close();
            return patient;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }

    public Doctor findDoctor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DoctorTable.TABLE_NAME, new String[]{
                        DoctorTable.COLUMN_ID,
                        DoctorTable.COLUMN_NAME,
                        DoctorTable.COLUMN_SURNAME,
                        DoctorTable.COLUMN_PATRONYMIC,
                        DoctorTable.COLUMN_SPECIALIZATION,
                        DoctorTable.COLUMN_OFFICE,
                        DoctorTable.COLUMN_PHONE,
                        DoctorTable.COLUMN_NOTES},
                DoctorTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Doctor doctor = new Doctor(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getString(7));
            cursor.close();
            db.close();
            return doctor;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }

    public Appointment findAppointment(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(AppointmentTable.TABLE_NAME, new String[]{
                        AppointmentTable.COLUMN_ID,
                        AppointmentTable.COLUMN_PATIENT_ID,
                        AppointmentTable.COLUMN_DOCTOR_ID,
                        AppointmentTable.COLUMN_DATE,
                        AppointmentTable.COLUMN_TIME,
                        AppointmentTable.COLUMN_NOTES,
                        AppointmentTable.COLUMN_SCHEDULING_DATE,
                        AppointmentTable.COLUMN_SCHEDULING_TIME},
                AppointmentTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Appointment appointment = new Appointment(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7));
            cursor.close();
            db.close();
            return appointment;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patientList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PatientTable.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Patient patient = new Patient(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7),
                        cursor.getString(8), cursor.getString(9), cursor.getString(10));
                patientList.add(patient);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return patientList;
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DoctorTable.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Doctor doctor = new Doctor(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7));
                doctorList.add(doctor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return doctorList;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointmentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + AppointmentTable.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Appointment appointment = new Appointment(cursor.getInt(0), cursor.getInt(1),
                        cursor.getInt(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7));
                appointmentList.add(appointment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return appointmentList;
    }

    public List<Action> getAllActions() {
        List<Action> actionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM \"" + ActionTable.TABLE_NAME + "\"", null);
        if (cursor.moveToFirst()) {
            do {
                Action action = new Action(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                actionList.add(action);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return actionList;
    }

    public int getPatientCount() {
        return getTableRowCount(PatientTable.TABLE_NAME);
    }

    public int getDoctorCount() {
        return getTableRowCount(DoctorTable.TABLE_NAME);
    }

    public int getAppointmentCount() {
        return getTableRowCount(AppointmentTable.TABLE_NAME);
    }

    public int getActionCount() {
        return getTableRowCount(ActionTable.TABLE_NAME);
    }

    private int getTableRowCount(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM \"" + tableName + "\"", null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }
}