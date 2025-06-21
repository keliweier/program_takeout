package com.mapper;

import com.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void saveBatch(List<DishFlavor> flavors);

    @Delete("DELETE from dish_flavor where dish_id = #{id}")
    void deleteByDishId(Long id);

    List<DishFlavor> findByDishId(Long id);
}
