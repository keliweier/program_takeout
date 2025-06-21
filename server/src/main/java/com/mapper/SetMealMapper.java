package com.mapper;

import com.annotation.AutoFill;
import com.dto.SetmealPageQueryDTO;
import com.entity.Setmeal;
import com.enumeration.OperationType;
import com.vo.DishItemVO;
import com.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetMealMapper {
    List<Long> findByCategoryId(Long id);

    List<SetmealVO> findAllByPage(SetmealPageQueryDTO setMealPageQueryDTO);

    @AutoFill(value = OperationType.INSERT)
    void saveSetMeal(Setmeal setmeal);

    SetmealVO findById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void updateSetMeal(Setmeal setmeal);

    @Delete("DELETE FROM setmeal WHERE id = #{id}")
    void deleteSetMeal(Long id);

    List<Setmeal> findByCategoryIdToList(Setmeal setmeal);

    List<DishItemVO> findBySetmealId(Long id);

    Integer countByMap(Map map);
}
