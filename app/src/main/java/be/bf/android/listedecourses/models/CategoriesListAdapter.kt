package be.bf.android.listedecourses.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import be.bf.android.listedecourses.R


class CategoriesListAdapter (private var listOfCategories: ArrayList<Category>, private var context: Context, private var categoriesListInterface: CategoriesListInterface):
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
        return Unit
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.categories_list_layout, convertView, false)
        }


        val categoriesListImg: ImageView? = convertView!!.findViewById(R.id.categoriesListImg)
        categoriesListImg!!.setImageResource(listOfCategories.get(position).icon)

        val tv_categoriesList = convertView!!.findViewById(R.id.tv_categoriesList) as TextView
        tv_categoriesList.text = listOfCategories.get(position).name

        val chkbox_categoriesList = convertView!!.findViewById(R.id.chkbox_categoriesList) as CheckBox
        chkbox_categoriesList.isChecked = listOfCategories.get(position).isSelected

        val checkbox = convertView.findViewById<CheckBox>(R.id.chkbox_categoriesList)
        checkbox.isChecked = listOfCategories.get(position).isSelected

        // When you click on the item line.
            convertView.setOnClickListener(View.OnClickListener { v: View? ->
                checkbox.toggle()
                this.categoriesListInterface.onItemChecked(position)
            })

        // When you click directly on the checkbox
            checkbox.setOnClickListener { v -> this.categoriesListInterface.onItemChecked(position) }

        return convertView
    }
}