package com.joeygibson.raspimorse.reader

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

import com.diozero.LED
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isWithin
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import kotlin.test.assertTrue

class TelegraphKeyReaderTest {
    @Test
    fun testBasicRead() {
        val duration = 1000L

        val led: LED = mock()
        var turnOns = 0
        var turnOffs = 0

        whenever(led.on()).then { turnOns++ }
        whenever(led.off()).then { turnOffs++ }

        val reader = TelegraphKeyWithLEDReader(led)
        reader.pressed()
        Thread.sleep(duration)
        reader.released()

        assertThat(turnOns, equalTo(1))
        assertThat(turnOffs, equalTo(1))

        assertTrue(reader.hasDataReady())

        val inputs = reader.asSequence()
        val input = inputs.first()

        assertThat(input.inputType, equalTo(InputType.KEY_PRESS))
        assertThat(input.duration,
                isWithin((duration * 0.9).toLong()..(duration * 1.1).toLong()))
    }

    @Test
    fun testMultipleRead() {
        val dot = 100L
        val dash = 1000L

        val led: LED = mock()
        var turnOns = 0
        var turnOffs = 0

        whenever(led.on()).then { turnOns++ }
        whenever(led.off()).then { turnOffs++ }

        val reader = TelegraphKeyWithLEDReader(led)

        val durations = listOf(dot, dot, dot, dash, dash, dash, dot, dot, dot)

        durations.forEach { duration ->
            reader.pressed()
            Thread.sleep(duration)
            reader.released()
        }

        assertThat(turnOns, equalTo(durations.size))
        assertThat(turnOffs, equalTo(durations.size))

        assertTrue(reader.hasDataReady())

        val inputs = reader.asSequence()
        val inputsList = inputs.take(durations.size).toList()

        assertThat(inputsList.size, equalTo(durations.size))

        inputsList.forEachIndexed { index, input ->
            val duration = durations[index]

            assertThat(input.inputType, equalTo(InputType.KEY_PRESS))
            assertThat(input.duration, isWithin(genRange(duration, 10)))
        }
    }

    fun genRange(duration: Long, tolerance: Int): ClosedRange<Long> {
        val lowerBound = (duration * ((100 - tolerance) / 100.0)).toLong()
        val upperBound = (duration * ((100 + tolerance) / 100.0)).toLong()

        return lowerBound..upperBound
    }
}