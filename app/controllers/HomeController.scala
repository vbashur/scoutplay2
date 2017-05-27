package controllers

import javax.inject._

import model._
import org.joda.time.DateTime
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.bson.BSONCountCommand.{Count, CountResult}
import reactivemongo.api.commands.bson.BSONCountCommandImplicits._
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, _}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller
  with MongoController with ReactiveMongoComponents {

  def adsCollection = reactiveMongoApi.db.collection[JSONCollection]("ads")

  def bsonCollection = reactiveMongoApi.db.collection[BSONCollection]("ads")


  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {

    Logger.info("Application startup...")

    val newAudiAvantA4 = NewCarAd("Audi A4 Avant", LPG, java.lang.Long.valueOf(30000))
    val usedBmwTouring5 = NewCarAd("BMW 530d Touring", Diesel, java.lang.Long.valueOf(49000))
    val usedVWPassatCC = UsedCarAd("VW Passat CC", Gasoline,
      java.lang.Long.valueOf(22500), java.lang.Long.valueOf(8900), Some(DateTime.now().minusYears(2)))

    val ads = List(
      newAudiAvantA4.toJsonObj,
      usedBmwTouring5.toJsonObj,
      usedVWPassatCC.toJsonObj
    )
    val query = BSONDocument("name" -> BSONDocument("$exists" -> true))
    val command = Count(query)
    val result: Future[CountResult] = bsonCollection.runCommand(command)
    result.map { res =>
      val numberOfDocs: Int = res.value
      if(numberOfDocs < 1) {
        adsCollection.bulkInsert(ads.toStream, true).foreach(i => Logger.info("Record added."))
      }
    }
    Ok("Ads database is ready.")
  }
}
