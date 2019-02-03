package com.example.bottomnavigationcheck;

import android.widget.ImageView;

public class Tyan {
    private String  name;
    private int icon;
    private int id;
    private boolean found;
    Tyan(String _name, int _icon, int _id, boolean _found) {
        setName(_name);
        setIcon(_icon);
        setId(_id);
        setFound(_found);
    }

    private void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon (){
        return icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }
}
