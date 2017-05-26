package controllers

import javax.inject.Inject

import model.{Advertisement, FuelType}
import play.api.libs.json.Json
import play.api.mvc.{Action, BodyParsers, Controller}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.{BSONDocument, BSONLong, BSONObjectID, BSONString}
import repositories.AdRepositoryImpl

import scala.concurrent.ExecutionContext.Implicits.global

class AdController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller
  with MongoController with ReactiveMongoComponents {

  def adRepository = new AdRepositoryImpl(reactiveMongoApi)

  def index = Action.async { implicit request =>
    adRepository.find().map(adv => Ok(Json.toJson(adv)))
  }

  def get(id: String) = Action.async { implicit request =>
    adRepository.get(BSONDocument(Advertisement.field_Id -> BSONString(id))).map(adv => Ok(Json.toJson(adv)))
  }

  def add = Action.async(BodyParsers.parse.json) { implicit request =>
    val title = (request.body \ Advertisement.field_Title).as[String]
    val fuelType = (request.body \  Advertisement.field_Fuel).as[String]
    val mileage = (request.body \  Advertisement.field_Mile).as[Long]
    val isNew = (request.body \  Advertisement.field_New).as[Boolean]
    val price = (request.body \  Advertisement.field_Price).as[Long]
    val registration = (request.body \  Advertisement.field_Reg).as[String]
    adRepository.save(BSONDocument(
      Advertisement.field_Title -> title,
      Advertisement.field_Fuel -> fuelType,
      Advertisement.field_Mile -> mileage,
      Advertisement.field_New -> isNew,
      Advertisement.field_Price -> price,
      Advertisement.field_Reg -> registration
    )).map(result => Created)
  }

  def update(id: String) = Action.async(BodyParsers.parse.json) { implicit request =>
    val title = (request.body \ Advertisement.field_Title).as[String]
    val fuelType = (request.body \  Advertisement.field_Fuel).as[String]
    val mileage = (request.body \  Advertisement.field_Mile).as[Long]
    val isNew = (request.body \  Advertisement.field_New).as[Boolean]
    val price = (request.body \  Advertisement.field_Price).as[Long]
    val registration = (request.body \  Advertisement.field_Reg).as[String]
    adRepository.update(BSONDocument(Advertisement.field_Id -> BSONString(id)),
      BSONDocument("$set" -> BSONDocument(
        Advertisement.field_Title -> title,
        Advertisement.field_Fuel -> fuelType,
        Advertisement.field_Mile -> mileage,
        Advertisement.field_New -> isNew,
        Advertisement.field_Price -> price,
        Advertisement.field_Reg -> registration
      )))
      .map(result => Accepted)
  }

  def delete(id: String) = adRepository.remove(BSONDocument(Advertisement.field_Id -> BSONString(id)))
    .map(result => Accepted);

}
