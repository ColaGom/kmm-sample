package com.colagom.kmm_sample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface State
interface Action
interface Event

abstract class Store<STATE : State, ACTION : Action, EVENT : Event> : Closeable {
    abstract val scope: CoroutineScope
    abstract val state: StateFlow<STATE>
    abstract val event: SharedFlow<EVENT>
    abstract fun dispatch(action: ACTION)

    val currentState get() = state.value
}