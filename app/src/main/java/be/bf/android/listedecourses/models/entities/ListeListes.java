package be.bf.android.listedecourses.models.entities;

public class ListeListes {
    private String listName;
    private String listTag;

    public ListeListes() {
    }

    public ListeListes(String listName, String listTag) {
        this.listName = listName;
        this.listTag = listTag;
    }

    @Override
    public String toString() {
        return "ListeListes{" +
                "listName='" + listName + '\'' +
                ", listTag='" + listTag + '\'' +
                '}';
    }

    public String getListName() {
        return listName;
    }

    public ListeListes setListName(String listName) {
        this.listName = listName;
        return this;
    }

    public String getListTag() {
        return listTag;
    }

    public ListeListes setListTag(String listTag) {
        this.listTag = listTag;
        return this;
    }
}
