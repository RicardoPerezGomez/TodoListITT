package com.example.todolistitt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DirectAction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistitt.db.ControladorDB;

public class MainActivity extends AppCompatActivity {
    ControladorDB controladorDB;
    private ArrayAdapter<String> mAdapter;
    ListView listViewTareas;
    String user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controladorDB = new ControladorDB(this);
        listViewTareas = (ListView) findViewById(R.id.listaTareas);
        user=getIntent().getStringExtra("user");  //AQUI RECIBO CON EL GETEXTRA EL "USER" QUE HE MANDADO EN LA ANTERIOR ACTIVITY
        actualizarUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrar:
                 finish();
                 System.exit(0);
                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                 */
                return true;
            case R.id.nuevaTarea:
                final EditText cajaTexto = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Nueva Tarea")
                        .setMessage("¿Qué quieres hacer a continuación?")
                        .setView(cajaTexto)// caja de texto donde escribiremos la tarea a guardar
                        .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String tarea = cajaTexto.getText().toString();
                                controladorDB.addUserTask(user, tarea);
                                Toast toast2 = new Toast(getApplicationContext()); //TOAST PERSONALIZADO
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_layout,
                                        (ViewGroup) findViewById(R.id.lytLayout));
                                TextView txtMsg =
                                        (TextView) layout.findViewById(R.id.txtMensaje);
                                txtMsg.setText("TAREA AÑADIDA");
                                toast2.setDuration(Toast.LENGTH_SHORT);
                                toast2.setView(layout);
                                toast2.show();

                                actualizarUI();
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create();
                dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void actualizarUI(){

        if(controladorDB.numeroRegistros(user) == 0){
            listViewTareas.setAdapter(null);
        }else{
            mAdapter = new ArrayAdapter<>(this,R.layout.item_tarea,R.id.task_title, controladorDB.obtenerTareas(user));
            listViewTareas.setAdapter(mAdapter);
        }
    }

    public void borrarTarea(View view){
        View parent = (View) view.getParent();
        TextView tareaTextView = (TextView) parent.findViewById(R.id.task_title);
        String tarea = tareaTextView.getText().toString();
        controladorDB.borrarTarea(tarea);
        Toast toast2 = new Toast(getApplicationContext()); //TOAST PERSONALIZADO
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout2,
                (ViewGroup) findViewById(R.id.lytLayout2));
        TextView txtMsg =
                (TextView)layout.findViewById(R.id.txtMensaje2);
        txtMsg.setText("TAREA REALIZADA");
        toast2.setDuration(Toast.LENGTH_SHORT);
        toast2.setView(layout);
        toast2.show();
        actualizarUI();
    }

}