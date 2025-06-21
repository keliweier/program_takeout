<template>
    <el-container class="dashboard">
        <el-header>
            <img src="#" alt="Logo" class="logo" />
            <nav class="status">{{ status }}</nav>
        </el-header>
        <el-container class="menu">
            <el-aside>
                    <nav><router-link to='/dashboard'>工作台</router-link></nav>
                    <nav><router-link to="/inform">数据统计</router-link></nav>
                    <nav><router-link to='/order'>订单管理</router-link></nav>
                    <nav><router-link to="/setmeal">套餐管理</router-link></nav>
                    <nav><router-link to='/dish'>菜品管理</router-link></nav>
                    <nav><router-link to='/category'>分类管理</router-link></nav>
                    <nav><router-link to="/employee">员工管理</router-link></nav>
            </el-aside>
            <el-main>
                <router-view/>
            </el-main>
        </el-container>
    </el-container>
    
</template>

<style>
    *{
        margin: 0;
        padding: 0;
    }
    a{
        text-decoration: none;
        color: white;
        display: block;
        width: 100%;
        height: 100%;
    }
    .dashboard {
        height: 97.5vh;
        /* display: flex;
        flex-direction: column; */
    }
    .el-header {
        height: 10% !important;
        background: orange;
        clear: both;
    }
    .menu {
        height: 90%;
        display: flex;
    }
    .el-aside {
        width: 10% !important;
        background: #222;
        color: white;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        align-items: center;
        
        
    }
    .el-aside nav{
        width: 80% ;
        
        text-align: center;
    }
    /* .el-aside nav:visited, 
    .el-aside nav:hover, 
    .el-aside nav:active{
        background: white;
        color: #222;
    } */
    .el-aside nav:visited a, 
    .el-aside nav:hover a, 
    .el-aside nav:active a{
        color:skyblue;
    }
    .custom-active{
        color: #222;
        background: white;
    }
    
    
    
    .el-main {
        width: 90% !important;
        
    }
        
    
</style>

<script>
export default {
    name: 'DashboardIndext',
    data() {
        return {
            status: '营业中'
        };
    },
    methods: {
        // 这里可以定义一些方法
    },
    mounted() {
        // 组件挂载后执行的代码
    }
};
</script>
