package pcru.phattara.spinnersqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "categories";
    private static final String TABLE_NAME = "category";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT)";
    private static final String CREATE_UNIQUE_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS "
            + COLUMN_NAME
            + " ON " + TABLE_NAME
            + "(" + COLUMN_NAME + ")";
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public  void onCreate(android.database.sqlite.SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_UNIQUE_INDEX);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addCategory(Category category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, category.getName());
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }
    public List<String> getAllCategories(){
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT %s FROM %s", COLUMN_NAME, TABLE_NAME), null);
        if (cursor.moveToFirst()){
            do {
                list.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
