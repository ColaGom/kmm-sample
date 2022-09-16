package com.colagom.kmm_sample

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

data class FooState(
    val age: Int = 20,
    val name: String = "Geunho"
) : State

sealed interface FooAction : Action
sealed interface FooEvent : Event

val store = FooStore()

class FooStore : Store<FooState, FooAction, FooEvent>() {
    private val job = Job()
    override val scope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main + job)

    override fun close() {
        job.cancel()
    }

    private val _state = MutableStateFlow(FooState())
    override val state: StateFlow<FooState> = _state.asStateFlow()
    override val event: SharedFlow<FooEvent> = MutableSharedFlow()

    init {
        println("init store")
        scope.launch {
            while (true) {
                delay(1000)
                _state.update {
                    it.copy(age = it.age + 1)
                }
            }
        }
    }

    override fun dispatch(action: FooAction) {
    }
}
