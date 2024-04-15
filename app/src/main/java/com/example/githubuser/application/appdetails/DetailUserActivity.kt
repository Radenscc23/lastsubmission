package com.example.githubuser.application.appdetails
import com.example.githubuser.R
import com.example.githubuser.adapters.SectionPagerAdapter
import com.example.githubuser.repo.Favorite
import com.example.githubuser.databinding.DetailUserBinding
import com.example.githubuser.api.ClickUser
import com.example.githubuser.api.CreateConnection
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserActivity : AppCompatActivity() {

    private lateinit var _isBinding: DetailUserBinding
    private var _isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _isBinding = DetailUserBinding.inflate(layoutInflater)
        setContentView(_isBinding.root)
        _isBinding.detailDataLayout.visibility = View.GONE
        val username = intent.getStringExtra(KEY_USERNAME)
        username?.let{
            showLoading(true) // Show progress bar initially
            checkInternetConnection(it) } }

    private fun checkInternetConnection(username: String) {
        val user = intent.getParcelableExtra<ClickUser>(KEY_USER)
        val networkConnection = CreateConnection(applicationContext)

        networkConnection.observe(this) { isConnected ->
            if (isConnected) {
                showLoading(true)
                val favorite = Favorite().apply {
                    login = username
                    id = intent.getIntExtra(KEY_ID, 0)
                    avatar_url = user?.avatarUrl }

                val detailViewModel: DetailUserModel by viewModels {
                    DetailFactory(username, application) }
                detailViewModel.isLoading.observe(this@DetailUserActivity) {
                    showLoading(it) }
                detailViewModel.detailUser.observe(this@DetailUserActivity) { userResponse ->
                    if (userResponse != null) {
                        setData(userResponse)
                        setTabLayoutAdapter(userResponse) } }
                detailViewModel.getFavoriteById(favorite.id!!)
                    .observe(this@DetailUserActivity) { listFav ->
                        _isFavorite = listFav.isNotEmpty()

                        _isBinding.detailFabFavorite.imageTintList = if (listFav.isEmpty()) {
                            ColorStateList.valueOf(Color.rgb(255, 255, 255))
                        } else {
                            ColorStateList.valueOf(Color.rgb(247, 106, 123))
                        } }
                _isBinding.detailFabFavorite.setOnClickListener {
                    if (_isFavorite) {
                        detailViewModel.delete(favorite)
                        Toast.makeText(
                            this@DetailUserActivity,
                            "${favorite.login} telah dipindahkan dari favorit",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        detailViewModel.insert(favorite)
                        Toast.makeText(
                            this@DetailUserActivity,
                            "${favorite.login} sudah ditambah di favorit",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                _isBinding.detailDataLayout.visibility = View.GONE
                _isBinding.detailAnimationLayout.visibility = View.VISIBLE } } }
    private fun setTabLayoutAdapter(user: ClickUser) {
        val sectionPagerAdapter = SectionPagerAdapter(this@DetailUserActivity)
        sectionPagerAdapter.model = user
        _isBinding.detailViewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(_isBinding.detailTabs, _isBinding.detailViewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    private fun setData(userResponse: ClickUser?) {
        if (userResponse != null) {
            with(_isBinding) {
                detailDataLayout.visibility = View.VISIBLE
                detailImage.visibility = View.VISIBLE
                Glide.with(root)
                    .load(userResponse.avatarUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.loading_icon)
                            .error(R.drawable.bg_error_theme)
                    )
                    .circleCrop()
                    .into(_isBinding.detailImage)
                detailName.visibility = View.VISIBLE
                detailUsername.visibility = View.VISIBLE
                detailName.text = userResponse.name
                detailUsername.text = userResponse.login
                if (userResponse.followers != null) {
                    detailFollowersValue.visibility = View.VISIBLE
                    detailFollowersValue.text = userResponse.followers
                } else {
                    detailFollowersValue.visibility = View.GONE
                }
                if (userResponse.followers != null) {
                    detailFollowers.visibility = View.VISIBLE
                } else {
                    detailFollowers.visibility = View.GONE
                }
                if (userResponse.following != null) {
                    detailFollowingValue.visibility = View.VISIBLE
                    detailFollowingValue.text = userResponse.following
                } else {
                    detailFollowingValue.visibility = View.GONE
                }
                if (userResponse.following != null) {
                    detailFollowing.visibility = View.VISIBLE
                } else {
                    detailFollowing.visibility = View.GONE
                }
            }
        } else {
            Log.i(TAG, "setData function is error")
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            _isBinding.progressBar.visibility = View.VISIBLE
        } else {
            _isBinding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val KEY_USER = "user"
        private const val TAG = "DetailActivity"
        const val KEY_USERNAME = "username"
        const val KEY_ID = "extra id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.fragment_one,
            R.string.fragment_two
        )
    }

}