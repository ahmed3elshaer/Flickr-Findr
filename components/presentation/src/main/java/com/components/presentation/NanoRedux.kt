package com.components.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

interface State
interface Action
interface Result

@OptIn(FlowPreview::class)
abstract class ViewModel<A : Action, R : Result, S : State>(initialState: S) :
    ViewModel() {
    private val state = MutableStateFlow(initialState)

    private val actions = MutableSharedFlow<A>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            actions
                .flatMapMerge { it.process() }
                .distinctUntilChanged()
                .scan(state.value) { oldState, result ->
                    oldState.reduce(result)
                }
                .collect(state)
        }
    }

    fun observeState(): StateFlow<S> = state

    fun dispatch(action: A) {
        viewModelScope
            .launch {
                actions.emit(action)
            }
    }

    protected abstract suspend fun A.process(): Flow<R>

    protected abstract fun S.reduce(result: R): S
}
