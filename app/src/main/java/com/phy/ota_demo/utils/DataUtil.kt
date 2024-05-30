package com.phy.ota_demo.utils

import java.lang.StringBuilder
import java.util.*

object DataUtils {
    /**
     * Desc: 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
     */
    fun isOdd(num: Int): Int {
        return num and 1
    }

    /**
     * Desc: Hex字符串转int
     */
    fun HexToInt(inHex: String): Int {
        return inHex.toInt(16)
    }

    fun ByteArray.toHexString(hasSpace: Boolean = true) = this.joinToString("") {
        (it.toInt() and 0xFF).toString(16).padStart(2, '0').toUpperCase() + if (hasSpace) " " else ""
    }

    /**
     * Desc: int转Hex字符串
     */
    fun IntToHex(intHex: Int): String {
        return Integer.toHexString(intHex)
    }

    /**
     * Desc: Hex字符串转byte
     */
    fun HexToByte(inHex: String): Byte {
        return inHex.toInt(16).toByte()
    }

    /**
     * Desc: 1字节转2个Hex字符
     */
    fun Byte2Hex(inByte: Byte): String {
        return String.format("%02x", *arrayOf<Any>(inByte)).uppercase(Locale.getDefault())
    }

    /**
     * Desc: 字节数组转转hex字符串
     */
    fun ByteArrToHex(inBytArr: ByteArray): String {
        val strBuilder = StringBuilder()
        for (valueOf in inBytArr) {
            strBuilder.append(Byte2Hex(java.lang.Byte.valueOf(valueOf)))
            strBuilder.append(" ")
        }
        return strBuilder.toString()
    }

    /**
     * Desc: 字节数组转转hex字符串，可选长度
     */
    fun ByteArrToHex(inBytArr: ByteArray, offset: Int, byteCount: Int): String {
        val strBuilder = StringBuilder()
        for (i in offset until byteCount) {
            strBuilder.append(Byte2Hex(java.lang.Byte.valueOf(inBytArr[i])))
        }
        return strBuilder.toString()
    }

    /**
     * Desc: 转hex字符串转字节数组
     */
    fun HexToByteArr(inHex: String): ByteArray {
        var inHex = inHex
        val result: ByteArray
        var hexlen = inHex.length
        if (isOdd(hexlen) == 1) {
            hexlen++
            result = ByteArray(hexlen / 2)
            inHex = "0$inHex"
        } else {
            result = ByteArray(hexlen / 2)
        }
        var j = 0
        var i = 0
        while (i < hexlen) {
            result[j] = HexToByte(inHex.substring(i, i + 2))
            j++
            i += 2
        }
        return result
    }

    /**
     * Desc: 按照指定长度切割字符串
     * @param inputString 需要切割的源字符串
     * @param length      指定的长度
     * @return
     */
    fun getDivLines(inputString: String, length: Int): List<String> {
        val divList: MutableList<String> = ArrayList()
        val remainder = inputString.length % length
        // 一共要分割成几段
        val number = Math.floor((inputString.length / length).toDouble()).toInt()
        for (index in 0 until number) {
            val childStr = inputString.substring(index * length, (index + 1) * length)
            divList.add(childStr)
        }
        if (remainder > 0) {
            val cStr = inputString.substring(number * length, inputString.length)
            divList.add(cStr)
        }
        return divList
    }

    /**
     * Desc: 计算长度，两个字节长度
     * @param val value
     * @return 结果
     */
    fun twoByte(`val`: String): String {
        var `val` = `val`
        if (`val`.length > 4) {
            `val` = `val`.substring(0, 4)
        } else {
            val l = 4 - `val`.length
            for (i in 0 until l) {
                `val` = "0$`val`"
            }
        }
        return `val`
    }

    /**
     * Desc: 校验和
     * @param cmd 指令
     * @return 结果
     */
    fun sum(cmd: String): String {
        var cmd = cmd
        val cmdList = getDivLines(cmd, 2)
        var sumInt = 0
        for (c in cmdList) {
            sumInt += HexToInt(c)
        }
        var sum = IntToHex(sumInt)
        sum = twoByte(sum)
        cmd += sum
        return cmd.uppercase(Locale.getDefault())
    }

    /**
     * Desc: Ascii字符转对应字符
     */
    fun stringTransformAscii(value: String): String {
        val sbu = StringBuffer()
        val chars = value.toCharArray()
        for (i in chars.indices) {
            if (i != chars.size - 1) {
                sbu.append(chars[i].code).append(",")
            } else {
                sbu.append(chars[i].code)
            }
        }
        return sbu.toString()
    }

    /**
     * Desc: 字符转对应Ascii字符
     */
    fun asciiTransformString(value: String): String {
        val sbu = StringBuffer()
        val chars = value.split(",").toTypedArray()
        for (i in chars.indices) {
            sbu.append(chars[i].toInt().toChar())
        }
        return sbu.toString()
    }

    /**
     * Desc: 字符串每隔两个字符添加 ':'
     */
    fun CharSplit(sAscii: String): String {
        var sAscii = sAscii
        val regex = "(.{2})"
        sAscii = sAscii.replace(regex.toRegex(), "$1:")
        sAscii = sAscii.substring(0, sAscii.length - 1)
        return sAscii
    }

    /**
     * Desc: 16进制Ascii码字符串转10进制整形字符串
     */
    fun AsciiToString(Ascii: String): String {
        val sAscii = CharSplit(Ascii)
        val sSplit = sAscii.split(":").toTypedArray()
        val stringBuffer = StringBuffer()
        for (i in sSplit.indices) {
            val num = HexToInt(sSplit[i])
            stringBuffer.append(asciiTransformString(Integer.toString(num)))
        }
        return stringBuffer.toString()
    }
}