package com.android.library.irregularview.commons.kotlin

object StringEx {

    fun String?.withEmpty() = withDefault("")

    fun String?.withDefault(defaultNullPlaceholder: String) = this ?: defaultNullPlaceholder
}