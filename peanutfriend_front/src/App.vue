<template>
    <Header/>
    <RouterView/>
</template>

<script>
import Header from "@/components/Header.vue";
import store from "@/scripts/store";
import axios from "axios";
import {useRoute} from "vue-router";
import {watch} from "vue";

export default {
    name: 'App',
    components: {
        Header
    },
    setup() {
        const check = () => {
            axios.get("/api/members/check").then(({data}) => {
                console.log(data);
                store.commit("setAccount", data || 0);
            })
        }

        const route = useRoute();

        watch(route, ()=>{
            check();
        })
    }
}
</script>

<style>
</style>
