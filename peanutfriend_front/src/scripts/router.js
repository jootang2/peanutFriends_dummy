import Home from "@/pages/Home.vue";
import Login from "@/pages/Login.vue";
import {createRouter, createWebHistory} from "vue-router";
import Join from "@/pages/Join.vue";
import AddBasket from "@/pages/AddBasket.vue";

const routes = [
    {path: '/', component: Home},
    {path: '/100', component: Login},
    {path: '/200', component: Join},
    {path: '/300', component: AddBasket}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;