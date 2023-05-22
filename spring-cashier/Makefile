all: clean

clean:
	mvn clean

compile:
	mvn compile

run: compile
	mvn spring-boot:run

build:
	mvn package

# MySQL DB

mysql:
	docker run --platform=linux/amd64 -d --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=connect mysql:8.0

mysql-shell:
	docker exec -it mysql bash

# Docker

docker-build: build
	docker build --platform=linux/amd64 -t spring-cashier .
	docker images

docker-push:
	docker login
	docker build --platform=linux/amd64 -t $(account)/spring-cashier .
	docker push $(account)/spring-cashier

#Compose

compose-up:
	docker-compose up --scale cashier=2 -d

lb-up:
	docker-compose up -d lb

gumball-up:
	docker-compose up -d cashier

redis-up:
	docker-compose up -d redis

mysql-up:
	docker-compose up -d mysql

compose-down:
	docker-compose down