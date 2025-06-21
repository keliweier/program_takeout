package com.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.constant.MessageConstant;
import com.constant.StatusConstant;
import com.dto.SetmealDTO;
import com.dto.SetmealPageQueryDTO;
import com.entity.Setmeal;
import com.entity.SetmealDish;
import com.exception.DeletionNotAllowedException;
import com.mapper.SetMealDishMapper;
import com.mapper.SetMealMapper;
import com.result.PageResult;
import com.service.SetMealService;
import com.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Override
    public PageResult findAllByPage(SetmealPageQueryDTO setMealPageQueryDTO) {
        PageHelper.startPage(setMealPageQueryDTO.getPage(), setMealPageQueryDTO.getPageSize());
        List<SetmealVO> list = setMealMapper.findAllByPage(setMealPageQueryDTO);
        Page<SetmealVO> p = (Page<SetmealVO>) list;
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Transactional
    @Override
    public void saveSetMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setMealMapper.saveSetMeal(setmeal);

        Long dishId = setmeal.getId();
        List<SetmealDish> setMealDishes = setmealDTO.getSetmealDishes();
        if (setMealDishes != null && !setMealDishes.isEmpty()) {
            setMealDishes.forEach(setMealDish -> {
                setMealDish.setSetmealId(dishId);
            });
            setMealDishMapper.saveSetMealDish(setMealDishes);
        }
    }

    @Transactional
    @Override
    public void updateSetMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setMealMapper.updateSetMeal(setmeal);

        setMealDishMapper.deleteByDishId(setmealDTO.getId());
        List<SetmealDish> setMealDishes = setmealDTO.getSetmealDishes();
        if (setMealDishes != null && !setMealDishes.isEmpty()) {
            setMealDishes.forEach(setMealDish -> {
                setMealDish.setSetmealId(setmealDTO.getId());
            });
            setMealDishMapper.saveSetMealDish(setMealDishes);
        }
    }

    @Override
    public SetmealVO findById(Long id) {
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setMealMapper.findById(id), setmealVO);
        setmealVO.setSetmealDishes(setMealDishMapper.findByDishId(id));
        return setmealVO;
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setMealMapper.updateSetMeal(setmeal);
    }

    @Transactional
    @Override
    public void deleteSetMeal(List<Long> ids) {
        for (Long id : ids) {
            SetmealVO setmealVO = setMealMapper.findById(id);
            if (Objects.equals(setmealVO.getStatus(), StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        for (Long id : ids) {
            setMealMapper.deleteSetMeal(id);
            setMealDishMapper.deleteByDishId(id);
        }
    }
}
