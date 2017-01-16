package com.joeygibson.raspimorse.decoder

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

import com.joeygibson.raspimorse.reader.Input
import com.joeygibson.raspimorse.reader.keyPress
import com.joeygibson.raspimorse.reader.silence
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class DefaultMorseCodeDecoderTest {
    @Test
    fun testDecoding() {
        val dotDuration = 100L
        val dashDuration = dotDuration * 3
        val dot = Input.keyPress(dotDuration)
        val dash = Input.keyPress(dashDuration)
        val interLetterSilence = Input.silence(dashDuration)
        val interWordSilence = Input.silence(dotDuration * 7)
        val tolerance = 10

        val sos = listOf(
                dot, dot, dot, interLetterSilence,
                dash, dash, dash, interLetterSilence,
                dot, dot, dot, interWordSilence)

        val decoder = DefaultMorseCodeDecoder(sos.asSequence(), dotDuration,
                dashDuration, tolerance)

        decoder.go()

        while (!decoder.hasDecodedChars()) {
            Thread.sleep(10)
        }

        val chars = decoder.decodedChars()

        assertThat(chars, equalTo(listOf('s', 'o', 's')))
    }
}