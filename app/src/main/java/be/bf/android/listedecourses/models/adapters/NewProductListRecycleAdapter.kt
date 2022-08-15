package be.bf.android.listedecourses.models.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.dal.ListeCoursesDAO
import be.bf.android.listedecourses.dal.ListeListesDAO
import be.bf.android.listedecourses.models.entities.ListeCourses
import be.bf.android.listedecourses.models.fragments.FragmentCreateList
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.catImg1
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.catImg2
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.catImg3
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.editItemPosition
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.editionMode
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.etProductName
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.etQuantity
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.fragmentMode
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.listOfCategories
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.listOfUnits
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.selectedUnit
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.uiVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewProductListRecycleAdapter(val newProducts: ArrayList<ListeCourses>, private val passedView: View, private val rootView: View): RecyclerView.Adapter<NewProductListRecycleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.new_products_list_layout, parent, false)
        return ViewHolder(v, passedView, rootView, parent.context)
    }

    override fun getItemCount(): Int {
        return newProducts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.newProdQty.text = newProducts[position].quantite.toString()
        holder.newProdUnit.text = listOfUnits[newProducts[position].uniteId]
        holder.newProdName.text = newProducts[position].produit

        if (newProducts[position].categorieProdId1 >= 0) {
            holder.newProdCat1Img.setImageResource(listOfCategories[newProducts[position].categorieProdId1].categoryIconId)
        } else {
            holder.newProdCat1Img.setImageResource(R.drawable.none_icon)
        }

        if (newProducts[position].categorieProdId2 >= 0) {
            holder.newProdCat2Img.setImageResource(listOfCategories[newProducts[position].categorieProdId2].categoryIconId)
        } else {
            holder.newProdCat2Img.setImageResource(R.drawable.none_icon)
        }

        if (newProducts[position].categorieProdId3 >= 0) {
            holder.newProdCat3Img.setImageResource(listOfCategories[newProducts[position].categorieProdId3].categoryIconId)
        } else {
            holder.newProdCat3Img.setImageResource(R.drawable.none_icon)
        }

        holder.newProdChkBox.isChecked = newProducts[position].achete != 0
    }

    @SuppressLint("ResourceAsColor")
    inner class ViewHolder(itemView:View, passedView: View, rootView: View, context: Context): RecyclerView.ViewHolder(itemView) {
        val addCategoriesBtn: Button
        val addProductBtn: Button
        val cancelCreateListBtn: Button
        val createListBtn: Button
        val spinner: Spinner
        var newProdQty: TextView
        var newProdUnit: TextView
        var newProdName: TextView
        var newProdCat1Img: ImageView
        var newProdCat2Img: ImageView
        var newProdCat3Img: ImageView
        var newProdChkBox: CheckBox

        init {
            addCategoriesBtn = passedView.findViewById(R.id.addCategoriesBtn)
            addProductBtn = passedView.findViewById(R.id.addProductBtn)
            cancelCreateListBtn = passedView.findViewById(R.id.cancelCreateListBtn)
            createListBtn = passedView.findViewById(R.id.createListBtn)
            spinner =  passedView.findViewById(R.id.unitsDropdown)
            newProdQty = itemView.findViewById(R.id.tv_newProdQty)
            newProdUnit = itemView.findViewById(R.id.tv_newProdUnit)
            newProdName = itemView.findViewById(R.id.tv_newProdName)
            newProdCat1Img = itemView.findViewById(R.id.newProdCat1Img)
            newProdCat2Img = itemView.findViewById(R.id.newProdCat2Img)
            newProdCat3Img = itemView.findViewById(R.id.newProdCat3Img)
            newProdChkBox = itemView.findViewById(R.id.newProdChkBx)

            itemView.setOnClickListener {
                editionMode = true
                editItemPosition = adapterPosition

                val archivedItem = newProducts[adapterPosition]

                // Make the edition UI visible
                    addCategoriesBtn.visibility = View.VISIBLE
                    cancelCreateListBtn.visibility = View.VISIBLE
                    createListBtn.visibility = View.VISIBLE
                    spinner.visibility = View.VISIBLE
                    etProductName!!.visibility = View.VISIBLE
                    etQuantity!!.visibility = View.VISIBLE
                    catImg1.visibility = View.VISIBLE
                    catImg2.visibility = View.VISIBLE
                    catImg3.visibility = View.VISIBLE
                    uiVisible = true

                // Change some of the UI
                    rootView.findViewById<FloatingActionButton>(R.id.backFloatingActionButton).hide()
                    addProductBtn.setText(R.string.edit) // Add product button becomes Edit button

                    if (fragmentMode.equals("viewing")) {
                        createListBtn.setText(R.string.save_list) // Create list button becomes Save list button
                    }

                etQuantity.setText(archivedItem.quantite.toString())
                selectedUnit = archivedItem.uniteId
                spinner.setSelection(selectedUnit)
                etProductName.setText(archivedItem.produit)

                if (archivedItem.categorieProdId1 >= 0) {
                    catImg1.setImageResource(listOfCategories[archivedItem.categorieProdId1].categoryIconId)
                } else {
                    catImg1.setImageResource(R.drawable.none_icon)
                }

                if (archivedItem.categorieProdId2 >= 0) {
                    catImg2.setImageResource(listOfCategories[archivedItem.categorieProdId2].categoryIconId)
                } else {
                    catImg2.setImageResource(R.drawable.none_icon)
                }

                if (archivedItem.categorieProdId3 >= 0) {
                    catImg3.setImageResource(listOfCategories[archivedItem.categorieProdId3].categoryIconId)
                } else {
                    catImg3.setImageResource(R.drawable.none_icon)
                }

                for (i in listOfCategories) {
                    i.isSelected = false
                }

                if (archivedItem.categorieProdId1 >= 0) {
                    listOfCategories[archivedItem.categorieProdId1].isSelected = true
                }

                if (archivedItem.categorieProdId2 >= 0) {
                    listOfCategories[archivedItem.categorieProdId2].isSelected = true
                }

                if (archivedItem.categorieProdId3 >= 0) {
                    listOfCategories[archivedItem.categorieProdId3].isSelected = true
                }
            }

            // Changes the value if the user checks or unchecks the item within the list
                newProdChkBox.setOnClickListener {
                    if (newProducts[adapterPosition].achete == 1) {
                        newProducts[adapterPosition].achete = 0
                    } else {
                        newProducts[adapterPosition].achete = 1
                    }

                    // Updates database when item checked or unchecked
                        val listeCoursesDAO = ListeCoursesDAO(context)
                        listeCoursesDAO.openWritable()
                        listeCoursesDAO.update(newProducts[adapterPosition].id, newProducts[adapterPosition])

                    notifyDataSetChanged()
                }
        }
    }

    fun deleteItem(position: Int) {
        newProducts.removeAt(position)
        notifyDataSetChanged()
    }
}