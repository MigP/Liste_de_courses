package be.bf.android.listedecourses.models.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.listedecourses.MainActivity
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.dal.ListeListesDAO
import be.bf.android.listedecourses.models.entities.GeneralList
import be.bf.android.listedecourses.models.entities.ListeCourses
import be.bf.android.listedecourses.models.entities.ListeListes
import be.bf.android.listedecourses.models.fragments.FragmentCreateList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GeneralListAdapter(val listsArray: ArrayList<GeneralList>, val passedContext: Context): RecyclerView.Adapter<GeneralListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.general_list_of_lists, parent, false)
        return ViewHolder(v, passedContext)
    }

    override fun getItemCount(): Int {
        return listsArray.size
    }

    override fun onBindViewHolder(holder: GeneralListAdapter.ViewHolder, position: Int) {
        holder.listName.text = listsArray[position].getListName()
        holder.listTag.text = listsArray[position].getListTag()
        holder.listItems.text = listsArray[position].getListItems()
        if (listsArray[position].getColour().equals("red")) {
            holder.listItems.setTextColor(passedContext.getResources().getColor(R.color.unfinished))
        } else if (listsArray[position].getColour().equals("green")) {
            holder.listItems.setTextColor(passedContext.getResources().getColor(R.color.finished))
        }
    }

    inner class ViewHolder(itemView:View, passedContext: Context): RecyclerView.ViewHolder(itemView) {
        var listName: TextView
        var listTag: TextView
        var listItems: TextView

        init {
            listName = itemView.findViewById(R.id.tv_general_listName)
            listTag = itemView.findViewById(R.id.tv_general_listTag)
            listItems = itemView.findViewById(R.id.tv_general_items)

            itemView.setOnClickListener {
                // Displays the detailed view of this list
                    val createListIntent = Intent(itemView.context, MainActivity::class.java)
                    createListIntent.putExtra("targetFragment", "create")
                    createListIntent.putExtra("fragmentMode", "listViewing")
                    createListIntent.putExtra("listId", listsArray[adapterPosition].getListId().toString()) // Adds the id of the list just clicked and passes it on to the create fragment
                    itemView.context.startActivity(createListIntent)
            }
        }

    }

    fun deleteItem(position: Int) {
        listsArray.removeAt(position)
        notifyDataSetChanged()
    }
}