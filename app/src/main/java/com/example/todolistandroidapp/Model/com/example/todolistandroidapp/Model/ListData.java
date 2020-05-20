package com.example.todolistandroidapp.Model;

import com.google.gson.annotations.SerializedName;

public class ListData {

    @SerializedName("id")
    private long listId;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String Description;

    @SerializedName("startsAt")
    private long startsAt;

    @SerializedName("endsAt")
    private long endsAt;

    @SerializedName("type")
    private int type;

    @SerializedName("priority")
    private Byte priority;

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

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public long getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(long startsAt) {
        this.startsAt = startsAt;
    }

    public long getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(long endsAt) {
        this.endsAt = endsAt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
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
