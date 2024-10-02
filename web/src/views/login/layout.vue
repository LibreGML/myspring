<template>
  <el-container class="container">
    <!-- <el-header class="header" height="80px">
      <img src="@/assets/login-logo.png" width="200px" alt="logo" /> -->
    <!-- <div class="nav">
        <el-popover placement="bottom" width="200" trigger="click">
          <div slot="reference" class="reference">
            <i class="iconfont el-icon-erweima"></i>xxx小程序
          </div>

          <el-image style="width: 200px; height: 200px" src=""></el-image>
        </el-popover>
        <el-popover placement="bottom" width="200" trigger="click">
          <div slot="reference" class="reference">
            <i class="iconfont el-icon-erweima"></i>xxx
          </div>
          <el-image
            style="width: 200px; height: 200px"
            :src="'@/assets/app-code.png'"
          ></el-image>
        </el-popover>
      </div> -->
    <!-- </el-header> -->
    <el-main class="main">
      <router-view></router-view>
    </el-main>
    <!-- <el-footer class="footer">
      <span>技术支持：xxxxxxxxxxxx</span>
      <el-divider direction="vertical" class="contacts" />
      <span class="contacts">客服电话：xxx-xxxx-xxx</span>
    </el-footer> -->
  </el-container>
</template>

<style scoped lang="scss">
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative; /**这样才能显示阴影 */
  box-shadow: 0 0px 12px 0 rgb(0 0 0 / 10%);
  padding: 0 40px;
  z-index: 99;
  .nav {
    display: flex;
    .reference {
      margin-left: 20px;
      font-size: 16px;
      color: #606266;
      cursor: pointer;
    }
    .reference:hover {
      color: #2b7bfb;
    }
    .iconfont {
      margin-right: 5px;
    }
  }
}
.main {
  height: 100vh;
  // height: calc(100vh - 70px);
  padding: 0;
  background-color: #faf9fc;
  background-image: url("~@/assets/login-bg.jpeg");
  background-repeat: no-repeat;
  background-position-y: center;
  background-size: 100%;
}

.footer {
  display: flex;
  justify-content: center;
  align-items: center;
  color: #c4c6cf;
  background-color: #fff;
  .contacts {
    display: none;
  }
}

@media screen and (max-width: 1440px) {
  .footer .contacts {
    display: inline-block;
  }
}

@media screen and (max-width: 992px) {
  .header {
    padding: 0 20px;
  }
}

@media screen and (max-width: 768px) {
  .header > img {
    width: 100%;
  }
  .header .nav {
    display: none;
  }
}
</style>
