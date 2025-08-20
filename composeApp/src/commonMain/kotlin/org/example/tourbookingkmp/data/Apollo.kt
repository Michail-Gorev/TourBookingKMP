package org.example.tourbookingkmp.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.http.HttpMethod

val apolloClient = ApolloClient.Builder()
    .httpServerUrl("http://192.168.1.32:4000")
    .httpMethod(HttpMethod.Post)
    .build()