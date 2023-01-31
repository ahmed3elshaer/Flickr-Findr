package com.search.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.search.data.local.model.SearchTermLocal

@Dao
interface SearchTermDao {
    @Query("SELECT * FROM term")
    suspend fun getAll(): List<SearchTermLocal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg terms: SearchTermLocal)

    @Query("DELETE FROM term")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(searchTerm: SearchTermLocal)
}
