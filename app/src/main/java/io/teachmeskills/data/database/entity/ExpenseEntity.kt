package io.teachmeskills.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.util.*

@Parcelize
@Entity(tableName = "expenses")
data class ExpenseEntity(

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
    @ColumnInfo(name="created")
    var created: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0,

    ):Parcelable{

    val createdDateFormat: String
        get() = DateFormat.getDateTimeInstance().format(created) // Date Format: Aug 22, 2021, 11:30 PM

}

// SELECT * FORM expenses WHERE expense.date > _date_1_ AND expense.date < _date_2_
