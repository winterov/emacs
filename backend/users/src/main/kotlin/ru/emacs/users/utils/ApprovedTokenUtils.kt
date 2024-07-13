package ru.emacs.users.utils

import java.security.SecureRandom
import java.util.*

object ApprovedTokenUtils {

    fun generateApprovedToken(targetStringLength: Int): String {
        val leftLimit = 48 /* numeral '0'*/
        val rightLimit = 122 /* litter 'z' */
        val random: Random = SecureRandom()
        return random.ints(leftLimit, rightLimit + 1)
            .filter { i: Int -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97) }
            .limit(targetStringLength.toLong())
            .collect(
                { StringBuilder() },
                { obj: java.lang.StringBuilder, codePoint: Int -> obj.appendCodePoint(codePoint) },
                { obj: java.lang.StringBuilder, s: java.lang.StringBuilder? -> obj.append(s) })
            .toString()
    }
}
