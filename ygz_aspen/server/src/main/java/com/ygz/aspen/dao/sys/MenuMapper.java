package com.ygz.aspen.dao.sys;

import com.ygz.aspen.model.sys.Menu;
import com.ygz.aspen.param.sys.MenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {

    Menu selectMenuById(@Param("menuId")Long menuId);

    int addMenu(Menu menu);

    List<Menu> selectMenuList(MenuDTO menuDTO);

}
