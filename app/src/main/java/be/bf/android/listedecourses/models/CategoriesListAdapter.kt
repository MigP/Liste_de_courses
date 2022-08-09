package be.bf.android.listedecourses.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.models.FragmentCreateList.Companion.selectedCategoriesCounter


class CategoriesListAdapter (private var listOfCategories: ArrayList<Category>, private var context: Context, var categoriesListInterface: CategoriesListInterface):
    BaseAdapter() {

    init {
        this.listOfCategories = listOfCategories;
        this.context = context;
        this.categoriesListInterface = categoriesListInterface;
    }

    override fun getCount(): Int {
        return listOfCategories.size
    }

    override fun getItem(position: Int): Any {
        return listOfCategories.get(position)
    }

    override fun getItemId(position: Int): Long {
        return listOfCategories.indexOf(getItem(position)).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.categories_list_layout, convertView, false)
        }


        val categoriesListImg: ImageView? = convertView!!.findViewById(R.id.categoriesListImg)
        categoriesListImg!!.setImageResource(listOfCategories.get(position).icon)

        val tv_categoriesList: TextView = convertView!!.findViewById(R.id.tv_categoriesList)
        tv_categoriesList.text = listOfCategories.get(position).name

        val checkbox: CheckBox = convertView.findViewById<CheckBox>(R.id.chkbox_categoriesList)
        checkbox.isChecked = listOfCategories.get(position).isSelected

        // Check the box when you click on the item text or icon
            convertView.setOnClickListener(View.OnClickListener { v: View? ->

                if (FragmentCreateList.listOfCategories.get(position).isSelected) {
                    checkbox.toggle()
                    FragmentCreateList.listOfCategories.get(position).isSelected = false
                    selectedCategoriesCounter--

                    this.categoriesListInterface.onItemChecked(position)
                } else if (selectedCategoriesCounter < 3) {
                    checkbox.toggle()
                    FragmentCreateList.listOfCategories.get(position).isSelected = true
                    selectedCategoriesCounter++

                    this.categoriesListInterface.onItemChecked(position)
                } else {
                    Toast.makeText(context, "You have reached your limit", Toast.LENGTH_SHORT).show()
                }
            })

        // Check the box when you click directly on the checkbox
            checkbox.setOnClickListener { v ->
                if (FragmentCreateList.listOfCategories.get(position).isSelected) {
                    FragmentCreateList.listOfCategories.get(position).isSelected = false
                    selectedCategoriesCounter--

                    this.categoriesListInterface.onItemChecked(position)
                } else if (selectedCategoriesCounter < 3) {
                    FragmentCreateList.listOfCategories.get(position).isSelected = true
                    selectedCategoriesCounter++

                    this.categoriesListInterface.onItemChecked(position)
                } else {
                    checkbox.toggle()
                    Toast.makeText(context, "You have reached your limit", Toast.LENGTH_SHORT).show()
                }
            }

        return convertView
    }
}