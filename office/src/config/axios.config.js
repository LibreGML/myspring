import Axios from 'axios';
import { Message } from 'element-ui'

// const baseUrl = "/waibao/web/";
const baseUrl = "";

const axios = Axios.create({
    baseURL: baseUrl,
    timeout: 300000,
});

axios.interceptors.request.use(
    config => {
        let token = sessionStorage.getItem('token');
        if (token) {
            config.headers.token = token;
        }
        return config;
    },
    err => {
        return Promise.reject(err);
    }
);

axios.interceptors.response.use(
    response => {
        let code = response.data.code;
        switch (code) {
            case 402:
                Message({
                    type: 'error',
                    message: response.data.message
                })
                window.location.href = '/#/login'
                break;
        }
        return response;
    },
    error => {
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    Message({
                        type: 'error',
                        message: error.response.data.message
                    })
                    break;
                case 500:
                    console.log(error.response, '服务器内部错误');
                    break;
                default:
                    break;
            }
        }
        return Promise.reject(error.response);
    }
);

export default axios;
