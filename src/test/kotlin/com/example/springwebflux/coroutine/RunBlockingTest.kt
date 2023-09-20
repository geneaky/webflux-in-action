package com.example.springwebflux.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class RunBlockingTest {


    @Test
    fun `run blocking`() {
        runBlocking {
            println("hello")
            println(Thread.currentThread().name)
        }

        println("world")
        println(Thread.currentThread().name)
    }

    @Test
    fun `launch test`() {

        runBlocking {
            launch {
                delay(500) // coroutine 라이브러리 전용 스레드를 차단하지 않고 일시정지 시킨다. 일시정지된 스레드는 코루틴내에서 다른 로직을 실행시킨다.
//                Thread.sleep(500) //blocking
                println("world")
            }

            println("hello")
        }
    }

    @Test
    fun `수행시간 측정`() {
        runBlocking {
            val job1: Job = launch {
                val elapsedTime = measureTimeMillis {
                    delay(150)
                }

                println("async task-1 $elapsedTime ms")
            }

            job1.cancel()

            val job2: Job = launch(start = CoroutineStart.LAZY) {
                val elapsedTime = measureTimeMillis {
                    delay(100)
                }

                println("async task-2 $elapsedTime ms")
            }

            println("start task-2")

            job2.start()
        }
    }

    fun sum(a: Int, b: Int) = a + b
    @Test
    fun `async test`() {
        runBlocking {

            val result1: Deferred<Int> = async {
                delay(100)
                sum(1, 3)
            }

            println("result1 : ${result1.await()}")

            val result2: Deferred<Int> = async {
                delay(100)
                sum(2, 5)
            }

            println("result2 : ${result2.await()}")
        }
    }

    fun printHello() = print("hello")
    suspend fun doSomething() = coroutineScope{ // runBlocking과 달리 현재 스레드가 블로킹되지 않고 동작한다

        launch {
            delay(200)
            println("world")
        }

        launch {
            printHello()
        }
    }

    @Test
    suspend fun `suspend test`() {
        //일시중단 - 재게
//        doSomething() //suspend 함수 직접 호출하면 컴파일 오류발생
        doSomething()
    }

    fun simple() : Flow<Int> = flow {
        println("Flow started")

        for(i in 0..5) {
            delay(100)
            emit(i) // publish
        }
    }

    @Test
    fun `flow test`() {
        runBlocking {
            val flow = simple()
            flow.collect { value -> println(value)} //subscribe
        }
    }
}