package com.joeygibson.raspimorse.decoder

import com.joeygibson.raspimorse.decoder.DotOrDash.DASH
import com.joeygibson.raspimorse.decoder.DotOrDash.DOT

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

val morseCodeMap = mapOf(
        listOf(DOT, DASH) to 'a',
        listOf(DASH, DOT, DOT, DOT) to 'b',
        listOf(DASH, DOT, DASH, DOT) to 'c',
        listOf(DASH, DOT, DOT) to 'd',
        listOf(DOT) to 'e',
        listOf(DOT, DOT, DASH, DOT) to 'f',
        listOf(DASH, DASH, DOT) to 'g',
        listOf(DOT, DOT, DOT, DOT) to 'h',
        listOf(DOT, DOT) to 'i',
        listOf(DOT, DASH, DASH, DASH) to 'j',
        listOf(DASH, DOT, DASH) to 'k',
        listOf(DOT, DASH, DOT, DOT) to 'l',
        listOf(DASH, DASH) to 'm',
        listOf(DASH, DOT) to 'n',
        listOf(DASH, DASH, DASH) to 'o',
        listOf(DOT, DASH, DASH, DOT) to 'p',
        listOf(DASH, DASH, DOT, DASH) to 'q',
        listOf(DOT, DASH, DOT) to 'r',
        listOf(DOT, DOT, DOT) to 's',
        listOf(DASH) to 't',
        listOf(DOT, DOT, DASH) to 'u',
        listOf(DOT, DOT, DOT, DASH) to 'v',
        listOf(DOT, DASH, DASH) to 'w',
        listOf(DASH, DOT, DOT, DASH) to 'x',
        listOf(DASH, DOT, DASH, DASH) to 'y',
        listOf(DASH, DASH, DOT, DOT) to 'z')
