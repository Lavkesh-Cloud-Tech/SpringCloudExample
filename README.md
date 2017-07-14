# Spring Cloud Example

In this Microservice POC, I used Spring Boot and Netflix OSS to implement microservice and try to cover below few core parts of microservice landscape :

- Service Discovery
- Config Server
- Spring Bus to do application config update on the fly
- Zuul Gate keeper

## Installation

To get started, please run below command to clone repository on your machine

```
git clone https://github.com/kbastani/spring-cloud-microservice-example
```

### Prerequisites

You need to install below software on your machine.

- Java 8
- Docker
- Eclipse or IntelliJ IDEA

### Running Application

You can compile project by running below command

```
./build-all.sh 1.0
```

Above command will compile project and create docker image for it. `1.0` is docker tag.

If you update docker tag then you need to update Docker Image Version info in **.env** file.

You can run below command in steps to start application:

*  `docker-compose up -d rabbitmq-server` 
*  `docker-compose up -d eureka-server`
*  `docker-compose up -d  config-server`
*  `docker-compose up -d  test-service-app`
*  `docker-compose up -d  security-service-app`
*  `docker-compose up -d  api-gateway-service-app`

Once application has been started you can run below command to check on which port number the application each service has been started.

`docker-compose ps`

It will list down each service with their port number information. 

## Access Application

To see Eureka dashboard you need to type below url in browser:

`http://localhost:<EUREKA_APP_PORT>/registry`

Enter username as **eureka** and password as **password**

It will list down all services that are get register with eureka server.

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.md](https://github.com/Lavkesh-Cloud-Tech/SpringCloudExample/blob/master/LICENSE) file for details
