<template>
  <!--Box-->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Custom Text Color Utilities</h6>
        </div>
        <div class="card-body">
            <p class="text-gray-100 p-3 bg-dark m-0">.text-gray-100</p>
            <p class="text-gray-200 p-3 bg-dark m-0">.text-gray-200</p>
            <p class="text-gray-300 p-3 bg-dark m-0">.text-gray-300</p>
            <p class="text-gray-400 p-3 bg-dark m-0">.text-gray-400</p>
            <p class="text-gray-500 p-3 m-0">.text-gray-500</p>
            <p class="text-gray-600 p-3 m-0">.text-gray-600</p>
            <p class="text-gray-700 p-3 m-0">.text-gray-700</p>
            <p class="text-gray-800 p-3 m-0">.text-gray-800</p>
            <p class="text-gray-900 p-3 m-0">.text-gray-900</p>
        </div>
    </div>
    <!--End of Box-->
</template>

<script>

import axios from "axios";
import jwt_decode from "jwt-decode";
import {reactive} from "vue";

export default {
    name: "Box",
    props: {
        basket: Object
    },
    setup() {
        const state = reactive({
            form: {

            }
        })
        function getCookie(name) {
            let matches = document.cookie.match(new RegExp(
                // eslint-disable-next-line
                "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
            ));
            return matches ? decodeURIComponent(matches[1]) : undefined;
        }
        if(getCookie("atk")) {
            const decoded = jwt_decode(getCookie("atk"))
            let now = new Date();
            let expiry = decoded.exp - Number(now.getTime().toString().substr(0, 10));
            if (expiry < 30) {
                const config = {
                    headers: {
                        "Authorization": "Bearer " + getCookie("atk"),
                    }
                }
                axios.post("/api/members/refreshToken", state.form, config).then(() => {
                })
            }
        }
        const joinBasket = (basketId) => {
            const config = {
                headers: {
                    "Authorization" : "Bearer " + getCookie("atk"),
                }
            }
            axios.post(`/api/basket/${basketId}/join`, state.form, config).then(() => {
                console.log('success')
                window.alert("참가신청에 성공했습니다.")
            })
        };
        return {joinBasket}
    }
}
</script>

<style scoped>

</style>