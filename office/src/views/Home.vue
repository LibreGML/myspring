<template>
    <div class="main-doby">
        <HomeMain :body1_list="mianBody1" :body2_list="mianBody2" :body3_list="mianBody3.tableData"
            :body4_list="mianBody4.tableData" :body5_list="mianBody5" :body3_left="mianBody3.other"
            :body4_left="mianBody4.other" />
        <div class="cooperate">
            <div class="titel">合作伙伴</div>
            <div class="cooperate-img">
                <el-image :src="item" v-for="item in partners" :key="item"></el-image>
            </div>
        </div>
    </div>
</template>
  
<script>
import HomeMain from '../components/HomeMain.vue'
export default {
    name: "Home",
    components: { HomeMain },
    data() {
        return {
            partners: [],
            mianBody1: [],
            mianBody2: [],
            mianBody3: {
                tableData: [],
                other: {}
            },
            mianBody4: {
                tableData: [],
                other: {}
            },
            mianBody5: []
        };
    },
    computed: {

    },
    mounted() {
        this.getPartners()
        this.getMianBody1()
        this.getMianBody2()
        this.getMianBody3()
        this.getMianBody4()
        this.getMianBody5()
    },
    methods: {
        getPartners() {
            this.$axios.get(`/api/website/get-base?key=partners`).then(res => {
                if (res.data.code === 200) {
                    this.partners = res.data.data.fieldValue ? JSON.parse(res.data.data.fieldValue) : []
                }
            })
        },
        getMianBody1() {
            this.$axios.get(`/api/website/get-base?key=mianBody1`).then(res => {
                if (res.data.code === 200) {
                    this.mianBody1 = res.data.data.fieldValue ? JSON.parse(res.data.data.fieldValue) : []
                }
            })
        },
        getMianBody2() {
            this.$axios.get(`/api/website/get-base?key=mianBody2`).then(res => {
                if (res.data.code === 200) {
                    this.mianBody2 = res.data.data.fieldValue ? JSON.parse(res.data.data.fieldValue) : []
                }
            })
        },
        getMianBody3() {
            this.$axios.get(`/api/website/get-base?key=mianBody3`).then(res => {
                if (res.data.code === 200) {
                    this.mianBody3 = res.data.data.fieldValue ? JSON.parse(res.data.data.fieldValue) : []
                }
            })
        },
        getMianBody4() {
            this.$axios.get(`/api/website/get-base?key=mianBody4`).then(res => {
                if (res.data.code === 200) {
                    this.mianBody4 = res.data.data.fieldValue ? JSON.parse(res.data.data.fieldValue) : []
                }
            })
        },
        getMianBody5() {
            this.$axios.get(`/api/website/get-base?key=mianBody5`).then(res => {
                if (res.data.code === 200) {
                    this.mianBody5 = res.data.data.fieldValue ? JSON.parse(res.data.data.fieldValue) : []
                }
            })
        },
    },
};
</script>

<style lang="scss" scoped>
@media screen and (max-width: 800px) {
    .main-doby {
        margin-top: 300px;
    }

    .home {
        width: 100%;
        background-color: #ffff;
    }

    .cooperate {
        height: 650px;
        background-color: #fff;
        border: 1px solid transparent;
        /* 隐藏实际的边框线 */
        box-shadow: 0 0 10px 5px rgba(156, 154, 154, 0.5);
        /* 虚化效果，调整模糊程度和阴影颜色 */

        .titel {
            padding-top: 40px;
            text-align: center;
            font-size: 100px;
        }

        .cooperate-img {
            display: flex;
            justify-content: center;
            gap: 50px;
            padding-top: 50px;

            .el-image {
                padding: 20px;
                width: 430px;
                height: 260px;
                border: rgba(156, 154, 154, 0.5) 1px solid;
            }
        }
    }
}

@media screen and (min-width: 800px) {

    .home {
        width: 100%;
        background-color: #ffff;
    }


    .cooperate {
        height: 350px;
        background-color: #fff;
        border: 1px solid transparent;
        /* 隐藏实际的边框线 */
        box-shadow: 0 0 10px 5px rgba(156, 154, 154, 0.5);
        /* 虚化效果，调整模糊程度和阴影颜色 */

        .titel {
            padding-top: 40px;
            text-align: center;
            font-size: 40px;
        }

        .cooperate-img {
            display: flex;
            justify-content: center;
            gap: 10px;
            padding-top: 50px;

            .el-image {
                padding: 20px;
                width: 170px;
                height: 80px;
                border: rgba(156, 154, 154, 0.5) 1px solid;
            }
        }
    }
}
</style>