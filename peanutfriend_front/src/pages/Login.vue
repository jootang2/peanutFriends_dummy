<template>
  <body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">
    <Lnb/>
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <TopBar/>

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <div class="container">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="login-pf">
              <div class="p-5">
                <div class="text-center">
                  <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
                </div>
                <form class="user">
                  <div class="form-group">
                    <input type="email" class="form-control form-control-user"
                           id="exampleInputEmail" aria-describedby="emailHelp"
                           placeholder="Enter Email Address..." v-model="state.form.email">
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-user"
                           id="exampleInputPassword" placeholder="Password" v-model="state.form.password">
                  </div>
                  <div class="form-group">
                    <div class="custom-control custom-checkbox small">
                      <input type="checkbox" class="custom-control-input" id="customCheck">
                      <label class="custom-control-label" for="customCheck">Remember
                        Me</label>
                    </div>
                  </div>
                  <a class="btn btn-primary btn-user btn-block" @click="submit()">
                    Login
                  </a>
                </form>
                <hr>
                <div class="text-center">
                  <a class="small" href="forgot-password.html">Forgot Password?</a>
                </div>
                <div class="text-center">
                  <a class="small" href="register.html">Create an Account!</a>
                </div>
              </div>
            </div>
          </div>
        </div>

          </div>

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  </body>
  <LogoutModal/>
</template>

<script>
import {reactive} from "vue";
import axios from "axios";
import store from "@/scripts/store";
import router from "@/scripts/router";
import LogoutModal from "@/components/LogoutModal.vue";
import TopBar from "@/components/TopBar.vue";
import Lnb from "@/components/LNB.vue";

export default {

  setup() {
    const state = reactive({
      form: {
        email: "",
        password: ""
      }
    })

    const test = async  (req) => {
      console.log("=============")
      console.log(req.headers.cookie['atk']);
    }

    const submit = () => {
      axios.post("/api/members/login", state.form).then((res)=>{
        store.commit('setAccount', res.data);
        console.log(res.data)
        sessionStorage.setItem("id", res.data);
        router.push({path: "/"})
      }).catch(() => {
          window.alert("로그인 정보가 존재하지 않습니다.")
      })
    }

    return {state, submit, test}
  },
  name: "Login",
  components: {LogoutModal, TopBar, Lnb}
}
</script>

<style scoped>

</style>