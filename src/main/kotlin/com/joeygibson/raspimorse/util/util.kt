package com.joeygibson.raspimorse.util

import java.io.FileInputStream
import java.util.*

/*
 * MIT License
 *
 * Copyright (c) 2017 Joey Gibson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

data class Config(val keyPin: Int = 17, val ledPin: Int = 22) {
    companion object {}
}

fun Config.Companion.from(fileName: String): Config {
    val props = loadProperties(fileName)

    val keyPin = props["key"].let { (it as? Int)?.toInt() ?: 0 }
    val ledPin = props["led"].let { (it as? Int)?.toInt() ?: 0 }

    return Config(keyPin = keyPin, ledPin = ledPin)
}

fun loadProperties(fileName: String) = Properties().apply {
    FileInputStream(fileName).use { fis ->
        load(fis)
    }
}
