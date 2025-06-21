package com.service;

import com.vo.BusinessDataVO;
import com.vo.DishOverViewVO;
import com.vo.OrderOverViewVO;
import com.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkSpaceService {

    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    OrderOverViewVO getOrderOverView();

    DishOverViewVO getDishOverView();

    SetmealOverViewVO getSetmealOverView();
}
