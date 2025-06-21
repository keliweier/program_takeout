import request from '@/utils/request';
import { AxiosResponse } from 'axios';

//登录
export const login = (data: any) =>
    request({
        url: '/employee/login',
        method: 'post',
        data: data
    });


//登出
export const userLogout=(params:any) => 
    request({
        url:'/employee/logout',
        method:'post',
        params
    })

//添加员工
export const addEmployee = (data: any) =>
    request({
        url: '/employee',
        method: 'post',
        data: data
    })

//获取员工列表
export const getEmployeeList = (params: any) =>
    request({
        url: '/employee/page',
        method: 'get',
        params: params
    })

//启用禁用员工账号
export const enanbleOrDisableEmp = (params: any) =>
    request({
        url: `/employee/status/${params.status}`,
        method: 'post',
        params: {id: params.id}
    })

//根据id查询员工账号
export const queryById = (id: number) =>
    request({
        url: `/employee/${id}`,
        method: 'get',
    })

//修改员工
export const updateEmployee=(data: any)=>
    request({
        url: '/employee',
        method: 'put',
        data: data
    })

