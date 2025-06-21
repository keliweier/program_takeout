import request from '@/utils/request';

/**
 *
 * 套餐管理
 *
 **/


// 查询列表数据
export  const getSetmealPage=(params: any)=>
    request({
        url: '/setmeal/page',
        method: 'get',
        params:params
    })

// 修改数据接口
export  const editSetmeal=(data:any)=>
    request({
        url: '/setmeal',
        method: 'put',
        data: data
    })

//删除数据接口
export  const deleteSetmeal=(ids: string)=>
    request({
        url: '/setmeal',
        method: 'delete',
        params: { ids }
    })

// 批量起售禁售
export  const setmealStatusByStatus=(params: any)=>
    request({
        url: `/setmeal/status/${params.status}`,
        method: 'post',
        params: {id:params.id}
    })



// 根据id查询详情接口
export const querySetmealById=(id: string | (string | null)[])=>
    request({
        url: `setmeal/${id}`,
        method: 'get'
    })

// 新增数据接口
export const addSetmeal=(data: any)=>
    request({
        url: '/setmeal',
        method: 'post',
        data:data
    })

//菜品分类数据查询
//可用category中的方法
// export const dishCategoryList=(params: any) => 
//     request({
//       url: `/category/list`,
//       method: 'get',
//       params: { ...params }
//     })

