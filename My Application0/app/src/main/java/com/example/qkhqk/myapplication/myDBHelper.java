package com.example.qkhqk.myapplication;

import java.io.File;

import android.R.bool;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Environment;

public  class myDBHelper extends   SQLiteOpenHelper
{
	public static Context context = null;

	/**
	 * �趨���ݿ�����
	 */
	public static String cDataBaseName = "asset.db";
	/**
	 * StringBuffer �����½���SQL���������\r\n���зָ�
	 */
	public static StringBuffer cTables = null;
	/**
	 * Ĭ�Ͻ���·�������cDBFullPath��=���� ��ֵ��Ч
	 */
	public static String cDriverPath = Environment.getExternalStorageDirectory().toString()+ File.separator+ "Android" + File.separator + "data";
	/**
	 * ���������ݿ�·��������ֵΪ��ʱ=cDriverPath + File.separator + cDataBaseName;
	 * �����Ϊ��ʱ����ʹ���趨��·��  ����  cDBFullPath=getApplicationContext().getDatabasePath(mysqlite.cDataBaseName).getParent();
	 */
	public static String cDBFullPath = "";
	public static SQLiteDatabase sqlitedb = null;


	public myDBHelper(Context context, String name, CursorFactory factory,
			int version) {
	
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
 
	}

	 
		private static void myDBOpen() {
			//CreateDataBase();
		      myDBHelper  mydbHelper=new 	myDBHelper(context,cDataBaseName,null,1);
		      
		      try {
				sqlitedb=	 mydbHelper.getWritableDatabase() ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	     
		}
		public static void myDBOpen(Boolean isDefaultPath) {
			//CreateDataBase();
			if(!isDefaultPath)
			{
				    CreateDataBase();
			}
			else {
				myDBOpen() ; 
			}
	 
		}
		
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		sqlitedb=arg0;
		if (cTables.toString().length() > 0) {
			String[] cCommands = cTables.toString().split("\r\n");
			for (String cCommand : cCommands) {
				sqlitedb.execSQL(cCommand);
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	private static void CreateDataBase() {

		File file;
		if (cDBFullPath.equals("")) {
			file = new File(cDriverPath);
			if (!file.exists()) {
				file.mkdirs();

			}
			cDBFullPath = cDriverPath;// + File.separator + cDataBaseName;
		}
		file = new File(cDBFullPath);
		if (!file.exists()) 
		{
			file.mkdirs();
		}
		file = new File(cDBFullPath+ File.separator + cDataBaseName);
		
		if (file.exists()) {
		   sqlitedb = SQLiteDatabase.openOrCreateDatabase( cDBFullPath+ File.separator + cDataBaseName, null);
		
		} else {
			try {
			 
				sqlitedb = SQLiteDatabase.openOrCreateDatabase( cDBFullPath+ File.separator + cDataBaseName, null);

				if (cTables.toString().length() > 0) {
					String[] cCommands = cTables.toString().split("\r\n");
					for (String cCommand : cCommands) {
						sqlitedb.execSQL(cCommand);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	
	private static void ExecutSql(StringBuffer cSqls) {
		if (sqlitedb != null && sqlitedb.isOpen()) {
			sqlitedb.execSQL(cSqls.toString());
		}
	}

	private static void ExecutSql(StringBuffer cSqls, boolean isAarray) {
		if (sqlitedb != null && sqlitedb.isOpen()) {
			if (isAarray) {
				if (cSqls.toString().length() > 0) {
					String[] cCommands = cSqls.toString().split("\r\n");
					for (String cCommand : cCommands) {
						sqlitedb.execSQL(cCommand);
					}
				}
			} else {
				sqlitedb.execSQL(cSqls.toString());
			}
		}
	}

	private static void ExecutSql(String cSqls) {
		if (sqlitedb != null && sqlitedb.isOpen()) {
			sqlitedb.execSQL(cSqls);
		}
	}

	private static void ExecutSql(String cSqls, Object[] object) {
		sqlitedb.execSQL(cSqls, object);

	}

	private static Cursor getRecords(StringBuffer cSqls) {
		return sqlitedb.rawQuery(cSqls.toString(), null);
	}

	private static Cursor getRecords(StringBuffer cSqls, String[] cArgs) {
		return sqlitedb.rawQuery(cSqls.toString(), cArgs);
	}

	private static Cursor getRecords(String cSqls) {
		return sqlitedb.rawQuery(cSqls, null);
	}

	private static Cursor getRecords(String cSqls, String[] cArgs) {
		return sqlitedb.rawQuery(cSqls, cArgs);

	}



	public static void myDBClose() {
		if (sqlitedb.isOpen())
			sqlitedb.close();
	}

	public static Cursor myRecords(StringBuffer cSqls) {
		return getRecords(cSqls);
	}

	public static Cursor myRecords(StringBuffer cSqls, String[] cArgs) {
		return getRecords(cSqls, cArgs);
	}

	public static Cursor myRecords(String cSqls) {
		return getRecords(cSqls);
	}

	public static Cursor myRecords(String cSqls, String[] cArgs) {
		return getRecords(cSqls, cArgs);
	}

	public static void myExecut(String cSqls) {
		ExecutSql(cSqls);
	}

	public static void myExecut(String cSqls, Object[] object) {
		ExecutSql(cSqls, object);

	}

	public static void myExecut(StringBuffer cSqls) {
		ExecutSql(cSqls);
	}

	public static void myExecut(StringBuffer cSqls, boolean isArray) {
		ExecutSql(cSqls, isArray);
	}

	public static long myRowCount(String cSqls) {
		Cursor cursor = getRecords(cSqls);
		cursor.moveToFirst();
		long iRowCount = cursor.getLong(0);
		cursor.close();
		return iRowCount;
	}

	public static String myGetSingle(String cSqls, String cType) {
		Cursor cursor = getRecords(cSqls);
		cursor.moveToFirst();
		String cValue = cursor.getString(0);
		cursor.close();
		return cValue;
	}
	
}