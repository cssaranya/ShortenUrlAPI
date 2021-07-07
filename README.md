# ShortenUrlAPI

<a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"></a>

## About

The shortenurl is an API for short URL creation.  

The main requirements that guided the design and implementation of the URL shortening API:
•	Design and implement an API for short URL creation
•	Implement forwarding of short URLs to the original ones
•	There should be some form of persistent storage
•	It should be readable, maintainable, and extensible where appropriate
•	The implementation should preferably be in Java

## Architecture overview

#### Project structure
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── neueda
│   │   │           └── shortenurl
│   │   │               ├── controller
│   │   │               │   └── UrlController.java
│   │   │               ├── domain
│   │   │               │   └── UrlDTO.java
│   │   │               ├── entity
│   │   │               │   └── Url.java
│   │   │               ├── exceptions
│   │   │               │   ├── UrlHashGenException.java
│   │   │               │   └── UrlNotFoundException.java
│   │   │               ├── repository
│   │   │               │   └── UrlRepository.java
│   │   │               └── service
│   │   │               │    └── UrlService.java
│	│	│				└──ShortenUrlApplication.java
│   │   └── resources
│   │   │   ├── static
│	│	│	│ 	└── index.html
│	│	│	└── templates
│   │   │       ├── application.properties
│   │   │       └── data.sql
│   └── test
│       └── java
│           └── com
│               └── neueda
│                   └── shortenurl
│                       ├── controller
│                       │   └── UrlController.java
│                       └── service
│                           └── UrlServiceTest.java
│						
├── pom.xml
└── README.md

#### Tech stack
* [Spring Boot](http://spring.io/projects/spring-boot) for creating the RESTful Web Services
* [MockMVC](https://spring.io/guides/gs/testing-web/) for testing the Web Layer
* [Mockito](https://site.mockito.org/) for testing the Services Layer
* [H2](https://www.postgresql.org/) as in memory database
* [Maven](https://maven.apache.org/) for managing the project's build

## Usage

Request Method | URI | Body (JSON) | Description |  
:---: | :--- | :---: | :--- |
GET | http://localhost/url/{alias} | - | Find original url and redirect | 
GET | http://localhost/url/{alias}/original | - | Find and return original url | 
POST | http://localhost/url/ | { "original": "[http...]" } | create url and return its alias in response headers | 

## License

MIT © [cssaranya](https://github.com/cssaranya).
