<template>
    <div class="dashboard-container">
    <div class="container">
      <div class="tableBar">
        <label style="margin-right: 5px">
          员工姓名: 
        </label>
        <el-input v-model="name" placeholder="请输入员工姓名" style="width: 15%" />
        <el-button type="primary" style="margin-left: 25px" @click="pageQuery()">查询</el-button>
        <el-button type="primary" style="float: right" @click="handleAddEmp">+添加员工</el-button>
      </div>
      <el-table
        :data="records"
        stripe
        style="width: 100%">
        <el-table-column
          prop="name"
          label="员工姓名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="username"
          label="账号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="手机号">
        </el-table-column>
        <el-table-column
          prop="status"
          label="账号状态">
          <template v-slot="scope">
            {{scope.row.status === 0 ? '禁用' : '启用'}}
          </template>
        </el-table-column>
        <el-table-column
          prop="updateTime"
          label="最后操作数据">
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="text" @click="handleUpdateEmp(scope.row)">修改</el-button>
            <el-button type="text" @click="handleStartOrStop(scope.row)">
                {{scope.row.status === 1 ? '禁用' : '启用'}}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        class="pageList"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>




<style lang="scss" scoped>
.disabled-text {
  color: #bac0cd !important;
}
.container{
    position: relative;
}



</style>


<script lang="ts">
import Vue from 'vue'
import { getEmployeeList ,enanbleOrDisableEmp} from '@/api/employee'
export default Vue.extend({
    name: 'EmployeeIndex',
    data() {
        return {
            name: '', // 员工姓名
            page: 1,
            pageSize: 10,
            total: 0,
            records: [],

        }
    },

    //生命周期方法
    created(){
        this.pageQuery()
    },

    methods: {
        //分页查询
        pageQuery() {
            const params={name: this.name, page: this.page, pageSize: this.pageSize};
            //通过向后端发送get请求获取员工数据
            getEmployeeList(params)
            .then((res)=>{
                if(res.data.code===1){
                    this.total=res.data.data.total;
                    this.records=res.data.data.records;
                }
            }).catch((error)=>{
                this.$message.error("请求出错了"+error.message);
            })
        },
        handleAddEmp(){
            this.$router.push('/employee/add');
        },
        //跳转到修改页面并通过地址栏传递id
        handleUpdateEmp(row: any){
            //判断操作的账号是否为管理员
            if(row.username==='admin'){
                this.$message.error('该账号为系统的管理员账号，无法修改');
                return;
            }
            this.$router.push({
                path: '/employee/add',
                query: {id: row.id}
            })
        },
        //启用禁用员工账号
        handleStartOrStop(row: any){
            //判断操作的账号是否为管理员
            if(row.username==='admin'){
                this.$message.error('该账号为系统的管理员账号，无法禁用');
                return;
            }

            //弹出确认提示框
            this.$confirm('确定要修改当前员工的状态吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(()=>{
                const p={
                    id: row.id,
                    status: !row.status ? 1 : 0
                }
                enanbleOrDisableEmp(p).then((res)=>{
                    if(res.data.code===1){
                        this.$message.success("员工状态修改成功！");
                        this.pageQuery();
                    }
                })
            }).catch(()=>{})

        },
        //pageSize变化
        handleSizeChange(pageSize: any){
            this.pageSize=pageSize;
            this.pageQuery();
        },
        //页码发送变化
        handleCurrentChange(page: any){
            this.page=page;
            this.pageQuery();
        }
    }
})
</script>