package com.example.flickfinder.domain

import com.example.flickfinder.data.CastCrewResponse
import com.example.flickfinder.data.DetailsResponse
import com.example.flickfinder.data.Release
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchmodeRepository @Inject constructor(
    private val apiService: WatchmodeApiService
) {
    fun fetchMoviesAndTvShows(apiKey: String): Single<List<Release>> {
        return apiService.getReleases(apiKey)
            .map { response -> response.releases }
    }

    fun fetchDetails(id: Int, apiKey: String): Single<DetailsResponse> {
        return apiService.getDetails(id, apiKey)
    }


    fun fetchCastAndCrew(id: Int, apiKey: String): Single<List<CastCrewResponse>> {
        return apiService.getCastAndCrew(id, apiKey)
    }
}
