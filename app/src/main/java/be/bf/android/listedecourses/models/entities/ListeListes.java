package be.bf.android.listedecourses.models.entities;

public class ListeListes {
    private int listId;

    public ListeListes() {
    }

    public ListeListes(int listId) {
        this.listId = listId;
    }

    @Override
    public String toString() {
        return "ListeListes{" +
                "listId='" + listId + '\'' +
                '}';
    }

    public int getListId() {
        return listId;
    }

    public ListeListes setListId(int listId) {
        this.listId = listId;
        return this;
    }
}
