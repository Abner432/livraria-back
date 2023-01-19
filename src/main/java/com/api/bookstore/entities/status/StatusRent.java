package com.api.bookstore.entities.status;

import lombok.Getter;

@Getter
public enum StatusRent {
    ENTREGUE ("ENTREGUE"),
    ATRASADO ("ATRASADO"),
    LENDO ("LENDO");

    private final String status;

    public String getRentStatus(){
        return this.status;
    }

    StatusRent(String status) {
        this.status = status;
    }
}
