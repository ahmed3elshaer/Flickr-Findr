package com.search.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.search.data.local.model.Term
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchTermDao {
    @Query("SELECT * FROM term")
    fun getAll(): Flow<List<Term>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg terms: Term)

    @Query("DELETE FROM term")
    fun deleteAll()
}
