package com.lab.jti.thai;

import com.lab.jti.thai.DBHelper.DB.ACTION_HISTORY;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class SQLiteProvider extends ContentProvider {

	/** URIのauthority. */
	private static final String AUTHORITY = "com.lab.jti.thai";
	/** SQLiteデータベースのファイル名. */
	private static final String DB_NAME = "pushprototype.db";
	/** SQLiteOpenHelperのインスタンス */
	private DBHelper mOpenHelper;
	/** DB version */
	private static final int DB_VERSION = 1;

	public static final Uri contentUri = Uri.parse("content://" + AUTHORITY + "/" + ACTION_HISTORY.TABLE_NAME);

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowId;
		try {
			rowId = db.insertOrThrow(uri.getPathSegments().get(0), null, values);
		} finally {
			db.close();
		}
		Uri resultUri = ContentUris.withAppendedId(uri, rowId);
		getContext().getContentResolver().notifyChange(resultUri, null);
		return resultUri;
	}

	@Override
	public boolean onCreate() {
		mOpenHelper = new DBHelper(getContext(), DB_NAME, null, DB_VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		Cursor c = db.query(uri.getPathSegments().get(0), projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		try {
			count = db.delete(uri.getPathSegments().get(0), selection, selectionArgs);
		} finally {
			db.close();
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		try {
			count = db.update(uri.getPathSegments().get(0), values, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
		} finally {
			db.close();
		}
		return count;
	}
}
