package com.example.hojinjo.restaurant1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MDBHelper extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest";

    public MDBHelper(Context context) {
        super(context, MContract.DB_NAME, null, MContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(MContract.Menu.CREATE_TABLE);       //데이터베이스가 없을때
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(MContract.Menu.DELETE_TABLE);
        onCreate(db);
    }

    public long insertUserByMethod(String name, String price, String menu, String restid) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MContract.Menu.KEY_NAME, name);
        values.put(MContract.Menu.KEY_PRICE, price);
        values.put(MContract.Menu.KEY_DESCRIPTION,menu);
        values.put(MContract.Menu.KEY_RESTID, restid);

        return db.insert(MContract.Menu.TABLE_NAME,null,values);
    }
    public Cursor getAllMenusByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(MContract.Menu.TABLE_NAME,null,null,null,null,null,null);
    }

    public Cursor getAllMenusByID(String rest_id) {
        Log.i("rest_id","rest_id="+rest_id);
        String [] projection = {
                MContract.Menu.KEY_NAME,
                MContract.Menu.KEY_PRICE,
                MContract.Menu.KEY_DESCRIPTION
        };

        String selection= MContract.Menu.KEY_RESTID + " = ? ";
        String [] selectionArgs ={ rest_id };

        SQLiteDatabase db = getReadableDatabase();
        return db.query( MContract.Menu.TABLE_NAME,  // 테이블이름
                projection,         // 프로젝션
                selection,    // 조건절=restid
                selectionArgs,      // 조건절에 대한 값
                null,
                null,
                null);
    }
}
