package com.ygz.aspen.model;

import lombok.Data;

@Data
public class User {

    private Long userId;

    private String uname;

    private String unick;

    private String password;

    private Integer isDeleted;

    private Long createTime;

    private Long updateTime;

}
