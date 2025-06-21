package com.controller.user;

import com.dto.OrdersPageQueryDTO;
import com.dto.OrdersPaymentDTO;
import com.dto.OrdersSubmitDTO;
import com.result.PageResult;
import com.result.Result;
import com.service.OrderUserService;
import com.vo.OrderPaymentVO;
import com.vo.OrderSubmitVO;
import com.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order")
@Slf4j
public class OrderUserController {

    @Autowired
    private OrderUserService orderUserService;

    @PostMapping("/submit")
    public Result<OrderSubmitVO> submitOrder(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("用户下单：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderUserService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    @PutMapping("/payment")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderUserService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

    @GetMapping("/historyOrders")
    public Result<PageResult> findHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO){
        log.info("查询历史订单：{}", ordersPageQueryDTO);
        return Result.success(orderUserService.findHistoryOrders(ordersPageQueryDTO));
    }

    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> findOrderDetailById(@PathVariable("id") Long id){
        log.info("查询订单详情：{}", id);
        return Result.success(orderUserService.findOrderDetailById(id));
    }

    @PutMapping("/cancel/{id}")
    public Result cancel(@PathVariable("id") Long id) throws Exception {
        log.info("取消订单：{}", id);
        orderUserService.cancelOrder(id);
        return Result.success();
    }

    @PostMapping("/repetition/{id}")
    public Result repetition(@PathVariable("id") Long id){
        log.info("再来一单：{}", id);
        orderUserService.repetition(id);
        return Result.success();
    }

    @GetMapping("reminder/{id}")
    public Result reminder(@PathVariable("id") Long id){
        log.info("催单：{}", id);
        orderUserService.reminder(id);
        return Result.success();
    }
}
