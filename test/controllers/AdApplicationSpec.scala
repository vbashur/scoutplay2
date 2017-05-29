package controllers

import akka.stream.impl.LastOptionStage
import model._
import org.joda.time.DateTime
import org.junit.runner.RunWith
import org.specs2.execute.Results
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.JsArray
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.{DefaultWriteResult, LastError, UpdateWriteResult}
import reactivemongo.bson.BSONDocument
import repositories.AdRepositoryImpl

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

@RunWith(classOf[JUnitRunner])
class AdApplicationSpec extends Specification with Results with Mockito  {

  val mockMongoApi = mock[ReactiveMongoApi]
  val mockAdRepository = mock[AdRepositoryImpl]

  class TestAdController() extends AdController(mockMongoApi) {
    override def adRepository: AdRepositoryImpl = mockAdRepository
  }

  val car1Title = "BMW i3"
  val car2Title = "VW Golf"
  val id = "59293cb9ba0d225dbfa15cdc"

  val testNewAuto = NewCarAd(car1Title, Electro, java.lang.Long.valueOf(69000))
  val testUsedAuto = UsedCarAd(car2Title, Gasoline,
    java.lang.Long.valueOf(500), java.lang.Long.valueOf(800), Some(DateTime.now().minusYears(18)))

  val ads = List(
    testNewAuto.toJsonObj,
    testUsedAuto.toJsonObj
  )

  val testAdController = new TestAdController

  "Ads#index" should {
    "return all ads" in {
      mockAdRepository.find()(any[ExecutionContext]) returns Future(ads)
      val result: Future[Result] = testAdController.index().apply(FakeRequest())

      contentAsJson(result) must be equalTo JsArray(ads)
      there was one(mockAdRepository).find()(any[ExecutionContext])
    }
  }

  "Ads#add" should {
    "add new ad" in {
      val writeResult = new DefaultWriteResult(true, 0, Nil, None, None, None)
      mockAdRepository.save(any[BSONDocument])(any[ExecutionContext]) returns Future(writeResult)

      val request = FakeRequest().withBody(testNewAuto.toJsonObj)
      val result: Future[Result] = testAdController.add()(request)

      status(result) must be equalTo CREATED
      there was one(mockAdRepository).save(any[BSONDocument])(any[ExecutionContext])
    }
  }


  "Ads#update" should {
    "update ad" in {

      val writeResult = new UpdateWriteResult(true, 1, 1, Nil, Nil, None, None, None)
      mockAdRepository.update(any[BSONDocument], any[BSONDocument])(any[ExecutionContext]) returns Future(writeResult)

      val request = FakeRequest().withBody(testUsedAuto.toJsonObj)
      val result: Future[Result] = testAdController.update(id)(request)

      status(result) must be equalTo ACCEPTED
      there was one(mockAdRepository).update(any[BSONDocument], any[BSONDocument])(any[ExecutionContext])
    }
  }

  "Ads#read" should {
    "read ad" in {

      mockAdRepository.get(any[BSONDocument])(any[ExecutionContext]) returns Future(Some(testUsedAuto.toJsonObj))

      val request = FakeRequest()
      val result: Future[Result] = testAdController.get(id).apply(request)

      status(result) must be equalTo OK
      contentAsJson(result) must be equalTo testUsedAuto.toJsonObj
      there was one(mockAdRepository).get(any[BSONDocument])(any[ExecutionContext])
    }
  }

  "Ads#remove" should {
    "remove ad" in {

      val writeResult = new DefaultWriteResult(true, 0, Nil, None, None, None)
      mockAdRepository.remove(any[BSONDocument])(any[ExecutionContext]) returns Future(writeResult)

      val request = FakeRequest()
      val result: Future[Result] = testAdController.delete(id).apply(request)

      status(result) must be equalTo ACCEPTED
      there was one(mockAdRepository).remove(any[BSONDocument])(any[ExecutionContext])
    }
  }

}
