package com.seback.moviedbcompose.discover.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DiscoverLatestPagingSource @Inject constructor(
    private val discoverRepository: Repository.Discover
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPage = params.key ?: 1
        val response = discoverRepository.latest(nextPage).first()
        if (response is Response.Success) {
            return LoadResult.Page(
                data = response.data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.data.isEmpty()) null else nextPage + 1
            )
        }
        if (response is Response.Error) {
            return LoadResult.Error(
                response.throwable ?: Throwable(response.message)
            )
        }
        return LoadResult.Error(Throwable("unknown"))
    }
}