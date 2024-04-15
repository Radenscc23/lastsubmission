package com.example.githubuser.adapters

import com.example.githubuser.api.ClickUser

interface OnItemClickCallback {
    fun onItemClicked(user: ClickUser)
}