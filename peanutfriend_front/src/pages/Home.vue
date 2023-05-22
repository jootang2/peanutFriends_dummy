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
          <h1 class="h3 mb-4 text-gray-800">Blank Page</h1>

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
import store from "@/scripts/store";
import Lnb from "@/components/LNB.vue";
import TopBar from "@/components/TopBar.vue";
import LogoutModal from "@/components/LogoutModal.vue";

export default {
  name: "Home",
  components: {LogoutModal, TopBar, Lnb},
  setup() {
    const logout = () => {
      store.commit('setAccount', 0);
      sessionStorage.removeItem("id");
      router.push({
        path: "/"
      })
    }

    return {logout}

  }
}
</script>

<style scoped>
</style>