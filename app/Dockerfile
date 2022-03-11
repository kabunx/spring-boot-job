FROM openjdk:8-jre-slim
LABEL maintainer="jonathan <jianyang1209@outlook.com>"
ARG JAR_FILE
ADD target/${JAR_FILE} app.jar
# 上海市区
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime  \
    && echo Asia/Shanghai > /etc/timezone \
# 端口
EXPOSE 8080 8081 8082
# 运行命令
ENTRYPOINT ["java", "-jar", "/app.jar"]