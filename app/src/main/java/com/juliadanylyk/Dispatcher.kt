package com.juliadanylyk

import kotlin.coroutines.experimental.CoroutineContext

interface Dispatcher {
    val ui: CoroutineContext
    val background: CoroutineContext
}