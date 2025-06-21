import { VuexModule, Module, Mutation, getModule, Action } from 'vuex-module-decorators';
import { login ,userLogout } from '@/api/employee';
import { 
    getToken, 
    setToken, 
    removeToken,
    getStoreId, 
    setStoreId, 
    removeStoreId, 
    setUserInfo, 
    getUserInfo, 
    removeUserInfo } from '@/utils/cookies'
import Cookies from 'js-cookie';
import store from '@/store'; // 引入 Vuex store 实例
import { Message } from 'element-ui'; // 引入 Element UI 的 Message 组件



//设置接口
export interface IUser {
    //用户信息
    userInfo: any;
    //用户名
    username: string;
    //令牌
    token: string;

    name: string
    avatar: string
    storeId: string
    introduction: string
    roles: string[]
}



//设置模块（类继承自接口）
@Module({/*动态加载*/'dynamic': true, store ,'name': 'user'})
class User extends VuexModule implements IUser{
    //用户信息
    public userInfo = {};
    //用户名
    public username = '';
    //令牌
    public token = getToken() || ''

    public name = ''
    public avatar = ''
    
    public storeId: string = getStoreId() || ''
    public introduction = ''
    public roles: string[] = []

    
    @Mutation   
    private SET_USER_INFO(userInfo: any) {
        this.userInfo = userInfo;
    }

    @Mutation
    private SET_USERNAME(username: string) {    
        this.username = username;
    }

    @Mutation
    private SET_TOKEN(token: string) {
        this.token = token;
    }

    @Mutation
    private SET_NAME(name: string) {
        this.name = name
    }

    @Mutation
    private SET_AVATAR(avatar: string) {
        this.avatar = avatar
    }

    @Mutation
    private SET_INTRODUCTION(introduction: string) {
        this.introduction = introduction
    }

     @Mutation
    private SET_ROLES(roles: string[]) {
        this.roles = roles
    }

    @Mutation
    private SET_STOREID(storeId: string) {
        this.storeId = storeId
    }



    @Action
    public async Login(useInfo:{ username: string, password: string })  {
            let {username} = useInfo;
            const {password} = useInfo;
            username = username.trim();
            const {data}=await login({username, password})
            
                if(data.code===1){
                    //登录成功后，设置用户信息到vuex中
                    this.SET_USER_INFO(data.data);
                    this.SET_USERNAME(username);
                    this.SET_TOKEN(data.data.token);
                    //保存到cookie中 
                    Cookies.set('username', username);
                    Cookies.set('token', data.data.token);
                    Cookies.set('userInfo', data.data);
                    return data;
                }       
                //登陆失败
                else {
                    return Message.error(data.msg)
                }
        }

    @Action
    public ResetToken () {
        removeToken()
        this.SET_TOKEN('')
        this.SET_ROLES([])
    }

    @Action
    public async changeStore(data: any) {
        this.SET_STOREID = data.data
        this.SET_TOKEN(data.authorization)
        setStoreId(data.data)
        setToken(data.authorization)
    }

    @Action
    public async GetUserInfo () {
        if (this.token === '') {
        throw Error('GetUserInfo: token is undefined!')
        }

        const data = JSON.parse(<string>getUserInfo()) //  { roles: ['admin'], name: 'zhangsan', avatar: '/login', introduction: '' }
        if (!data) {
        throw Error('Verification failed, please Login again.')
        }

        const { roles, name, avatar, introduction, applicant, storeManagerName, storeId='' } = data // data.user
        // roles must be a non-empty array
        if (!roles || roles.length <= 0) {
        throw Error('GetUserInfo: roles must be a non-null array!')
        }

        this.SET_ROLES(roles)
        this.SET_USER_INFO(data)
        this.SET_NAME(name || applicant || storeManagerName)
        this.SET_AVATAR(avatar)
        this.SET_INTRODUCTION(introduction)
    }

    @Action
    public async LogOut () {
        const { data } = await userLogout({})
        removeToken()
        this.SET_TOKEN('')
        this.SET_ROLES([])
        Cookies.remove('username')
        Cookies.remove('user_info')
        removeUserInfo()
    }
}

export const UserModule = getModule(User); // 获取模块实例
export default UserModule; // 导出模块实例

