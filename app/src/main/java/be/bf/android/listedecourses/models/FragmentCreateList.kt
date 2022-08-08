package be.bf.android.listedecourses.models

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.dal.CategoriesDAO
import be.bf.android.listedecourses.dal.UnitesDAO
import be.bf.android.listedecourses.models.entities.Categories
import be.bf.android.listedecourses.models.entities.ListeCourses
import be.bf.android.listedecourses.models.entities.Unites


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

        // Create dropdown menu with units to choose from
            val spinner: Spinner =  v.findViewById(R.id.unitsDropdown);
            val adapter: ArrayAdapter<*> = ArrayAdapter<Any>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getUnits() as List<Any>
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                    println("--------------------- " + listOfUnits.get(i))
                    selectedUnit = i
                }
                override fun onNothingSelected(adapterView: AdapterView<*>) {
                }

            }

        // --- TEST --- creates new product object:
            newProduct = ListeCourses()
                .setProduit("apple")
                .setQuantite(2)
                .setUniteId(7)
                .setCategorieProdId1(1)
                .setCategorieProdId2(0)
                .setCategorieProdId3(0)
                .setAchete(0)

        return v
    }

    companion object {
        var selectedItemsList: ArrayList<String> = ArrayList()
        lateinit var listOfCategories: ListOfCategories
        lateinit var listOfUnits: ArrayList<String>
        var selectedUnit: Int = 0
        lateinit var newProduct: ListeCourses

        @JvmStatic
        fun newInstance() =
            FragmentCreateList().apply {
            }
    }

    private fun addCategories(view: View) {
        //TODO create an alert window with a checklist of the categories

//        // --- TEST --- Changes an image src:
//            val img: ImageView? = getView()?.findViewById(R.id.iv_cat1)
//            img!!.setImageResource(listOfCategories.getImage(10))
//
//        // --- TEST --- Reads info:
//            println("----------------- newProduct: " + newProduct.produit.toString() + " , Acheté: " + newProduct.achete.toString())
//            newProduct.setAchete(1)
//            println("----------------- newProduct: " + newProduct.produit.toString() + " , Acheté: " + newProduct.achete.toString())

        // Creating the dialog
        val dialogbuider = AlertDialog.Builder(requireContext())
        dialogbuider.setCancelable(false)
        dialogbuider.setTitle("Pick up to 3 categories")

        val catListIterator = listOfCategories.nameList.iterator()
        var counter = 0
        val catListArrayList: ArrayList<Category> = ArrayList()

        while(catListIterator.hasNext()){
            val entry = catListIterator.next()

            catListArrayList.add(Category(listOfCategories.getName(counter), listOfCategories.getImage(counter), listOfCategories.getIsSelected(counter)))
            counter++
        }

        val categoriesListAdapter = CategoriesListAdapter(catListArrayList, requireContext(), object : CategoriesListInterface {
            override fun onItemChecked(position: Int) {
                catListArrayList.get(position)
                    .isSelected = !catListArrayList.get(position).isSelected
            }
        })

        dialogbuider.setAdapter(
            categoriesListAdapter
        ) { dialog: DialogInterface?, which: Int -> }

        dialogbuider.setPositiveButton("OK") { dialogInterface: DialogInterface?, which: Int ->
            val itensexib2 = StringBuilder()
            selectedItemsList.clear()
            for (item in catListArrayList) {
                if (item.isSelected) {
                    println("***************** " + item.name)
                }
            }
        }

        val dialog = dialogbuider.create()
        val listView = dialog.listView
        listView.divider = ColorDrawable(Color.GRAY)
        listView.dividerHeight = 2
        dialog.show()
    }

    private fun addProduct(view: View) {
        //TODO validate product data and insert it into the recycler adapter

        // Sets the created product name
            val et_productName: EditText? = getView()?.findViewById(R.id.et_productName)
            if (et_productName != null) {
                newProduct!!.setProduit(et_productName.text.toString())
            }

        // Sets the created product quantity
            val et_quantity: EditText? = getView()?.findViewById(R.id.et_quantity)
            if (et_quantity != null) {
                newProduct!!.setQuantite(et_quantity.text.toString().toInt())
            }

        // Sets the created product unit
            newProduct!!.setUniteId(selectedUnit)

        Toast.makeText(requireContext(), "" + newProduct.quantite + " " + listOfUnits.get(newProduct.uniteId) + " of " + newProduct.produit, Toast.LENGTH_SHORT).show()
    }

    private fun cancelCreate(view: View) {
        setFragmentResult("requestKey", bundleOf("createFragmentData" to "cancelCreate"))
    }

    private fun createList(view: View) {
        //TODO validate all items present and create new list
    }

    private fun getProductCategories(): ListOfCategories { // Gets product categories from the database and puts them into a ListCategories object
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()

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

        return ListOfCategories(categoriesArrayList, imagesArrayList, isSelectedArrayList)
    }

    private fun getUnits(): ArrayList<String> { // Gets units from the database and puts them into an ArrayList
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()

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
}