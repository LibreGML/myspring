<template>
  <iframe :src="pdfUrl" style="width:100%;height:100vh"></iframe>
</template>

<script>
export default {
  props: {
    pdfUrl: {
      type: String,
      required: true,
    },
  },
};
</script>
