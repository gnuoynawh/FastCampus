package com.gnuoynawh.prj.part5.shopping.data.response

import com.gnuoynawh.prj.part5.shopping.data.entity.product.ProductEntity
import java.util.Date

data class ProductResponse(
    val id: String,
    val createdAt: Long,
    val productName: String,
    val productPrice: String,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
) {

    fun toEntity(): ProductEntity =
        ProductEntity(
            id = id.toLong(),
            createdAt = Date(createdAt),
            productName = productName,
            productPrice = productPrice.toDouble().toInt(),
            productImage = productImage,
            productType = productType,
            productIntroductionImage = productIntroductionImage
        )

}