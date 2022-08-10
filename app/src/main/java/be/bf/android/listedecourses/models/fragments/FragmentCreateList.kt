package be.bf.android.listedecourses.models.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.dal.CategoriesDAO
import be.bf.android.listedecourses.dal.UnitesDAO
import be.bf.android.listedecourses.models.CategoriesListInterface
import be.bf.android.listedecourses.models.entities.Category
import be.bf.android.listedecourses.models.adapters.CategoriesListAdapter
import be.bf.android.listedecourses.models.adapters.NewProductListRecycleAdapter
import be.bf.android.listedecourses.models.entities.Categories
import be.bf.android.listedecourses.models.entities.ListeCourses
import be.bf.android.listedecourses.models.entities.Unites


class FragmentCreateList : Fragment() {
    private var newProdListLayoutManager: RecyclerView.LayoutManager? = null
    private var newProdListAdapter: RecyclerView.Adapter<NewProductListRecycleAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_create_list, container, false)

        val addCategoriesBtn: Button = v.findViewById(R.id.addCategoriesBtn)
        val addProductBtn: Button = v.findViewById(R.id.addProductBtn)
        val cancelCreateListBtn: Button = v.findViewById(R.id.cancelCreateListBtn)
        val createListBtn: Button = v.findViewById(R.id.createListBtn)

        addCategoriesBtn.setOnClickListener(this::addCategories)
        addProductBtn.setOnClickListener(this::addProduct)
        cancelCreateListBtn.setOnClickListener(this::cancelCreate)
        createListBtn.setOnClickListener(this::createList)

        listOfCategories = getProductCategories()
        listOfUnits = getUnits()

        // Creates the RecyclerView that contains the list of products
            newProdListLayoutManager = LinearLayoutManager(requireContext())
            val recyclerView = v.findViewById<RecyclerView>(R.id.create_list_recycler)
            recyclerView.layoutManager = newProdListLayoutManager
            newProdListAdapter = NewProductListRecycleAdapter(entries)
            recyclerView.adapter = newProdListAdapter

        // Create dropdown menu with units to choose from
            val spinner: Spinner =  v.findViewById(R.id.unitsDropdown);
            val adapter: ArrayAdapter<*> = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getUnits() as List<Any>
            )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                    selectedUnit = i
                }
                override fun onNothingSelected(adapterView: AdapterView<*>) {
                }
            }

        return v
    }

    companion object {
        var selectedCategoriesCounter = 0
        var selectedUnit: Int = 0
        var listOfCategories = ArrayList<Category>()
        var listOfUnits = ArrayList<String>()
        var entries = arrayListOf<ListeCourses>()

        @JvmStatic
        fun newInstance() =
            FragmentCreateList().apply {
            }
    }

    private fun addCategories(view: View) { // Adds up to 3 categories to the product being created by the user
        // Creats the alert dialog with the categories to be selected
            val dialogbuider = AlertDialog.Builder(requireContext())
            dialogbuider.setCancelable(false)
            dialogbuider.setTitle(R.string.pick_3_categories)

            val categoriesListAdapter = CategoriesListAdapter(listOfCategories, requireContext(), object :
                CategoriesListInterface {
                    override fun onItemChecked(position: Int) {
                }
            })

            dialogbuider.setAdapter(categoriesListAdapter) { dialog: DialogInterface?, which: Int -> }

            dialogbuider.setPositiveButton("OK") { dialogInterface: DialogInterface?, which: Int ->
                // Resetting the 3 selected category images
                    var resID = resources.getIdentifier("none_icon", "drawable", requireContext().packageName)
                    changeImg(requireView().findViewById(R.id.iv_cat1), resID)
                    changeImg(requireView().findViewById(R.id.iv_cat2), resID)
                    changeImg(requireView().findViewById(R.id.iv_cat3), resID)

                // Changes the selected category images to the relevant image resources
                    var imgToChangeCounter = 1
                    for (item in listOfCategories) {
                        if (item.isSelected) {
                            when (imgToChangeCounter) {
                                1 -> {
                                    changeImg(requireView().findViewById(R.id.iv_cat1), item.icon)
                                }
                                2 -> {
                                    changeImg(requireView().findViewById(R.id.iv_cat2), item.icon)
                                }
                                3 -> {
                                    changeImg(requireView().findViewById(R.id.iv_cat3), item.icon)
                                }
                            }
                            imgToChangeCounter++
                        }
                    }
            }

            val dialog = dialogbuider.create()
            val listView = dialog.listView
            dialog.show()
    }

    private fun addProduct(view: View) { // Adds the created product to the list being created
        val regex = """[\|\/\"]""".toRegex()
        val et_productName: EditText? = getView()?.findViewById(R.id.et_productName)
        val et_quantity: EditText? = getView()?.findViewById(R.id.et_quantity)

        if (et_productName!!.text.toString().length == 0) { // Data field validation
            Toast.makeText(requireContext(), R.string.you_must_include_product_name, Toast.LENGTH_SHORT).show()
        } else if (et_quantity!!.text.toString().length == 0) { // Data field validation
            Toast.makeText(requireContext(), R.string.you_must_include_quantity, Toast.LENGTH_SHORT).show()
        } else if (regex.containsMatchIn(et_productName.text.toString())) { // Data field validation
            Toast.makeText(requireContext(), R.string.forbidden_characters, Toast.LENGTH_SHORT).show()
        } else {
            // Creates an ArrayList containing the names of the selected categories for this product
                // Fills up catsSel with the selected categories names
                    val catsSel = ArrayList<Int>()
                    for (item in listOfCategories) {
                        if (item.isSelected) {
                            catsSel.add(item.icon)
                        }
                    }
                // Fills up the remainder of catsSel with empty categories names (-1)
                    if (catsSel.isEmpty()) {
                        catsSel.add(R.drawable.none_icon)
                    }
                    if (catsSel.size == 1) {
                        catsSel.add(R.drawable.none_icon)
                    }
                    if (catsSel.size == 2) {
                        catsSel.add(R.drawable.none_icon)
                    }

            // Adds created product to the recyclerview that contains the list of products
                var newProduct = ListeCourses()
                    .setQuantite(et_quantity.text.toString().toInt())
                    .setUniteId(selectedUnit)
                    .setProduit(et_productName!!.text.toString())
                    .setCategorieProdId1(catsSel.get(0))
                    .setCategorieProdId2(catsSel.get(1))
                    .setCategorieProdId3(catsSel.get(2))
                    .setAchete(0)
                entries.add(newProduct)
                (newProdListAdapter as NewProductListRecycleAdapter).notifyDataSetChanged()

            // Reset all the inputs
                et_productName.setText("")
                et_quantity.setText("")
                var resID = resources.getIdentifier("none_icon", "drawable", requireContext().packageName)
                changeImg(requireView().findViewById(R.id.iv_cat1), resID)
                changeImg(requireView().findViewById(R.id.iv_cat2), resID)
                changeImg(requireView().findViewById(R.id.iv_cat3), resID)
                for (item in listOfCategories) {
                    item.isSelected = false
                }
                selectedUnit = 0
                selectedCategoriesCounter = 0
        }
    }

    private fun cancelCreate(view: View) { // Closes the alert dialog box
        setFragmentResult("requestKey", bundleOf("createFragmentData" to "cancelCreate"))
    }

    private fun createList(view: View) {
        //TODO validate all items present and create new list
    }

    private fun getProductCategories(): ArrayList<Category> { // Gets product categories from the database and returns them in an ArrayList of Category objects
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val categoriesArrayList = ArrayList<String>()
        val imagesArrayList = ArrayList<Int>()
        val isSelectedArrayList = ArrayList<Boolean>()

        val categoriesDAO = CategoriesDAO(context)
        categoriesDAO.openReadable()

        // Creates ArrayList with all the categories from database
            if (prefs.getString("language", "") == "en") {
                for (item: Categories in categoriesDAO.findAll()) {
                    categoriesArrayList.add(item.categorieProdEn.toString())
                }
            } else if (prefs.getString("language", "") == "fr") {
                for (item: Categories in categoriesDAO.findAll()) {
                    categoriesArrayList.add(item.categorieProdFr.toString())
                }
            }

        // Creates ArrayList with the icon images ID for all the categories
            for (item: Categories in categoriesDAO.findAll()) {
                var resID = resources.getIdentifier(item.imageSrc, "drawable", requireContext().packageName)
                imagesArrayList.add(resID)
            }

        // Creates ArrayList with the values of isSelected all set to false
            for (item: Categories in categoriesDAO.findAll()) {
                isSelectedArrayList.add(false)
            }

        // Creates ArrayList of Category objects
            val _listOfCategories = ArrayList<Category>()
            val listOfCategoriesIterator = categoriesArrayList.iterator()
            var counter = 0
            while(listOfCategoriesIterator.hasNext()){
                listOfCategoriesIterator.next()

                _listOfCategories.add(Category(categoriesArrayList.get(counter), imagesArrayList.get(counter), isSelectedArrayList.get(counter)))
                counter++
            }

        return _listOfCategories
    }

    private fun getUnits(): ArrayList<String> { // Gets units from the database and puts them into an ArrayList
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val unitsArrayList = ArrayList<String>()

        val unitesDAO = UnitesDAO(context)
        unitesDAO.openReadable()

        // Creates ArrayList with all the units from database
            if (prefs.getString("language", "") == "en") {
                for (item: Unites in unitesDAO.findAll()) {
                    unitsArrayList.add(item.unitEn.toString())
                }
            } else if (prefs.getString("language", "") == "fr") {
                for (item: Unites in unitesDAO.findAll()) {
                    unitsArrayList.add(item.unitFr.toString())
                }
            }

        return unitsArrayList
    }

    private fun changeImg(targetImg: ImageView, imgResourceId: Int) { // Changes the source of a given image
        targetImg.setImageResource(imgResourceId)
    }
}