package be.bf.android.listedecourses.models

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.dal.CategoriesDAO
import be.bf.android.listedecourses.models.entities.Categories
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentShowList : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_show_lists, container, false)

        val categoriesDAO = CategoriesDAO(context)
        categoriesDAO.openReadable()
//        var t: TextView = v.findViewById(R.id.textView2)
//        for (item: Categories in categoriesDAO.findAll())
//            t.setText(t.text.toString().plus("\n").plus(item.categorieProd))
        return v

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentShowList().apply {
            }
    }
}