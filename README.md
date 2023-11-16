
# REST Sakila Api Web Service

This API provides comprehensive data access and creation capabilities across a wide range of categories, including retrieving film details, managing customer information, processing rental transactions, and handling payment records.
It has been recently developed following RESTful principles, offering versatile and efficient interactions with the data.


## Documentation

https://documenter.getpostman.com/view/26688798/2s9YXia2bb

## Can be run as a Jar or a WAR
```bash
maven clean install
```


## Get Started

- Clone the repository to your computer
```bash
git clone https://github.com/ayman76/SakilaApi.git
```
- Open Project in your editor
- Configure application.properties

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/sakila?createDatabaseIfNotExist=true
spring.datasource.username={username}
spring.datasource.password={password}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# Allows To Hibernate to generate SQL optimized for a particular DBMS
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
# drop n create table, good for testing, comment this in production
spring.jpa.hibernate.ddl-auto=update
# Show SQL queries
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

```

- Run the app using maven

```bash
maven spring-boot:run
```
The app will start running at http://localhost:8080
## EndPoints
- Actor
- Category
- Country
- Language
- City
- Address
- Staff
- Customer
- Rental
- Payment
- Store
- Inventory
- Film
