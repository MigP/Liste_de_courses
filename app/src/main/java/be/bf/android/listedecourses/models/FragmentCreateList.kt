package be.bf.android.listedecourses.models

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import be.bf.android.listedecourses.MainActivity
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.dal.CategoriesDAO
import be.bf.android.listedecourses.models.FragmentShowList
import be.bf.android.listedecourses.models.entities.Categories
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentCreateList : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_create_list, container, false)

        val addProductBtn: Button = v.findViewById(R.id.addProductBtn)
        val cancelCreateListBtn: Button = v.findViewById(R.id.cancelCreateListBtn)
        val createListBtn: Button = v.findViewById(R.id.createListBtn)

        addProductBtn.setOnClickListener(this::addProduct)
        cancelCreateListBtn.setOnClickListener(this::cancelCreate)
        createListBtn.setOnClickListener(this::createList)





        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentCreateList().apply {
            }
    }

    fun addProduct(view: View) {
        //TODO validate product data and insert it into the recycler adapter
        println("--- You just added a product to the list ---")
    }

    fun cancelCreate(view: View) {
        setFragmentResult("requestKey", bundleOf("createFragmentData" to "cancelCreate"))
    }

    fun createList(view: View) {
        //TODO validate all items present and create new list
        println("--- You just created a new list ---")
    }
}