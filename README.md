# Proxy Thumbor API

Proxy thumbor API to resize images by categories and alias with docker-compose for supply

````
proxy-thumbor-api
 ├── src
 │   ├── java
 │   │    └── com.proxy.thumborapi
 │   │               ├── config
 │   │               ├── exception
 │   │               ├── model
 │   │               ├── service
 │   │               ├── thumbor
 │   │               ├── web
 │   │               └── ThumborApiApplication.java
 │   └── resources
 │          ├── application.yml #config sizes and categories for default
 │          ├── application-pre.yml #config sizes and categories for pre-production envoriment
 │          ├── application-pro.yml #config sizes and categories for production envoriment
 │          └── aplication-uat.yml #config sizes and categories for uat envoriment
 │   
 ├── test
 │   
 ├── build.gradle  # config to gradle
 ├── docker-compose.yml # file to supply envoriment thumbor oficial engine
 └── README.md
 ````

## Development
The project uses a **docker container** as development environment.

### Docker pre-requisites
* To have [**docker**](https://www.docker.com/) (last version) installed in your system.
* To have [**docker-compose**](https://docs.docker.com/compose/) (version >= 1.6) installed in your system.
* Java [**1.8 sdk install**](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)

First step add this dns in your /etc/hosts:
```bash
127.0.0.1 thumbor_server
```

second step:
```bash
docker-compose up -d
```

Finally run sprintBoot, sorry not have docker container for run api, not yet :-(:

```bash
gradle bootRun
```

Execute test:
```bash
gradle test
```

or use Gradle projects un your IntellijIDEA:  pmp-thumbor-api \[bootRun\]

#### View list of images, only AWS bucket:
````
http://localhost:8080/images/list
``

#### Sample and check image

Legacy with prefix /api
```
http://localhost:8080/api/resize/0x0/sample.jpg
```

with size:
```
http://localhost:8080/resize/es/zoom/sample-two.jpg
```

or webp (chrome only)
```
http://localhost:8080/resize/es/zoom/sample-two.jpg?webp=true
```