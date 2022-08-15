package be.bf.android.listedecourses

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import be.bf.android.listedecourses.dal.ListeListesDAO
import be.bf.android.listedecourses.dal.UnitesDAO
import be.bf.android.listedecourses.databinding.ActivityMainBinding
import be.bf.android.listedecourses.models.fragments.FragmentCreateList
import be.bf.android.listedecourses.models.fragments.FragmentShowList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle(R.string.app_name)

        // Gets the extra info contained in the intent and decides which fragment to display
            val extra = this.intent.extras

        // Shows fragment
            if (extra != null) {
            val targetFragment = extra.getString("targetFragment") // Recovers information about which of the two fragments will be displayed (create or show)

            if (targetFragment.equals("show")) {
                binding.createListFloatingActionButton.show()
                val fm: FragmentManager = supportFragmentManager
                val transaction: FragmentTransaction = fm.beginTransaction()
                val showListFragment = FragmentShowList.newInstance()

                transaction
                    .add(R.id.main_fragment_frame, showListFragment)
                    .commit()
            } else if (targetFragment.equals("create")){
                var listName = ""
                var listTag = ""
                var listId = 0
                binding.createListFloatingActionButton.hide()

                val fragmentMode = extra.getString("fragmentMode") // Recovers information about which mode is active (create a new list or show/edit an existing one)
                val fm: FragmentManager = supportFragmentManager
                val transaction: FragmentTransaction = fm.beginTransaction()

                if (fragmentMode.equals("listCreation")) { // This mode is for the creation of a new list
                    binding.backFloatingActionButton.hide()
                    listName = extra.getString("newListName")!!
                    listTag = extra.getString("newListTag")!!


                } else if (fragmentMode.equals("listViewing")) { // This mode is for the viewing and edition of an existing list

                    //TODO Get list name and tag from database and pass it to new fragment
                    val listeListesDAO = ListeListesDAO(this)
                    listeListesDAO.openReadable()
                    listId = extra.getString("listId")?.toInt()!! // Recovers information about the id of the list which was clicked on
                    listName = listeListesDAO.findById(listId)[0].listName
                    listTag = listeListesDAO.findById(listId)[0].listTag

                }

                val createListFragment = FragmentCreateList.newInstance(listName, listTag, listId.toString())

                transaction
                    .add(R.id.main_fragment_frame, createListFragment)
                    .commit()

                // Adds a listener to the create fragment for when the cancel button is clicked
                    fm.setFragmentResultListener("requestKey", this) { key, bundle ->
                        val result = bundle.getString("createFragmentData")
                        if (result.equals("cancelCreate")) { // Return to the splash screen
                            val homeIntent = Intent(this, SplashActivity::class.java)
                            startActivity(homeIntent)
                        }
                    }
            }
        }

        // ----------- TEST - replaces fragment with another
//        val transaction2: FragmentTransaction = fm.beginTransaction()
//        transaction2.replace(R.id.main_fragment_frame, FragmentShowList.newInstance())
//        transaction2.addToBackStack(null)
//        transaction2.commit()
        // ----------- TEST -----------------------------------



        binding.createListFloatingActionButton.setOnClickListener(this::createList)
        binding.backFloatingActionButton.setOnClickListener(this::goHome)
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
                    createListIntent.putExtra("newListName", listName.text.toString())
                    createListIntent.putExtra("newListTag", listTag.text.toString())
                    startActivity(createListIntent)
                }
            }
            cancelBtn.setOnClickListener { dialog.cancel() }
            dialog.show()
    }

    private fun goHome(view: View) { // Goes back to the splash screen
        val goHomeIntent = Intent(this, SplashActivity::class.java)
        startActivity(goHomeIntent)
    }
}