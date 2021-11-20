package com.developersbreach.architecturecomponentsjava;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.widget.Toast;

        import com.developersbreach.architecturecomponentsjava.model.Attendance;

public class AuthDatabase  extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "Users";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Users";
    public String User ="no";
    public String k="0";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        query = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY, Login TEXT, Password TEXT,Allow TEXT)";
        db.execSQL(query);
    }

    public AuthDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String login, String Password,String Allow) {
        SQLiteDatabase sqLiteDatabase = this .getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Login", login);
        values.put("Password", Password);
        values.put("Allow", Allow);
        long result = sqLiteDatabase.insert(TABLE_NAME, null , values);
        if(result == -1){
            Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Успешно зарегистрирован", Toast.LENGTH_SHORT).show();
        }
        sqLiteDatabase.close();
    }
    public void setData(){
        User ="no";
        k="0";
    }
    public String getUser(){
        return User;
    }
    public String getk(){
        return k;
    }
    public int auth(String Login, String Password) {
        String select_query= "SELECT *FROM " + TABLE_NAME;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);
        if (cursor.moveToFirst()) {
            do {
                Attendance noteModel = new Attendance();
                if (cursor.getString(1).equals(Login)) {
                    if (cursor.getString(2).equals(Password)) {
                        Toast.makeText(context, "Вход выполнен", Toast.LENGTH_SHORT).show();
                        User=Login;
                        k=cursor.getString(3);
                        return 0;
                    }else{
                        Toast.makeText(context, "Неверный пароль", Toast.LENGTH_SHORT).show();
                        return 1;
                    }
                }
            }while (cursor.moveToNext());
            Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_SHORT).show();
        }
        return 1;
    }

}