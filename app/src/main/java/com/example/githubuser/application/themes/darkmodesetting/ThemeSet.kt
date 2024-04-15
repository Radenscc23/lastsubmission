package com.example.githubuser.application.themes.darkmodesetting
import com.example.githubuser.databinding.DarkmodeSettingBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider

class ThemeSet : AppCompatActivity() {
    private val appData: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var _isBinding: DarkmodeSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _isBinding = DarkmodeSettingBinding.inflate(layoutInflater)
        setContentView(_isBinding.root)
        val pref = ThemePreferences.getInstance(appData)
        val viewModel = ViewModelProvider(
            this,
            ThemeFactory(pref)
        ).get(ThemeModel::class.java)

        viewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    _isBinding.switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    _isBinding.switchTheme.isChecked = false } })

        _isBinding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            _isBinding.switchTheme.isChecked = isChecked
            viewModel.saveThemeSetting(isChecked) } }
}