package com.gnuoynawh.prj.part5.shopping.data.response

data class ProductsResponse(
    val items: List<ProductResponse>,
    val count: Int
)