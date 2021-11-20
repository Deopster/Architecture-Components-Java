package com.developersbreach.architecturecomponentsjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developersbreach.architecturecomponentsjava.main.VehiclesFragment;

public class MainActivity extends AppCompatActivity {
    private NavController mNavigationController;
    MyDatabaseHelper myDB;
    AuthDatabase logDB;
    DatabaseHelper DbAttendence;
    VehiclesFragment upd;
    private static Context context;
    private TextView Stud,fir,sec,Guy,Key,Password2,login,Password,Nick,keys;
    Button Reg,log,ex,b,b1;
    String allow = "0";
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);
        myDB = new MyDatabaseHelper(MainActivity.this);
        DbAttendence = new DatabaseHelper(MainActivity.this);
        logDB =new AuthDatabase(MainActivity.this);
        mNavigationController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, mNavigationController);
    }
    public void Add(View view){
        allow=logDB.getk();
        if (allow.equals("1234")) {
            Stud = findViewById(R.id.StudentFio);
            text = String.valueOf(Stud.getText());
            Stud.setText("");
            myDB.addBook(text);
        }else {
            Toast.makeText(context, "У вас нет доступа", Toast.LENGTH_SHORT).show();
        }
    }
    public void addsub(View view){
        allow=logDB.getk();
        if (allow.equals("1234")) {
            fir = findViewById(R.id.fir);
            sec = findViewById(R.id.sec);
            Guy = findViewById(R.id.detail_vehicle_name_text_view);
            DbAttendence.addNotes(fir.getText().toString(), sec.getText().toString(), Guy.getText().toString());
            fir.setText("");
            sec.setText("");
            Toast.makeText(context, "Успешно добавлено", Toast.LENGTH_SHORT).show();
        } else {
        Toast.makeText(context, "У вас нет доступа", Toast.LENGTH_SHORT).show();
    }
    }
    public void RegF(View view){

        log.setVisibility(View.GONE);
        Key.setVisibility(View.VISIBLE);
        Reg.setVisibility(View.VISIBLE);
        Password2.setVisibility(View.VISIBLE);
    }
    public void LoginF(View view){

        log.setVisibility(View.VISIBLE);
        Key.setVisibility(View.GONE);
        Reg.setVisibility(View.GONE);
        Password2.setVisibility(View.GONE);
    }
    public void showlog(View view){
        showDialog();
    }
    public void showDialog() {
        final EditText title, des;
        Button close;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.dialog);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        keys = dialog.findViewById(R.id.KeyS);
        b = dialog.findViewById(R.id.button);
        b1 = dialog.findViewById(R.id.button2);
        Nick = dialog.findViewById(R.id.Nick);
        ex = dialog.findViewById(R.id.Ex);
        Key = dialog.findViewById(R.id.Key);
        log = dialog.findViewById(R.id.Log);
        Reg = dialog.findViewById(R.id.Reg);
        login = dialog.findViewById(R.id.login);
        Password = dialog.findViewById(R.id.Password);
        Password2 = dialog.findViewById(R.id.Password2);
        String User =logDB.getUser();
        if (User.equals("no")) {
            b.setVisibility(View.VISIBLE);
            b1.setVisibility(View.VISIBLE);
            log.setVisibility(View.VISIBLE);
            Key.setVisibility(View.GONE);
            Reg.setVisibility(View.GONE);
            Password2.setVisibility(View.GONE);
            Nick.setVisibility(View.GONE);
            keys.setVisibility(View.GONE);
            ex.setVisibility(View.GONE);
        }
        else{
            b.setVisibility(View.GONE);
            b1.setVisibility(View.GONE);
            log.setVisibility(View.GONE);
            Key.setVisibility(View.GONE);
            Reg.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
            Password.setVisibility(View.GONE);
            Password2.setVisibility(View.GONE);
            Nick.setVisibility(View.VISIBLE);
            Nick.setText(User);
            ex.setVisibility(View.VISIBLE);
            keys.setVisibility(View.VISIBLE);
            keys.setText(logDB.getk());
            keys.setTag(logDB.getk());
        }
        close = (Button) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                    dialog.cancel();
            }
        });
        ex.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                allow="0";
                logDB.setData();
                dialog.cancel();
            }
        });
        Reg.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                if (login.getText().toString().isEmpty()) {
                    login.setError("Введите логин");
                }else if(Password.getText().toString().isEmpty()) {
                    Password.setError("Введите пароль");
                }else if(!Password2.getText().toString().equals(Password.getText().toString())) {
                    Password2.setError("Пароли не совпадают");
                }else {
                    logDB.addUser(login.getText().toString(),Password.getText().toString(),Key.getText().toString());
                    dialog.cancel();
                }
            }
        });
        log.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                if (login.getText().toString().isEmpty()) {
                    login.setError("Введите логин");
                }else if(Password.getText().toString().isEmpty()) {
                    Password.setError("Введите пароль");
                }else {
                    int temp = logDB.auth(login.getText().toString(), Password.getText().toString());
                    if (temp==0) {
                        dialog.cancel();
                    }
                }
            }
        });
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
    @Override
    public boolean onSupportNavigateUp() {
        return mNavigationController.navigateUp();
    }
}

