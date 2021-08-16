package io.teachmeskills.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses", primaryKeys = ["amount", "title"])
data class Expense(
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name="id")
//    var id: Int = 0,
    @ColumnInfo(name="title")
    var title: String,
    @ColumnInfo(name="currency")
    var currency: String,
    @ColumnInfo(name="amount")
    var amount: Double,
    @ColumnInfo(name="tag")
    var tag: String,
    @ColumnInfo(name="date")
    var date: String,
    @ColumnInfo(name="note")
    var note: String,

){

}
