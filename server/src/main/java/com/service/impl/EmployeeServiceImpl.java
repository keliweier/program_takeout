package com.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.constant.MessageConstant;
import com.constant.PasswordConstant;
import com.constant.StatusConstant;
import com.context.BaseContext;
import com.dto.EmployeeDTO;
import com.dto.EmployeeLoginDTO;
import com.dto.EmployeePageQueryDTO;
import com.dto.PasswordEditDTO;
import com.entity.Employee;
import com.exception.AccountLockedException;
import com.exception.AccountNotFoundException;
import com.exception.PasswordEditFailedException;
import com.exception.PasswordErrorException;
import com.mapper.EmployeeMapper;
import com.result.PageResult;
import com.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public PageResult findAllByPage(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        List<Employee> employeeList = employeeMapper.findAllByPage(employeePageQueryDTO);
        Page<Employee> p = (Page<Employee>) employeeList;
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void saveEmp(EmployeeDTO employeedto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeedto, employee);
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        Long empId = BaseContext.getCurrentId();

        employeeMapper.saveEmp(employee);
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeMapper.updateEmp(employee);
    }

    @Override
    public void updateEmp(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employeeMapper.updateEmp(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeMapper.findById(id);
    }

    @Override
    public void updatePassword(PasswordEditDTO passwordEditDTO) {
        passwordEditDTO.setEmpId(BaseContext.getCurrentId());
        String oldPassword = employeeMapper.findById(passwordEditDTO.getEmpId()).getPassword();
        if (!DigestUtils.md5DigestAsHex(passwordEditDTO.getOldPassword().getBytes()).equals(oldPassword)) {
            throw new PasswordEditFailedException(MessageConstant.PASSWORD_EDIT_FAILED);
        } else if (passwordEditDTO.getNewPassword().equals(passwordEditDTO.getOldPassword())) {
            throw new PasswordEditFailedException(MessageConstant.PASSWORD_SAME);
        } else {
            Employee employee = Employee.builder()
                    .id(passwordEditDTO.getEmpId())
                    .password(DigestUtils.md5DigestAsHex(passwordEditDTO.getNewPassword().getBytes()))
                    .build();
            employeeMapper.updateEmp(employee);
        }
    }

}
