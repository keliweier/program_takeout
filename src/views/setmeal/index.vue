<template>
    <div class="dashboard-container">
        <div class="container">
            <div class="tableBar">
                <label style="margin-right: 10px">套餐名称：</label>
                <el-input placeholder="请输入套餐名称"
                            v-model="name"
                            clearable
                            style="width: 14%"
                            @clear="initFun"
                            @keyup.enter.native="initFun"/>
                
                <label style="margin-right: 10px; margin-left: 20px">套餐分类：</label>
                <el-select placeholder="请选择"
                            v-model="categoryId"
                            style="width: 14%"
                            clearable
                            @clear='initFun'>
                    <el-option v-for="item in dishCategoryList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"/>
                </el-select>

                <label style="margin-right: 10px; margin-left: 20px">售卖状态：</label>
                <el-select placeholder="请选择"
                            v-model="dishStatus"
                            style="width: 14%"
                            clearable
                            @clear='initFun'>
                    <el-option v-for="item in saleStatus"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"/>
                </el-select>

                <el-button class="normal-btn continue"
                            @click="init(true)">
                    查询
                </el-button>

                <div class="tableLab">
                <el-button
                        type="primary"
                        @click="deleteHandle('批量')">批量删除</el-button>
                <!-- <span class="blueBug non" @click="statusHandle('1')">批量启售</span>
                <span
                    style="border: none"
                    class="delBut non"
                    @click="statusHandle('0')"
                    >批量停售</span
                > -->
                <el-button type="primary"
                            style="margin-left: 15px"
                            @click="addSetMeal('add')">
                    + 新建套餐
                </el-button>
            </div>
            </div>

            

            <el-table v-if="tableData.length"
                        :data='tableData'
                        class="tableBox"
                        scripe
                        @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="25" />
                
                <el-table-column prop='name' label='套餐名称'/>
                
                <el-table-column prop='image' label='套餐图片'>
                    <template slot-scope="{ row }">
                        <el-image style="width: 80px; height: 40px; border: none; cursor: pointer"
                                :src="row.image">
                            <div slot="error"
                                class="image-slot">
                                <img src="./../../assets/noImg.png"
                                    style="width: auto; height: 40px; border: none">
                            </div>
                        </el-image>
                    </template>
                </el-table-column>

                <el-table-column prop='categoryName' label='套餐分类'/>
                
                <el-table-column prop='price' label='套餐价'>
                    <template slot-scope="scope">
                        <span>￥{{ ((scope.row.price ).toFixed(2) * 100) / 100 }}</span>
                    </template>
                </el-table-column>

                <el-table-column label='售卖状态'>
                    <template v-slot="scope">
                        <div class="tableColumn-status"
                                :class="{ 'stop-use': String(scope.row.status) === '0' }">
                            {{scope.row.status===1? '启售':'停售'}}
                        </div>
                    </template>
                </el-table-column>

                <el-table-column prop='updateTime' label='最后操作时间'/>

                <el-table-column label='操作' 
                                width="250" 
                                align=center>
                    <template v-slot="scope">
                        <el-button type="text"
                                size="small"
                                class="blueBug"
                                @click="addSetMeal(scope.row)">
                            修改
                        </el-button>
                        <el-button type="text"
                                size="small"
                                class="delBut"
                                @click="deleteHandle('单删', scope.row.id)">
                            删除
                        </el-button>
                        <el-button type="text"
                                size="small"
                                class="blueBug non"
                                :class="{
                                    blueBug: scope.row.status == '0',
                                    delBut: scope.row.status != '0'
                                }"
                                @click="statusHandle(scope.row)">
                            {{ scope.row.status == '0' ? '启售' : '停售' }}
                        </el-button>
                    </template>
                </el-table-column>
            </el-table> 

            <Empty v-else
             :is-search="isSearch" />

            <el-pagination v-if="counts > 10"
                            class="pageList"
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :page-sizes='[10,20,30,40]'
                            :page-size="pageSize"
                            :current-page="page"
                            :total="counts"
                            layout="total,size,spre,pager,next,jumper"/>
            
        </div>
    </div>
</template>



<script >
import {
  getSetmealPage,
//   editSetmeal,
  deleteSetmeal,
  setmealStatusByStatus
} from '@/api/setMeal'
import {getCategoryByType} from '@/api/category';
import HeadLable from '@/components/HeadLable/index.vue';
import InputAutoComplete from '@/components/InputAutoComplete/index.vue'
import Empty from '@/components/Empty/index.vue'

export default {
    name: 'setmealIndex',
    components: {
        HeadLable,
        InputAutoComplete,
        Empty
    },
    data(){
        return{
            checkList: [],   //当前表格选中的元素
            isSearch: false,
            name: '',
            page: 1,
            pageSize: 10,
            counts: 0,
            records: [],
            categoryId:'',
            dishCategoryList: [],
            dishStatus:'',
            saleStatus : [
                {
                value: 0,
                label: '停售'
                },
                {
                value: 1,
                label: '启售'
                },
            ],
            tableData:[],

        }
    },
    created(){
        //页面加载完成后完成查询
        this.init(true);
        //查询套餐分类，用于填充查询页面下拉框
        this.getDishCategoryList();
    },
    methods:{
        //获取套餐分类下拉数据
        getDishCategoryList(){
            getCategoryByType({type : 2})
            .then((res)=>{
                if(res.data.code===1)
                    this.dishCategoryList=(res.data.data).map(
                        item => {
                            return { value: item.id, label: item.name }});
            }).catch((error)=>{
                this.$message.error("请求出错了"+error.message);
            })
        },

        //开始查询
        async init(isSearch){
            if(isSearch===true){
                this.page=1;
                this.pageSize=10;
            }
            await getSetmealPage({
                page: this.page,
                pageSize: this.pageSize,
                name: this.name,
                categoryId: this.categoryId,
                status: this.dishStatus
            }).then((res)=>{
                if(res.data.code===1){
                    this.tableData=res.data.data.records;
                    this.counts=res.data.data.total;
                }
                else
                    this.$message.error(res.data.msg);
            }).catch((error)=>{
                this.$message.error("请求出错了"+error.message);
            })
        },
        
        initFun(){
            return 0;
        },

        //删除
        deleteHandle(type,id){
            let ids='';//如果是批量删除，形式为例如1，2，3
            if(type==='批量'){//批量删除
                if(this.checkList.length===0)
                    return this.$message.error('请选择删除对象');
                ids=this.checkList.join(',');
            }else{//单一删除
                ids=id;
            }

            this.$confirm('确定要删除套餐吗','提示',{
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(()=>{
                deleteSetmeal(ids).then((res)=>{
                    if(res.data.code===1){
                        this.$message.success("删除成功");
                        this.init();
                    }
                    else
                        this.$message.error(res.data.msg);
                }).catch((error)=>{
                    this.$message.error("请求出错了"+error.message);
                })
            }).catch((err)=>{});
        },

        //获得勾选中的项的id
        handleSelectionChange(val){
            let arr=[];
            val.forEach((element)=>{
                arr.push(element.id);
            })
            this.checkList=arr;
        },

        //添加套餐或修改套餐
        addSetMeal(st){
            if(st==='add')
                this.$router.push('/setmeal/add')
            else 
                this.$router.push({ 
                    path: '/setmeal/add', 
                    query: { id: st.id } 
                })
        },

        //改变状态
        statusHandle(row){
            this.$confirm('确定要修改套餐状态吗？','提示',{
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(()=>{
                const p={status: row.status ? 0 : 1,
                    id : row.id
                }
                setmealStatusByStatus(p).then((res)=>{
                    if(res.data.code===1){
                        this.$message.success("售卖状态改变成功");
                        this.init();
                    }
                    else
                        this.$message.error(res.data.msg);
                }).catch((error)=>{
                    this.$message.error("请求出错了"+error.message);
                })
            }).catch((err)=>{});
            
        },

        //页面变化
        handleSizeChange(pageSize){
            this.pageSize=pageSize;
            this.init();
        },
        handleCurrentChange(page){
           this.page=page;
           this.init();
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
        margin-bottom: 20px;
        .tableLab {
          float: right;
          
        }
      }

    //   .tableBox {
    //     width: 100%;
    //     border: 1px solid $gray-5;
    //     border-bottom: 0;
    //   }

    //   .pageList {
    //     text-align: center;
    //     margin-top: 30px;
    //   }
      //查询黑色按钮样式
      .normal-btn {
        background: #333333;
        color: white;
        margin-left: 20px;
      }
    }
  }
}
</style>
