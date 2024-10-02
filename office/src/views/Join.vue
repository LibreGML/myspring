<template>
    <div class="home">
        <div class="join-us" :style="{ backgroundImage: 'url(' + bakUrl + ')' }">
            <div class="select">
                <div :class="select === 1 ? 'join-select' : 'join-no-select'" @click="select = 1">商家注册</div>
                <div class="join-no-select" @click="openNewWindow">商家登录</div>
                <div :class="select === 2 ? 'join-select' : 'join-no-select'" @click="select = 2">供应商入驻</div>
                <div class="join-no-select" @click="openNewWindow">供应商登录</div>
            </div>
            <div class="join-mian" :class="{ 'show': select === 1, 'hide': select !== 1 }">
                <el-form :model="ruleForm" ref="ruleForm1" label-width="80px" class="demo-ruleForm">
                    <el-form-item label="商家名称" prop="name" :rules="{
                        required: true, message: '请输入商家名称', trigger: 'blur'
                    }">
                        <el-input v-model="ruleForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="账号" prop="account" :rules="{
                        required: true, message: '请输入账号', trigger: 'blur'
                    }">
                        <el-input v-model="ruleForm.account"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password" :rules="{
                        required: true, message: '请输入密码', trigger: 'blur'
                    }">
                        <el-input v-model="ruleForm.password"></el-input>
                    </el-form-item>
                    <el-form-item label="验证码" prop="code" :rules="{
                        required: true, message: '请输入验证码', trigger: 'blur'
                    }">
                        <el-input v-model="ruleForm.code" class="codeInput"></el-input>
                        <CodeVessel ref="CodeVessel" class="codeVessel" style="float: right;" />
                    </el-form-item>
                    <el-form-item>
                        <el-button style="float: right;" type="primary" @click="submitForm('ruleForm1')">提交</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="join-mian" :class="{ 'show': select === 2, 'hide': select !== 2 }">
                <el-form :model="ruleForm" ref="ruleForm2" label-width="80px" class="demo-ruleForm">
                    <div v-if="action === 1">
                        <el-form-item label="选择货源系统" prop="sourceSystem" :rules="{
                            required: true, message: '请选择货源系统', trigger: 'change'
                        }">
                            <el-select class="select-ie" style="width: 100%;" v-model="ruleForm.sourceSystem"
                                placeholder="请选择">
                                <el-option v-for="item in sourceOptions" :key="item.id" :label="item.name" :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="货源网站地址" prop="sourceWebsite" :rules="{
                            required: true, message: '请输入货源网站地址', trigger: 'blur'
                        }">
                            <el-input v-model="ruleForm.sourceWebsite"></el-input>
                        </el-form-item>
                        <el-form-item label="对接账号" prop="dockingAccount" :rules="{
                            required: true, message: '请输入对接账号', trigger: 'blur'
                        }">
                            <el-input v-model="ruleForm.dockingAccount"></el-input>
                        </el-form-item>
                        <el-form-item label="对接秘钥" prop="dockingKey" :rules="{
                            required: true, message: '请输入对接秘钥', trigger: 'blur'
                        }">
                            <el-input v-model="ruleForm.dockingKey"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button v-loading="loading" style="margin-right: 10px;" type="primary"
                                @click="testConnectivity('ruleForm2')">检测</el-button>
                            <span class="testConnec"
                                :style="{ color: islitong === '连通性测试成功' ? 'green' : (islitong === '连通性测试失败' ? 'red' : '') }">{{
                                    islitong
                                }}</span>

                            <el-button style="float: right;" type="primary" @click="next('ruleForm2')">下一步</el-button>
                        </el-form-item>
                    </div>
                    <div v-if="action === 2">
                        <el-form-item label="供应商名称" prop="name" :rules="{
                            required: true, message: '请输入供应商名称', trigger: 'blur'
                        }">
                            <el-input v-model="ruleForm.name"></el-input>
                        </el-form-item>
                        <el-form-item label="账号" prop="account" :rules="{
                            required: true, message: '请输入账号', trigger: 'blur'
                        }">
                            <el-input v-model="ruleForm.account"></el-input>
                        </el-form-item>
                        <el-form-item label="密码" prop="password" :rules="{
                            required: true, message: '请输入密码', trigger: 'blur'
                        }">
                            <el-input v-model="ruleForm.password"></el-input>
                        </el-form-item>
                        <el-form-item label="申请理由" prop="applyReason" :rules="{
                            required: true, message: '请输入申请理由', trigger: 'blur'
                        }">
                            <el-input type="textarea" :rows="5" resize="none" v-model="ruleForm.applyReason"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button style="float: right;margin-left: 20px;" type="primary"
                                @click="submitForm('ruleForm2')">提交</el-button>
                            <el-button style="float: right;" type="primary" @click="action = 1">上一步</el-button>
                        </el-form-item>
                    </div>
                </el-form>
            </div>
        </div>
    </div>
</template>
<script>
import CodeVessel from '../components/CodeVessel.vue'
export default {
    name: "Join",
    components: { CodeVessel },
    data() {
        return {
            islitong: "未检测连通性",
            select: 1,
            action: 1,
            sourceOptions: [],
            loading: false,
            ruleForm: {
                code: "",
                name: '',
                sourceSystem: '',
                sourceWebsite: '',
                dockingAccount: '',
                dockingKey: '',
                account: '',
                password: '',
                applyReason: '',
            },
            bakUrl: ""
        };
    },
    computed: {
    },
    mounted() {
        this.getAllSource()
        this.getbak()
    },
    methods: {
        getbak() {
            this.$axios.get(`/api/website/get-base?key=jion`).then(res => {
                if (res.data.code === 200) {
                    this.bakUrl = res.data.data.fieldValue ? JSON.parse(res.data.data.fieldValue)[0] : []
                }
            })
        },
        getAllSource() {
            this.$axios.get("/api/official/account/all-source").then(res => {
                if (res.data.code === 200) {
                    this.sourceOptions = res.data.data
                }
            })
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (formName === "ruleForm1") {
                        if (this.$refs.CodeVessel.verifyCode.options.code !== this.ruleForm.code) {
                            this.$message({
                                message: '验证码错误',
                                type: 'error'
                            });
                            return;
                        }
                    }
                    let url = formName === "ruleForm1" ? "business" : "supplier"
                    this.$axios.post(`/api/official/account/register/${url}`, this.ruleForm).then(res => {
                        if (res.data.code === 200) {
                            this.$message({
                                message: '注册成功',
                                type: 'success'
                            });
                            this.$confirm('注册成功, 是否跳转登录页面?', '提示', {
                                confirmButtonText: '跳转',
                                cancelButtonText: '取消',
                                type: 'success'
                            }).then(() => {
                                this.openNewWindow()
                            })
                        } else {
                            this.$message({
                                message: res.data.msg,
                                type: 'error'
                            });
                        }
                    })
                }
            })
        },
        next(formName) {
            let that = this
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.islitong === "未检测连通性") {
                        that.$message({
                            message: '未检测连通性',
                            type: 'warning'
                        });
                        return
                    }
                    if (this.islitong === "连通性测试失败") {
                        that.$message({
                            message: '连通性测试失败',
                            type: 'warning'
                        });
                        return
                    }
                    if (this.islitong === "连通性测试成功") {
                        this.action = 2
                        return
                    }
                }
            });
        },
        testConnectivity(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.loading = true
                    this.islitong = "             "
                    this.$axios.post("/api/official/account/connectivity", this.ruleForm).then(res => {
                        if (res.data.code === 200) {
                            if (res.data.data) {
                                this.islitong = "连通性测试成功"
                            } else {
                                this.islitong = "连通性测试失败"
                            }
                        } else {
                            this.islitong = "连通性测试失败"
                        }
                        this.loading = false
                    })
                }
            })
        },
        openNewWindow() {
            window.open('/web', '_blank');
        }
    },
};
</script>
<style lang="scss" scoped>
@media screen and (max-width: 800px) {

    .testConnec {
        font-size: 40px;
    }

    .codeInput {
        width: 100%;
        float: left;
    }

    .codeVessel {
        margin-top: 50px;
        height: 140px;
        width: 500px;
        float: right;
    }

    .home {
        overflow: hidden;
        width: 100%;
        background-color: #ffff;
        min-height: calc(100vh - 820px);
    }

    .join-us {
        width: 100%;
        min-height: calc(100vh - 820px);
        padding-top: 100px;
        background-repeat: no-repeat;
        background-size: cover;

        .select {
            margin-top: 550px;
            width: 100%;
            display: flex;
            justify-content: center;

            .join-select {
                cursor: pointer;
                margin-left: 30px;
                padding: 20px;
                font-size: 70px;
                background-color: rgba(2, 134, 222, 0.8);
                border-radius: 20px;
                border: 1px solid rgba(2, 134, 222, 0.8);
                color: #fff;
            }

            .join-no-select {
                cursor: pointer;
                margin-left: 30px;
                padding: 20px;
                font-size: 70px;
                font-weight: 600;
                background-color: rgba(255, 255, 255, 0.6);
                border-radius: 20px;
                border: 1px solid rgba(2, 134, 222, 0.8);
                color: #295ccb;
            }
        }
    }

    .join-mian {
        top: 300px;
        padding: 50px;
        width: calc(100vw - 920px);
        height: 1520px;
        margin: 0 auto;
        position: relative;
        border-radius: 20px;
        left: 0;
        animation: slideIn 0.5s ease-in-out forwards;
        background-color: rgba(255, 255, 255, 0.6);
    }

    .join-mian.show {
        animation: slideOut 0.5s ease-in-out forwards;
    }

    .join-mian.hide {
        display: none;
    }

    @keyframes slideIn {
        0% {
            left: 0;
        }

        100% {
            left: 100%;
        }
    }

    @keyframes slideOut {
        0% {
            left: -100%;
        }

        100% {
            left: 0;
        }
    }
}

@media screen and (min-width: 800px) {

    .codeInput {
        width: calc(100% - 220px);
        float: left;
    }

    .codeVessel {
        height: 40px;
        width: 200px;
        float: right;
    }

    .home {
        overflow: hidden;
        width: 100%;
        background-color: #ffff;
    }

    .join-us {
        width: 100%;
        min-height: 815px;
        padding-top: 100px;
        background-repeat: no-repeat;
        background-size: cover;

        .select {
            margin-top: 50px;
            width: 100%;
            display: flex;
            justify-content: center;

            .join-select {
                cursor: pointer;
                margin-left: 30px;
                padding: 20px;
                font-size: 20px;
                background-color: rgba(2, 134, 222, 0.8);
                border-radius: 20px;
                border: 1px solid rgba(2, 134, 222, 0.8);
                color: #fff;
            }

            .join-no-select {
                cursor: pointer;
                margin-left: 30px;
                padding: 20px;
                font-size: 20px;
                font-weight: 600;
                background-color: rgba(255, 255, 255, 0.6);
                border-radius: 20px;
                border: 1px solid rgba(2, 134, 222, 0.8);
                color: #295ccb;
            }
        }
    }

    .join-mian {
        top: 100px;
        padding: 50px;
        width: 600px;
        margin: 0 auto;
        position: relative;
        border-radius: 20px;
        left: 0;
        animation: slideIn 0.5s ease-in-out forwards;
        background-color: rgba(255, 255, 255, 0.6);
    }

    .join-mian.show {
        animation: slideOut 0.5s ease-in-out forwards;
    }

    .join-mian.hide {
        display: none;
    }

    @keyframes slideIn {
        0% {
            left: 0;
        }

        100% {
            left: 100%;
        }
    }

    @keyframes slideOut {
        0% {
            left: -100%;
        }

        100% {
            left: 0;
        }
    }
}
</style>
<style lang="scss">
@media screen and (max-width: 800px) {
    .demo-ruleForm {
        .el-form-item {
            margin-top: 100px;
        }

        .el-form-item__label {
            font-size: 60px;
            line-height: 150px;
        }

        .el-input__inner {
            margin-top: 20px;
            height: 100px;
            font-size: 50px;
        }

        .el-form-item__error {
            font-size: 50px;
        }
    }
    .el-select-dropdown__empty {
        font-size: 50px;
    }

    .el-select-dropdown__item{
        font-size: 50px;
        height: 90px;
        line-height: 90px;
    }

    .el-button {
        font-size: 50px;
    }

}
</style>