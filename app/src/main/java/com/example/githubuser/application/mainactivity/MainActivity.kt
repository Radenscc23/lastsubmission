package com.example.githubuser.application.mainactivity
import com.example.githubuser.R
import com.example.githubuser.adapters.OnItemClickCallback
import com.example.githubuser.adapters.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.api.ClickUser
import com.example.githubuser.api.CreateConnection
import com.example.githubuser.repo.UserRepository
import com.example.githubuser.application.appdetails.DetailUserActivity
import com.example.githubuser.application.sections.favoritesection.FavUserActivity
import com.example.githubuser.application.themes.darkmodesetting.ThemeSet
import com.example.githubuser.application.themes.darkmodesetting.ThemePreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager


class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var _isUserBinding: ActivityMainBinding

    private fun setUpToolbar() { _isUserBinding.toolbar.setOnMenuItemClickListener(this) }
    private fun showLoading(state: Boolean) {
        if (state) {
            _isUserBinding.progressBar.visibility = View.VISIBLE
        } else {
            _isUserBinding.progressBar.visibility = View.GONE } }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.btn_setting -> {
                val setting = Intent(this, ThemeSet::class.java)
                startActivity(setting)
                true
            }
            R.id.btn_favorite -> {
                val favorite = Intent(this, FavUserActivity::class.java)
                startActivity(favorite)
                true
            }
            else -> false } }

    private val adapter: UserAdapter by lazy { UserAdapter() }
    private lateinit var mainViewModel: MainModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _isUserBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_isUserBinding.root)
        setUpToolbar()
        setViewModel()
        darkModeCheck()
        checkInternetConnection()
        setUpSearchView()
    }

    private fun setViewModel(){
        val pref = ThemePreferences.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, MainFactory(pref))[MainModel::class.java]
    }
    private fun darkModeCheck(){
        mainViewModel.getThemeSettings().observe(this@MainActivity,{isDarkModeActive ->
            if (isDarkModeActive) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        })

    }


    private fun setUpSearchView() {
        with(_isUserBinding) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    showLoading(true)
                    UserRepository.getUserBySearch(query)
                    mainViewModel.userSearch.observe(this@MainActivity) { searchUserResponse ->
                        if (searchUserResponse != null) {
                            adapter.addDataToList(searchUserResponse)
                            showLoading(false)
                            _isUserBinding.rvMain.visibility = View.VISIBLE
                            searchView.clearFocus()
                        } }
                    return true }
                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                } }) } }



    private fun checkInternetConnection() {
        val networkConnection = CreateConnection(applicationContext)
        networkConnection.observe(this, { isConnected ->
            if (isConnected) {
                val query = _isUserBinding.searchView.query.toString()
                if (query.isEmpty()) {
                    mainViewModel.userList.observe(this, { userResponse ->
                        if (userResponse != null) {
                            adapter.addDataToList(userResponse)
                            setUserData()
                        }
                    })
                } else {
                    UserRepository.getUserBySearch(query)
                    mainViewModel.userSearch.observe(this@MainActivity) { searchUserResponse ->
                        if (searchUserResponse != null) {
                            adapter.addDataToList(searchUserResponse)
                            _isUserBinding.rvMain.visibility = View.VISIBLE
                        }
                    }
                }
            } else {
                val query = _isUserBinding.searchView.query.toString()
                if (query.isEmpty()) {
                    mainViewModel.userList.observe(this, { userResponse ->
                        if (userResponse != null) {
                            adapter.addDataToList(userResponse)
                            setUserData()
                        }
                    })
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Tidak ada koneksi internet",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }


    private fun hideUserList() {
        _isUserBinding.rvMain.layoutManager = null
        _isUserBinding.rvMain.adapter = null }

    private fun setUserData() {
        with(_isUserBinding) {
            val layoutManager =
                GridLayoutManager(this@MainActivity,1,  GridLayoutManager.VERTICAL, false)
            rvMain.layoutManager = layoutManager
            rvMain.adapter = adapter
            adapter.setOnItemClickCallback(object: OnItemClickCallback {
                override fun onItemClicked(user: ClickUser) {
                    hideUserList()
                    val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.KEY_USER, user)
                    intent.putExtra(DetailUserActivity.KEY_USERNAME, user.login)
                    intent.putExtra(DetailUserActivity.KEY_ID, user.id)
                    startActivity(intent)
                }
            })
        }
    }




}
