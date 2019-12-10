package com.example.android_notes_app

class dbManager
{
    var dbName = "MyNotes"
    var dbTable = "Notes"
    var colID = "ID"
    var colTitle = "Title"
    var colDes = "Description"
    var dbVersion = 1
    var sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + dbTable + " (" + colID + " INTEGER PRIMARY KEY, " + colTitle + " TEXT, " + colDes + " TEXT);"
    var sqlDB:SQLiteDatabase? = null

    constructor(context: Context)
    {
        var db = DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }
    inner class DatabaseHelperNotes: SQLiteOpenHelper
    {
        var context:Context? = null
        constructor(context: Context):super(context, dbName, null, dbVersion)
        {
            this.context = context
        }
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS " + dbTable)
        }
    }

    fun Insert(values:ContentValues):Long
    {
        var ID = sqlDB!!.insert( dbTable,"",values)
        return ID
    }

    fun Query(projection:Array<String>,selection:String,selectionArgs:Array<String>,SorOrder:String):Cursor
    {
        var qb=SQLiteQueryBuilder()
        qb.tables = dbTable
        var curser = qb.query(sqlDB,projection,selection,selectionArgs,null,null,SorOrder)
        return curser
    }

    fun delete(selection:String,selectionArgs:Array<String>):Int
    {
        val count = sqlDB!!.delete(dbTable,selection,selectionArgs)
        return count
    }
    fun update(values:ContentValues,selection: String,selectionArgs: Array<String>):Int
    {
        val count = sqlDB!!.update(dbTable,values,selection,selectionArgs)
        return count
    }
}