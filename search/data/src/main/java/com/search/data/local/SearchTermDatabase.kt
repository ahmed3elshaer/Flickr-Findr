package com.search.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.search.data.local.model.SearchTermLocal

@Database(entities = [SearchTermLocal::class], version = 1)
abstract class SearchTermDatabase : RoomDatabase() {
    abstract fun searchTermDao(): SearchTermDao
}
