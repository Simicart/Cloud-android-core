package com.simicart.core.notification.entity;

import java.io.Serializable;

public class NotificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    String id;
    String showPopup = "1";
    String title = "";
    String message;
    String url;
    String image;
    String device;
    String seq_num;
    String updated_at;
    String created_at;
    String type = "0";
    String productID;
    String categoryID;
    String categoryName = "";
    String hasChild;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getSeq_num() {
        return seq_num;
    }

    public void setSeq_num(String seq_num) {
        this.seq_num = seq_num;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getShowPopup() {
        return showPopup;
    }

    public void setShowPopup(String showPopup) {
        this.showPopup = showPopup;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object object) {

        NotificationEntity other = (NotificationEntity) object;

        if (other.getShowPopup() != null) {
            if (!showPopup.equals(other.getShowPopup())) {
                return false;
            }
        } else {
            return false;
        }
        if (other.getTitle() != null) {
            if (!title.equals(other.getTitle())) {
                return false;
            }
        } else {
            return false;
        }
        if (other.getMessage() != null) {
            if (!message.equals(other.getMessage())) {
                return false;
            }
        } else {
            return false;
        }
        if (other.getImage() != null) {
            if (!image.equals(other.getImage())) {
                return false;
            }
        } else {
            return false;
        }
        if (other.getUrl() != null) {
            if (!url.equals(other.getUrl())) {
                return false;
            }
        } else {
            return false;
        }
        if (other.getType() != null) {
            if (!type.equals(other.getType())) {
                return false;
            }
        } else {
            return false;
        }
        if (other.getProductID() != null) {
            if (!productID.equals(other.getProductID())) {
                return false;
            }
        } else {
            return false;
        }
        if (other.getCategoryID() != null) {
            if (!categoryID.equals(other.getCategoryID())) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
