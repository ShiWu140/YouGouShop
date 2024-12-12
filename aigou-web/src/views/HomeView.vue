<script>
export default {
  mounted() {
    if (localStorage.getItem('user') === null) {
      this.$router.push('/login');
    } else {
      this.$router.push('/admin');
    }
  }
}
</script>

<template>

</template>

<style>

</style>