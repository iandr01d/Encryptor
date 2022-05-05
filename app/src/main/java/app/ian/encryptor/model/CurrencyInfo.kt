package app.ian.encryptor.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.ian.encryptor.Constants

@Entity(tableName = Constants.CURRENCY_TABLE)
data class CurrencyInfo(@PrimaryKey val id: String, val name: String, val symbol: String)
