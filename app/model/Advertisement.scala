package model

import org.joda.time.DateTime
import play.api.libs.json._

abstract class Advertisement(id: Long,
                             title: String,
                             fuel: FuelType,
                             price: Long,
                             isNew: Boolean,
                             mileage: Long,
                             firstRegistration: Option[DateTime]) {
  def toJsonObj = JsObject(Seq(
    "id" -> JsNumber(id),
    "title" -> JsString(title),
    "fuel" -> JsString(fuel.toString),
    "price" -> JsNumber(price),
    "new" -> JsBoolean(isNew),
    "mileage" -> JsNumber(mileage),
    "firstRegistration" -> JsString(firstRegistration  match {
      case Some(date) =>  date.year().get() + "/" + date.monthOfYear().get()
      case None => "Unknown"
    })
  ))
}

case class UsedCarAd(id: Long, title: String, fuel: FuelType, price: Long, mileage: Long, firstRegistration: Option[DateTime])
  extends Advertisement(id, title, fuel, price, isNew = false, mileage, firstRegistration)

case class NewCarAd(id: Long, title: String, fuel: FuelType, price: Long)
  extends Advertisement(id, title, fuel, price, isNew = true, mileage = 0, firstRegistration = None)



