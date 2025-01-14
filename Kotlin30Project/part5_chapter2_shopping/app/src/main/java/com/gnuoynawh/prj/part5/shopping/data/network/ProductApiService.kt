package com.gnuoynawh.prj.part5.shopping.data.network

import com.gnuoynawh.prj.part5.shopping.data.response.ProductResponse
import com.gnuoynawh.prj.part5.shopping.data.response.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(): Response<ProductsResponse>

    @GET("products/{productId}")
    suspend fun getProduct(@Path("productId") productId: Long): Response<ProductResponse>

}