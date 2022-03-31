package ch.epfl.people.utils.functional

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.Hex
import kotlin.experimental.xor

object StringUtils {
    fun convertToEmail(hex: String): String? {
        try {
            val decoded: ByteArray =
                Hex.decodeHex(hex.removePrefix("/cdn-cgi/l/email-protection#").toCharArray())
            val firstByte = decoded[0]
            val newBytes = ByteArray(decoded.size - 1)
            for (i in decoded.indices) {
                val result = (decoded[i] xor firstByte) as Byte
                if (i == 0) {
                    continue
                }
                newBytes[i - 1] = result
            }
            return String(newBytes)
        } catch (e: DecoderException) {
            e.printStackTrace()
        }
        return null
    }

}