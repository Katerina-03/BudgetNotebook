package org.example;

import lombok.*;
import java.util.*;

@Getter
@Setter
public class Category {
    private String name;
    private List<Category> subcategories;
    private List<Transaction> transactions;
    private List<String> mcc;

    public Category(String name){
        this.name = name;
        this.mcc = new ArrayList<>();
        this.subcategories = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }


    public boolean containsMcc (String thisMcc){
        return  mcc.contains(thisMcc);
    }

    public void addMcc (String newMcc){
        if (newMcc !=null && !containsMcc(newMcc)){
            mcc.add(newMcc);
        }
    }

}
