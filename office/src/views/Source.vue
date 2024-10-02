<template>
    <div class="source_mian">
        <div class="source">
            <div class="heard-1">
                <div class="heard-1-l">
                    <el-carousel>
                        <el-carousel-item v-for="(item, index) in allist" :key="index" style="height: 100%;">
                            <el-image :src="item.url" style="height: 100%;width: 100%;border-radius: 1%;"></el-image>
                        </el-carousel-item>
                    </el-carousel>
                    <div class="heard-1-l-d">
                        <el-image style="width: 49%;border-radius: 3%;" :src="hardl"></el-image>
                        <el-image style="width: 49%;border-radius: 3%;" :src="hardr"></el-image>
                    </div>
                </div>
                <div class="heard-1-r">
                    <div class="user-info">
                        <div class="hserd-img">
                            <img :src="userInfo.headPic || getmoren()" class="hserd" alt="logo" />
                        </div>
                        <div class="heard-name">{{ userInfo.name }} </div>
                        <div class="heard-button">
                            <el-button size="small" @click="gogrzx(`/web`)"
                                round>后台管理</el-button>
                            &nbsp;&nbsp;&nbsp;
                            <el-button size="small" type="primary"
                                @click="gogrzx(`/web/#/my-user`)" round>个人中心</el-button>
                        </div>
                    </div>
                    <div class="user-quanyi">
                        <div class="heard-til">尊享权益</div>
                        <div class="til-boy">
                            <el-image class="til-img" :src="equity"></el-image>
                        </div>
                    </div>
                </div>
            </div>
            <div class="sjop-li sor" v-if="class1.category.length">
                <div class="sor-top">
                    <div class="sjop-li-tit"> 一级分类</div>
                    <div class="sor-search">
                        <el-input placeholder="输入关键字" v-model="search1" clearable class="input-with-select">
                            <el-button @click="getCategory(1)" slot="append" icon="el-icon-search">
                                搜索
                            </el-button>
                        </el-input>
                    </div>
                </div>
                <div class="sjop-li-con">
                    <div class="sjop-li-card" :class="{ 'selectItem': selectFl1 === item.id }"
                        @click="changeSelectlevel1(item.id)" v-for="(item, index) in class1.category" :key="index">
                        <div class="sjop-li-image">
                            <el-image :src="item.image"></el-image>
                        </div>
                        <div class="sjop-li-text">{{ item.name }}</div>
                    </div>
                </div>
            </div>

            <div class="sjop-li sor" v-if="selectlevel > 1 && class2.category.length">
                <div class="sor-top">
                    <div class="sjop-li-tit"> 二级分类</div>
                    <div class="sor-search">
                        <el-input placeholder="输入关键字" v-model="search2" clearable class="input-with-select">
                            <el-button @click="getCategory(2)" slot="append" icon="el-icon-search">
                                搜索
                            </el-button>
                        </el-input>
                    </div>
                </div>
                <div class="sjop-li-con">
                    <div class="sjop-li-card" :class="{ 'selectItem': selectFl2 === item.id }"
                        @click="changeSelectlevel2(item.id)" v-for="(item, index) in class2.category" :key="index">
                        <div class="sjop-li-image">
                            <el-image :src="item.image"></el-image>
                        </div>
                        <div class="sjop-li-text">{{ item.name }}</div>
                    </div>
                </div>
            </div>
            <div class="sor">
                <div class="sor-fl">
                    <div class="sor-top" v-if="selectlevel > 2 && flList.length">
                        <div class="sor-fl-tetel">三级分类</div>
                        <div class="sor-search">
                            <el-input placeholder="输入关键字" v-model="search3" clearable class="input-with-select">
                                <el-button @click="getCategory(3)" slot="append" icon="el-icon-search">
                                    搜索
                                </el-button>
                            </el-input>
                        </div>
                    </div>
                    <div class="sjop-li-con" v-if="selectlevel > 2 && flList.length">
                        <div class="sjop-li-card" :class="{ 'selectItem': selectFl3 === item.id }"
                            @click="changeSelectlevel3(item.id)" v-for="(item, index) in flList" :key="index">
                            <div class="sjop-li-image">
                                <el-image :src="item.image"></el-image>
                            </div>
                            <div class="sjop-li-text">{{ item.name }}</div>
                        </div>
                    </div>

                    <div class="sor-top">
                        <div class="sor-search other-serch">
                            <el-input placeholder="输入商品关键字" v-model="search" clearable class="input-with-select">
                                <el-button @click="getProduct(1)" slot="append" icon="el-icon-search">
                                    搜索
                                </el-button>
                            </el-input>
                        </div>
                    </div>
                    <div class="sor-sp" id="pageId" v-loading="loading">
                        <div class="sp-card" v-for="(itme, index) in spList" :key="index"
                            @click="$router.push(`/goods?id=${itme.id}`);">
                            <div class="sp-xl"><span> 销量{{ itme.xl }}</span></div>
                            <el-image fit="fill" :src="itme.image"></el-image>
                            <div class="sp-name">{{ itme.name }}</div>
                            <div class="sp-name2" style="display: flex;justify-content: space-between;">
                                <el-tag type="warning">库存充足</el-tag>
                                <el-tag type="success">库存充足</el-tag>
                                <el-tag type="info">库存充足</el-tag>
                            </div>
                            <div class="sp-name2" style="display: flex;justify-content: space-between;">
                                <div class="sp-pic">￥{{ getPrice(itme.price) }}</div>
                                <el-tag class="danger-tig" type="danger">查看详情</el-tag>
                            </div>
                        </div>
                    </div>
                    <el-pagination @current-change="handleCurrentChange" :current-page="currentPage" :page-size="15"
                        layout="total, prev, pager, next, jumper" :total="total">
                    </el-pagination>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
export default {
    name: "Source",
    data() {
        return {
            loading: false,
            search: "",
            search1: "",
            search2: "",
            search3: "",
            selectFl1: "",
            selectFl2: "",
            selectFl3: "",
            selectlevel: 1,
            flList: [],
            currentPage: 1,
            total: 0,
            class1: {
                name: "",
                category: []
            },
            class2: {
                name: "",
                category: []
            },
            allist: [],
            hardl: "",
            hardr: "",
            equity: "",
            spList: [],
            userInfo: {
                name: '未登录',
                headPic: "",
            }
        };
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
        this.getHeadData()
        this.getCategory(1)
        this.getProduct(1)
    },
    methods: {
        getmoren() {
            return require('../assets/login-bg.jpeg')
        },
        changeSelectlevel1(item) {
            if (this.selectFl1 === item) {
                this.selectFl1 = ""
                this.selectFl2 = ""
                this.selectFl3 = ""
                this.selectlevel = 1
            } else {
                this.selectFl1 = item;
                this.selectFl2 = ""
                this.selectFl3 = ""
                this.selectlevel = 2
                this.getCategory(2)
            }
            this.getProduct(1)
        },
        changeSelectlevel2(item) {
            if (this.selectFl2 === item) {
                this.selectFl1 = ""
                this.selectFl2 = ""
                this.selectFl3 = ""
                this.selectlevel = 1
            } else {
                this.selectFl2 = item;
                this.selectFl3 = ""
                this.selectlevel = 3
                this.getCategory(3)
            }
            this.getProduct(1)
        },
        changeSelectlevel3(item) {
            if (this.selectFl3 === item) {
                this.selectFl1 = ""
                this.selectFl2 = ""
                this.selectFl3 = ""
                this.selectlevel = 1
            } else {
                this.selectFl3 = item;
                this.selectlevel = 3
            }
            this.getProduct(1)
        },
        getAccount() {
            const jbj_userInfo = localStorage.getItem("jbj_userInfo");
            if (jbj_userInfo) {
                const userInfo = JSON.parse(jbj_userInfo);
                if (userInfo.account.roleType === "1") {
                    this.userInfo = {
                        name: "总站长",
                        headPic: userInfo.account.headPic || localStorage.getItem('jbj_logo'),
                    }
                } else if (userInfo.account.roleType === "2") {
                    this.userInfo = {
                        name: userInfo.business.name,
                        headPic: userInfo.account.headPic || localStorage.getItem('jbj_logo'),
                    }
                } else {
                    this.userInfo = {
                        name: userInfo.supplier.name,
                        headPic: userInfo.account.headPic || localStorage.getItem('jbj_logo'),
                    }
                }
            } else {
                this.userInfo = {
                    name: '未登录',
                    headPic: "",
                }
            }
        },
        getHeadData() {
            this.$axios.get("/api/official/source/head").then(res => {
                if (res.data.code === 200) {
                    this.allist = res.data.data.carousel ? JSON.parse(res.data.data.carousel) : [];
                    let data = res.data.data.twe ? JSON.parse(res.data.data.twe) : {};
                    this.hardl = data.twel || ""
                    this.hardr = data.twer || ""
                    this.equity = data.equity || ""
                }
            })
        },
        getCategory(val) {
            let search = ""
            let selectFl = ""
            if (val === 1) {
                search = this.search1
            }
            if (val === 2) {
                search = this.search2
                selectFl = this.selectFl1
            }
            if (val === 3) {
                search = this.search3
                selectFl = this.selectFl2
            }
            this.$axios.get(`/api/official/source/get-category-pi?key=${selectFl}&name=${search}`).then(res => {
                if (res.data.code === 200) {
                    if (val === 1) {
                        this.class1.category = res.data.data
                    }
                    if (val === 2) {
                        this.class2.category = res.data.data
                    }
                    if (val === 3) {
                        this.flList = res.data.data
                    }
                }
            })
        },
        getPrice(val) {
            if (val) {
                return parseFloat(val).toFixed(2);
            }
            return ""
        },
        getProduct(page) {
            let selectFl = ""
            if (this.selectFl1) {
                selectFl = this.selectFl1
            }
            if (this.selectFl2) {
                selectFl = this.selectFl2
            }
            if (this.selectFl3) {
                selectFl = this.selectFl3
            }
            this.loading = true
            this.spList = []
            this.$axios.post("/api/official/source/get-product", {
                "category": selectFl,
                "name": this.search,
                "pageNum": page || this.currentPage,
                "pageSize": 16
            }).then(res => {
                if (res.data.code === 200) {
                    this.spList = res.data.data.records
                    this.currentPage = res.data.data.current
                    this.total = res.data.data.total
                }
                this.loading = false
            })
        },
        handleCurrentChange(val) {
            this.getProduct(val)
        },
        gogrzx(val) {
            window.open(val, '_blank')
        }
    },
};
</script>
  
<style lang="scss" scoped>
@media screen and (max-width: 800px) {
    .source_mian {
        padding-top: 300px;
        width: 100%;
        background: rgb(255, 255, 255);
    }

    .source {
        width: 90%;
        margin: 0 auto;
    }

    .heard-1 {
        padding-top: 100px;
        width: 100%;

        .heard-1-l {
            width: 100%;
            height: 100%;

            .el-carousel {
                z-index: 999;
                height: 750px
            }

            .heard-1-l-d {
                height: 350px;
                display: flex;
                justify-content: space-between;
            }
        }

        .heard-1-r {
            margin: 0 auto;
            width: 100%;
            height: 802px;
            padding-right: 30px;

            .user-info {
                float: right;
                background-color: #FFF;
                width: 50%;
                height: 44%;
                border-radius: 10px;
                padding-top: 200px;
                font-size: 50px;

                .hserd-img {
                    width: 200px;
                    height: 100px;
                    margin: 0 auto;

                    .hserd {
                        width: 200px;
                        height: 200px;
                        border-radius: 200px;
                        text-align: center;
                    }
                }

                .heard-name {
                    margin-top: 150px;
                    text-align: center;
                    font-size: 55px;
                }

                .heard-button {
                    margin: 0 auto;
                    margin-top: 20px;
                    text-align: center;
                }

            }

            .user-quanyi {
                float: left;
                margin-top: 1%;
                background-color: #FFF;
                width: 50%;
                height: 100%;
                border-radius: 10px;

                .heard-til {
                    position: relative;
                    top: 30px;
                    font-size: 55px;
                    text-align: center;
                }

                .til-boy {
                    position: relative;
                    top: 50px;
                    padding: 0px 40px;
                    width: calc(100% - 80px);
                    height: calc(80% - 40px);

                    .til-img {
                        height: 100%;
                        width: 100%;
                    }

                }
            }
        }
    }

    .sjop-li {
        width: calc(100% - 260px);
        margin: 25px auto;
        background-color: #FFF;
        padding-left: 100px;
        padding-right: 100px;
        padding-top: 20px;
        padding-bottom: 20px;

        .sjop-li-tit {
            margin-top: 6px;
            padding-left: 5PX;
            border-left: rgb(213, 150, 150) 8px solid;
            margin-bottom: 30PX;
            font-size: 51px;
        }
    }

    .sjop-li-con {
        display: flex;
        flex-wrap: wrap;
        row-gap: 20px;
    }

    .selectItem {
        background-color: rgba(35, 50, 162, 0.5);
        box-shadow: 0 0 10px rgba(35, 50, 162, 0.5) !important;
    }

    .sjop-li-card {
        height: 300px;
        width: 300px;
        padding: 5px;
        margin: 10px;
        cursor: pointer;

        .sjop-li-image {
            margin: 0 auto;
            height: 205px;
            width: 205px;

            .el-image {
                height: 205px;
                width: 205px;
                border-radius: 60px;
            }
        }

        .sjop-li-text {
            font-size: 51px;
            line-height: 60px;
            text-align: center;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

    }

    .sjop-li-card:hover {
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    }


    .sor {
        width: calc(100% - 260px);
        margin: 25px auto;
        background-color: #FFF;
        padding-left: 100px;
        padding-right: 100px;
        padding-top: 20px;
        padding-bottom: 20px;

        .sor-top {
            display: flex;
        }

        .sor-search {
            margin-left: 50px;
        }

        .other-serch {
            margin-left: 0 !important;
            margin-top: 50px;
        }

        .sor-fl {
            margin-top: 50px;
            margin-right: 200px;
            padding-top: 20px;
            margin: 0 auto;

            .sor-fl-tetel {
                margin-top: 5px;
                padding-left: 5PX;
                border-left: rgb(213, 150, 150) 8px solid;
                margin-bottom: 30PX;
                font-size: 21px;
            }

            .el-tag {
                cursor: pointer;
                margin-left: 10px;
                margin-top: 10px;
            }

            .sor-sp {
                margin-top: 50px;
                width: 100%;
                margin-bottom: 100px;
                display: flex;
                flex-wrap: wrap;
                row-gap: 20px;
                column-gap: 20px;
            }

            .el-pagination {
                width: 100%;
                text-align: right;
                padding-bottom: 100px;
            }
        }

    }

    .sp-card {
        cursor: pointer;
        width: 43%;
        padding: 0 50px 50px 50px;
        background-color: rgba(233, 234, 234, 0.5);

        .el-image {
            margin-top: 30px;
            width: 100%;
            height: 500px;
        }

        .sp-xl {
            position: relative;
            top: 105px;
            z-index: 99;
            font-size: 50px;


            span {
                padding: 5px 10px;
                background-color: rgba(118, 195, 202, 0.3);
            }
        }

        .sp-name {
            font-size: 55px;
            width: 100%;
        }

        .sp-name2 {
            margin-top: 30px;
        }

        .sp-pic {
            font-size: 59px;
            color: red;
            line-height: 100px;
        }

        .el-tag {
            margin-left: 0 !important;
        }

        .sp-tip {
            margin-top: 10px;
        }
    }
}

@media screen and (min-width: 800px) {
    .source_mian {
        width: 100%;
        background: rgb(255, 255, 255);
    }

    .source {
        width: 75%;
        margin: 0 auto;
    }

    .heard-1 {
        padding-top: 100px;
        width: 100%;
        display: flex;
        justify-content: space-around;

        .heard-1-l {
            width: 70%;
            height: 100%;
            padding-left: 30px;
            padding-right: 30px;

            .el-carousel {
                z-index: 999;
                height: 450px
            }

            .heard-1-l-d {
                height: 200px;
                display: flex;
                justify-content: space-between;
            }
        }

        .heard-1-r {
            width: 30%;
            height: 602px;
            padding-right: 30px;

            .user-info {
                box-shadow: 0 2px 5px rgb(0 0 0 / 20%);
                background-color: #FFF;
                width: 100%;
                height: 44%;
                border-radius: 10px;
                padding-top: 50px;

                .hserd-img {
                    width: 100px;
                    height: 100px;
                    margin: 0 auto;


                    .hserd {
                        width: 100px;
                        height: 100px;
                        border-radius: 100px;
                        text-align: center;
                    }
                }

                .heard-name {
                    margin-top: 20px;
                    text-align: center;
                    font-size: 25px;
                }

                .heard-button {
                    margin: 0 auto;
                    margin-top: 20px;
                    text-align: center;
                }

            }

            .user-quanyi {
                box-shadow: 0 2px 5px rgb(0 0 0 / 20%);
                margin-top: 1%;
                background-color: #FFF;
                width: 100%;
                height: 55%;
                border-radius: 10px;

                .heard-til {
                    position: relative;
                    top: 30px;
                    font-size: 25px;
                    text-align: center;
                }

                .til-boy {
                    position: relative;
                    top: 50px;
                    padding: 0px 40px;
                    width: calc(100% - 80px);
                    height: calc(80% - 40px);
                    display: flex;
                    flex-wrap: wrap;
                    gap: 50px;


                    .til-img {
                        height: 100%;
                        width: 100%;
                    }

                }
            }
        }
    }

    .sjop-li {
        width: calc(100% - 260px);
        margin: 25px auto;
        background-color: #FFF;
        padding-left: 100px;
        padding-right: 100px;
        padding-top: 20px;
        padding-bottom: 20px;

        .sjop-li-tit {
            margin-top: 6px;
            padding-left: 5PX;
            border-left: rgb(213, 150, 150) 8px solid;
            margin-bottom: 30PX;
            font-size: 21px;
        }
    }

    .sjop-li-con {
        display: flex;
        flex-wrap: wrap;
        row-gap: 20px;
    }

    .selectItem {
        background-color: rgba(35, 50, 162, 0.5);
        box-shadow: 0 0 10px rgba(35, 50, 162, 0.5) !important;
    }

    .sjop-li-card {
        height: 85px;
        width: 85px;
        padding: 5px;
        margin: 10px;
        // box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        cursor: pointer;

        .sjop-li-image {
            margin: 0 auto;
            height: 60px;
            width: 60px;

            .el-image {
                height: 60px;
                width: 60px;
                border-radius: 60px;
            }
        }

        .sjop-li-text {
            font-size: 16px;
            line-height: 22px;
            text-align: center;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

    }

    .sjop-li-card:hover {
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    }


    .sor {
        width: calc(100% - 260px);
        margin: 25px auto;
        background-color: #FFF;
        padding-left: 100px;
        padding-right: 100px;
        padding-top: 20px;
        padding-bottom: 20px;

        .sor-top {
            display: flex;
        }

        .sor-search {
            margin-left: 50px;
        }

        .other-serch {
            margin-left: 0 !important;
            margin-top: 50px;
        }

        .sor-fl {
            margin-top: 50px;
            margin-right: 200px;
            padding-top: 20px;
            margin: 0 auto;

            .sor-fl-tetel {
                margin-top: 5px;
                padding-left: 5PX;
                border-left: rgb(213, 150, 150) 8px solid;
                margin-bottom: 30PX;
                font-size: 21px;
            }

            .el-tag {
                cursor: pointer;
                margin-left: 10px;
                margin-top: 10px;
            }

            .sor-sp {
                margin-top: 50px;
                width: 100%;
                margin-bottom: 100px;
                display: flex;
                flex-wrap: wrap;
                row-gap: 20px;
                column-gap: 20px;
            }

            .el-pagination {
                width: 100%;
                text-align: right;
                padding-bottom: 100px;
            }
        }

    }

    .sp-card {
        cursor: pointer;
        width: 250px;
        padding: 0 20px 20px 20px;
        background-color: rgba(233, 234, 234, 0.5);

        .el-image {
            width: 250px;
            height: 200px;
        }

        .sp-xl {
            position: relative;
            top: 35px;
            z-index: 99;


            span {
                padding: 5px 10px;
                background-color: rgba(118, 195, 202, 0.3);
            }
        }

        .sp-name {
            font-size: 15px;
            width: 250px;
            height: 50px;
            // white-space: nowrap;
            // overflow: hidden;
            // text-overflow: ellipsis;
        }

        .sp-pic {
            font-size: 19px;
            color: red;
            line-height: 50px;
        }

        .el-tag {
            margin-left: 0 !important;
        }

        .sp-tip {
            margin-top: 10px;
        }
    }
}
</style>
<style  lang="scss">
@media screen and (max-width: 800px) {
    .el-button {
        margin-top: 20px;
        font-size: 50px;
    }

    .el-carousel__container {
        height: calc(100% - 50px);
    }

    .sor-search {
        .el-input {
            width: 85%;
            font-size: 51px;

            .el-input__inner {
                border-top-left-radius: 30px;
                border-bottom-left-radius: 30px;
                background: #a09d9d;
                width: 115%;
                padding-right: 73px;
                font-size: 51px;
                height: 100%;
            }

            .el-input-group__append {
                cursor: pointer;
                margin-left: -20px;
                border-radius: 30px;
                background-color: #409eff;
                color: #f2f6fc;
                border: #409eff;
                font-size: 51px;
            }
        }

    }

    .el-tag {
        height: 60px;
        font-size: 50px;
        line-height: 60px;
    }

    .el-pagination__total {
        margin-top: 20px;
        font-size: 65px !important;
    }

    .el-pagination__jump {
        font-size: 70px !important;
        line-height: 1;
    }

    .el-pager .number {
        font-size: 70px !important;
        line-height: 1;
    }

    .el-pagination .el-icon {
        font-size: 70px !important;
       
    }

    .el-pagination .el-input {
        height: 70px;
        width: 70px;
    }

    .el-pagination .el-input .el-input__inner {
        height: 70px;
        font-size: 70px !important;
        line-height: 0;
    }
}

@media screen and (min-width: 800px) {
    .el-carousel__container {
        height: calc(100% - 50px);
    }

    .sor-search {
        .el-input {
            width: 85%;
            font-size: 18px;

            .el-input__inner {
                border-top-left-radius: 30px;
                border-bottom-left-radius: 30px;
                background: #a09d9d;
                width: 115%;
                padding-right: 73px;
            }

            .el-input-group__append {
                cursor: pointer;
                margin-left: -20px;
                border-radius: 30px;
                background-color: #409eff;
                color: #f2f6fc;
                border: #409eff;
            }
        }

    }
}
</style>