<template>
    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <router-link to="/" class="sidebar-brand d-flex align-items-center justify-content-center">
        <div class="sidebar-brand-icon rotate-n-15">
        </div>
        <div class="sidebar-brand-text mx-3">Peanut Friends</div>
      </router-link>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item">
        <a class="nav-link" href="index.html">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>Dashboard</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Interface
      </div>

      <!-- Nav Item - Pages Collapse Menu -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
           aria-expanded="true" aria-controls="collapseTwo">
          <i class="fas fa-fw fa-cog"></i>
          <span>Components</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Custom Components:</h6>
            <a class="collapse-item" href="buttons.html">Buttons</a>
            <a class="collapse-item" href="cards.html">Cards</a>
          </div>
        </div>
      </li>

      <!-- Nav Item - Utilities Collapse Menu -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities"
           aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-wrench"></i>
          <span>Utilities</span>
        </a>
        <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities"
             data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Custom Utilities:</h6>
            <a class="collapse-item" href="utilities-color.html">Colors</a>
            <a class="collapse-item" href="utilities-border.html">Borders</a>
            <a class="collapse-item" href="utilities-animation.html">Animations</a>
            <a class="collapse-item" href="utilities-other.html">Other</a>
          </div>
        </div>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Addons
      </div>

      <!-- Nav Item - Pages Collapse Menu -->
      <li class="nav-item active">
        <a class="nav-link" href="#" data-toggle="collapse" data-target="#collapsePages" aria-expanded="true"
           aria-controls="collapsePages">
          <i class="fas fa-fw fa-folder"></i>
          <span>Pages</span>
        </a>
        <div id="collapsePages" class="collapse show" aria-labelledby="headingPages"
             data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Login Screens:</h6>
            <router-link to="/100" class="collapse-item" v-if="!$store.state.account.id">Login</router-link>
            <router-link to="/100" class="collapse-item" @click="logout()" v-else>Logout</router-link>
            <router-link to="/200" class="collapse-item">Join</router-link>
            <router-link to="/300" class="collapse-item" >Add Basket</router-link>
            <div class="collapse-divider"></div>
            <h6 class="collapse-header">Other Pages:</h6>
            <router-link to="/400" class="collapse-item">Basket</router-link>
            <a class="collapse-item active" href="blank.html">Blank Page</a>
          </div>
        </div>
      </li>

      <!-- Nav Item - Charts -->
      <li class="nav-item">
        <a class="nav-link" href="charts.html">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>Charts</span></a>
      </li>

      <!-- Nav Item - Tables -->
      <li class="nav-item">
        <a class="nav-link" href="tables.html">
          <i class="fas fa-fw fa-table"></i>
          <span>Tables</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>
    </ul>
</template>

<script>
import store from "@/scripts/store";
import router from "@/scripts/router";
import axios from "axios";
import jwt_decode from 'jwt-decode';
import {reactive} from "vue";

export default {
  name: 'Lnb',
  setup() {
    const state = reactive({
      form: {
        refreshToken: ""
      }
    })

    console.log(document.cookie)
    console.log("====")

    function getCookie(name) {
      let matches = document.cookie.match(new RegExp(
          // eslint-disable-next-line
          "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
      ));
      return matches ? decodeURIComponent(matches[1]) : undefined;
    }
    state.form.refreshToken = getCookie("rtk")
    console.log(getCookie("atk"))
    if(getCookie("atk")){
      const decoded = jwt_decode(getCookie("atk"))
      let now = new Date();
      let expiry = decoded.exp - Number(now.getTime().toString().substr(0, 10));
      if(expiry < 30){
        const config = {
          headers: {
            "Authorization" : "Bearer " + getCookie("atk"),
          }
        }
        axios.post("/api/members/refreshToken", state.form, config).then(()=>{
        })
      }
      console.log(now)
      console.log(expiry)
    }

    const logout = () => {
      const config = {
        headers: {
          "Authorization" : "Bearer " + getCookie("atk"),
        }
      }

      axios.post("/api/members/logout", state.form, config).then(()=>{
        store.commit('setAccount', 0);
        router.push({
          path: "/"
        })
      })

      sessionStorage.removeItem("id");

    }

    return {logout}

  }
}
</script>

<style scoped>
</style>
