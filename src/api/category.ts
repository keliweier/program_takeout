import request from '@/utils/request';

//分类分页查询
export const getCategoryPage=(params:any)=>
    request({
        url: '/category/page',
        method: 'get',
        params:params
    })


//删除分类
export const deleCategory=(ids: string)=>
    request({
        url: '/category',
        method: 'delete',
        params: {id: ids}
    })

//修改分类
export const editCategory=(data:any)=>
    request({
        url: '/category',
        method:'put',
        data: {...data}
    })


//新增分类
export const addCategory=(data: any)=>
    request({
        url: '/category',
        method: 'post',
        data: data
    })


//启用禁用分类
export const enableOrDisableEmployee=(data:any)=>
    request({
        url: `/category/status/${data.status}`,
        method: 'post',
        params: {id:data.id}
    })


//查询套餐分类
export const getCategoryByType=(param:any)=>
    request({
        url: '/category/list',
        method: 'get',
        params: param
    })