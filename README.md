# Spring-cashier
**Enterprise Software Class Project**
Full Stack Spring Boot Enterprise application designed for a cashier to process drink orders from a customer's mobile app

## Deploy MySQL on Docker
1. Navigate to the repository directory and start the database.
```
docker run --platform=linux/amd64 -d --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=<INSERT PASSWORD> mysql:8.0
```
2. Set up the database by entering bash in the Docker console and creating the tables for storing orders and security sessions. The console should prompt the user to enter the password, which is the same as the one from the previous command.
```
//Enter MySQL Console
mysql -u root -p
```
3. Inside the MySQL console, create the tables for orders and cashier user information
```
//Create the Databases and grant permissions
create database starbucks ;
create database cashier;
create user 'admin'@'%' identified by 'welcome';
grant all on cashier.* to 'admin'@'%';
grant all on starbucks.* to 'admin'@'%';
use cashier;
```
4. Create session tables to temporarily store user login sessions.
```
//Create Sessions Table
CREATE TABLE SPRING_SESSION (
  PRIMARY_ID CHAR(36) NOT NULL,
  SESSION_ID CHAR(36) NOT NULL,
  CREATION_TIME BIGINT NOT NULL,
  LAST_ACCESS_TIME BIGINT NOT NULL,
  MAX_INACTIVE_INTERVAL INT NOT NULL,
  EXPIRY_TIME BIGINT NOT NULL,
  PRINCIPAL_NAME VARCHAR(100),
  CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
  SESSION_PRIMARY_ID CHAR(36) NOT NULL,
  ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
  ATTRIBUTE_BYTES BLOB NOT NULL,
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;
```
5. Then build the image for spring-cashier, starbucks-api, and starbucks-worker by using Make File commands in each projects' directory.
```
make docker-build
```
6. Now deploy all the projects onto Docker using the Make File command.
```
make compose-up
```
7. The application should now prompt the account creation screen on port localhost:8080.
8. Now build the mobile app simulation by typing the following make command
```
make starbucks-app-run:
```
9. Enter the Starbucks mobile phone simulation and order a desired drink
