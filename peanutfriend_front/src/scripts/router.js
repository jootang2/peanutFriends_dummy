import Home from "@/pages/Home.vue";
import Login from "@/pages/Login.vue";
import {createRouter, createWebHistory} from "vue-router";
import Join from "@/pages/Join.vue";
import AddBasket from "@/pages/AddBasket.vue";

const routes = [
    {path: '/', component: Home},
    {path: '/login', component: Login},
    {path: '/join', component: Join},
    {path: '/add/basket', component: AddBasket}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;