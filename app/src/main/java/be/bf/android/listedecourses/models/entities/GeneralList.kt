package be.bf.android.listedecourses.models.entities

class GeneralList {
    private var listName: String? = null
    private var listTag: String? = null
    private var listItems: String? = null

    fun GeneralList() {}

    fun GeneralList(listName: String?, listTag: String?, listItems: String?) {
        this.listName = listName
        this.listTag = listTag
        this.listItems = listItems
    }

    override fun toString(): String {
        return "ListeListes{" +
                "listName='" + listName + '\'' +
                ", listTag='" + listTag + '\'' +
                ", listItems='" + listItems + '\'' +
                '}'
    }

    fun getListName(): String? {
        return listName
    }

    fun setListName(listName: String?): GeneralList? {
        this.listName = listName
        return this
    }

    fun getListTag(): String? {
        return listTag
    }

    fun setListTag(listTag: String?): GeneralList? {
        this.listTag = listTag
        return this
    }

    fun getListItems(): String? {
        return listItems
    }

    fun setListItems(listItems: String?): GeneralList? {
        this.listItems = listItems
        return this
    }
}