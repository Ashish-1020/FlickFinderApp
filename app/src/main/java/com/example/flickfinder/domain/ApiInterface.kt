package com.example.flickfinder.domain


import com.example.flickfinder.data.CastCrewResponse
import com.example.flickfinder.data.DetailsResponse
import com.example.flickfinder.data.WatchmodeResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WatchmodeApiService {
    @GET("releases")
    fun getReleases(
        @Query("apiKey") apiKey: String
    ): Single<WatchmodeResponse>

    @GET("title/{id}/details/")
    fun getDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String,
        @Query("append_to_response") appendToResponse: String = "sources"
    ): Single<DetailsResponse>




    @GET("title/{id}/cast-crew/")
    fun getCastAndCrew(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): Single<List<CastCrewResponse>>
}
