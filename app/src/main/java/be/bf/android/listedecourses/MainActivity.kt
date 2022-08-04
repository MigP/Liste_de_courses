package be.bf.android.listedecourses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import be.bf.android.listedecourses.databinding.ActivityMainBinding
import be.bf.android.listedecourses.models.FragmentShowList
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        setTitle(R.string.app_name)

        // Shows fragment
        val fm: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

            transaction
                .add(R.id.frame_main, FragmentShowList.newInstance())
                .commit()

        // Set default language in preferences if none is yet defined
        val prefs = PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )
        val editor = prefs.edit()

        if (prefs.getString("language", "") == "") {
            editor.putString("language", "en")
            editor.apply()
        }

        // Set the correct language according to preferences, dealing with language codes of regional variations (en_gb, en_us, ...)
        if (getResources().getConfiguration().locale.toString().subSequence(0, 2) != prefs.getString("language", "")) {
            setLanguage(prefs.getString("language", "")!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // Creates language menu
        val inflater: MenuInflater = getMenuInflater()
        inflater.inflate(R.menu.language_menu, menu)
        return true
    }

    fun onItemClick(item: MenuItem) { // Language menu click handler
        var lang = "en"
        if (item.toString() == "English") {
            lang = "en"
        } else if (item.toString() == "Français") {
            lang = "fr"
        }

        val prefs = PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )

        if (prefs.getString("language", "") != lang) {
            val editor = prefs.edit()

            editor.putString("language", lang)
            editor.apply()

            setLanguage(lang)
        }
    }

    fun setLanguage(lang: String) { // Function that sets the locale and restarts the activity
        val myLocale = Locale(lang)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(myLocale)
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, SplashActivity::class.java)
        refresh.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        finish()
        startActivity(refresh)
    }
}