package com.example.flickfinder.ui.home


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.flickfinder.data.CastCrewResponse
import com.example.flickfinder.data.DetailsResponse

import com.example.flickfinder.data.Release
import com.example.flickfinder.domain.WatchmodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WatchmodeRepository
) : ViewModel() {

    private val _releases = MutableStateFlow<List<Release>>(emptyList())
    val releases: StateFlow<List<Release>> = _releases

    // State to store the selected release details
    private val _releaseDetails = MutableStateFlow<DetailsResponse?>(null)
    val releaseDetails: StateFlow<DetailsResponse?> = _releaseDetails


    private val _castAndCrew = MutableStateFlow<List<CastCrewResponse>>(emptyList())
    val castAndCrew: StateFlow<List<CastCrewResponse>> = _castAndCrew


    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading


    private val _loadingdetail = MutableStateFlow(false)
    val loadingdetail: StateFlow<Boolean> = _loadingdetail

    fun loadReleases(apiKey: String) {
        _loading.value = true // Start loading
        repository.fetchMoviesAndTvShows(apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                _releases.value = result
                _loading.value = false // Loading finished
            }, { error ->
                Log.e("HomeViewModel", "Error fetching releases", error)
                _loading.value = false // Loading finished with error
            })
    }

    // Fetching details for a specific release by ID
    fun loadDetail(id: Int, apiKey: String) {
        _loadingdetail.value = true
        repository.fetchDetails(id, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                // Storing the fetched details in _releaseDetails
                _releaseDetails.value = result
                _loadingdetail.value = false
            }, { error ->
                Log.e("HomeViewModel", "Error fetching release details", error)
                _loading.value = false
            })
    }


    fun loadCastAndCrew(id: Int, apiKey: String) {
        repository.fetchCastAndCrew(id, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                _castAndCrew.value = result
            }, { error ->
                Log.e("HomeViewModel", "Error fetching cast and crew", error)
            })
    }

}
