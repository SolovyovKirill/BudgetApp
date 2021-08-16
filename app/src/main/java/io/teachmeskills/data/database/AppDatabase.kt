package io.teachmeskills.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.teachmeskills.data.database.dao.ExpenseDao
import io.teachmeskills.data.database.entity.Expense

@Database(
    entities = [Expense::class], version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getExpenseDao() : ExpenseDao

    companion object {

        @Volatile
        private var database: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase{
            return if (database == null) {
                database = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "database"
                ).build()
                database as AppDatabase
            } else database as AppDatabase
        }
    }
}