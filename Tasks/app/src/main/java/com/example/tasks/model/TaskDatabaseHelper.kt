package com.example.tasks.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class TaskDatabaseHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "userTask.db"
        private const val DATABASE_VERSION = 1

        // Tabla de usuarios
        private const val TABLE_USER = "user"
        private const val COLUMN_USER_ID = "idUser"
        const val COLUMN_IMAGEPATH = "imageRoute"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"

        // Tabla de tareas
        private const val TABLE_TASK = "tasks"
        private const val COLUMN_TASK_ID = "idTask"
        private const val COLUMN_TASK_TITLE = "title"
        private const val COLUMN_TASK_DESCRIPTION = "description"
        private const val COLUMN_TASK_TIME = "time"

        //singleton arci=chitecture
        private var taskDBHelper:TaskDatabaseHelper?= null
        fun createDatabase(context:Context):TaskDatabaseHelper?{
            if(taskDBHelper == null){
                taskDBHelper=TaskDatabaseHelper(context)
            }
            return taskDBHelper
        }

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserTable = """
            CREATE TABLE $TABLE_USER (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_IMAGEPATH TEXT ,
                $COLUMN_USERNAME TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()

        val createTaskTable = """
    CREATE TABLE $TABLE_TASK (
        $COLUMN_TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_USER_ID INTEGER,
        $COLUMN_TASK_TITLE TEXT,
        $COLUMN_TASK_DESCRIPTION TEXT,
        $COLUMN_TASK_TIME TEXT,
        FOREIGN KEY($COLUMN_USER_ID) REFERENCES $TABLE_USER($COLUMN_USER_ID) ON DELETE CASCADE
    )
""".trimIndent()

        db.execSQL(createUserTable)
        db.execSQL(createTaskTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        onCreate(db)
    }

    fun insertUser(imagePath:String, username: String, password: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IMAGEPATH, imagePath)
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        return db.insert(TABLE_USER, null, values)
    }

    fun validateInitSesion(userName:String, password:String):Boolean{

        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COLUMN_USER_ID, $COLUMN_USERNAME, $COLUMN_IMAGEPATH FROM $TABLE_USER WHERE $COLUMN_USERNAME = ? and $COLUMN_PASSWORD = ?"  ,

            arrayOf(userName,password)

        )
        return if (cursor.moveToFirst()) {
            cursor.close()
            true
        } else {
            cursor.close()
            false
        }
    }
    fun getUser(userName: String): Map<String, String> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COLUMN_USER_ID, $COLUMN_USERNAME, $COLUMN_IMAGEPATH FROM $TABLE_USER WHERE $COLUMN_USERNAME = ?",

            arrayOf(userName)

        )
        return if (cursor.moveToFirst()) {
            val user = mapOf(
                COLUMN_USER_ID to cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)).toString(),  // Correcto: COLUMN_USER_ID
                COLUMN_USERNAME to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                COLUMN_IMAGEPATH to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEPATH)),
            )
            cursor.close()
            user
        } else {
            cursor.close()
            emptyMap()
        }
    }
    fun insertTask(userId: Int, title: String, description: String, time: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, userId)
            put(COLUMN_TASK_TITLE, title)
            put(COLUMN_TASK_DESCRIPTION, description)
            put(COLUMN_TASK_TIME, time)
        }
        return db.insert(TABLE_TASK, null, values)
    }

    fun getAllTasks(userId:Int): List<Task> {
        val db = readableDatabase
        val tasks:MutableList<Task> = mutableListOf()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TASK WHERE $COLUMN_USER_ID = ?",
            arrayOf(userId.toString())
            )

        Log.d("aaaaaaaaaaaaa",userId.toString())
        try {
            while (cursor.moveToNext()) {
               tasks.add(
                   Task(
                       id= cursor.getColumnIndexOrThrow(COLUMN_TASK_ID),
                       userId= cursor.getColumnIndexOrThrow(COLUMN_USER_ID),
                       title= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_TITLE)),
                       description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_DESCRIPTION)),
                       time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_TIME)),
                   )
               )

            }
        } finally {
            cursor.close()
        }
        return tasks
    }

    fun deleteUser(userId: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_USER, "$COLUMN_USER_ID = ?", arrayOf(userId.toString()))
    }

    fun deleteTask(taskId: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_TASK, "$COLUMN_TASK_ID = ?", arrayOf(taskId.toString()))
    }
}