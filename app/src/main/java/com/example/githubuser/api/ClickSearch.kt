package com.example.githubuser.api
import com.google.gson.annotations.SerializedName

data class ClickSearch(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_result") val incompleteResult: Boolean, val items: ArrayList<ClickUser>?
)