package com.example.todolistandroidapp.Model;

import com.google.gson.annotations.SerializedName;

public class ListTypeData {

    @SerializedName("id")
    private int listTypeId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String Description;

    @SerializedName("createdDate")
    private long createdDate;

    @SerializedName("updatedDate")
    private long updatedDate;

    @SerializedName("ownerBy")
    private String ownerBy;

    @SerializedName("modifierBy")
    private String modifierBy;

    @SerializedName("status")
    private Byte status;

    public int getListTypeId() {
        return listTypeId;
    }

    public void setListTypeId(int listTypeId) {
        this.listTypeId = listTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getOwnerBy() {
        return ownerBy;
    }

    public void setOwnerBy(String ownerBy) {
        this.ownerBy = ownerBy;
    }

    public String getModifierBy() {
        return modifierBy;
    }

    public void setModifierBy(String modifierBy) {
        this.modifierBy = modifierBy;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
