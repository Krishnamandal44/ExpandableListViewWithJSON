package com.example.mandal.krishna.expandablelistviewwithjson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/3/16.
 */
public class CategoryPojo {

    private String title;
    private String id;
    private List<SubCategoryPojo> subCategoryPojos = new ArrayList<SubCategoryPojo>();

    public CategoryPojo(String id, String title, List<SubCategoryPojo> subCategoryPojos) {
        this.id=id;
        this.title = title;
        this.subCategoryPojos = subCategoryPojos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SubCategoryPojo> getSubCategoryPojos() {
        return subCategoryPojos;
    }

    public void setSubCategoryPojos(List<SubCategoryPojo> subCategoryPojos) {
        this.subCategoryPojos = subCategoryPojos;
    }
}
