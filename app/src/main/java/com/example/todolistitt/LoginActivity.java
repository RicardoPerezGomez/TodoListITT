package com.example.todolistitt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistitt.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

import java.text.BreakIterator;

public class LoginActivity extends AppCompatActivity  {
    TextInputEditText Usuario, Password;
    ControladorDB controladorDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        controladorDB = new ControladorDB(this);
        Usuario = (TextInputEditText) findViewById(R.id.cajaUser);
        Password = (TextInputEditText) findViewById(R.id.cajaPass);

        Typeface miFuente = Typeface.createFromAsset(getAssets(),"leixo.ttf");
        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setTypeface(miFuente);
    }

    public void crearCuenta (View view){
        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }

    public void login (View view){
        String user = Usuario.getText().toString();
        String pass = Password.getText().toString();

        if(user.isEmpty()&& pass.isEmpty()) {
            Toast toast = Toast.makeText(this,"ERROR CAMPOS VACIOS",Toast.LENGTH_LONG);
            toast.show();

        }else if (controladorDB.userPass(user,pass)==1) {//SI EL METODO ME DEVUELVE QUE SU VALOR ES IGUAL A 1 QUIERE DECIR QUE EL USUARIO EXISTE

            Toast toast = Toast.makeText(this,"DATOS CORRECTOS",Toast.LENGTH_LONG);
            toast.show();

            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("user",user); //CON EL PUTEXTRA MANDO ESTE DATO "USER" A LA SIGUIENTE ACTIVITY
            startActivity(intent);
            Usuario.setText("");
            Password.setText("");
        }else{
            Toast toast = Toast.makeText(this,"USUARIO Y/O CONTRASEÑA INVALIDOS",Toast.LENGTH_LONG);
            toast.show();
        }

    }


}