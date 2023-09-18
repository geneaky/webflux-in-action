package com.example.springwebflux

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class Router {

    //router -> client에게 전달받은 요청을 해석후 핸들러에게 전달
    @Bean
    fun helloRouter(handler: HelloHandler) : RouterFunction<ServerResponse> =
        route()
            .GET("/", handler::sayHello)
//            .POST("/", handler::sayHello)
//            .DELETE("/", handler::sayHello)
            .build()

    @Bean
    fun userRouter(handler: UserHandler) : RouterFunction<ServerResponse> =
        router {
            "/users".nest {
                GET("/{id}", handler::getUser)
                GET("/", handler::getAll)
            }
        }
}