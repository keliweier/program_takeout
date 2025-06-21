package com.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.constant.MessageConstant;
import com.constant.StatusConstant;
import com.dto.CategoryDTO;
import com.dto.CategoryPageQueryDTO;
import com.entity.Category;
import com.exception.DeletionNotAllowedException;
import com.mapper.CategoryMapper;
import com.mapper.DishMapper;
import com.mapper.SetMealMapper;
import com.result.PageResult;
import com.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private DishMapper dishMapper;

    @Override
    public PageResult findAllByPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        List<Category> categoryList = categoryMapper.findAllByPage(categoryPageQueryDTO);
        Page<Category> p = (Page<Category>) categoryList;
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void saveCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setStatus(StatusConstant.ENABLE);
        categoryMapper.saveCategory(category);
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .build();
        categoryMapper.updateCategory(category);
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        categoryMapper.updateCategory(category);
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        List<Long> dishIds = dishMapper.findByCategoryId(id);
        if (dishIds != null && !dishIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        List<Long> setMealIds = setMealMapper.findByCategoryId(id);
        if (setMealIds != null && !setMealIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteCategory(id);
    }

    @Override
    public List<Category> findByType(Integer type) {
        return categoryMapper.findByType(type);
    }
}
