package com.codelabs.agrimate.ui.common.impl

import com.codelabs.agrimate.ui.components.AGSelectInputProvider

sealed class UnitState {
    object HA : UnitState()
    object M2 : UnitState()
}

data class UnitSelectInputImpl(
    override val label: String,
    override val value: String,
    val state: UnitState
) : AGSelectInputProvider