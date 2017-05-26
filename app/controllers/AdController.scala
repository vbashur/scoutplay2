package controllers

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import repositories.AdRepositoryImpl
import scala.concurrent.ExecutionContext.Implicits.global

class AdController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller
  with MongoController with ReactiveMongoComponents {

  def adRepository = new AdRepositoryImpl(reactiveMongoApi)

  def index = Action.async { implicit request =>
    adRepository.find().map(adv => Ok(Json.toJson(adv)))
  }

  def get(id: String) = TODO;

  def add = TODO;

  def update(id: String) = TODO;

  def delete(id: String) = TODO;


}
