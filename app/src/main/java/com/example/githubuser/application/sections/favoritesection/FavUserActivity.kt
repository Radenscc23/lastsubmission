package com.example.githubuser.application.sections.favoritesection
import com.example.githubuser.adapters.SettingAdapter
import com.example.githubuser.repo.Favorite
import com.example.githubuser.databinding.UserFavoriteActivityBinding
import com.example.githubuser.application.appdetails.DetailUserActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager


class FavUserActivity : AppCompatActivity() {
    private val bindingActivity get() = _isBinding
    private lateinit var favModel: FavUserModel
    private var _isBinding: UserFavoriteActivityBinding? = null
    private val chooseAdapter: SettingAdapter by lazy { SettingAdapter() }


    private fun obtainViewModel(activity: AppCompatActivity): FavUserModel {
        val factory = FavFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavUserModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _isBinding = UserFavoriteActivityBinding.inflate(layoutInflater)
        setContentView(bindingActivity?.root)
        favModel = obtainViewModel(this@FavUserActivity)
        setUpList()
        setUserFavorite() }


    private fun setUpList() {
        with(bindingActivity) {
            val layoutManager = LinearLayoutManager(this@FavUserActivity)
            this?.rvFavorite?.layoutManager = layoutManager
            val itemDecoration =
                DividerItemDecoration(this@FavUserActivity, layoutManager.orientation)
            this?.rvFavorite?.addItemDecoration(itemDecoration)
            this?.rvFavorite?.adapter = chooseAdapter
            chooseAdapter.setOnItemClickCallback(object : SettingAdapter.OnItemClickCallback {
                override fun onItemClicked(favEntity: Favorite) {
                    val intent = Intent(this@FavUserActivity, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.KEY_USERNAME, favEntity.login)
                    intent.putExtra(DetailUserActivity.KEY_ID, favEntity.id)
                    startActivity(intent)
                } }) } }
    override fun onDestroy() {
        super.onDestroy()
        _isBinding = null
    }
    private fun setUserFavorite() {
        favModel = obtainViewModel(this@FavUserActivity)
        favModel.getAllFavorites().observe(this@FavUserActivity, { favList ->
            if (favList != null) {
                chooseAdapter.setListFavorite(favList)
            } }) }

}