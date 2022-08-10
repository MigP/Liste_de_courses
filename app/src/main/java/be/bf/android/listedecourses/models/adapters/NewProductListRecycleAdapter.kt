package be.bf.android.listedecourses.models.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.models.entities.ListeCourses
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.listOfUnits

class NewProductListRecycleAdapter(newProducts: ArrayList<ListeCourses>): RecyclerView.Adapter<NewProductListRecycleAdapter.ViewHolder>() {
    val newProducts = newProducts

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.new_products_list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return newProducts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.newProdQty.text = newProducts.get(position).quantite.toString()
        holder.newProdUnit.text = listOfUnits.get(newProducts.get(position).uniteId)
        holder.newProdName.text = newProducts.get(position).produit
        holder.newProdCat1Img.setImageResource(newProducts.get(position).categorieProdId1)
        holder.newProdCat2Img.setImageResource(newProducts.get(position).categorieProdId2)
        holder.newProdCat3Img.setImageResource(newProducts.get(position).categorieProdId3)
        holder.newProdChkBox.isChecked = if (newProducts.get(position).achete == 0) false else true
    }

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
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
                val position: Int = adapterPosition

                // -------------------- TEST --------------------
                Toast.makeText(itemView.context, "You have clicked on item: ${newProducts.get(position).produit}", Toast.LENGTH_SHORT).show()
                // -------------------- TEST --------------------
            }
        }
    }
}