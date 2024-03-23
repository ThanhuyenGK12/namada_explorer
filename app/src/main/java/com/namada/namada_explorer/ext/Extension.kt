package com.namada.namada_explorer.ext

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

val String.formatDate: String
    get() {
        val locale = Locale.getDefault()
        for (
        format in listOf(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'", locale),
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", locale),
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale),
        )
        ) {
            try {
                val date = format.parse(this)
                if (date != null) {
                    return SimpleDateFormat(
                        "HH:mm, dd/MM/yyyy",
                        locale
                    ).format(date)
                }
            } catch (e: Exception) {
                continue
            }
        }
        return this
    }


val Number.formatWithCommas: String
    get() = DecimalFormat("#,###").format(this)

val String.center3Dot: String
    get() {
        return "${substring(0..<3)}...${substring((length - 3)..<length)}"
    }

val Long.formatNumber: String
    get() {
        val suffix = arrayOf("", "K", "M", "B", "T", "P", "E")
        var value = this.toDouble()
        var index = 0
        while (value >= 1000) {
            value /= 1000
            index++
        }
        return String.format("%.0f%s", value, suffix[index])
    }