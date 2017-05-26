package model


sealed abstract class FuelType
case object Gasoline extends FuelType
case object Diesel extends FuelType
case object LPG extends FuelType
case object Hybrid extends FuelType
case object Electro extends FuelType
