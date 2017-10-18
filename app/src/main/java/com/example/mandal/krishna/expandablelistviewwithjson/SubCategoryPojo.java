package com.example.mandal.krishna.expandablelistviewwithjson;

/**
 * Created by root on 2/3/16.
 */
public class SubCategoryPojo {


    private String title;
    private String id;

    public SubCategoryPojo(String title, String id) {
        this.title = title;
        this.id = id;
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
}
