package com.joeygibson.raspimorse.decoder

import com.joeygibson.raspimorse.decoder.DotOrDash.DASH
import com.joeygibson.raspimorse.decoder.DotOrDash.DOT
import com.joeygibson.raspimorse.reader.Input
import com.joeygibson.raspimorse.util.genRange
import com.joeygibson.raspimorse.util.isBetween
import java.util.concurrent.locks.ReentrantLock

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

class DefaultMorseCodeDecoder(val inputSequence: Sequence<Input>,
                              val dotDuration: Long, val dashDuration: Long,
                              val tolerance: Int) : MorseCodeDecoder {
    private val interLetterSilenceDuration = (dotDuration * 3).genRange(tolerance)
    private val interWordSilenceDuration = (dotDuration * 7).genRange(tolerance)

    private val workingList = mutableListOf<DotOrDash>()
    private val chars = mutableListOf<Char>()
    private val lock = ReentrantLock()

    override fun go() {
        for (input in inputSequence) {
            if (isInterLetterSilence(input) ||
                    isInterWordSilence(input)) {
                val letter = decodeWorkingList(workingList)

                if (letter != null) {
                    workingList.clear()

                    chars.add(letter)
                }
            } else {
                val dod = convertToDotOrDash(input.duration)
                workingList.add(dod)
            }
        }
    }

    override fun hasDecodedChars() = !chars.isEmpty()

    override fun decodedChars(): List<Char> {
        val copiedChars = synchronized(lock) {
            val slice = chars.slice(0..chars.size - 1)
            chars.clear()

            slice
        }

        return copiedChars
    }

    fun convertToDotOrDash(duration: Long) =
            if (duration.isBetween(dotDuration.genRange(tolerance))) {
                DOT
            } else {
                DASH
            }

    fun decodeWorkingList(list: List<DotOrDash>) = morseCodeMap[list]

    fun isInterLetterSilence(input: Input) = input.isSilence() &&
            input.duration.isBetween(interLetterSilenceDuration)

    fun isInterWordSilence(input: Input) = input.isSilence() &&
            input.duration.isBetween(interWordSilenceDuration)
}