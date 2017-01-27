package com.github.serezhka.vkdump.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Entity
@Table(name = "gift", schema = "vkdump")
public class GiftEntity extends AbstractEntity {

    public Integer giftId;
    public String thumb48;
    public String thumb96;
    public String thumb256;

    @Column(name = "gift_id")
    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    @Column(name = "thumb_48")
    public String getThumb48() {
        return thumb48;
    }

    public void setThumb48(String thumb48) {
        this.thumb48 = thumb48;
    }

    @Column(name = "thumb_96")
    public String getThumb96() {
        return thumb96;
    }

    public void setThumb96(String thumb96) {
        this.thumb96 = thumb96;
    }

    @Column(name = "thumb_256")
    public String getThumb256() {
        return thumb256;
    }

    public void setThumb256(String thumb256) {
        this.thumb256 = thumb256;
    }
}
