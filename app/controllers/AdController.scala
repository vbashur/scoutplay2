package controllers

import javax.inject.Inject

import play.api.mvc.Controller
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import repositories.AdRepositoryImpl


class AdController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller
  with MongoController with ReactiveMongoComponents {

  def adRepository = new AdRepositoryImpl(reactiveMongoApi)

  def index = TODO

  def get(id: String) = TODO;

  def add = TODO;

  def update(id: String) = TODO;

  def delete(id: String) = TODO;


}
