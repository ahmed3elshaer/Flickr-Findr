package com.search.domain.usecase.impl

import com.search.domain.usecase.GetAllSearchTerms
import com.search.domain.usecase.SearchPhotosByText
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(ViewModelComponent::class)
@Module
internal abstract class SearchDomainModule {
    @Binds
    abstract fun bindGetAllSearchTerms(
        getAllSearchTermsImpl: GetAllSearchTermsImpl
    ): GetAllSearchTerms

    @Binds
    abstract fun bindSearchPhotosByText(
        searchPhotosByTextImpl: SearchPhotosByTextImpl
    ): SearchPhotosByText
}
