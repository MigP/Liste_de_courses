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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentShowList.newInstance] factory method to
 * create an instance of this fragment.
 */
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

        var t: TextView = v.findViewById(R.id.textView2)
        t.setText("")
        for (item: Categories in categoriesDAO.findAll())
            t.setText(t.text.toString().plus(item.categorieProd).plus("\n"))
        return v

    }

    companion object {
        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            FragmentShowList().apply {
//            }

        fun newInstance() =
            FragmentShowList().apply {
            }
    }
}