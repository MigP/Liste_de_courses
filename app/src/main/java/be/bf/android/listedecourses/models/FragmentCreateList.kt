package be.bf.android.listedecourses.models

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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

        val deleteFloatingActionButton: FloatingActionButton = v.findViewById(R.id.deleteFloatingActionButton)
        val addFloatingActionButton: FloatingActionButton = v.findViewById(R.id.addFloatingActionButton)
        val cancelCreateListBtn: Button = v.findViewById(R.id.cancelCreateListBtn)
        val createListBtn: Button = v.findViewById(R.id.createListBtn)

        deleteFloatingActionButton.setOnClickListener(this::deleteProduct)
        addFloatingActionButton.setOnClickListener(this::addProduct)
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

    fun deleteProduct(view: View) {

    }

    fun addProduct(view: View) {

    }

    fun cancelCreate(view: View) {

    }

    fun createList(view: View) {

    }
}