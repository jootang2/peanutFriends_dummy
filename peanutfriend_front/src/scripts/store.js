import {createStore} from 'vuex'

const store = createStore({
    state: {
        account: {id: 0},
        token : null
    },
    getters: {
        isLogin(state){
            return state.token == null ? false : true ;
        }
    },
    mutations: {
        setAccount(state, payload) {
            state.account.id = payload;
        },
        setToken(state, _token){
            state.token = _token;
        }
    },
    actions: {
        setToken:({commit} , _token) => {
            commit('setToken', _token)
        }
    }
})

export default store;