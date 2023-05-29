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
                  <div class="col-lg-7">
                    <div class="p-5">
                      <div class="text-center">
                        <h1 class="h4 text-gray-900 mb-4">Create an Peanut Basket!</h1>
                      </div>
                      <form class="user">
                        <div class="form-group row">
                        </div>
                        <div class="form-group">
                          <input type="text" class="form-control form-control-user" id="basketName"
                                 placeholder="바스켓 이름" v-model="state.form.name">
                        </div>
                        <div class="form-group row">
                          <div class="col-sm-6 mb-3 mb-sm-0">
                            <input type="text" class="form-control form-control-user"
                                   id="basketStartDate" placeholder="시작 날짜" v-model="state.form.startDate">
                          </div>
                          <div class="col-sm-6">
                            <input type="text" class="form-control form-control-user"
                                   id="basketEndDate" placeholder="끝 나는 날짜" v-model="state.form.endDate">
                          </div>
                        </div>
                        <a class="btn btn-primary btn-user btn-block" @click="submit()">
                          create Basket
                        </a>
                      </form>
                      <hr>
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
import LogoutModal from "@/components/LogoutModal.vue";
import TopBar from "@/components/TopBar.vue";
import Lnb from "@/components/LNB.vue";
import {reactive} from "vue";
import axios from "axios";
import router from "@/scripts/router";

export default {
  setup() {
    const state = reactive({
      form: {
        name: "",
        startDate: "",
        endDate: ""
      }
    })

    const submit = () => {
      axios.post("/api/basket/create", state.form).then((res) => {
        console.log(res)
        router.push({path: "/"})
      }).catch(() => {
        window.alert("바스켓 생성에 실패했습니다.")
      })
    }

    return {state, submit}
  },
  name: "AddBasket",
  components: {Lnb, TopBar, LogoutModal}
}
</script>

<style scoped>

</style>