package com.codelabs.agrimate.ui.common.impl

import com.codelabs.agrimate.ui.components.AGSelectInputProvider

data class TypeOfPlatingSelectInputImpl(
    override val label: String,
    override val value: String,
    val unit: String
) :
    AGSelectInputProvider