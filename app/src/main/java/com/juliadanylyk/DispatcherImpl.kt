package com.juliadanylyk

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

object DispatcherImpl : Dispatcher {

    override val ui: CoroutineContext = UI

    override val background: CoroutineContext = CommonPool

}