<template>
    <div class="heard-body">
        <div class="heard-main">
            <el-image class="heard-logo" :src="logo" />
            <div class="heard-bor">
                <div :class="{ 'active': active === 'home' }" @click="changePage('home')">首页</div>
                <div class="bo" :class="{ 'active': active === 'source' }" @click="changePage('source')">货源商品</div>
                <div class="bo" :class="{ 'active': active === 'join' }" @click="changePage('join')">加入我们</div>
            </div>
            <div class="heard-tilte">
                <div class="tilte">聚比价数字权益系统</div>
                <div class="ind">全网唯一首家，数字权益给货源聚合比价系统</div>
            </div>
            <div class="heard-login" @click="openNewWindow">
                登录
            </div>
            <!-- <div class="heard-logined" @click="openNewWindow" style="background-color: none" v-else>
                <el-image :src="userInfo.headPic"></el-image>
            </div> -->
        </div>
    </div>
</template>
  
<script>
export default {
    name: "heard",
    components: {},
    data() {
        return {
            active: 'home',
            userInfo: {
                name: '未登录',
                headPic: "",
            }
        };
    },
    computed: {
        logo() {
            return localStorage.getItem('jbj_logo') || "";
        },
    },
    watch: {
        '$route.path': function (newPath, oldPath) {
            this.active = newPath.replace("/", "")
        }
    },
    mounted() {
        window.addEventListener("setItemEvent", (e) => {
            setTimeout(() => {
                this.getAccount()
            }, 1000);
        });
        window.addEventListener('storage', (event) => {
            this.getAccount()
        });
        this.getAccount()
    },
    methods: {
        getAccount() {
            const jbj_userInfo = localStorage.getItem("jbj_userInfo");
            if (jbj_userInfo) {
                const userInfo = JSON.parse(jbj_userInfo);
                if (userInfo.account.roleType === "1") {
                    this.userInfo = {
                        name: "总站长",
                        headPic: userInfo.account.headPic,
                    }
                } else if (userInfo.account.roleType === "2") {
                    this.userInfo = {
                        name: userInfo.business.name,
                        headPic: userInfo.account.headPic,
                    }
                } else {
                    this.userInfo = {
                        name: userInfo.supplier.name,
                        headPic: userInfo.account.headPic,
                    }
                }
            } else {
                this.userInfo = {
                    name: '未登录',
                    headPic: "",
                }
            }
        },
        changePage(e) {
            this.active = e
            this.$router.push(`/${e}`);
        },
        openNewWindow() {
            window.open('/web', '_blank');
        }
    },
};
</script>
  
<style lang="scss" scoped>
@media screen and (max-width: 800px) {
    .heard-body {
        user-select: none;  
        z-index: 2000;
        position: fixed;
        top: 0px;
        height: 300px;
        width: 100%;
        background-color: rgb(255, 255, 255);
        box-shadow: 0 0 5px 5px #d6dde4;
    }
    .heard-main {
        margin: 0 auto;
        width: 100%;
        display: flex;
        justify-content: space-between;

        .heard-logo {
            height: 300px;
            margin-left: 20px;
        }

        .heard-tilte {
            display: none;
            height: 300px;
            width: 500px;

            .tilte {
                color: rgb(77, 124, 227);
                font-size: 30px;
                line-height: 50px;
            }

            .ind {
                font-size: 50px;
            }
        }

        .heard-bor {
            margin-left: 100px;
            height: 300px;
            width: 900px;
            display: flex;
            align-items: center;
            font-size: 50px;
            line-height: 80px;
            line-height: 30px;
            color: rgba(0, 0, 0, 0.5);
            cursor: pointer;

            .bo {
                height: 30px;
                margin-left: 20px;
                padding-left: 20px;
                border-left: rgba(0, 0, 0, 0.2) 3px solid;
            }

            .active {
                color: #000;
            }
        }

        .heard-login {
            cursor: pointer;
            color: rgb(255, 255, 255);
            font-size: 50px;
            line-height: 300px;
            text-align: center;
            width: 300px;
            height: 300px;
            background-color: rgb(56, 141, 227);
        }

        .heard-logined {
            cursor: pointer;
            color: rgb(255, 255, 255);
            font-size: 25px;
            line-height: 80px;
            text-align: center;
            width: 80px;
            height: 200px;
            border: 40px;

            .el-image {
                width: 80px;
                height: 80px;
            }
        }

    }
}

@media screen and (min-width: 800px) {
    .heard-body {
        z-index: 2000;
        position: fixed;
        top: 0px;
        height: 80px;
        width: 100%;
        background-color: rgb(255, 255, 255);
        box-shadow: 0 0 5px 5px #d6dde4;
    }

    .heard-main {
        margin: 0 auto;
        width: 100%;
        display: flex;
        justify-content: space-between;

        .heard-logo {
            height: 80px;
            margin-left: 20px;
        }

        .heard-tilte {
            height: 80px;
            width: 500px;

            .tilte {
                color: rgb(77, 124, 227);
                font-size: 30px;
                line-height: 50px;
            }

            .ind {
                font-size: 20px;
            }
        }

        .heard-bor {
            height: 80px;
            width: 900px;
            display: flex;
            align-items: center;
            font-size: 22px;
            line-height: 80px;
            line-height: 30px;
            color: rgba(0, 0, 0, 0.5);
            cursor: pointer;

            .bo {
                height: 30px;
                margin-left: 20px;
                padding-left: 20px;
                border-left: rgba(0, 0, 0, 0.2) 3px solid;
            }

            .active {
                color: #000;
            }
        }

        .heard-login {
            cursor: pointer;
            color: rgb(255, 255, 255);
            font-size: 25px;
            line-height: 80px;
            text-align: center;
            width: 200px;
            height: 80px;
            background-color: rgb(56, 141, 227);
        }

        .heard-logined {
            cursor: pointer;
            color: rgb(255, 255, 255);
            font-size: 25px;
            line-height: 80px;
            text-align: center;
            width: 80px;
            height: 80px;
            border: 40px;

            .el-image {
                width: 80px;
                height: 80px;
            }
        }

    }
}
</style>
  