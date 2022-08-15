package be.bf.android.listedecourses.models.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.listedecourses.MainActivity
import be.bf.android.listedecourses.R
import be.bf.android.listedecourses.models.entities.GeneralList

class DetailedListAdapter(val listsArray: ArrayList<ArrayList<GeneralList>>, val passedContext: Context): RecyclerView.Adapter<DetailedListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailedListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.detailed_list_of_lists, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listsArray[0].size
    }
    override fun onBindViewHolder(holder: DetailedListAdapter.ViewHolder, position: Int) {
        holder.listName.text = listsArray[0][position].getListName()
        holder.listTag.text = listsArray[0][position].getListTag()
        holder.listDate.text = listsArray[0][position].getListDate()
        holder.listItems.text = listsArray[0][position].getListItems()
        if (listsArray[0][position].getColour().equals("red")) {
            holder.listItems.setTextColor(passedContext.getResources().getColor(R.color.unfinished))
        } else if (listsArray[0][position].getColour().equals("green")) {
            holder.listItems.setTextColor(passedContext.getResources().getColor(R.color.finished))
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var listName: TextView
        var listTag: TextView
        var listDate: TextView
        var listItems: TextView

        init {
            listName = itemView.findViewById(R.id.tv_detailed_listName)
            listTag = itemView.findViewById(R.id.tv_detailed_listTag)
            listDate = itemView.findViewById(R.id.tv_detailed_date)
            listItems = itemView.findViewById(R.id.tv_detailed_items)

            itemView.setOnClickListener {
                // Displays the detailed view of this list
                    val createListIntent = Intent(itemView.context, MainActivity::class.java)
                    createListIntent.putExtra("targetFragment", "create")
                    createListIntent.putExtra("fragmentMode", "listViewing")
                    createListIntent.putExtra("listId", listsArray[0][adapterPosition].getListId().toString()) // Adds the id of the list just clicked and passes it on to the create fragment
                    itemView.context.startActivity(createListIntent)
            }
        }
    }

    fun deleteItem(position: Int) {
        listsArray[0].removeAt(position)
        notifyDataSetChanged()
    }
}