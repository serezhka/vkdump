package com.github.serezhka.vkdump.dao.online.entity;

import com.github.serezhka.vkdump.dao.entity.PhotoEntity;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public class FaveEntity {

    private String type;

    private PhotoEntity photo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PhotoEntity getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoEntity photo) {
        this.photo = photo;
    }
}
