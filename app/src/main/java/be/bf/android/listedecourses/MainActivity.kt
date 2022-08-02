package be.bf.android.listedecourses

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import be.bf.android.listedecourses.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTitle(R.string.app_name)
        setContentView(binding.getRoot())

        // Set default language
            val prefs = PreferenceManager.getDefaultSharedPreferences(
                applicationContext
            )
            val editor = prefs.edit()

            if (prefs.getString("language", "") == "") {
                editor.putString("language", "en")
                editor.apply()
            }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // Language menu
        val inflater: MenuInflater = getMenuInflater()
        inflater.inflate(R.menu.language_menu, menu)
        return true
    }

    fun onItemClick(item: MenuItem) { // Language menu click handler
        var lang = ""
        if (item.toString() == "English") {
            lang = "en"
        } else if (item.toString() == "Français") {
            lang = "fr"
        }
        val prefs = PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )

        val editor = prefs.edit()
        editor.putString("language", lang)
        editor.apply()
        val myLocale = Locale(lang)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(myLocale)
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, MainActivity::class.java)
        refresh.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        finish()
        startActivity(refresh)
    }
}

