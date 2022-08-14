package be.bf.android.listedecourses.models.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.dal.ListeCoursesDAO
import be.bf.android.listedecourses.dal.ListeListesDAO
import be.bf.android.listedecourses.models.adapters.DetailedListAdapter
import be.bf.android.listedecourses.models.adapters.NewProductListRecycleAdapter
import be.bf.android.listedecourses.models.entities.GeneralList
import be.bf.android.listedecourses.models.entities.ListeCourses
import be.bf.android.listedecourses.models.entities.ListeListes
import com.google.android.material.snackbar.Snackbar

class FragmentShowList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_show_lists, container, false)

        // Creates the RecyclerView that contains the list of lists
            // Gets the data into a variable
                val listeListes = ArrayList<ArrayList<GeneralList>>()
                listeListes.add(createListeListes(false))

            // Passes the data on to the recyclerview
                var detailedListAdapterLayoutManager: RecyclerView.LayoutManager? = null
                var detailedListAdapter: RecyclerView.Adapter<DetailedListAdapter.ViewHolder>? = null
                detailedListAdapterLayoutManager = LinearLayoutManager(requireContext())
                val recyclerView = v.findViewById<RecyclerView>(R.id.show_list_recycler)
                recyclerView.layoutManager = detailedListAdapterLayoutManager
                detailedListAdapter = DetailedListAdapter(listeListes, requireContext())
                recyclerView.adapter = detailedListAdapter

        // Implements click listener for the filter switch
            var filterSwitch: Switch
            filterSwitch = v.findViewById(R.id.listFilter)
            filterSwitch.setOnCheckedChangeListener { compoundButton, b ->

                if (filterSwitch.isChecked()) {
                    listeListes.removeAt(0)
                    listeListes.add(createListeListes(true))
                    (detailedListAdapter as DetailedListAdapter).notifyDataSetChanged()
                } else {
                    listeListes.removeAt(0)
                    listeListes.add(createListeListes(false))
                    (detailedListAdapter as DetailedListAdapter).notifyDataSetChanged()
                }
            }

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentShowList().apply {
            }
    }

    private fun createListeListes(filtered: Boolean): ArrayList<GeneralList> {
        val listeListes = ArrayList<GeneralList>()
        // Fetches info from database
            var listId = 0
            var listItemCount = 0
            var checkedListItemCount = 0

            val listeCoursesDAO = ListeCoursesDAO(requireContext())
            listeCoursesDAO.openReadable()
            val listeListesDAO = ListeListesDAO(requireContext())
            listeListesDAO.openReadable()

            for ((count, listsItem: ListeListes) in listeListesDAO.findAll().withIndex()) { // Database listes_listes table
                checkedListItemCount = 0

                listId = listeListesDAO.findId()[count]
                listItemCount = listeCoursesDAO.findByListId(listId).size

                for (productsItem: ListeCourses in listeCoursesDAO.findByListId(listId)) { // Database liste_courses table
                    if (productsItem.achete == 1) checkedListItemCount++
                }

                if (filtered == false || (filtered == true && listItemCount.toString() !== checkedListItemCount.toString())) {
                    var generalList = GeneralList()
                        ?.setListName(listsItem.listName)
                        ?.setListTag(listsItem.listTag)
                        ?.setListDate(listsItem.date)
                        ?.setListItems(checkedListItemCount.toString() + "/" + listItemCount.toString())
                    if (listItemCount.toString() == checkedListItemCount.toString()) generalList!!.setColour(
                        "green"
                    ) else generalList!!.setColour("red")
                    listeListes.add(generalList!!)
                }
            }

        return listeListes
    }
}