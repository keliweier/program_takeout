package com.mapper;

import com.annotation.AutoFill;
import com.dto.CategoryPageQueryDTO;
import com.entity.Category;
import com.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> findAllByPage(CategoryPageQueryDTO categoryPageQueryDTO);

    @AutoFill(value = OperationType.INSERT)
    void saveCategory(Category category);

    @AutoFill(value = OperationType.UPDATE)
    void updateCategory(Category category);

    @Delete("delete from category where id = #{id}")
    void deleteCategory(Long id);

    List<Category> findByType(Integer type);
}
