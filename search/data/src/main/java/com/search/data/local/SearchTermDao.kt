package com.search.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.search.data.local.model.SearchTermLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchTermDao {
    @Query("SELECT * FROM term")
    fun getAll(): Flow<List<SearchTermLocal>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg terms: SearchTermLocal)

    @Query("DELETE FROM term")
    fun deleteAll()

    @Delete
    fun delete(searchTerm: SearchTermLocal)
}
