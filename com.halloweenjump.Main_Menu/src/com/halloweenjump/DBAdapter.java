package com.halloweenjump;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	private static final String KEY_ROWID = "_id";
	private static final String KEY_NAME = "name";
	private static final String KEY_SCORES = "scores";
	private static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "scores_db";
	private static final String DATABASE_TABLE = "tbl_scores";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE =
	"create table tbl_scores (_id integer primary key autoincrement, "
	+ "name text not null, scores int not null);";

	private final Context context;

	private DatabaseHelper dBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx)
	{
		this.context = ctx;
		dBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(DATABASE_CREATE);
			Log.v("data_base", "Database created");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion,
		int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion
			+ " to "
			+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS tbl_scores");
			onCreate(db);
		}
	}

	//---opens the database---
	public DBAdapter open() throws SQLException
	{
		db = dBHelper.getWritableDatabase();
		return this;
	}

	//---closes the database---
	public void close()
	{
	dBHelper.close();
	}

	//---insert a score into the database---
	public long insertScores(String name, int scores)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_SCORES, scores);
		Log.e("", "saved");
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	//---deletes a particular score---
	public boolean deleteScores(long rowId)
	{
	return db.delete(DATABASE_TABLE, KEY_ROWID +
	"=" + rowId, null) > 0;
	}

	//---retrieves all the tbl_scores---
	public Cursor getAllScores()
	{
	return db.query(false, DATABASE_TABLE, new String[] {
	KEY_ROWID,
	KEY_NAME,
	KEY_SCORES},
	null,null,null, null, KEY_SCORES, null);
	}
}
