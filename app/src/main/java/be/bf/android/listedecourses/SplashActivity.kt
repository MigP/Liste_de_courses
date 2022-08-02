package be.bf.android.listedecourses

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import be.bf.android.listedecourses.databinding.ActivitySplashBinding
import java.util.*


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        setTitle(R.string.app_name)

        // Set default language
            val prefs = PreferenceManager.getDefaultSharedPreferences(
                applicationContext
            )
            val editor = prefs.edit()

            if (prefs.getString("language", "") == "") {
                editor.putString("language", "en")
                editor.apply()
            }

        // Splash title fade in
        val splashTitle: TextView = findViewById<View>(R.id.tv_splashTitle) as TextView
        val titleFadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein10)
        splashTitle.startAnimation(titleFadeIn)

        // Splash image fade in
        val myImageView: ImageView = findViewById<View>(R.id.splashImg) as ImageView
        val myFadeInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein15)
        myImageView.startAnimation(myFadeInAnimation)

        // Splash button fade in
        val splashBtn: Button = findViewById<View>(R.id.splashBtn) as Button
        val buttonFadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein20)
        splashBtn.startAnimation(buttonFadeIn)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // Language menu
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
        Toast.makeText(this, "Prefs: " + prefs.getString("language", "") + "\nSelected:" + lang, Toast.LENGTH_LONG).show()
        if (prefs.getString("language", "") !== lang) {
            val editor = prefs.edit()

            editor.putString("language", lang)
            editor.apply()
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
}

