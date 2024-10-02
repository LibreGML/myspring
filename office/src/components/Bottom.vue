<template>
    <div id="foot">
        <div class="foot_content">
            <div class="foot_child_content w1320 dis_flex">
                <div class="foot_logo fl"><el-image :src="logo" style="width: 90%; height: 100%;" />
                </div>
                <div class="foot_content_c">
                    <ul class="dis_flex foot_content_c_tit">
                        <li>{{ bottom.formInline1.title }}</li>
                        <li>{{ bottom.formInline2.title }}</li>
                        <li>{{ bottom.formInline3.title }}</li>
                    </ul>
                    <ul class="dis_flex foot_content_c_child"><span>
                            <li><a :href="bottom.formInline1.title1Url">{{ bottom.formInline1.title1 }}</a></li>
                            <li><a :href="bottom.formInline1.title2Url">{{ bottom.formInline1.title2 }}</a></li>
                            <li><a :href="bottom.formInline1.title3Url">{{ bottom.formInline1.title3 }}</a></li>
                            <li><a :href="bottom.formInline1.title4Url">{{ bottom.formInline1.title4 }}</a></li>
                        </span> <span>
                            <li><a :href="bottom.formInline2.title1Url">{{ bottom.formInline2.title1 }}</a></li>
                            <li><a :href="bottom.formInline2.title2Url">{{ bottom.formInline2.title2 }}</a></li>
                            <li><a :href="bottom.formInline2.title3Url">{{ bottom.formInline2.title3 }}</a></li>
                            <li><a :href="bottom.formInline2.title4Url">{{ bottom.formInline2.title4 }}</a></li>
                        </span> <span>
                            <li><a :href="bottom.formInline3.title1Url">{{ bottom.formInline3.title1 }}</a></li>
                            <li><a :href="bottom.formInline3.title2Url">{{ bottom.formInline3.title2 }}</a></li>
                            <li><a :href="bottom.formInline3.title3Url">{{ bottom.formInline3.title3 }}</a></li>
                            <li><a :href="bottom.formInline3.title4Url">{{ bottom.formInline3.title4 }}</a></li>
                        </span></ul>
                </div>
                <div class="foot_chat fr">
                    <div class="foot_chat_eem fl"><img :src="bottom.QRCode[0]" alt="" style="width: 100%; height: 100%;">
                    </div>
                    <div class="foot_txt"><span class="foot_txt_tit">扫码联系客服</span><br><br> <br> <br><span
                            class="foot_txt_tit">工作日24小时在线</span></div>
                </div>
            </div>
            <div class="foot_bottom"><span></span>
                <div class="dis_flex" style="justify-content: center;">
                    <h6 class="fl" style="width: auto; color: rgb(255, 255, 255); margin: 20px 0px 20px 20px;"><a
                            target="_blank" :href="bottom.formInline4.leftUrl" style="color: rgb(255, 255, 255);">{{
                                bottom.formInline4.left }}</a></h6>
                    <h6 class="fl" style="width: auto; color: rgb(255, 255, 255); margin: 20px 0px 20px 20px;"><a
                            target="_blank" :href="bottom.formInline4.rightUrl" style="color: rgb(255, 255, 255);">{{
                                bottom.formInline4.right }}
                        </a></h6>
                    <h6 class="fl" style="width: auto; color: rgb(255, 255, 255); margin: 20px 0px 20px 20px;"><a
                            target="_blank" href="https://beian.miit.gov.cn" style="color: rgb(255, 255, 255);"></a></h6>
                    <h6 class="fl" style="width: auto; color: rgb(255, 255, 255); margin: 20px 0px 20px 20px;"><a
                            target="_blank" href="https://beian.miit.gov.cn" style="color: rgb(255, 255, 255);"></a></h6>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
export default {
    name: "bottom",
    components: {},
    data() {
        return {
            bottom: {
                QRCode: [],
                formInline1: {},
                formInline2: {},
                formInline3: {},
                formInline4: {},
            }
        };
    },
    computed: {
        logo() {
            return localStorage.getItem('jbj_logo') || "";
        },
    },
    mounted() {
        this.getClass()
    },
    methods: {
        getClass() {
            this.$axios.get(`/api/website/get-base?key=bottom`).then(res => {
                if (res.data.code === 200) {
                    this.bottom = JSON.parse(res.data.data.fieldValue)
                }
            })
        },
    },
};
</script>
  
<style lang="scss" scoped>
@media screen and (max-width: 800px) {
    #foot {
        width: 100%;
        height: 520px;
        background-color: #373b41;
        box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
        padding-top: 20px;
    }

    .logo_div {
        width: 100%;
        height: 100%;
    }

    .foot_content {
        padding-top: 50px;
        background-color: #373b41;
        width: 100%;
        height: 620px;
    }

    .foot_child_content {
        // align-items: center;
    }

    .foot_logo {
        display: none;
        width: 213px;
        height: 71px;
    }

    .foot_content_c {
        width: calc(100vw - 900px);
        height: 620px;
        color: #fff;
    }

    .foot_content_c_tit {
        font-size: 50px;
    }

    .foot_content_c_child {
        flex-wrap: wrap;
        font-size: 50px;
    }

    .foot_content_c_child span {
        padding: 0 5px;
    }

    .foot_content_c_child li {
        flex: 1;
        margin: 10px 0;
        text-align: center;
    }

    .foot_content_c_child li a {
        color: #fff;
    }

    .foot_chat {
        margin-top: 30px;
        width: 450px;
        height: auto;
    }

    .foot_chat_eem {
        width: 350px;
        height: 350px;
        background-color: yellow;
        margin-right: 90px;
    }

    .foot_txt {
        color: #fff;
    }

    .foot_txt_tit {
        display: none;
        font-size: 50px;
        color: #fff;
        text-align: start;
        margin-bottom: 10px;
    }

    .foot_txt_dec {
        font-size: 50px;
        margin-top: 20px;
    }

    .foot_bottom {
        display: none;
        width: 100%;
        height: auto;
    }

    .foot_bottom span {
        height: 0.5px;
        background-color: #525457;
        margin: 0 auto;
        margin-top: 60px;
    }

    .dis_flex {
        display: flex;
        justify-content: space-between;
    }

    .w1320 {
        width: 1720px;
        margin: 0 auto;
    }

    .Radius20 {
        border-radius: 10px;
        overflow: hidden;
    }

    .boxShadow {
        box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
    }

    span,
    img {
        display: block;
    }

    ul {
        display: block;
        margin: 0 !important;
    }

    li {
        list-style: none;
        display: block;
        font-size: 70px;
        color: #fff;
    }

    em {
        font-style: inherit;
        display: block;
    }

    p {
        display: block;
        margin: 0;
    }

    dl,
    dl>dt,
    dl>dt>dd {
        display: block;
        font-weight: 400;
        margin: 0;
    }

    dd {
        overflow: hidden;
        text-overflow: ellipsis;
    }

    a {
        font-size: 50px;
        text-decoration: none !important;
        display: block;
        color: #333333;
        text-overflow: ellipsis;
        overflow: hidden;
    }

    a:hover {
        display: block;
        text-decoration: no ne;
        transition: all 0.6s;
        color: var(--colors) !important;
    }

    button:focus {
        border: none;
    }

    .fl {
        float: left;
    }

    .fr {
        float: right;
    }
}

@media screen and (min-width: 800px) {
    #foot {
        width: 100%;
        height: 320px;
        background-color: #373b41;
        box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
        padding-top: 20px;
    }

    .logo_div {
        width: 100%;
        height: 100%;
    }

    .foot_content {
        background-color: #373b41;
        width: 100%;
        height: 320px;
    }

    .foot_child_content {
        align-items: center;
    }

    .foot_logo {
        width: 213px;
        height: 71px;
    }

    .foot_content_c {
        width: 607px;
        height: 120px;
        margin-left: 25px;
        color: #fff;
    }

    .foot_content_c_tit {
        font-size: 20px;
    }

    .foot_content_c_child {
        flex-wrap: wrap;
        font-size: 16px;
    }

    .foot_content_c_child span {
        padding: 0 5px;
    }

    .foot_content_c_child li {
        flex: 1;
        margin: 10px 0;
        text-align: center;
    }

    .foot_content_c_child li a {
        color: #fff;
    }

    .foot_chat {
        width: 300px;
        height: auto;
    }

    .foot_chat_eem {
        width: 138px;
        height: 133px;
        background-color: yellow;
        margin-right: 20px;
    }

    .foot_txt {
        color: #fff;
    }

    .foot_txt_tit {
        font-size: 16px;
        color: #fff;
        text-align: start;
        margin-bottom: 10px;
    }

    .foot_txt_dec {
        font-size: 16px;
        margin-top: 20px;
    }

    .foot_bottom {
        width: 100%;
        height: auto;
    }

    .foot_bottom span {
        width: 1000px;
        height: 0.5px;
        background-color: #525457;
        margin: 0 auto;
        margin-top: 60px;
    }

    .dis_flex {
        display: flex;
        justify-content: space-between;
    }

    .w1320 {
        width: 1720px;
        margin: 0 auto;
    }

    .Radius20 {
        border-radius: 10px;
        overflow: hidden;
    }

    .boxShadow {
        box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
    }

    span,
    img {
        display: block;
    }

    ul {
        display: block;
        margin: 0 !important;
    }

    li {
        list-style: none;
        display: block;
        font-size: 23px;
        color: #fff;
    }

    em {
        font-style: inherit;
        display: block;
    }

    p {
        display: block;
        margin: 0;
    }

    dl,
    dl>dt,
    dl>dt>dd {
        display: block;
        font-weight: 400;
        margin: 0;
    }

    dd {
        overflow: hidden;
        text-overflow: ellipsis;
    }

    a {
        font-size: 18px;
        text-decoration: none !important;
        display: block;
        color: #333333;
        text-overflow: ellipsis;
        overflow: hidden;
    }

    a:hover {
        display: block;
        text-decoration: no ne;
        transition: all 0.6s;
        color: var(--colors) !important;
    }

    button:focus {
        border: none;
    }

    .fl {
        float: left;
    }

    .fr {
        float: right;
    }
}
</style>
  