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
import androidx.appcompat.app.AppCompatActivity
import be.bf.android.listedecourses.dal.CategoriesDAO
import be.bf.android.listedecourses.dal.UnitesDAO
import be.bf.android.listedecourses.databinding.ActivitySplashBinding
import be.bf.android.listedecourses.models.entities.Categories
import be.bf.android.listedecourses.models.entities.Unites
import java.util.*


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        setTitle(R.string.app_name)

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

        // Splash title fade in
            val splashTitle: TextView = findViewById<View>(R.id.tv_splashTitle) as TextView
            val titleFadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein10)
            titleFadeIn.setStartOffset(1000)
            splashTitle.startAnimation(titleFadeIn)

        // Splash image fade in
            val splashImg: ImageView = findViewById<View>(R.id.splashImg) as ImageView
            val splashImageAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein10)
            splashImageAnimation.setStartOffset(500)
            splashImg.startAnimation(splashImageAnimation)

        // Show lists button fade in
            val showListsBtn: Button = findViewById<View>(R.id.showListsBtn) as Button
            val showListsBtnFadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein20)
            showListsBtnFadeIn.setStartOffset(1500)
            showListsBtn.startAnimation(showListsBtnFadeIn)

        // Create list button fade in
            val createListBtn: Button = findViewById<View>(R.id.createListBtn) as Button
            val createListBtnFadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein20)
            createListBtnFadeIn.setStartOffset(1500)
            createListBtn.startAnimation(createListBtnFadeIn)

        binding.createListBtn.setOnClickListener(this::createList)
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

    fun createList(view: View) {

        // Populate Table unites
//        val unitesDAO = UnitesDAO(this)
//        unitesDAO.openWritable()
//        unitesDAO.insert(Unites("mg"))
//        unitesDAO.insert(Unites("g"))
//        unitesDAO.insert(Unites("kg"))
//        unitesDAO.insert(Unites("ml"))
//        unitesDAO.insert(Unites("cl"))
//        unitesDAO.insert(Unites("l"))
//        unitesDAO.insert(Unites("unit"))

        // Populate Table categories
//        val categoriesDAO = CategoriesDAO(this)
//        categoriesDAO.openWritable()
//        categoriesDAO.insert(
//            Categories(
//                "fruits"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "vegetables"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "canned_goods"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "frozen_food"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "regional_products"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "meat"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "fish_and_seafood"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "dairy_products"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "condiments_and_spices"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "sauces_and_oils"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "snacks"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "bread_and_bakery"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "beverages"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "pasta_and_rice"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "cereals"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "baking"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "personal_care"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "health_care"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "paper_and_wrap"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "household_supplies"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "baby_items"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "pet_supplies"
//            )
//        )
//        categoriesDAO.insert(
//            Categories(
//                "other_items"
//            )
//        )

        val loginIntent = Intent(this, MainActivity::class.java)
        startActivity(loginIntent)
    }
}

