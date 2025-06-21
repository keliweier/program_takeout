package com.service;

import com.dto.SetmealDTO;
import com.dto.SetmealPageQueryDTO;
import com.result.PageResult;
import com.vo.SetmealVO;

import java.util.List;

public interface SetMealService {
    PageResult findAllByPage(SetmealPageQueryDTO setMealPageQueryDTO);

    void saveSetMeal(SetmealDTO setmealDTO);

    void updateSetMeal(SetmealDTO setmealDTO);

    SetmealVO findById(Long id);

    void updateStatus(Integer status, Long id);

    void deleteSetMeal(List<Long> ids);
}
