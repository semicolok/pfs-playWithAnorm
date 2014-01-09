package models

case class Product (
  id: Long,
  ean: Long,
  name: String,
  description: String
  )

case class Warehouse (id: Long, name: String)

case class StockItem (
  id: Long,
  productId: Long,
  warehouseId: Long,
  quantity: Long
  )

object Product {
  
}