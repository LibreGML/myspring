FROM registry.cn-shenzhen.aliyuncs.com/peopledata/k8s:nginxv88
WORKDIR /usr/share/nginx/html
ADD dist .
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["nginx","-g","daemon off;"]