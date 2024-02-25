package com.codelabs.agrimate.ui.common.impl

import com.codelabs.agrimate.ui.components.AGSelectInputProvider

data class RegionSelectInputImpl(
    override val label: String,
    override val value: String,
) : AGSelectInputProvider