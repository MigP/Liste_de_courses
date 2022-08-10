package be.bf.android.listedecourses

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import be.bf.android.listedecourses.databinding.ActivityMainBinding
import be.bf.android.listedecourses.models.fragments.FragmentCreateList
import be.bf.android.listedecourses.models.fragments.FragmentShowList

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
                    .add(R.id.main_fragment_frame, FragmentCreateList.newInstance(extra.getString("newListName")!!, extra.getString("newListTag")!!))
                    .commit()
            }
        }

        binding.createListFloatingActionButton.setOnClickListener(this::createList)
    }

    fun createList(view: View) { // Refreshes the main activity and sends the relevant extra in intent so that the create list fragment is visible
        val createListIntent = Intent(this, MainActivity::class.java)
        createListIntent.putExtra("targetFragment", "create")
        startActivity(createListIntent)
    }

    fun showLists(view: View) { // Refreshes the main activity and sends the relevant extra in intent so that the show lists fragment is visible
        val showListsIntent = Intent(this, MainActivity::class.java)
        showListsIntent.putExtra("targetFragment", "show")
        startActivity(showListsIntent)
    }
}