package be.bf.android.listedecourses.models.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.models.entities.ListeCourses
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.editItemPosition
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.editionMode
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.listOfCategories
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.listOfUnits
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.selectedUnit

class NewProductListRecycleAdapter(val newProducts: ArrayList<ListeCourses>, private val passedView: View): RecyclerView.Adapter<NewProductListRecycleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.new_products_list_layout, parent, false)
        return ViewHolder(v, passedView)
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
    inner class ViewHolder(itemView:View, passedView: View): RecyclerView.ViewHolder(itemView) {
        var newProdQty: TextView
        var newProdUnit: TextView
        var newProdName: TextView
        var newProdCat1Img: ImageView
        var newProdCat2Img: ImageView
        var newProdCat3Img: ImageView
        var newProdChkBox: CheckBox

        init {
            newProdQty = itemView.findViewById(R.id.tv_newProdQty)
            newProdUnit = itemView.findViewById(R.id.tv_newProdUnit)
            newProdName = itemView.findViewById(R.id.tv_newProdName)
            newProdCat1Img = itemView.findViewById(R.id.newProdCat1Img)
            newProdCat2Img = itemView.findViewById(R.id.newProdCat2Img)
            newProdCat3Img = itemView.findViewById(R.id.newProdCat3Img)
            newProdChkBox = itemView.findViewById(R.id.newProdChkBx)

            itemView.setOnClickListener {
                editionMode = true

                val archivedItem = newProducts[adapterPosition]
                val etQuantity = passedView.findViewById<EditText>(R.id.et_quantity)
                val unitsDropdown = passedView.findViewById<Spinner>(R.id.unitsDropdown)
                val etProductName = passedView.findViewById<EditText>(R.id.et_productName)
                val ivCat1 = passedView.findViewById<ImageView>(R.id.iv_cat1)
                val ivCat2 = passedView.findViewById<ImageView>(R.id.iv_cat2)
                val ivCat3 = passedView.findViewById<ImageView>(R.id.iv_cat3)
                val addProdBtn = passedView.findViewById<Button>(R.id.addProductBtn)

                etQuantity.setText(archivedItem.quantite.toString())
                selectedUnit = archivedItem.uniteId
                unitsDropdown.setSelection(selectedUnit)
                etProductName.setText(archivedItem.produit)

                if (archivedItem.categorieProdId1 >= 0) {
                    ivCat1.setImageResource(listOfCategories[archivedItem.categorieProdId1].categoryIconId)
                } else {
                    ivCat1.setImageResource(R.drawable.none_icon)
                }

                if (archivedItem.categorieProdId2 >= 0) {
                    ivCat2.setImageResource(listOfCategories[archivedItem.categorieProdId2].categoryIconId)
                } else {
                    ivCat2.setImageResource(R.drawable.none_icon)
                }

                if (archivedItem.categorieProdId3 >= 0) {
                    ivCat3.setImageResource(listOfCategories[archivedItem.categorieProdId3].categoryIconId)
                } else {
                    ivCat3.setImageResource(R.drawable.none_icon)
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

                editItemPosition = adapterPosition
                addProdBtn.setText(R.string.edit)
            }

            // Changes the value if the user checks or unchecks the item within the list
                newProdChkBox.setOnClickListener {
                    if (newProducts[adapterPosition].achete == 1) {
                        newProducts[adapterPosition].achete = 0
                        notifyDataSetChanged()
                    } else {
                        newProducts[adapterPosition].achete = 1
                        notifyDataSetChanged()
                    }
                }
        }
    }
    fun deleteItem(position: Int) {
        newProducts.removeAt(position)
        notifyDataSetChanged()
    }
}