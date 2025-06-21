<template>
    <div class="add-employee">
        <div class="header">
            <h3 v-if="this.optType==='add'">添加员工</h3>
            <h3 v-else>修改员工信息</h3>
        </div>
        <div class="main">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="账号" prop="username">
                    <el-input v-model="ruleForm.username" placeholder="请输入账号"></el-input>
                </el-form-item>
                <el-form-item label="员工姓名" prop="name">
                    <el-input v-model="ruleForm.name" placeholder="请输入员工姓名"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="ruleForm.phone" placeholder="请输入手机号"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-radio-group v-model="ruleForm.sex">
                        <!-- v-model表示对应的变量，label表示变量可取的值 -->
                        <el-radio label=2>女</el-radio>
                        <el-radio label=1>男</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="身份证号" prop="idNumber">
                    <el-input v-model="ruleForm.idNumber" placeholder="请输入身份证号"></el-input>
                </el-form-item>
                <div class="form-actions">
                    <el-button v-if="this.optType === 'add'" 
                        type="primary" @click="submitAddForm('ruleForm',false)">保存</el-button>
                    <el-button v-if="this.optType === 'update'" 
                        type="primary" @click="submitUpdateForm()">保存</el-button>
                    <!-- 判断是添加页面还是修改页面 -->
                    <el-button 
                        v-if="this.optType === 'add'"
                        type="primary"
                        @click="submitAddForm('ruleForm',true)">保存并继续添加员工
                    </el-button>
                    <el-button @click="()=>this.$router.push('/employee')">返回</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>


<style>
.header {
    padding: 20px;
    margin-bottom: 20px;
    background-color: #f5f5f5;
    border-bottom: 1px solid #e0e0e0;
}
.el-form-item {
   width: 50%;
   margin: 30px auto;
}
.form-actions {
    text-align: center;
    margin-top: 20px;
}
</style>


<script>
import { addEmployee , queryById , updateEmployee} from '@/api/employee'; 


export default {
    name: 'AddEmployee',
    data() {
        return {
            optType: 'add', // 当前操作类型，add添加页面,update修改页面
            ruleForm: {
                name: '',
                username: '',
                phone: '',
                sex: '2' , // 默认性别为女
                idNumber: ''
            },
            rules: {
                name: [
                    { required: true, message: '请输入员工姓名', trigger: 'blur' }
                ],
                username: [
                    { required: true, message: '请输入账号', trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
                ],
                idNumber: [
                    { required: true, trigger: 'blur' ,validator: (rule, value, callback) => {
                        if (!value) {
                            callback(new Error('请输入身份证号'));
                        } else if (!/(^[1-9]\d{5}\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}$)|(^[1-9]\d{5}(18|19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[0-9Xx]$)/.test(value)) {
                            callback(new Error('请输入正确的身份证号'));
                        } else {
                            callback();
                        }
                    }},
                    
                ],
                sex: [
                    { required: true, message: '请选择性别', trigger: 'change' }
                ]
            }
        };
    },
    created(){
        //获得路由参数，如果有则为修改操作，否则为新增操作
        this.optType=this.$route.query.id ? 'update' : 'add';
        if(this.optType==='update')
            //修改操作，根据id查询员工
            queryById(this.$route.query.id).then((res)=>{
                if(res.data.code===1)
                    this.ruleForm=res.data.data;
            })
    },
    methods: {
        submitAddForm(formName,isContinue){
            //表单校验
            this.$refs[formName].validate((valid)=>{
                //校验通过
                if(valid){
                    addEmployee(this.ruleForm).then((res)=>{
                        //服务器返回成功
                        if(res.data.code === 1){
                            this.$message.success('添加员工成功');
                            //如果是继续添加，则清空表单
                            if(isContinue){
                                this.ruleForm={
                                    name: '',
                                    username: '',
                                    phone: '',
                                    sex: '2' , // 默认性别为女
                                    idNumber: ''
                                }
                            }
                            else{
                                //返回列表
                                this.$router.push('/employee');
                            }
                        }
                        else{
                            this.$message.error(res.data.msg);
                        }
                    });
                }
                else{
                    this.$message.error('请填写完整信息');
                }
        })
        },
        submitUpdateForm(){
            this.$refs.ruleForm.validate((valid)=>{
                if(valid){
                    updateEmployee(this.ruleForm).then((res)=>{
                        if(res.data.code===1){
                            this.$message.success("员工信息修改成功");
                            this.$router.push('/employee');
                        }
                        else{
                            this.$message.error(res.data.msg);
                        }        
                    })
                }
                else{
                    this.$message.error('请填写完整信息');
                }
            })
        }
    },
    mounted() {
        // Code to run when the component is mounted
    }
};


</script>
