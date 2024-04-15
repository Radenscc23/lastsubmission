package com.example.githubuser.application.sections.fragmentsection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.api.ClickUser
import com.example.githubuser.api.ApiConfig


class FragmentModel(username: String) : ViewModel() {
    private val _isDataFailed = MutableLiveData<Boolean>()
    val userDataFailed: LiveData<Boolean> = _isDataFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val userLoading: LiveData<Boolean> = _isLoading
    private val _isFollowers = MutableLiveData<ArrayList<ClickUser>?>()
    val followerList: LiveData<ArrayList<ClickUser>?> = _isFollowers
    private val _isFollowing = MutableLiveData<ArrayList<ClickUser>?>()
    val followingList: LiveData<ArrayList<ClickUser>?> = _isFollowing
    private var viewModelJob = Job()
    private val kotlinCoroutine = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        viewModelScope.launch {
            getFollowersList(username)
            getFollowingList(username) }
        Log.i(TAG, "FollFragment is Created") }

    private suspend fun getFollowersList(username: String) {
        kotlinCoroutine.launch {
            _isLoading.value = true
            val result = ApiConfig.getApiService().getFollowersList(username)
            try{
                _isLoading.value = false
                _isFollowers.postValue(result)
            }catch (e: Exception){
                _isLoading.value = false
                _isDataFailed.value = true
                Log.e(TAG, "OnFailure: ${e.message.toString()}")
            } } }

    private suspend fun getFollowingList(username: String) {
        kotlinCoroutine.launch {
            _isLoading.value = true
            val result = ApiConfig.getApiService().getFollowingList(username)
            try{
                _isLoading.value = false
                _isFollowing.postValue(result)
            }catch (e: Exception){
                _isLoading.value = false
                _isDataFailed.value = true
                Log.e(TAG, "OnFailure: ${e.message.toString()}")
            } } }
    companion object { private const val TAG = "FollowsViewModel" }
}