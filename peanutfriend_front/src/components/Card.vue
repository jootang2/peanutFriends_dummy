<template>
  <!--Card-->
    <div style="display: flex">
        <div class="col-xl-3 col-md-6 mb-4">
            <router-link to="/500" class="card border-left-warning shadow h-100 py-2" style="cursor: pointer">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                {{ basket.masterMember.name }}
                            </div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">{{ basket.name }}</div>
                            <div class="h5 mb-0 text-gray-800" style="font-size: 20px">{{ basket.startDate }}</div>
                            <div class="h5 mb-0 text-gray-800">{{ basket.endDate }}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-comments fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </router-link>
        </div>
        <a class="btn btn-success btn-lg" style="height: 42px;margin-top: 107px;" @click="joinBasket(basket.basketId)">참가 신청</a>
    </div>
  <!--End of Card-->
</template>

<script>

import axios from "axios";
import jwt_decode from "jwt-decode";
import {reactive} from "vue";

export default {
    name: "Card",
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

        const basketDetail = (basketId) => {
            const config = {
                headers: {
                    "Authorization" : "Bearer " + getCookie("atk"),
                }
            }
            axios.get(`/api/basket/${basketId}`, config).then((res) => {
                console.log('바구니 상세정보 클릭함')
                console.log(res)

            })
        };
        return {joinBasket}
    }
}
</script>

<style scoped>

</style>