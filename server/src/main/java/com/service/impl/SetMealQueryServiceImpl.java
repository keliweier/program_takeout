package com.service.impl;

import com.constant.StatusConstant;
import com.entity.Setmeal;
import com.mapper.SetMealMapper;
import com.service.SetMealQueryService;
import com.vo.DishItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetMealQueryServiceImpl implements SetMealQueryService {

    @Autowired
    private SetMealMapper setMealMapper;

    @Override
    public List<Setmeal> findByCategoryId(Long categoryId) {
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);
        return setMealMapper.findByCategoryIdToList(setmeal);
    }

    @Override
    public List<DishItemVO> findBySetmealId(Long id) {
        return setMealMapper.findBySetmealId(id);
    }
}
