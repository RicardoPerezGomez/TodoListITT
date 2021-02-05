package com.example.todolistitt.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.widget.Toast;

public class ControladorDB extends SQLiteOpenHelper {
    public ControladorDB( Context context) {
        super(context, "com.example.todolistitt.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USUARIOS (IdUser INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT UNIQUE NOT NULL, PASSWORD TEXT NOT NULL );");
       // db.execSQL("CREATE TABLE TAREAS ( IdTarea INTEGER PRIMARY KEY AUTOINCREMENT,NOMBRE TEXT NOT NULL, IdUser INTEGER, FOREIGN KEY (IdUser)  REFERENCES USUARIOS(IdUser));");
        db.execSQL("CREATE TABLE TAREAS (IdTarea INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT NOT NULL, USER TEXT NOT NULL, FOREIGN KEY(USER)REFERENCES USUARIOS(NOMBRE));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void addUser(String nombre,String password ){ //METODO AGREGAR USUARIO
        ContentValues cv = new ContentValues();
        cv.put("NOMBRE", nombre);
        cv.put("PASSWORD", password);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("USUARIOS",null,cv);
        db.close();
       }

    public void addUserTask(String user,String tarea ){ //METODO AGREGAR TAREA DE USUARIO

        ContentValues cv = new ContentValues();
        cv.put("USER",user);
        cv.put("NOMBRE", tarea);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("TAREAS",null,cv);
        db.close();
    }

    public void borrarTarea( String tarea){  //METODO BORRAR TAREA DE USUARIO
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TAREAS","NOMBRE=?",new String[]{tarea});
        db.close();
    }



    public int buscar(String usuario){  //METODO PARA BUSCAR USUARIO
        int regs = 0;
        SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery("select nombre from usuarios where nombre = ?", new String[]{usuario});
         regs = cursor.getCount();
        if( regs > 0){
           return regs;

        }
        db.close();
        return regs;
    }

   public int userPass(String user, String pass){ //METODO PARA LOGEARSE, PARA ENCONTRAR USUARIO Y PASSWORD
        int a = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from usuarios",null);
        if(cursor!=null && cursor.moveToFirst()){
            do{
                if(cursor.getString(1).equals(user) && cursor.getString(2).equals(pass)){
                    a++;
                }

            }while(cursor.moveToNext());
        }
        return a;
    }

    public int numeroRegistros(String user) { //METODO PARA CONTAR EL NUMERO DE REGISTROS DEL USUARIO
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *  FROM TAREAS where user = ?",new String[]{user});
        return cursor.getCount();
    }

    public String[] obtenerTareas(String user) { //METODO PARA OBTENER TAREAS DE ESE USUARIO
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from tareas where user=?", new String[]{String.valueOf(user)});
        int regs = cursor.getCount();
        if(regs == 0){
            db.close();
            return null;
        }else{
            String [] tareas = new String[regs];
            cursor.moveToFirst();
            for(int i =0; i<regs; i++){
                tareas[i] = cursor.getString(1);
                cursor.moveToNext();
            }
            db.close();
            return tareas;
        }
    }


}
