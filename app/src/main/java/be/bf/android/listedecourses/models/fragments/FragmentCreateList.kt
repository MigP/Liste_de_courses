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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.dal.CategoriesDAO
import be.bf.android.listedecourses.dal.ListeCoursesDAO
import be.bf.android.listedecourses.dal.ListeListesDAO
import be.bf.android.listedecourses.dal.UnitesDAO
import be.bf.android.listedecourses.models.CategoriesListInterface
import be.bf.android.listedecourses.models.gestures.SwipeGesture
import be.bf.android.listedecourses.models.adapters.CategoriesListAdapter
import be.bf.android.listedecourses.models.adapters.NewProductListRecycleAdapter
import be.bf.android.listedecourses.models.entities.*


class FragmentCreateList : Fragment() {
    private var newProdListLayoutManager: RecyclerView.LayoutManager? = null
    private var newProdListAdapter: RecyclerView.Adapter<NewProductListRecycleAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        entries.clear()
        // Gets name and tag from the parameters passed when the fragment was instantiated
            listName = arguments?.getString(NEW_LIST_NAME)!!
            listTag = arguments?.getString(NEW_LIST_TAG)!!
        // Sets the text for the titles
            val title: TextView = v.findViewById(R.id.tv_createListTitle)
            val subTitle: TextView = v.findViewById(R.id.tv_createListTag)
            title.text = listName
            subTitle.text = listTag

        // Creates the RecyclerView that contains the list of products
            newProdListLayoutManager = LinearLayoutManager(requireContext())
            val recyclerView = v.findViewById<RecyclerView>(R.id.create_list_recycler)
            recyclerView.layoutManager = newProdListLayoutManager
            newProdListAdapter = NewProductListRecycleAdapter(entries, v)
            recyclerView.adapter = newProdListAdapter

            val swipeGesture = object: SwipeGesture(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when (direction){
                        ItemTouchHelper.RIGHT -> {
                            (newProdListAdapter as NewProductListRecycleAdapter).deleteItem(viewHolder.adapterPosition)
                        }

                        ItemTouchHelper.LEFT -> {
                            (newProdListAdapter as NewProductListRecycleAdapter).deleteItem(viewHolder.adapterPosition)
                        }
                    }
                }
            }

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)

        // Create dropdown menu with units to choose from
            val spinner: Spinner =  v.findViewById(R.id.unitsDropdown)
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
        var editItemPosition = 0
        var listOfCategories = ArrayList<Category>()
        var listOfUnits = ArrayList<String>()
        var entries = arrayListOf<ListeCourses>()
        var listName = ""
        var listTag = ""
        var editionMode = false

        const val NEW_LIST_NAME = "newListName"
        const val NEW_LIST_TAG = "newListTag"

        fun newInstance(name: String, tag: String): FragmentCreateList {
            val fragment = FragmentCreateList()

            val bundle = Bundle().apply {
                putString(NEW_LIST_NAME, name)
                putString(NEW_LIST_TAG, tag)
            }

            fragment.arguments = bundle

            return fragment
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
                    val resID = resources.getIdentifier("none_icon", "drawable", requireContext().packageName)
                    changeImg(requireView().findViewById(R.id.iv_cat1), resID)
                    changeImg(requireView().findViewById(R.id.iv_cat2), resID)
                    changeImg(requireView().findViewById(R.id.iv_cat3), resID)

                // Changes the selected category images to the relevant image resources
                    var imgToChangeCounter = 1
                    for (item in listOfCategories) {
                        if (item.isSelected) {
                            when (imgToChangeCounter) {
                                1 -> {
                                    changeImg(requireView().findViewById(R.id.iv_cat1), item.categoryIconId)
                                }
                                2 -> {
                                    changeImg(requireView().findViewById(R.id.iv_cat2), item.categoryIconId)
                                }
                                3 -> {
                                    changeImg(requireView().findViewById(R.id.iv_cat3), item.categoryIconId)
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

    private fun addProduct(view: View) { // Adds or edits the created product to the list being created
        val regex = """[|/\\"]""".toRegex()
        val etProductName: EditText? = getView()?.findViewById(R.id.et_productName)
        val etQuantity: EditText? = getView()?.findViewById(R.id.et_quantity)

        if (etProductName!!.text.toString().isEmpty()) { // Data field validation
            Toast.makeText(requireContext(), R.string.you_must_include_product_name, Toast.LENGTH_SHORT).show()
        } else if (etQuantity!!.text.toString().isEmpty()) { // Data field validation
            Toast.makeText(requireContext(), R.string.you_must_include_quantity, Toast.LENGTH_SHORT).show()
        } else if (regex.containsMatchIn(etProductName.text.toString())) { // Data field validation
            Toast.makeText(requireContext(), R.string.forbidden_characters, Toast.LENGTH_SHORT).show()
        } else {
            // Creates an ArrayList containing the names of the selected categories for this product
                // Fills up catsSel with the selected categories names
                    val catsSel = ArrayList<Int>()
                    for (item in listOfCategories) {
                        if (item.isSelected) {
                            catsSel.add(item.categoryId)
                        }
                    }
                // Fills up the remainder of catsSel with empty categories names (-1)
                    if (catsSel.isEmpty()) {
                        catsSel.add(-1)
                    }
                    if (catsSel.size == 1) {
                        catsSel.add(-1)
                    }
                    if (catsSel.size == 2) {
                        catsSel.add(-1)
                    }

            // If in edit mode, delete edited item from list
                if (editionMode) { // Remove unedited item from list
                    entries.removeAt(editItemPosition)
                    (newProdListAdapter as NewProductListRecycleAdapter).notifyDataSetChanged()
                }


            // Adds created product to the recyclerview that contains the list of products
                val listeListes = ListeListesDAO(requireContext())
                listeListes.openReadable()
                val newProduct = ListeCourses()
                    .setListeId(listeListes.findLastId() + 1)
                    .setQuantite(etQuantity.text.toString().toInt())
                    .setUniteId(selectedUnit)
                    .setProduit(etProductName.text.toString())
                    .setCategorieProdId1(catsSel[0])
                    .setCategorieProdId2(catsSel[1])
                    .setCategorieProdId3(catsSel[2])
                    .setAchete(0)
            // If in edit mode add edited item to the original position on the list
                if (editionMode) { // Ends edition mode and returns to default mode
                    editionMode = false
                    entries.add(editItemPosition, newProduct)
                } else {
                    entries.add(newProduct)
                }
                (newProdListAdapter as NewProductListRecycleAdapter).notifyDataSetChanged()

            // Reset all the inputs
                etProductName.setText("")
                etQuantity.setText("")
                val resID = resources.getIdentifier("none_icon", "drawable", requireContext().packageName)
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

    private fun cancelCreate(view: View) { // Ends edition mode or returns to the splash screen
        if (editionMode) { // Ends edition mode and returns to default mode
            editionMode = false
            requireView().findViewById<EditText>(R.id.et_quantity).setText("")
            selectedUnit = 0
            requireView().findViewById<Spinner>(R.id.unitsDropdown).setSelection(0)
            requireView().findViewById<EditText>(R.id.et_productName).setText("")
            requireView().findViewById<ImageView>(R.id.iv_cat1).setImageResource(R.drawable.none_icon)
            requireView().findViewById<ImageView>(R.id.iv_cat2).setImageResource(R.drawable.none_icon)
            requireView().findViewById<ImageView>(R.id.iv_cat3).setImageResource(R.drawable.none_icon)
            editItemPosition = 0
            requireView().findViewById<Button>(R.id.addProductBtn).setText(R.string.add_product)
        } else { // Returns to the splash screen
            setFragmentResult("requestKey", bundleOf("createFragmentData" to "cancelCreate"))
        }
    }

    private fun createList(view: View) { // Adds the list to the database tables and returns to splash screen
        val listeCourses = ListeCoursesDAO(requireContext())
        val listListes = ListeListesDAO(requireContext())

        listeCourses.openWritable()
        val entriesIterator = entries.iterator()
        var iteratorCounter = 0

        while(entriesIterator.hasNext()){
            entriesIterator.next()
            listeCourses.insert(entries[iteratorCounter])
            iteratorCounter++
        }

        val newList = ListeListes()
            .setListName(listName)
            .setListTag(listTag)

        listListes.openWritable()
        listListes.insert(newList)

        setFragmentResult("requestKey", bundleOf("createFragmentData" to "cancelCreate"))
    }

    private fun getProductCategories(): ArrayList<Category> { // Gets product categories from the database and returns them in an ArrayList of Category objects
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val categoriesArrayList = ArrayList<String>()
        val categoryIdArrayList = ArrayList<Int>()
        val isSelectedArrayList = ArrayList<Boolean>()
        val imagesIdArrayList = ArrayList<Int>()

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

        // Creates ArrayList with the IDs for all the categories
            for ((catCounter, item: Categories) in categoriesDAO.findAll().withIndex()) {
                    categoryIdArrayList.add(catCounter)
            }

        // Creates ArrayList with the values of isSelected all set to false
            for (item: Categories in categoriesDAO.findAll()) {
                isSelectedArrayList.add(false)
            }

        // Creates ArrayList with the icon images ID for all the categories
            for (item: Categories in categoriesDAO.findAll()) {
                imagesIdArrayList.add(getImgResourceId(item.imageSrc))
            }

        // Creates ArrayList of Category objects
            val _listOfCategories = ArrayList<Category>()
            val listOfCategoriesIterator = categoriesArrayList.iterator()
            var counter = 0
            while(listOfCategoriesIterator.hasNext()){
                listOfCategoriesIterator.next()

                _listOfCategories.add(Category(
                    categoriesArrayList[counter],
                    categoryIdArrayList[counter],
                    isSelectedArrayList[counter],
                    imagesIdArrayList[counter]
                ))
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

    private fun getImgResourceId(identifier: String): Int {
        return resources.getIdentifier(identifier, "drawable", requireContext().packageName)
    }
}