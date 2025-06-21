package com.service;

import com.entity.Setmeal;
import com.vo.DishItemVO;

import java.util.List;

public interface SetMealQueryService {
    List<Setmeal> findByCategoryId(Long categoryId);

    List<DishItemVO> findBySetmealId(Long id);
}
