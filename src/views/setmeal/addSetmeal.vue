<template>
    <div class="addBrand-container">
        <div class="container">
            <el-form ref='ruleForm'
                    :rules='rules'
                    :model='ruleForm'
                    :inline="true"
                    label-width="180px"
                    class="demo-ruleForm">
                <div class="on">
                    <el-form-item label="套餐名称:"
                                    prop="name"
                                    required>
                        <el-input v-model="ruleForm.name"
                                placeholder="请填写套餐名称"
                                maxlength="14" />
                    </el-form-item>

                    <el-form-item label="套餐分类:"
                                    prop="idType"
                                    required>
                        <el-select v-model="ruleForm.idType"
                                placeholder="请选择套餐分类"
                                @change="$forceUpdate()">
                        <el-option v-for="(item, index) in setMealList"
                                    :key="index"
                                    :label="item.name"
                                    :value="item.id" />
                        </el-select>
                    </el-form-item> 
                
                    <el-form-item label="套餐价格:"
                                    prop="price"
                                    required>
                        <el-input v-model="ruleForm.price"
                                placeholder="请设置套餐价格" />
                    </el-form-item>
                </div>
                <div>
                <el-form-item label="套餐菜品:"
                                required>
                    <el-form-item style="border: 1px solid #ccc;
                                    width: 680px;">
                    <div class="addDish" style="width: 680px;">
                        <span v-if="dishTable.length == 0"
                            class="addBut"
                            @click="openAddDish('new')">
                        + 添加菜品</span>
                        <div v-if="dishTable.length != 0"
                            class="content">
                            <div class="addBut"
                                style="margin-bottom: 20px"
                                @click="openAddDish('change')">
                                + 添加菜品
                            </div>
                        <div class="table">
                            <el-table :data="dishTable"
                                    style="width: 100%">
                            <el-table-column prop="name"
                                            label="菜品名称"
                                            width="180"
                                            align="center" />
                            <el-table-column prop="price"
                                            label="原价"
                                            width="180"
                                            align="center">
                                <template slot-scope="scope">
                                {{ (Number(scope.row.price).toFixed(2) * 100) / 100 }}
                                </template>
                            </el-table-column>
                            <el-table-column 
                                            label="份数"
                                            align="center">
                                <template slot-scope="scope">
                                <el-input-number v-model="scope.row.copies"
                                                size="small"
                                               style="width: 100px;"
                                                :min="1"
                                                :max="99"
                                                label="描述文字" />
                                </template>
                            </el-table-column>
                            <el-table-column 
                                            label="操作"
                                            width="180px;"
                                            align="center">
                                <template slot-scope="scope">
                                <el-button type="text"
                                            size="small"
                                            class="delBut non"
                                            @click="delDishHandle(scope.$index)">
                                    删除
                                </el-button>
                                </template>
                            </el-table-column>
                            </el-table>
                        </div>
                        </div>
                    </div>
                    </el-form-item>
                    </el-form-item>
                    </div>
                    <div>
                    <el-form-item label="套餐图片:"
                                    required
                                    prop="image">
                        <ImageUpload :prop-image-url="imageUrl"
                                    @imageChange="imageChange">
                        图片大小不超过2M<br>仅能上传 PNG JPEG JPG类型图片<br>建议上传200*200或300*300尺寸的图片
                        </ImageUpload>
                    </el-form-item>
                    </div>
                    <!-- <el-form-item
                    label="商品码:"
                    prop="code"
                    >
                    <el-input
                        v-model="ruleForm.code"
                        placeholder="请输入商品码"
                    />
                    </el-form-item> -->
                    <div class="address">
                        <el-form-item label="套餐描述:">
                            <el-input v-model="ruleForm.description"
                                    type="textarea"
                                    :rows="3"
                                    style="width: 777px"
                                    maxlength="200"
                                    placeholder="套餐描述，最长200字" />
                        </el-form-item>
                    </div>
                    <div class="subBox address">
                    <el-form-item>
                        <el-button @click="() => $router.back()">
                        取消
                        </el-button>
                        <el-button type="primary"
                                :class="{ continue: actionType === 'add' }"
                                @click="submitForm('ruleForm', false)">
                        保存
                        </el-button>
                        <el-button v-if="actionType == 'add'"
                                type="primary"
                                @click="submitForm('ruleForm', true)">
                        保存并继续添加
                        </el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
            <el-dialog v-if="dialogVisible"
                        title="添加菜品"
                        class="addDishList"
                        :visible.sync="dialogVisible"
                        width="60%"
                        :before-close="handleClose">
                <el-input v-model="value"
                            class="seachDish"
                            placeholder="请输入菜品名称进行搜索"
                            style="width: 293px; height: 40px"
                            size="small"
                            clearable>
                    <i slot="prefix"
                        class="el-input__icon el-icon-search"
                        style="cursor: pointer"
                        @click="seachHandle"/>
                </el-input>
                <AddDish v-if="dialogVisible"
                        ref="adddish"
                        :check-list="checkList"
                        :seach-key="seachKey"
                        :dish-list="dishList"
                        @checkList="getCheckList" />
                <span slot="footer"
                        class="dialog-footer">
                    <el-button @click="handleClose">取 消</el-button>
                    <el-button type="primary"
                            @click="addTableList">添 加</el-button>
                </span>
        </el-dialog>
    </div>
</template>



<script>
import ImageUpload from '@/components/ImgUpload/index.vue';
import { querySetmealById, addSetmeal, editSetmeal } from '@/api/setMeal';
import { getCategoryList } from '@/api/dish';
import AddDish from './components/AddDish.vue';
import { number } from 'echarts';

export default{
    name: 'AddSatmeal',
    components:{
        ImageUpload,
        AddDish
    },
    data(){
        return{
            ruleForm:{
                name: '',
                categoryId: '',
                price: '',
                code: '',
                image: '',
                description: '',
                dishList: [],
                status: true,
                idType: ''
            },
            rules:{
                name:[
                    {required: true, trigger: 'blur', validator: (rule,value,callback)=>{
                        if (!value) {
                            callback(new Error('请输入套餐名称'))
                        } else {
                            const reg = /^([A-Za-z0-9\u4e00-\u9fa5]){2,20}$/
                            if (!reg.test(value)) {
                            callback(new Error('套餐名称输入不符，请输入2-20个非特殊字符'))
                            } else {
                            callback()
                            }
                        }
                    }
                    }
                ],
                idType:[
                   {required: true, type: 'number', trigger: 'change',message: '请选择套餐分类'}
                ],
                image:[
                    {required: true, trigger: 'blur', message: '菜品图片不能为空'}
                ],
                price:[
                    {required: true, trigger: 'blur', validator: (rule,value,callback)=>{
                        const reg = /^([1-9]\d{0,5}|0)(\.\d{1,2})?$/
                        if (!reg.test(value) || Number(value) <= 0) {
                            callback(
                            new Error(
                                '套餐价格格式有误，请输入大于零且最多保留两位小数的金额'
                            )
                            )
                        } else {
                            callback()
                        }
                    }}
                ],
                code:[
                    {required: true, message: '请输入商品码', trigger: 'blur'}
                ]  
            },
            actionType: '',
            seachKey: '',
            dishList: [],
            imageUrl: '',
            setMealList: [],
            dishTable: [],
            dialogVisible: false,
            checkList: []
        }
    },
    created() {
        this.getDishTypeList()
        this.actionType = this.$route.query.id ? 'edit' : 'add'
        if (this.actionType == 'edit') {
            this.init()
        }
    },
    methods :{
        //获取套餐分类
        getDishTypeList(){
            getCategoryList({ type: 2})
            .then(res => {
                if (res && res.data && res.data.code === 1) {
                    this.setMealList = res.data.data.map((obj) => ({
                    ...obj,
                    idType: obj.id
                    }))
                } else {
                    this.$message.error(res.data.msg)
                }
            })
        },

        async init(){
            querySetmealById(this.$route.query.id)
            .then(res => {
                if (res && res.data && res.data.code === 1) {
                    this.ruleForm = res.data.data
                    // this.ruleForm.status = res.data.data.status == '1';
                    // this.ruleForm.price = res.data.data.price
                    // this.imageUrl = `http://172.17.2.120:8080/common/download?name=${res.data.data.image}`
                    this.imageUrl = res.data.data.image
                    this.checkList = res.data.data.setmealDishes
                    this.dishTable = res.data.data.setmealDishes.reverse()
                    this.ruleForm.idType = res.data.data.categoryId
                } else {
                    this.$message.error(res.data.msg)
                }
            })
        },

         // 添加菜品
        openAddDish(val){
            this.seachKey = ''
            this.dialogVisible = true
        },

        // 删除套餐菜品
        delDishHandle(index){
            this.dishTable.splice(index, 1)
            this.checkList = this.dishTable
        },
        // 图片改变
        imageChange(value) {
            this.ruleForm.image = value;
        },
        
        seachHandle(){
             this.seachKey = this.value
        },
        

        // 获取添加菜品数据 - 确定加菜倒序展示
        getCheckList(value) {
            this.checkList = [...value].reverse();
        },
        // 取消添加菜品
        handleClose(done) {
            this.dialogVisible = false
            this.checkList = JSON.parse(
                JSON.stringify(this.dishTable))
        },

         // 保存添加菜品列表
        addTableList() {
            this.dishTable = JSON.parse(
                JSON.stringify(this.checkList));
            this.dishTable.forEach((n) => {
                n.copies = 1
            })
            this.dialogVisible = false
        },


        submitForm(formName,st){
           (this.$refs[formName]).validate((valid) => {
                if (valid) {
                    if (this.dishTable.length === 0) {
                        return this.$message.error('套餐下菜品不能为空');
                    }
                    if (!this.ruleForm.image) 
                        return this.$message.error('套餐图片不能为空');
                    let prams = { ...this.ruleForm };
                    prams.setmealDishes = this.dishTable.map(
                        (obj) => ({
                            copies: obj.copies,
                            dishId: obj.dishId,
                            name: obj.name,
                            price: obj.price
                    }));
                    prams.status =
                        (this.actionType === 'add' ? 0 : (this.ruleForm.status ? 1 : 0));
                    prams.categoryId = this.ruleForm.idType
                    // delete prams.dishList
                    if (this.actionType == 'add') {
                        delete prams.id;
                        addSetmeal(prams).then(res => {
                            if (res && res.data && res.data.code === 1) {
                                this.$message.success('套餐添加成功！')
                                if (!st) {
                                    this.$router.push({ path: '/setmeal' })
                                } else {
                                    (this).$refs.ruleForm.resetFields()
                                    this.checkList = [];
                                    this.dishList = [];
                                    this.dishTable = [];
                                    this.imageUrl = '';
                                    this.ruleForm = {
                                        name: '',
                                        categoryId: '',
                                        price: '',
                                        code: '',
                                        image: '',
                                        description: '',
                                        dishList: [],
                                        status: true,
                                        id: '',
                                        idType: ''
                                    } 
                                    
                                }
                            } else {
                                this.$message.error(res.data.msg)
                            }
                        }).catch(err => {
                            this.$message.error('请求出错了：' + err.message)
                        })
                    } else {
                        delete prams.updateTime;
                        editSetmeal(prams).then(res => {
                            if (res.data.code === 1) {
                                this.$message.success('套餐修改成功！')
                                this.$router.push({ path: '/setmeal' })
                            } else {
                                // this.$message.error(res.data.desc || res.data.message)
                            }
                        }).catch(err => {
                            this.$message.error('请求出错了：' + err.message)
                        })
                    }
                } else {
                    // console.log('error submit!!')
                    return false;
                }
            })
        }
    }
}
</script>

<style>
.container .on .el-input{
    width: 350px;
}

/* .addDishList{
    width: 800px;
} */

.addDish {
    width: 720px;
}

.addDishList{
    padding: 0px;
}


.addDish .addBut {
    background: #ffc200;
    display: inline-block;
    padding: 0px 20px;
    border-radius: 3px;
    line-height: 40px;
    cursor: pointer;
    border-radius: 4px;
    color: #333333;
    font-weight: 500;
}

.addDish .content {
    background: #fafafb;
    padding: 20px;
    border: solid 1px #d8dde3;
    border-radius: 3px;
}

.subBox {
    padding-top: 30px;
    text-align: center;
    border-top: solid 1px #ccc;
}

.seachDish{
    margin-top: 20px;
    margin-left: 30px;
}

.table{
    width: 650px;
}

.el-dialog__body {
    padding: 0px !important;
}
</style>




