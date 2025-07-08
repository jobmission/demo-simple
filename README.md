
### Postgres
````
CREATE DATABASE demo_01;
CREATE USER user_dev WITH PASSWORD 'pass_dev';
GRANT ALL PRIVILEGES ON DATABASE demo_01 TO user_dev;
````

### mysql
````
CREATE DATABASE IF NOT EXISTS demo_01 DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
create user 'user_dev'@'localhost' identified by 'pass_dev';
grant all privileges on demo_01.* to 'user_dev'@'localhost';
````

### sqlite config
````
    datasource:
        url: jdbc:sqlite:path\to\demo_01.db
        username:
        password:
        driver-class-name: org.sqlite.JDBC
````

### 反向生成mybatis mapper
````
mvn mybatis-generator:generate -X
````

### 添加opencv lib到path环境变量中
````
C:\programs\opencv\build\java\x64
````
