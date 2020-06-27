package com.ygz.aspen.vo.user.res;

import lombok.Data;

import java.util.List;

@Data
public class UserMenuVO {

    private Integer menuLevel;

    private String path;

    private String component;

    private String redirect;

    private String name;

    private MenuMeatVO meatInfo;

    private List<UserMenuVO> childrenList;

}
