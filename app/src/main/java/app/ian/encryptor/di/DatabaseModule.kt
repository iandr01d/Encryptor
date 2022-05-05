package app.ian.encryptor.di

import android.content.Context
import androidx.room.Room
import app.ian.encryptor.Constants
import app.ian.encryptor.db.CurrencyInfoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CurrencyInfoDatabase {
        return Room.databaseBuilder(
            context,
            CurrencyInfoDatabase::class.java,
            Constants.CURRENCY_TABLE
        ).build()
    }
}