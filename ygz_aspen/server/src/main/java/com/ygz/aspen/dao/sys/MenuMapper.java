package com.ygz.aspen.dao.sys;

import com.ygz.aspen.model.sys.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MenuMapper {

    Menu selectMenuById(@Param("menuId")Long menuId);

    int addMenu(Menu menu);

}
