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
import mu.KotlinLogging
import java.lang.System.currentTimeMillis
import java.util.concurrent.ArrayBlockingQueue

/**
 * Processor for input events, that turns on an LED when the button
 * is pressed. It turns it off again when the release occurs.
 */
class TelegraphKeyWithLEDReader(val led: LED) : TelegraphKeyReader {
    private val logger = KotlinLogging.logger {}
    private var pressedAt: Long = 0
    private val queue = ArrayBlockingQueue<Input>(1000)

    override fun pressed() {
        pressedAt = currentTimeMillis()
        led.on()

        logger.debug { "Pressed at: $pressedAt" }
    }

    override fun released() {
        led.off()

        val input = Input(InputType.KEY_PRESS, currentTimeMillis() - pressedAt)

        logger.debug { "Input: $input" }

        queue.add(input)
    }

    override fun asSequence() = generateSequence {
        while (queue.isEmpty()) {
            Thread.sleep(10)
        }

        queue.remove()
    }

    override fun hasDataReady() = queue.isNotEmpty()
}