package com.example.d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button doclog, plog, docsign, psign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        installSplashScreen();

        doclog = (Button) findViewById(R.id.button);
        doclog.setOnClickListener(v -> click());

        plog = (Button) findViewById(R.id.button3);
        plog.setOnClickListener(v -> click1());

        docsign = (Button) findViewById(R.id.button2);
        docsign.setOnClickListener(v -> click2());

        psign = (Button) findViewById(R.id.button4);
        psign.setOnClickListener(v -> click3());


    }

    private void installSplashScreen() {
    }

    public void click()
    {
        Intent intent = new Intent(this, DoctorLogin.class);
        startActivity(intent);
    }

    public void click1()
    {
        Intent intent = new Intent(this, PatientLogin.class);
        startActivity(intent);
    }

    public void click2()
    {
        Intent intent = new Intent(this, DoctorRegistration.class);
        startActivity(intent);
    }

    public void click3()
    {
        Intent intent = new Intent(this, PatientReg.class);
        startActivity(intent);
    }

}