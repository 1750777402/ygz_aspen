package com.ygz.aspen.model;

import lombok.Data;

@Data
public abstract class BaseModel {

    private Long created;

    private Long updated;

    private Integer isDeleted;

}
