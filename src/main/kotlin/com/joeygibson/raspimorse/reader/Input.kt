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

enum class InputType {
    KEY_PRESS,
    SILENCE
}

data class Input(val inputType: InputType, val duration: Long) {
    fun isKeyPress() = inputType == InputType.KEY_PRESS
    fun isSilence() = !isKeyPress()

    companion object {}
}

fun Input.Companion.silence(duration: Long) = Input(InputType.SILENCE, duration)
fun Input.Companion.keyPress(duration: Long) = Input(InputType.KEY_PRESS, duration)