package com.example.todolistapp.DI

import android.content.Context
import com.example.todolistapp.Database.TaskDao
import com.example.todolistapp.Database.TaskDatabase
import com.example.todolistapp.TaskApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): TaskApplication {
        return app as TaskApplication
    }
    //provides instance of the db to the  view model through injection
    @Provides
    @Singleton
    fun provideDatabase(application: TaskApplication): TaskDatabase {
        return TaskDatabase.getDatabase(application)
    }
}