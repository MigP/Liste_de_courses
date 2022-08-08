package be.bf.android.listedecourses.models

class ListOfCategories (val _nameList: ArrayList<String>, val _imageList: ArrayList<Int>, var _isSelectedList: ArrayList<Boolean>){
    val nameList: ArrayList<String>
    val imageList: ArrayList<Int>
    val isSelectedList: ArrayList<Boolean>

    init {
        nameList = _nameList
        imageList = _imageList
        isSelectedList = _isSelectedList
    }

    fun getName(index: Int): String {
        return nameList.get(index)
    }

    fun getImage(index: Int): Int {
        return imageList.get(index)
    }

    fun getIsSelected(index: Int): Boolean {
        return isSelectedList.get(index)
    }
}