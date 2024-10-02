<template>
  <div :style="{ height: height + 'px', zIndex: zIndex }">
    <div
      :class="className"
      :style="{
        top: isSticky ? stickyTop + 'px' : '',
        zIndex: zIndex,
        position: position,
        width: width,
        height: height + 'px',
      }"
    >
      <slot>
        <div>sticky</div>
      </slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "Sticky",
  props: {
    stickyTop: {
      type: Number,
      default: 0,
    },
    zIndex: {
      type: Number,
      default: 1,
    },
    className: {
      type: String,
      default: "",
    },
    target: {
      type: String,
      default: ".main",
    },
  },
  data() {
    return {
      container: null,

      active: false,
      position: "",
      width: undefined,
      height: undefined,
      isSticky: false,
    };
  },
  mounted() {
    this.height = this.$el.getBoundingClientRect().height;
    this.container = window;

    if (this.target) {
      this.container = document.querySelector(this.target);
    }

    this.container.addEventListener("scroll", this.handleScroll);
    this.container.addEventListener("resize", this.handleResize);
  },
  activated() {
    this.handleScroll();
  },
  destroyed() {
    this.container.removeEventListener("scroll", this.handleScroll);
    this.container.removeEventListener("resize", this.handleResize);
  },
  methods: {
    sticky() {
      if (this.active) {
        return;
      }
      this.position = "fixed";
      this.active = true;
      this.width = `${this.width}px`;
      this.isSticky = true;
    },
    handleReset() {
      if (!this.active) {
        return;
      }
      this.reset();
    },
    reset() {
      this.position = "";
      this.width = "auto";
      this.active = false;
      this.isSticky = false;
    },
    handleScroll() {
      const { width } = this.$el.getBoundingClientRect();
      this.width = width || "auto";
      const offsetTop = this.$el.getBoundingClientRect().top;
      if (offsetTop < this.stickyTop) {
        this.sticky();
        return;
      }
      this.handleReset();
    },
    handleResize() {
      if (this.isSticky) {
        this.width = `${this.$el.getBoundingClientRect().width}px`;
      }
    },
  },
};
</script>
