import Vue from 'vue'
import Vuex from 'vuex'
import {IUser} from './modules/user'

Vue.use(Vuex)

// export default new Vuex.Store({
//   state: {
//   },
//   getters: {
//   },
//   mutations: {
    
//   },
//   actions: {
//   },
//   modules: {
//   }
// })


export interface IRootState {
  user: IUser
}

export default new Vuex.Store<IRootState>({})
