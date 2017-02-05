package com.skula.cts.services;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseService {
	private static final String DATABASE_NAME = "cts.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_FAVORITE = "favorite";

	private Context context;
	private SQLiteDatabase database;
	private SQLiteStatement statement;

	public DatabaseService(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.database = openHelper.getWritableDatabase();
	}

	public void bouchon() {
		insertFavorite("Wilson");
		insertFavorite("Faubourg de Saverne");
		insertFavorite("Copenhague (SCHILTIGHEIM)");
	}

	/*****************************************************/
	/******************* FAVORIE ************************/
	/*****************************************************/
	public String insertFavorite(String label) {
		String sql = "insert into " + TABLE_FAVORITE + "(id, label) values (?,?)";
		String id = String.valueOf(getNextFavoriteId());
		statement = database.compileStatement(sql);
		statement.bindString(1, id);
		statement.bindString(2, label);
		statement.executeInsert();

		return id;
	}

	public void updateFavorite(String id, String newLabel) {
		ContentValues args = new ContentValues();
		args.put("label", newLabel);
		;
		database.update(TABLE_FAVORITE, args, "id=" + id, null);
	}

	public void deleteFavorite(String id) {
		database.delete(TABLE_FAVORITE, "id='" + id + "'", null);
	}

	public List<String> getFavorites() {
		List<String> res = new ArrayList<String>();
		Cursor cursor = database.query(TABLE_FAVORITE, new String[] { "label" }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				res.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return res;
	}

	public int getNextFavoriteId() {
		Cursor cursor = database.query(TABLE_FAVORITE, new String[] { "max(id)" }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				return cursor.getInt(0) + 1;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return 1;
	}

	private static class OpenHelper extends SQLiteOpenHelper {
		public OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL("CREATE TABLE " + TABLE_FAVORITE + "(id INTEGER PRIMARY KEY, label TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
			onCreate(database);
		}
	}
}
