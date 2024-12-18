package com.example.todolistapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolistapp.Model.Task
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [Task::class], version = 3)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private const val DB_NAME = "Task_DB"

    @Volatile
    private var instance: TaskDatabase? = null

    /**
     * Gets a singleton instance of the database.
     */
    fun getDatabase(context: Context): TaskDatabase {
        val userPassphrase = charArrayOf('T', 'M', 'C') //leaving this here for testing purposes
        val passphrase = SQLiteDatabase.getBytes(userPassphrase)
        val state = SQLCipherUtils.getDatabaseState(context, DB_NAME)
        //checks if db unencrypted and will encrypt so it isn't viewable even from app inspector
        if (state == SQLCipherUtils.State.UNENCRYPTED) {
            SQLCipherUtils.encrypt(
                context,
                DB_NAME,
                passphrase
            )
        }
        val factory = SupportFactory(passphrase)

        if (instance == null) {
            synchronized(TaskDatabase::class.java) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context, TaskDatabase::class.java, DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .enableMultiInstanceInvalidation()
                        .openHelperFactory(factory) //encryption line
                        .build()
                }
            }
        }
        return instance!!
        }
    }
}