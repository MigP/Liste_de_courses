package be.bf.android.listedecourses

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import be.bf.android.listedecourses.databinding.ActivityMainBinding
import be.bf.android.listedecourses.models.FragmentCreateList
import be.bf.android.listedecourses.models.FragmentShowList
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        setTitle(R.string.app_name)

        // Gets the extra info contained in the intent and decides which fragment to display
            val extra = this.intent.extras

        // Shows fragment
            if (extra != null) {
                val f = extra.getString("targetFragment")

                if (f.equals("show")) {
                    binding.createListFloatingActionButton.show()
                    val fm: FragmentManager = supportFragmentManager
                    val transaction: FragmentTransaction = fm.beginTransaction()

                    transaction
                        .add(R.id.main_fragment_frame, FragmentShowList.newInstance())
                        .commit()
                } else if (f.equals("create")){
                    binding.createListFloatingActionButton.hide()
                    val fm: FragmentManager = supportFragmentManager
                    fm.setFragmentResultListener("requestKey", this) { key, bundle ->
                        val result = bundle.getString("createFragmentData")
                        if (result.equals("cancelCreate")) { // Show splash screen
                            val homeIntent = Intent(this, SplashActivity::class.java)
                            startActivity(homeIntent)
                        }
                     }
                    val transaction: FragmentTransaction = fm.beginTransaction()

                    transaction
                        .add(R.id.main_fragment_frame, FragmentCreateList.newInstance())
                        .commit()
                }
            }

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

        binding.createListFloatingActionButton.setOnClickListener(this::createList)
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

    fun createList(view: View) { // Jumps to the main activity and sends the relevant extra in intent so that the create list fragment is visible
        val createListIntent = Intent(this, MainActivity::class.java)
        createListIntent.putExtra("targetFragment", "create")
        startActivity(createListIntent)
    }

    fun showLists(view: View) { // Jumps to the main activity and sends the relevant extra in intent so that the create list fragment is visible
        val showListsIntent = Intent(this, MainActivity::class.java)
        showListsIntent.putExtra("targetFragment", "show")
        startActivity(showListsIntent)
    }
}