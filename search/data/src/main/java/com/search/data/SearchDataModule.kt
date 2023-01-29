package com.search.data

import android.content.Context
import androidx.room.Room
import com.search.data.local.SearchTermDao
import com.search.data.local.SearchTermDatabase
import com.search.data.remote.SearchPhotosRemote
import com.search.domain.repository.PhotosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SearchDataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SearchTermDatabase =
        Room.databaseBuilder(
            context,
            SearchTermDatabase::class.java,
            "SEARCH_TERM_DATABASE"
        ).build()

    @Provides
    fun provideDao(database: SearchTermDatabase): SearchTermDao {
        return database.searchTermDao()
    }

    @Provides
    fun provideSearchPhotosRemote(httpClient: HttpClient): SearchPhotosRemote {
        return SearchPhotosRemote(httpClient = httpClient)
    }

    @Provides
    fun provideSearchPhotosRepository(
        searchPhotosRemote: SearchPhotosRemote
    ): PhotosRepository {
        return PhotosRepositoryImpl(
            searchPhotosRemote = searchPhotosRemote
        )
    }
}
