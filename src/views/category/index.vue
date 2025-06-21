<template>
    <div class="dashboard-container">
        <div class="container">
            <div class="tableBar"
                    style="display: inline-block; width: 100%">
                <label style="margin-right: 10px">
                    分类名称：</label>
                <el-input style="width: 15%"
                            placeholder="请填写分类名称"
                            v-model="name"
                            clearable
                            @clear="init"
                            @keyup.enter.native="init"/>
                <label style="margin-right: 5px; margin-left: 20px">
                    分类类型：</label>
                <el-select v-model="categoryType"
                            placeholder="请选择"
                            clearable
                            style="width: 15%"
                            @clear="init">
                    <!--  :key唯一标识，:value值，:label显示的选项-->
                    <el-option v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"/>
                </el-select>
                <el-button class="normal-btn continue"
                            @click="init(true)">查询</el-button>
                <div style="float: right">
                    <el-button type="primary"
                                class="continue" 
                                @click="addClass('class')">
                        +新增菜品分类
                    </el-button>
                    <el-button type="primary"
                                style="margin-left:20px"
                                @click="addClass('meal')">
                        +新增套餐分类
                    </el-button>
                </div>
            </div>
            <el-table v-if="tableData.length"
                            :data="tableData"
                            stripe
                            class="tableBox">
                    <el-table-column
                            prop="name"
                            label="分类名称">
                    </el-table-column>
                    <el-table-column
                            prop="type"
                            label="分类类型">
                            <template slot-scope="scope">
                                <span>{{ scope.row.type == '1' ? '菜品分类' : '套餐分类' }}</span>
                            </template>
                    </el-table-column>
                    <el-table-column
                            prop="sort"
                            label="排序">
                    </el-table-column>
                    <el-table-column label="状态">
                        <template slot-scope="scope">
                            <div class="tableColumn-status"
                                :class="{ 'stop-use': String(scope.row.status) === '0' }">
                            {{ String(scope.row.status) === '0' ? '禁用' : '启用' }}
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="updateTime"
                            label="操作时间">
                    </el-table-column>
                    <el-table-column
                            label="操作"
                            width="200">
                        <template v-slot="scope">
                            <el-button type="text" 
                                        size="small"
                                        class="blueBug"
                                        @click="editHandle(scope.row)">
                                修改
                            </el-button>
                            <el-button type="text" 
                                        size="small"
                                        class="delBut"
                                        @click="deleteHandle(scope.row.id)">
                                删除
                            </el-button>
                            <el-button type="text"
                                        size="small"
                                        class="non" 
                                        :class="{
                                            blueBug: scope.row.status == '0',
                                            delBut: scope.row.status != '0'
                                        }"
                                        @click="statusHandle(scope.row)">
                                {{scope.row.status === 1 ? '禁用' : '启用'}}
                            </el-button>
                        </template>
                    </el-table-column>
                    
            </el-table>   
            <Empty v-else
                :is-search="isSearch" />

            <el-pagination v-if="counts>10"
                            class="pageList"
                            :current-page="page"
                            :page-sizes="[10, 20, 30, 40]"
                            :page-size="pageSize"
                            :total="counts"
                            @size-chage="handleSizeChange"
                            @current-change="handleCurrentChange"
                            layout="total,sizes,pre,pager,next,jumper"/>
        </div>
        
        <el-dialog :visible.sync="classData.dialogVisible"
                    :title="classData.title"
                    width="30%"
                    :before-close="handleClose">
            <!-- ref:组件名，rule:输入要求，model:绑定的数据 -->
            <el-form ref="classData"
                    :rules="rules"
                    :model="classData"
                    class="demo-form-inline"
                    label-width="100px">
                <!-- prop:根据rule判断是否合规 -->
                <el-form-item label="分类名称"
                            prop="name">
                    <!-- v-model：双向绑定输入 -->
                    <el-input placeholder="请输入分类名称"
                            v-model="classData.name"
                            maxlength="20"/>
                </el-form-item>
                <el-form-item label="排序："
                            prop="sort">
                    <el-input placeholder="请输入排序"
                            v-model="classData.sort"/>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button size="medium"
                            @click="(classData.dialogVisible = false),
                                $refs.classData.resetFields()">
                    取消
                </el-button>
                <el-button type="primary"
                            :class="{ continue: actionType === 'add' }"
                            size="medium"
                            @click="submitForm()">确 定</el-button>
                <el-button v-if="action != 'edit'"
                            type="primary"
                            size="medium"
                            @click="submitForm('go')">
                    保存并继续添加
                </el-button>
            </span>

        </el-dialog>
    </div> 
</template>

<script >
//引入组件
import HeadLable from '@/components/HeadLable/index.vue'
import Empty from '@/components/Empty/index.vue'
import {
  getCategoryPage,           //分类分页查询
  deleCategory,              //删除分类
  editCategory,              //修改分类
  addCategory,               //新增分类
  enableOrDisableEmployee    //启用禁用分类
} from '@/api/category';


export default{
    name: 'CategoryIndex',
    components: {
        HeadLable,
        Empty
    },
    data(){
        return{
            name:'',
            categoryType:'',
            counts: 0,
            actionType: '',
            page: 1,
            pageSize: 10,
            type: 1,
            action: '',
            isSearch: false,
            options: [
                {
                value: 1,
                label: '菜品分类'
                },
                {
                value: 2,
                label: '套餐分类'
                }
            ],
            tableData:[],
            classData:{
                title: '添加菜品分类',
                dialogVisible: false,
                categoryId: '',
                name: '',
                sort: ''
            },
            rules:{
                name: [
                    { required: true, strigger: 'blur', 
                        validator:(rule,value,callback)=>{
                            const reg=new RegExp('^[a-zA-Z\u4e00-\u9fa5]+$');
                            //不能为空
                            if(!value)
                                callback(new Error("分类名称不能为空"));
                            //2-20个字符
                            else if(value.length<2||value.length>20)
                                callback(new Error("输入长度错误，请输入2-20个字符"))
                            //要求为英文字母（大小写不限）
                            // 或中文字符（\u4e00-\u9fa5）
                            //至少一个字符
                            else if(!reg.test(value))
                                callback(new Error("输入包含特殊范围"));
                            else
                                callback();
                        }
                    }
                ],
                sort:[
                    {
                        required: true,
                        trigger: 'blur',
                        validator:(rule,value,callback)=>{
                            //要求分类不为空
                            if(value||String(value)==='0'){
                                //输入有非数字
                                const reg=/^[\d]*$/;
                                if(!reg.test(value))
                                    callback(new Error("排序只能为数字"))
                                //输入大于99
                                else if(Number(value)>99)
                                    callback(new Error("排序只能为0-99"));
                            }
                            else{
                                callback(new Error("排序不能为空"));
                            }
                            
                        }
                    }
                ]

            }
        }
    },
    created(){
        this.init();
    },
    methods:{
        //初始化信息
        async init(isSearch){
            this.isSearch = isSearch
            await getCategoryPage({
            page: this.page,
            pageSize: this.pageSize,
            name: this.name ? this.name : undefined,
            type: this.categoryType ? this.categoryType : undefined
            })
            .then(res => {
                if (String(res.data.code) === '1') {
                    this.tableData = res.data.data.records;
                    this.counts = Number(res.data.data.total)
                } else {
                    this.$message.error(res.data.msg)
                }
            })
            .catch((err) => {
                this.$message.error('请求出错了：' + err.message)
            })
        },

        // 添加
        addClass(className){
           if (className === 'class') {
                this.classData.title = '新增菜品分类'
                this.type = '1'
            } else {
                this.classData.title = '新增套餐分类'
                this.type = '2'
            }
           
            this.action = 'add'
            this.classData.name = ''
            this.classData.sort = ''
            this.classData.dialogVisible = true
            this.actionType = 'add'
            // alert("添加分类");
            // alert(this.classData.dialogVisible?'弹窗已打开':'弹窗未打开');
        },
        // 修改
        editHandle(dat){
            this.classData.title = '修改分类'
            this.action = 'edit'
            this.classData.name = dat.name
            this.classData.sort = dat.sort
            this.classData.id = dat.id
            this.classData.dialogVisible = true
            this.actionType = 'edit'
        },
        // 关闭弹窗
        handleClose(st) {
            console.log(this.$refs.classData, 'this.$refs.classData')
            this.classData.dialogVisible = false
            //对该表单项进行重置，将其值重置为初始值并移除校验结果
            this.$refs.classData.resetFields()
        },

        //状态修改
        statusHandle(row) {
            this.id = row.id
            this.status = row.status
            this.$confirm('确认调整该分类的状态?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                customClass: 'customClass'
            }).then(() => {
            enableOrDisableEmployee({ id: this.id, status: !this.status ? 1 : 0 })
                .then(res => {
                if (String(res.status) === '200') {
                    this.$message.success('分类状态更改成功！')
                    this.init()
                }
                })
                .catch(err => {
                this.$message.error('请求出错了：' + err.message)
                })
            }).catch(() => {})
        },

        //删除
        deleteHandle(id){
            this.$confirm('此操作将永久删除该分类，是否继续？', '确定删除', {
            confirmButtonText: '删除',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
            deleCategory(id)
                .then(res => {
                if (res.data.code === 1) {
                    this.$message.success('删除成功！')
                    this.init()
                } else {
                    this.$message.error(res.data.msg)
                }
                })
                .catch(err => {
                this.$message.error('请求出错了：' + err.message)
                })
            }).catch(() => {
            })
        },
       
        //分页
        handleSizeChange(val){
            this.pageSize = val
            this.init()
        },

        //页面跳转
        handleCurrentChange(val){
            this.page = val
            this.init()
        },
       
        
        

        //数据提交
        submitForm(st){
             if (this.action === 'add') {
                // this.$refs.classData.validate((valid) => {
                //     if (valid) {

                    addCategory({
                        name: this.classData.name,
                        type: this.type,
                        sort: this.classData.sort
                    }).then(res => {
                        if (res.data.code === 1) {
                            this.$message.success('分类添加成功！')
                            this.$refs.classData.resetFields()
                            if (!st) {
                                this.classData.dialogVisible = false
                            }
                            this.init()
                        } else {
                            this.$message.error(res.data.desc || res.data.msg)
                        }
                    }).catch(err => {
                        this.$message.error('请求出错了：' + err.message)
                    })
                //     }
                // })
                } else {
                // this.$refs.classData.validate((value) => {
                //     if (value) {
                    editCategory({
                        id: this.classData.id,
                        name: this.classData.name,
                        sort: this.classData.sort
                    }).then(res => {
                        if (res.data.code === 1) {
                            this.$message.success('分类修改成功！')
                            this.classData.dialogVisible = false
                            this.$refs.classData.resetFields()
                            this.init()
                        } else {
                            this.$message.error(res.data.desc || res.data.msg)
                        }
                        })
                        .catch(err => {
                        this.$message.error('请求出错了：' + err.message)
                        })
                    // }
                // })
            }
        }

    }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    // margin: 30px;

    .container {
      background: #fff;
      position: relative;
      z-index: 1;
    //   padding: 30px 28px;
      border-radius: 4px;

      .tableBar {
        display: flex;
        margin-bottom: 20px;
        justify-content: space-between;
      }

      .tableBox {
        width: 100%;
        border: 1px solid #ccc;
        border-bottom: 0;
      }

      .pageList {
        text-align: center;
        margin-top: 30px;
      }
      //查询黑色按钮样式
      .normal-btn {
        background: #333333;
        color: white;
        margin-left: 20px;
      }
      .el-dialog{
        position: relative;
        z-index: 9999;
      }
    }
  }
}
</style>
