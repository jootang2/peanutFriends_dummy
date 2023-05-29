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
          <div style="display: flex;flex-wrap: wrap;">
            <Card v-for="i in 12" :key="i"/>
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


import Lnb from "@/components/LNB.vue";
import TopBar from "@/components/TopBar.vue";
import LogoutModal from "@/components/LogoutModal.vue";
import Card from "@/components/Card.vue";

export default {
  name: "Basket",
  components: {Card, LogoutModal, TopBar, Lnb},
  setup() {
  }
}
</script>

<style scoped>
</style>