package com.components.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

interface State
interface Action
interface Effect
interface Result

@OptIn(FlowPreview::class)
abstract class ViewModel<A : Action, R : Result, S : State, E : Effect>(initialState: S) :
    ViewModel() {
    private val state = MutableStateFlow(initialState)
    private val sideEffect = MutableSharedFlow<E>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val actions = MutableSharedFlow<A>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            actions
                .flatMapMerge { it.process() }
                .distinctUntilChanged()
                .onEach {
                    sideEffect.tryEmit(it.effect())
                }
                .scan(state.value) { oldState, result ->
                    oldState.reduce(result)
                }
                .collect(state)
        }
    }

    fun observeState(): StateFlow<S> = state
    fun observeSideEffect(): Flow<E> = sideEffect

    fun dispatch(action: A) {
        viewModelScope
            .launch {
                actions.emit(action)
            }
    }

    protected abstract suspend fun A.process(): Flow<R>

    protected abstract operator fun S.plus(result: R): S
    protected abstract fun R.effect(): E

    protected fun S.reduce(result: R): S {
        return this + result
    }
}

public fun <T> Flow<T>.onEmptyEmitFrom(
    flow: suspend () -> Flow<T>
) = onEmpty { emitAll(flow()) }
