package com.service;

import com.vo.OrderReportVO;
import com.vo.SalesTop10ReportVO;
import com.vo.TurnoverReportVO;
import com.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end);

    UserReportVO getUserCount(LocalDate begin, LocalDate end);

    OrderReportVO getOrdersReport(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);

    void exportBusinessData(HttpServletResponse response);
}
