package com.sagrd.kevin_p1_ap2.di

import android.content.Context
import androidx.room.Room
import com.sagrd.kevin_p1_ap2.data.local.DivisionDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)

object AppModule {
    @Provides
    fun providesDivisionDao(db:DivisionDB) = db.divisionDao()

    @Provides
    @Singleton
    fun providesDivisionDatabase(@ApplicationContext appContext : Context) :DivisionDB =
        Room.databaseBuilder(
            appContext,
            DivisionDB::class.java,
            "Division.db")
            .fallbackToDestructiveMigration()
            .build()
}