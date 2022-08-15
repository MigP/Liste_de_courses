package be.bf.android.listedecourses.models.entities

class GeneralList {
    private var listId: Int = 0
    private var listName: String? = null
    private var listTag: String? = null
    private var listDate: String? = null
    private var listItems: String? = null
    private var colour: String = ""

    fun GeneralList() {}

    fun GeneralList(listId: Int, listName: String?, listTag: String?, listItems: String?, listDate: String?, colour: String) {
        this.listId = listId
        this.listName = listName
        this.listTag = listTag
        this.listItems = listItems
        this.listDate = listDate
        this.colour = colour
    }

    override fun toString(): String {
        return "ListeListes{" +
                "listId='" + listId + '\'' +
                ", listName='" + listName + '\'' +
                ", listTag='" + listTag + '\'' +
                ", listItems='" + listItems + '\'' +
                ", listDate='" + listDate + '\'' +
                ", colour='" + colour + '\'' +
                '}'
    }

    fun getListId(): Int {
        return listId
    }

    fun setListId(listId: Int): GeneralList? {
        this.listId = listId
        return this
    }

    fun getColour(): String {
        return colour
    }

    fun setColour(colour: String): GeneralList? {
        this.colour = colour
        return this
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

    fun getListDate(): String? {
        return listDate
    }

    fun setListDate(listDate: String?): GeneralList? {
        this.listDate = listDate
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