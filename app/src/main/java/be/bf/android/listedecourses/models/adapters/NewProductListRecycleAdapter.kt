package be.bf.android.listedecourses.models.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.models.entities.ListeCourses
import be.bf.android.listedecourses.models.fragments.FragmentCreateList.Companion.listOfUnits

class NewProductListRecycleAdapter: RecyclerView.Adapter<NewProductListRecycleAdapter.ViewHolder>() {

    // ------------ TEST -------------------
    var newProduct1 = ListeCourses()
        .setQuantite(500)
        .setUniteId(1)
        .setProduit("peanut butter jelly")
        .setCategorieProdId1(R.drawable.apple)
        .setCategorieProdId2(R.drawable.breads)
        .setCategorieProdId3(R.drawable.cereals)
        .setAchete(0)

    var newProduct2 = ListeCourses()
        .setQuantite(250)
        .setUniteId(3)
        .setProduit("condensed milk")
        .setCategorieProdId1(R.drawable.baby)
        .setCategorieProdId2(R.drawable.cupcake)
        .setCategorieProdId3(R.drawable.frozen_food)
        .setAchete(1)

    var newProduct3 = ListeCourses()
        .setQuantite(15)
        .setUniteId(6)
        .setProduit("big melons")
        .setCategorieProdId1(R.drawable.sauce_bottle)
        .setCategorieProdId2(R.drawable.pet)
        .setCategorieProdId3(R.drawable.mineral_water)
        .setAchete(0)

    var entries = arrayListOf<ListeCourses>(newProduct1, newProduct2, newProduct3)
    // ------------ TEST -------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.new_products_list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.newProdQty.text = entries.get(position).quantite.toString()
        holder.newProdUnit.text = listOfUnits.get(entries.get(position).uniteId)
        holder.newProdName.text = entries.get(position).produit
        holder.newProdCat1Img.setImageResource(entries.get(position).categorieProdId1)
        holder.newProdCat2Img.setImageResource(entries.get(position).categorieProdId2)
        holder.newProdCat3Img.setImageResource(entries.get(position).categorieProdId3)
        holder.newProdChkBox.isChecked = if (entries.get(position).achete == 0) false else true
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
        }
    }
}