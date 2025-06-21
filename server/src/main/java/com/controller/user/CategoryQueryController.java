package com.controller.user;

import com.entity.Category;
import com.result.Result;
import com.service.CategoryQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/category")
@Slf4j
public class CategoryQueryController {

    @Autowired
    private CategoryQueryService categoryQueryService;

    @GetMapping("/list")
    public Result<List<Category>> findAll(Integer type) {
        log.info("查询所有分类");
        List<Category> list = categoryQueryService.findByType(type);
        return Result.success(list);
    }
}
