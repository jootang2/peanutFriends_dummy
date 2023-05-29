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
                <div class="row">
                  <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
                  <div class="col-lg-7">
                    <div class="p-5">
                      <div class="text-center">
                        <h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
                      </div>
                      <form class="user">
                        <div class="form-group">
                          <input type="text" class="form-control form-control-user" id="joinName"
                                 placeholder="Name" v-model="state.form.name">
                        </div>
                        <div class="form-group">
                          <input type="email" class="form-control form-control-user" id="joinEmail"
                                 placeholder="Email Address" v-model="state.form.email">
                        </div>
                        <div class="form-group row">
                          <div class="col-sm-6 mb-3 mb-sm-0">
                            <input type="password" class="form-control form-control-user"
                                   id="joinPassword" placeholder="Password" v-model="state.form.password">
                          </div>
                          <div class="col-sm-6">
                            <input type="password" class="form-control form-control-user"
                                   id="joinRepeatPassword" placeholder="Repeat Password">
                          </div>
                        </div>
                        <a class="btn btn-primary btn-user btn-block" @click="submit()">
                          Register Account
                        </a>
                        <hr>
                        <a href="index.html" class="btn btn-google btn-user btn-block">
                          <i class="fab fa-google fa-fw"></i> Register with Google
                        </a>
                        <a href="index.html" class="btn btn-facebook btn-user btn-block">
                          <i class="fab fa-facebook-f fa-fw"></i> Register with Facebook
                        </a>
                      </form>
                      <hr>
                      <div class="text-center">
                        <a class="small" href="forgot-password.html">Forgot Password?</a>
                      </div>
                      <div class="text-center">
                        <a class="small" href="login.html">Already have an account? Login!</a>
                      </div>
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


import router from "@/scripts/router";
import Lnb from "@/components/LNB.vue";
import TopBar from "@/components/TopBar.vue";
import LogoutModal from "@/components/LogoutModal.vue";
import {reactive} from "vue";
import axios from "axios";

export default {
  name: "Join",
  components: {LogoutModal, TopBar, Lnb},
  setup() {
    const state = reactive({
      form: {
        email: "",
        name: "",
        password: ""
      }
    })

    const submit = () => {
      axios.post("/api/members/signUp", state.form).then((res)=>{
        console.log(res.data)
        window.alert("회원가입이 완료되었습니다.")
        router.push({path: "/"})
      }).catch(() => {
        window.alert("회원가입에 실패했습니다.")
      })
    }

    return {state, submit}

  }
}
</script>

<style scoped>
</style>