package com.colagom.kmm_sample

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun interface Closeable {
    fun close()
}

class StoreWrapper<T : State, A : Action, U : Event>(
    private val store: Store<T, A, U>
) : Closeable by store {
    fun dispatch(action: A) {
        store.dispatch(action)
    }

    fun watchState(block: (T) -> Unit) {
        store.state
            .onEach(block)
            .launchIn(store.scope)
    }

    fun watchEvent(block: (U) -> Unit) {
        store.event
            .onEach(block)
            .launchIn(store.scope)
    }
}