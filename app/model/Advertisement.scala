package model

import org.joda.time.DateTime
import play.api.libs.json._

abstract class Advertisement(title: String,
                             fuel: FuelType,
                             price: Long,
                             isNew: Boolean,
                             mileage: Long,
                             firstRegistration: Option[DateTime]) {
  def toJsonObj = JsObject(Seq(
    Advertisement.field_Title -> JsString(title),
    Advertisement.field_Fuel -> JsString(fuel.toString),
    Advertisement.field_Price -> JsNumber(price),
    Advertisement.field_New -> JsBoolean(isNew),
    Advertisement.field_Mile -> JsNumber(mileage),
    Advertisement.field_Reg -> JsString(firstRegistration  match {
      case Some(date) =>  date.year().get() + "/" + date.monthOfYear().get()
      case None => "Unknown"
    })
  ))
}

case class UsedCarAd(title: String, fuel: FuelType, price: Long, mileage: Long, firstRegistration: Option[DateTime])
  extends Advertisement(title, fuel, price, isNew = false, mileage, firstRegistration)

case class NewCarAd(title: String, fuel: FuelType, price: Long)
  extends Advertisement(title, fuel, price, isNew = true, mileage = 0, firstRegistration = None)


object Advertisement {
  val field_Id = "_id"
  val field_Title = "title"
  val field_Fuel = "fuel"
  val field_Price = "price"
  val field_New = "new"
  val field_Mile = "mileage"
  val field_Reg = "firstRegistration"
}
