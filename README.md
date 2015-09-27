### Problem definition
Create a tiny RESTful web service with the following business requirements:

- Application must expose REST API endpoints for the following functionality:
    - ask question
    - list all accepted questions
    - list all accepted questions by country code
- User should be able to ask question publicly by providing question text. 
- Service must perform question validation according to the following rules and reject question if:
    -  Question contains blacklisted words listed in a dictionary
    - **N** questions / second are asked from a single country (essentially we want to limit number of questions coming from a country in a given timeframe)
- Service must perform origin country resolution using [the following web service](http://www.telize.com) and store country code together with the question. Because networking is unreliable and services tend to fail, let's agree on default country code - "lv".


### Requirements
 - jdk8
 - maven3
 - mongodb

### Run

    $ mvn clean spring-boot:run -Dserver.port=8080
    

### Settings

Properties are in src/main/resources/application.yml

Mongo db access:

    spring:
      data:
        mongodb:
          database: "ask"
          host: "localhost"
          port: 27017

Http server listening options:

    server:
      port: 8080
      address: "0.0.0.0"

Geo IP service settings:

    geoip:
      telize:
        url: "http://www.telize.com/geoip/{ip}"
      defaultCountry: "lv"

Country limit settings and blacklist of words:

    question:
      limit:
        byCountry:
          count: 10
          seconds: 1
        blacklist: [fuck, shit, badword1, badword2, badword3]
