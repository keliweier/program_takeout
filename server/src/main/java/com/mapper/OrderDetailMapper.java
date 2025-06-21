package com.mapper;

import com.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    void saveOrderDetail(List<OrderDetail> orderDetails);

    List<OrderDetail> getByOrderId(Long orderId);
}
