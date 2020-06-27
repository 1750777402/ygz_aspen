<template>
  <div class="dashboard-container">
    {{ userText }}
  </div>
</template>

<script>

export default {
  name: 'User',
  // components: {  },
  data() {
    return {
      userText: 'user center'
    }
  }
}
</script>
