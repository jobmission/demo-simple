

````
CREATE DATABASE IF NOT EXISTS demo_01 DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
create user 'user_dev'@'localhost' identified by 'pass_dev';
grant all privileges on demo_01.* to 'user_dev'@'localhost';
````

````
反向生成mybatis mapper
mvn mybatis-generator:generate -X
````
