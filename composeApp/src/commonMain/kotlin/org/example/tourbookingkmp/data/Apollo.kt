package org.example.tourbookingkmp.data

import com.apollographql.apollo.ApolloClient

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://graphql-tours-server.onrender.com")
    .build()