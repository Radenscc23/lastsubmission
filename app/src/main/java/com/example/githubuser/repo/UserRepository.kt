package com.example.githubuser.repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.githubuser.api.ClickSearch
import com.example.githubuser.api.ClickUser
import com.example.githubuser.api.ApiConfig

object UserRepository {
    val _isUser = MutableLiveData<ArrayList<ClickUser>?>()
    val _isSearch = MutableLiveData<ArrayList<ClickUser>?>()
    val _isLoading = MutableLiveData<Boolean>()
    val _isFailed = MutableLiveData<Boolean>()
    var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private const val TAG = "UserRepo"
    suspend fun getListUser() {
        coroutineScope.launch {
            _isLoading.value = true
            val getUserDeferred = ApiConfig.getApiService().getUserListAsync()
            try {
                _isLoading.value = false
                _isFailed.value = false
                _isUser.postValue(getUserDeferred) } catch (e: Exception) {
                _isLoading.value = false
                _isFailed.value = true
                Log.e(TAG, "onFailure: ${e.message.toString()}") } }
    }

    fun getUserBySearch(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserBySearch(user)
        client.enqueue(object : Callback<ClickSearch> {
            override fun onResponse(
                call: Call<ClickSearch>,
                response: Response<ClickSearch>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isFailed.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.items != null) {
                            _isSearch.postValue(responseBody.items)
                        }
                    } } }

            override fun onFailure(call: Call<ClickSearch>, t: Throwable) {
                _isLoading.value = false
                _isFailed.value = true
                Log.e("UserRepo", "onFailure: ${t.message.toString()}")
            }
        }) } }