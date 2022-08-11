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
                binding.backFloatingActionButton.hide()
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