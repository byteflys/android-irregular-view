package com.android.library.irregularview.commons.kotlin

object Strings {

    fun String?.withEmpty() = withDefault("")

    fun String?.withDefault(defaultNullPlaceholder: String) = this ?: defaultNullPlaceholder
}