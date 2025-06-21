package com.service;

import com.dto.OrdersPageQueryDTO;
import com.dto.OrdersPaymentDTO;
import com.dto.OrdersSubmitDTO;
import com.result.PageResult;
import com.vo.OrderPaymentVO;
import com.vo.OrderSubmitVO;
import com.vo.OrderVO;

public interface OrderUserService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

    PageResult findHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderVO findOrderDetailById(Long id);

    void cancelOrder(Long id) throws Exception;

    void repetition(Long id);

    void reminder(Long id);
}
