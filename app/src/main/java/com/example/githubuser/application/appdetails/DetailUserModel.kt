package com.example.githubuser.application.appdetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.repo.Favorite
import com.example.githubuser.api.ClickUser
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.repo.SettingRepository


class DetailUserModel(username: String, app: Application) : ViewModel() {
    private val mFavoriteRepository: SettingRepository = SettingRepository(app)
    private val _userDetail = MutableLiveData<ClickUser?>()
    val detailUser: LiveData<ClickUser?> = _userDetail
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isDataFailed = MutableLiveData<Boolean>()
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    init {
        viewModelScope.launch { getDetailUser(username) }
        Log.i(TAG, "DetailViewModel is Created") }

    private suspend fun getDetailUser(username: String) {
        Log.d(TAG, "getDetailUser called")
        coroutineScope.launch {
            _isLoading.value = true
            val getUserDetailDeferred = ApiConfig.getApiService().getDetailUserAsync(username)
            try {
                _isLoading.value = false
                _isDataFailed.value = false
                _userDetail.postValue(getUserDetailDeferred) }
            catch (e: Exception) {
                _isLoading.value = false
                _isDataFailed.value = true
                Log.e(TAG, "onFailure: ${e.message.toString()}")
            } } }

    fun insert(favEntity: Favorite) { mFavoriteRepository.insert(favEntity) }

    fun delete(favEntity: Favorite) { mFavoriteRepository.delete(favEntity) }

    fun getFavoriteById(id: Int): LiveData<List<Favorite>> { return mFavoriteRepository.getuserById(id) }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

}