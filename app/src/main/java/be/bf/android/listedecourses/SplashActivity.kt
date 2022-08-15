package be.bf.android.listedecourses

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.listedecourses.dal.CategoriesDAO
import be.bf.android.listedecourses.dal.ListeCoursesDAO
import be.bf.android.listedecourses.dal.ListeListesDAO
import be.bf.android.listedecourses.dal.UnitesDAO
import be.bf.android.listedecourses.databinding.ActivitySplashBinding
import be.bf.android.listedecourses.models.adapters.GeneralListAdapter
import be.bf.android.listedecourses.models.adapters.NewProductListRecycleAdapter
import be.bf.android.listedecourses.models.entities.*
import be.bf.android.listedecourses.models.fragments.FragmentCreateList
import be.bf.android.listedecourses.models.gestures.SwipeGesture
import java.util.*
import kotlin.collections.ArrayList


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            if (resources.configuration.locale.toString().subSequence(0, 2) != prefs.getString("language", "")) {
                setLanguage(prefs.getString("language", "")!!) // <---- This refreshes the activity!!!
            }

        // Detect if the database exists and create it with the default values if it doesn't
            val unitesDAO = UnitesDAO(this)
            unitesDAO.openReadable()
            if (unitesDAO.findAll().size == 0) initialiseDb()

        // Splash title fade in
            val splashTitle: TextView = findViewById<View>(R.id.tv_splashTitle) as TextView
            val titleFadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein10)
            titleFadeIn.startOffset = 500
            splashTitle.startAnimation(titleFadeIn)

        // Splash image fade in
            val splashImg: ImageView = findViewById<View>(R.id.splashImg) as ImageView
            val splashImageAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein10)
            splashImageAnimation.startOffset = 250
            splashImg.startAnimation(splashImageAnimation)

        // Show lists button fade in
            val showListsBtn: Button = findViewById<View>(R.id.showListsBtn) as Button
            val showListsBtnFadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein10)
            showListsBtnFadeIn.startOffset = 1000
            showListsBtn.startAnimation(showListsBtnFadeIn)

        // Create list button fade in
            val createListBtn: Button = findViewById<View>(R.id.createListBtn) as Button
            val createListBtnFadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein10)
            createListBtnFadeIn.startOffset = 1000
            createListBtn.startAnimation(createListBtnFadeIn)

        binding.showListsBtn.setOnClickListener(this::showLists)
        binding.createListBtn.setOnClickListener(this::createList)

        // Creates the RecyclerView that contains the list of lists
            val listeListes = ArrayList<GeneralList>()

            // Fetches info from database
                var listId = 0
                var listItemCount = 0
                var checkedListItemCount = 0

                val listeCoursesDAO = ListeCoursesDAO(this)
                listeCoursesDAO.openReadable()
                val listeListesDAO = ListeListesDAO(this)
                listeListesDAO.openReadable()

                for ((count, listsItem: ListeListes) in listeListesDAO.findAll().withIndex()) { // Database listes_listes table
                    checkedListItemCount = 0

                    listId = listeListesDAO.findId()[count]
                    listItemCount = listeCoursesDAO.findByListId(listId).size

                    for (productsItem: ListeCourses in listeCoursesDAO.findByListId(listId)) { // Database liste_courses table
                        if (productsItem.achete == 1) checkedListItemCount++
                    }

                    var generalList = GeneralList()
                        ?.setListId(listId)
                        ?.setListName(listsItem.listName)
                        ?.setListTag(listsItem.listTag)
                        ?.setListDate(listsItem.date)
                        ?.setListItems(checkedListItemCount.toString() + "/" + listItemCount.toString())
                    if (listItemCount.toString() == checkedListItemCount.toString()) generalList!!.setColour("green") else generalList!!.setColour("red")
                    listeListes.add(generalList!!)
                }

            // Passes the data on to the recyclerview
                var generalListAdapterLayoutManager: RecyclerView.LayoutManager? = null
                var generalListAdapter: RecyclerView.Adapter<GeneralListAdapter.ViewHolder>? = null
                generalListAdapterLayoutManager = LinearLayoutManager(this)
                val recyclerView = findViewById<RecyclerView>(R.id.lists_preview_recycler)
                recyclerView.layoutManager = generalListAdapterLayoutManager
                generalListAdapter = GeneralListAdapter(listeListes, this)
                recyclerView.adapter = generalListAdapter


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // Creates language menu
        val inflater: MenuInflater = menuInflater
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

    private fun setLanguage(lang: String) { // Function that sets the locale and refreshes the activity
        val myLocale = Locale(lang)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(myLocale)
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, SplashActivity::class.java)
        refresh.flags = Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        finish()
        startActivity(refresh)
    }

    private fun createList(view: View) { // Asks the user for the name of the list and its tags, then jumps to the main activity and sends the relevant extra in intent so that the create list fragment is visible
        // Creates an AlertDialog that asks the user to choose a name and a tag for the new list
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.create_list_layout)
            val listName = dialog.findViewById(R.id.et_listName) as EditText
            val listTag = dialog.findViewById(R.id.et_listTag) as EditText
            val createBtn = dialog.findViewById(R.id.createListBtn) as Button
            val cancelBtn = dialog.findViewById(R.id.cancelBtn) as Button
            createBtn.setOnClickListener {
                // Validates input fields
                if (listName.text.toString().isEmpty()) { // Data field validation
                    Toast.makeText(this, R.string.you_must_enter_name, Toast.LENGTH_SHORT).show()
                } else if (listTag.text.toString().isEmpty()) { // Data field validation
                    Toast.makeText(this, R.string.you_must_enter_tag, Toast.LENGTH_SHORT).show()
                } else {
                    dialog.dismiss()
                    val createListIntent = Intent(this, MainActivity::class.java)
                    createListIntent.putExtra("targetFragment", "create")
                    createListIntent.putExtra("fragmentMode", "listCreation")
                    createListIntent.putExtra("listId", 0)
                    createListIntent.putExtra("newListName", listName.text.toString())
                    createListIntent.putExtra("newListTag", listTag.text.toString())
                    startActivity(createListIntent)
                }
            }
            cancelBtn.setOnClickListener { dialog.cancel() }
            dialog.show()
    }

    private fun showLists(view: View) { // Jumps to the main activity and sends the relevant extra in intent so that the show lists fragment is visible
        val showListsIntent = Intent(this, MainActivity::class.java)
        showListsIntent.putExtra("targetFragment", "show")
        startActivity(showListsIntent)
    }

    private fun initialiseDb() {
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
            categoriesDAO.insert(Categories("Légumes", "Vegetables", "lettuce"))
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

