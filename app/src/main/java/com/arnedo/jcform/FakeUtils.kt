package com.arnedo.jcform

import kotlinx.coroutines.delay
import kotlin.random.Random

suspend fun simulateDelay() {
    delay(Random.nextLong(300, 1_500))
}

suspend fun simulateDelayLong() {
    delay(Random.nextLong(3_000, 5_000))
}

