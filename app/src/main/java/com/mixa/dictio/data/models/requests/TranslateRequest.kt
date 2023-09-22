package com.mixa.dictio.data.models.requests

data class TranslateRequest (
    var q: String,
    var source: String,
    var target: String,
    var format: String,
    var api_key: String
)