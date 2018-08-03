package com.postpc.nimrod.sappa_postpc.models;

public class CategorySearchModel {

    private String name;
    private boolean isSelected;

    public CategorySearchModel(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public CategorySearchModel(CategorySearchModel category) {
        this.name = category.getName();
        this.isSelected = category.isSelected();
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
