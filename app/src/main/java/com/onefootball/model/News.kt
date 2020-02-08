package com.onefootball.model

import com.google.gson.annotations.SerializedName

/**
 * @author Dmitry Tkachuk
 * Created on 07.02.2020
 */
data class News(

    @SerializedName("title")
    val title: String,

    @SerializedName("image_url")
    val imageURL: String,

    @SerializedName("resource_name")
    val resourceName: String,

    @SerializedName("resource_url")
    val resourceURL: String,

    @SerializedName("news_link")
    val newsLink: String
)