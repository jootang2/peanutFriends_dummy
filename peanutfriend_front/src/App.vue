<template>
  <Header/>
  <Home/>
</template>

<script>
import Header from "@/components/Header.vue";
import Home from "@/pages/Home.vue";

export default {
  name: 'App',
  components: {
    Home,
    Header
  }
}
</script>

<style>
</style>
