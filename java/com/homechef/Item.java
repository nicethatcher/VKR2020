package com.homechef;

import java.util.List;

public class Item {

    String id,itemName;
    //List<String> itemList;

    public Item(){

    }

    public Item(String id, String itemName) {
        this.id = id;
        this.itemName = itemName;
        //this.itemList = itemList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
