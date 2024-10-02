<template>
    <div class="mianbody">
        <div class="mian-ls" style="display: flex;">
            <div class="mian-l">
                <el-image :src="goodsInfo.image"></el-image>
            </div>
            <div class="mian-r">
                <div class="mian-r-titel">{{ goodsInfo.name }}</div>
                <div class="mian-r-body1">
                    <el-row>
                        <el-col class="til" :span="4">
                            价格:
                        </el-col>
                        <el-col :span="20">
                            ￥{{ getPrice(goodsInfo.price) }}
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col class="til" :span="4">
                            最大限购:
                        </el-col>
                        <el-col :span="20">
                            {{ goodsInfo.inventory }}
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col class="til" :span="4">
                            商品id:
                        </el-col>
                        <el-col :span="20">
                            {{ goodsInfo.id }}
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col class="til" :span="4">
                            下单附加信息:
                        </el-col>
                        <el-col :span="20">
                            <el-tag v-for="(item, index) in goodsInfo.attach" :key="index">{{ item }}</el-tag>
                        </el-col>
                    </el-row>
                </div>
                <div class="mian-r-body1">
                    <el-tabs v-model="activeName">
                        <el-tab-pane label="商品详情" name="first">
                            <div v-html="goodsInfo.details"></div>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
export default {
    name: "Goods",
    data() {
        return {
            activeName: "first",
            goodsInfo: {}
        }
    },
    computed: {

    },
    mounted() {
        this.getGoods()
    },
    methods: {
        getPrice(val) {
            if (val) {
                return parseFloat(val).toFixed(2);
            }
            return ""
        },
        getGoods() {
            this.$axios.get(`/api//official/source/detail?key=${this.$route.query.id}`).then(res => {
                if (res.data.code === 200) {
                    this.goodsInfo = res.data.data || {};
                }
            })
        },
    },
};


</script>

<style lang="scss" scoped>
@media screen and (max-width: 800px) {
    .mianbody {
        margin: 0 250px;
        margin-bottom: 50px;
        min-height: calc(100vh - 1220px);
        background: hsl(0, 0%, 100%);
        padding-top: 400px;

        .mian-ls {
            display: block;
            flex-wrap: wrap;
        }

        .mian-l {
            margin: 0 auto;
            width: 800px;

            .el-image {
                width: 800px;
                height: 800px;
            }
        }

        .mian-r {
            width: 90%;
            margin: 0 auto;

            .mian-r-titel {
                font-weight: 600;
                font-size: 80px;
            }

            .mian-r-body1 {
                padding: 50px;
                margin-top: 30px;
                background: #f5f2f2;
                font-size: 50px;

                .el-row {
                    margin-top: 10px;
                }
                .el-col {
                    width: 55%;
                    margin-top: 10px;
                }

                .til {
                    width: 45% !important;
                }
                .el-row:nth-child(1) {
                    line-height: 60px;

                    .el-col:nth-child(2) {
                        color: red;
                        font-size: 50px;
                    }
                }
            }
        }
    }
}

@media screen and (min-width: 800px) {
    .mianbody {
        margin: 250px;
        margin-top: 140px;
        margin-bottom: 50px;
        min-height: calc(100vh - 530px);
        background: #FFF;

        .mian-l {
            width: 300px;
            padding: 30px;

            .el-image {
                width: 300px;
                height: 300px;
            }
        }

        .mian-r {
            width: calc(100% - 300px);
            padding: 30px;

            .mian-r-titel {
                font-weight: 400;
                font-size: 20px;
            }

            .mian-r-body1 {
                padding: 30px;
                margin-top: 30px;
                background: #f5f2f2;

                .el-row {
                    margin-top: 10px;
                }

                .el-row:nth-child(1) {
                    line-height: 50px;

                    .el-col:nth-child(2) {
                        color: red;
                        font-size: 30px;
                    }
                }

            }
        }
    }
}
</style>
<style lang="scss" >
@media screen and (max-width: 800px) {
    .el-tabs__item {
        font-size: 60px;
    }

    .el-tag {
        height: 60px;
        font-size: 50px;
        line-height: 60px;
    }
}
</style>