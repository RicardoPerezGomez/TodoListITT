package com.example.todolistitt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistitt.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

public class RegistroActivity extends AppCompatActivity {
    TextInputEditText RegUsuario, RegPassword;
    ControladorDB controladorDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        controladorDB = new ControladorDB(this);
        RegUsuario = (TextInputEditText) findViewById(R.id.cajaRegUser);
        RegPassword = (TextInputEditText) findViewById(R.id.cajaRegPass);
        Typeface miFuente = Typeface.createFromAsset(getAssets(),"leixo.ttf");
        TextView titulo = (TextView) findViewById(R.id.titulo_user);
        titulo.setTypeface(miFuente);

    }



    public void registro(View view ){
        String user = RegUsuario.getText().toString();
        String pass = RegPassword.getText().toString();

        if(controladorDB.buscar(user)>0){  //SI EL METODO ME DEVUELVE MAYOR QUE CERO QUIERE DECIR QUE EXISTE UN USUARIO
            Toast toast = Toast.makeText(this,"USUARIO YA EXISTE",Toast.LENGTH_LONG);
            toast.show();
            RegUsuario.setText("");
            RegPassword.setText("");

        }else if (user.isEmpty()&& pass.isEmpty()) {
            Toast toast = Toast.makeText(this,"ERROR CAMPOS VACIOS",Toast.LENGTH_LONG);
            toast.show();
        }else{
            controladorDB.addUser(user,pass);
            Toast toast = Toast.makeText(this,"REGISTRO EXITOSO",Toast.LENGTH_LONG);
            toast.show();
            RegUsuario.setText("");
            RegPassword.setText("");
            Intent intent = new Intent(this,LoginActivity.class);//INTENT PARA PASAR A LA SGTE ACTIVITY
            startActivity(intent);
        }
    }

}