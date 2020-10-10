package com.lucasdev.pagingexam.data

data class Response(
    var count: Int,
    var previous: String,
    var next: String,
    var results: List<Result>
)