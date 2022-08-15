package be.bf.android.listedecourses.models.entities;

import androidx.annotation.NonNull;

import java.util.Date;

public class ListeListes {
    private int id;
    private String listName;
    private String listTag;
    private String date;

    public ListeListes() {
    }

    public ListeListes(int id, String listName, String listTag, String date) {
        this.id = id;
        this.listName = listName;
        this.listTag = listTag;
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return "ListeListes{" +
                "id='" + id + '\'' +
                ", listName='" + listName + '\'' +
                ", listTag='" + listTag + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public ListeListes setId(int id) {
        this.id = id;
        return this;
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

    public String getDate() {
        return date;
    }

    public ListeListes setDate(String date) {
        this.date = date;
        return this;
    }
}
