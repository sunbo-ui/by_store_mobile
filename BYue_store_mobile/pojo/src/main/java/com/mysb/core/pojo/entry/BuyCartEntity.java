package com.mysb.core.pojo.entry;

import java.io.Serializable;

public class BuyCartEntity implements Serializable {
    private Long id; //goodsId
    private String name;
    private String price;
    private Integer num;
    private String smallImage;
    private Boolean checked;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "BuyCartEntity{" +
                "id=" + id +
                ", price='" + price + '\'' +
                ", num='" + num + '\'' +
                ", smallImage='" + smallImage + '\'' +
                '}';
    }
}
