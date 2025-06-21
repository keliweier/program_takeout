package com.service;

import com.dto.OrdersCancelDTO;
import com.dto.OrdersConfirmDTO;
import com.dto.OrdersPageQueryDTO;
import com.dto.OrdersRejectionDTO;
import com.result.PageResult;
import com.vo.OrderStatisticsVO;
import com.vo.OrderVO;

public interface OrderAdminService {
    PageResult findByPage(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statistics();

    OrderVO findDetails(Long id);

    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    void cancel(OrdersCancelDTO ordersCancelDTO);

    void delivery(Long id);

    void complete(Long id);
}
