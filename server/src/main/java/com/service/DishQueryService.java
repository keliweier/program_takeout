package com.service;

import com.vo.DishVO;

import java.util.List;

public interface DishQueryService {
    List<DishVO> findByCategoryId(Long categoryId);
}
