package io.teachmeskills.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0,

):Parcelable{

}
