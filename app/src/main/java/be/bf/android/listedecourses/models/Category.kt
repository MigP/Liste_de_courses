package be.bf.android.listedecourses.models

class Category (var name: String, var icon: Int, var isSelected: Boolean) {

    init {
        this.name = name
        this.icon = icon
        this.isSelected = isSelected
    }
}