package com.service;

import com.dto.EmployeeDTO;
import com.dto.EmployeeLoginDTO;
import com.dto.EmployeePageQueryDTO;
import com.dto.PasswordEditDTO;
import com.entity.Employee;
import com.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    PageResult findAllByPage(EmployeePageQueryDTO employeePageQueryDTO);

    void saveEmp(EmployeeDTO employeedto);

    void updateStatus(Integer status, Long id);

    void updateEmp(EmployeeDTO employeeDTO);

    Employee findById(Long id);

    void updatePassword(PasswordEditDTO passwordEditDTO);
}
