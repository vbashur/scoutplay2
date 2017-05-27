# Cars advertisement service


The service allows users to place new car adverts, view and modify existing car adverts

### Model

Car advert has the following fields:
* **_id** : automatically assigned by the service;
* **title** (_required_): **string**, e.g. _"Audi A4 Avant"_;
* **fuel** (_required_): could have one of the following values: **Diesel,Gasoline,LPG,Electro,Hybrid**
* **price** (_required_): **integer**;
* **new** (_required_): **boolean**, indicates if car is new or used;
* **mileage** (_only for used cars_): **integer**;
* **first registration** (_only for used cars_): date in format YYYY/MM

Example:
```json
{
   "_id":{"$oid":"592927e3ba0d225dbfa15942"},
   "title":"Audi A4 Avant",
   "fuel":"LPG",
   "price":30000,
   "new":true,
   "mileage":0,
   "firstRegistration":"Unknown"
}

```

### HTTP routes

Read all available ads
```
GET        /api/ads
```

Read the ad with the given id
```
GET        /api/ads/:id
```
Publish new ad - correct model represented in JSON format (see above) must be provided as a payload
```
POST       /api/ads
```
Update existing ad with the given id - - correct model represented in JSON format (see above) must be provided as a payload
```
PATCH      /api/ads/:id
```

### Launching application

Cars advertisement service is written in Scala and uses Play! framework
within Mongo database, sbt is used as an automation buid tool.

Go to the root of the source code directory and type ```sbt run``` in command line.

By default application is available by link http://localhost:9000

### Links

[Scala](https://www.scala-lang.org/)

[SBT](http://www.scala-sbt.org/)

[Play!](https://playframework.com/)

[MongoDB](https://www.mongodb.com/)






