package top.laoshuzi.ddns

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    println("hello")
    val job = GlobalScope.launch {

    }

    runBlocking {
        job.join()
    }
}