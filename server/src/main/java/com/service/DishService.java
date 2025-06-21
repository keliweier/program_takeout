package com.service;

import com.dto.DishDTO;
import com.dto.DishPageQueryDTO;
import com.result.PageResult;
import com.vo.DishVO;

import java.util.List;

public interface DishService {
    PageResult findAllByPage(DishPageQueryDTO dishPageQueryDTO);

    void saveDish(DishDTO dishDTO);

    void deleteDish(List<Long> ids);

    DishVO findById(Long id);

    void updateDish(DishDTO dishDTO);

    void updateStatus(Integer status, Long id);

    List<DishVO> findByCategoryId(Long categoryId);
}
