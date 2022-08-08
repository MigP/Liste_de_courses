package be.bf.android.listedecourses.models

class Category (var name: String, var icon: Int, var isSelected: Boolean) {

    init {
        this.name = name
        this.icon = icon
        this.isSelected = isSelected
    }

//    fun getName(): String {
//        return this.name
//    }
//
//    fun getIcon(): Int {
//        return this.icon
//    }
//
//    fun getIsSelected(): Boolean {
//        return this.isSelected
//    }
}