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
import be.bf.android.listedecourses.dal.ListeCoursesDAO
import be.bf.android.listedecourses.dal.ListeListesDAO
import be.bf.android.listedecourses.dal.UnitesDAO
import be.bf.android.listedecourses.databinding.ActivitySplashBinding
import be.bf.android.listedecourses.models.entities.Categories
import be.bf.android.listedecourses.models.entities.ListeCourses
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
            titleFadeIn.setStartOffset(500)
            splashTitle.startAnimation(titleFadeIn)

        // Splash image fade in
            val splashImg: ImageView = findViewById<View>(R.id.splashImg) as ImageView
            val splashImageAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein10)
            splashImageAnimation.setStartOffset(250)
            splashImg.startAnimation(splashImageAnimation)

        // Show lists button fade in
            val showListsBtn: Button = findViewById<View>(R.id.showListsBtn) as Button
            val showListsBtnFadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein10)
            showListsBtnFadeIn.setStartOffset(1000)
            showListsBtn.startAnimation(showListsBtnFadeIn)

        // Create list button fade in
            val createListBtn: Button = findViewById<View>(R.id.createListBtn) as Button
            val createListBtnFadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein10)
            createListBtnFadeIn.setStartOffset(1000)
            createListBtn.startAnimation(createListBtnFadeIn)

        binding.showListsBtn.setOnClickListener(this::showLists)
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

    fun createList(view: View) { // Jumps to the main activity and sends the relevant extra in intent so that the create list fragment is visible
        val createListIntent = Intent(this, MainActivity::class.java)
        createListIntent.putExtra("targetFragment", "create")
        startActivity(createListIntent)
    }

    fun showLists(view: View) { // Jumps to the main activity and sends the relevant extra in intent so that the show lists fragment is visible
        val showListsIntent = Intent(this, MainActivity::class.java)
        showListsIntent.putExtra("targetFragment", "show")
        startActivity(showListsIntent)

//        initialPopulateDb()
//        val listeCoursesDAO = ListeCoursesDAO(this)
//        listeCoursesDAO.openWritable()
//        listeCoursesDAO.insert(ListeCourses(1, "Apples", 8, 7, 1, 2, 3, 0))
//        println(listeCoursesDAO.toString())
    }

    fun initialPopulateDb() {
        // Populate Table unites
            val unitesDAO = UnitesDAO(this)
            unitesDAO.openWritable()
            unitesDAO.insert(Unites("mg", "mg"))
            unitesDAO.insert(Unites("g", "g"))
            unitesDAO.insert(Unites("kg", "kg"))
            unitesDAO.insert(Unites("ml", "ml"))
            unitesDAO.insert(Unites("cl", "cl"))
            unitesDAO.insert(Unites("l", "l"))
            unitesDAO.insert(Unites("unités", "units"))

        // Populate Table categories
            val categoriesDAO = CategoriesDAO(this)
            categoriesDAO.openWritable()
            categoriesDAO.insert(Categories("Fruits", "Fruit", "apple"))
            categoriesDAO.insert(Categories("Conserves", "Canned Goods", "canned_food"))
            categoriesDAO.insert(Categories("Surgelés", "Frozen Food", "frozen_food"))
            categoriesDAO.insert(Categories("Produits régionaux", "Regional products", "noodle"))
            categoriesDAO.insert(Categories("Viande", "Meat", "pork_leg"))
            categoriesDAO.insert(Categories("Poissons et crustacés", "Fish and shellfish", "seafood"))
            categoriesDAO.insert(Categories("Produits laitiers", "Dairy products", "dairy_products"))
            categoriesDAO.insert(Categories("Condiments et épices", "Condiments and spices", "salt_and_pepper"))
            categoriesDAO.insert(Categories("Sauces et huiles", "Sauces and oils", "sauce_bottle"))
            categoriesDAO.insert(Categories("Snacks", "Snacks","snack"))
            categoriesDAO.insert(Categories("Pain et boulangerie", "Bread and bakery", "breads"))
            categoriesDAO.insert(Categories("Boissons", "Beverages", "mineral_water"))
            categoriesDAO.insert(Categories("Pâtes et riz", "Pasta and rice", "pasta"))
            categoriesDAO.insert(Categories("Céréales", "Cereals", "cereals"))
            categoriesDAO.insert(Categories("Pâtisserie", "Baking", "cupcake"))
            categoriesDAO.insert(Categories("Soins personnels", "Personal care", "toiletries"))
            categoriesDAO.insert(Categories("Soins de santé", "Health care", "alcohol"))
            categoriesDAO.insert(Categories("Papier et emballage", "Paper and wrap", "paper_towel"))
            categoriesDAO.insert(Categories("Articles ménagers", "Household supplies", "cleaning_products"))
            categoriesDAO.insert(Categories("Articles pour bébé", "Baby items", "baby"))
            categoriesDAO.insert(Categories("Animaux domestiques", "Pet supplies", "pet"))
            categoriesDAO.insert(Categories("Autres articles", "Other items", "more"))
    }
}

