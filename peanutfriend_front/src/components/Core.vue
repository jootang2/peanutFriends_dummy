<template>
  <!-- Bootstrap core JavaScript-->
  <component :is="script" src="vendor/jquery/jquery.min.js"></component>
  <component :is="script" src="vendor/bootstrap/js/bootstrap.bundle.min.js"></component>

  <!-- Core plugin JavaScript-->
  <component :is="script" src="vendor/jquery-easing/jquery.easing.min.js"></component>

  <!-- Custom scripts for all pages-->
  <component :is="script" src="js/sb-admin-2.min.js"></component>
</template>

<script>
export default {
  name: 'Core',
}
</script>

<style scoped>
</style>
