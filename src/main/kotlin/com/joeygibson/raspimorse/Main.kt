package com.joeygibson.raspimorse

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

import com.diozero.Button
import com.diozero.LED
import com.diozero.api.GpioPullUpDown
import com.diozero.util.SleepUtil
import com.joeygibson.raspimorse.decoder.DefaultMorseCodeDecoder
import com.joeygibson.raspimorse.reader.TelegraphKeyWithLEDReader
import com.joeygibson.raspimorse.util.Config
import com.joeygibson.raspimorse.util.from
import com.natpryce.hamkrest.should.describedAs
import joptsimple.OptionParser
import mu.KotlinLogging
import kotlin.system.exitProcess

val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    val optParser = OptionParser()

    val keyOpt = optParser.accepts("key").withRequiredArg().ofType(Int::class.java)
                .describedAs("The pin number the telegraph key connects to")

    val ledOpt = optParser.accepts("led").withRequiredArg().ofType(Int::class.java)
                .describedAs("The pin number of the LED")

    optParser.acceptsAll(listOf("properties", "props", "p")).withRequiredArg()
                .describedAs("properties file with pin assignments")

    optParser.acceptsAll(listOf("help", "h", "?")).describedAs("show help screen")

    val options = optParser.parse(*args)

    if (options.has("help")) {
        optParser.printHelpOn(System.out)
        exitProcess(1)
    }

    val config = if (options.has("properties")) {
        Config.from(options.valueOf("properties").toString())
    } else {
        if (!options.has("key") || !options.has("led")) {
            System.err.println("You must specify the pins to use")
            optParser.printHelpOn(System.out)
            exitProcess(2)
        }

        val keyPin = options.valueOf(keyOpt)
        val ledPin = options.valueOf(ledOpt)

        println("key: $keyPin")
        println("led: $ledPin")

        Config(keyPin = keyPin, ledPin = ledPin)
    }

    logger.info { "Running with $config" }

    val button = Button(config.keyPin, GpioPullUpDown.PULL_UP)
    val led = LED(config.ledPin)

    val keyReader = TelegraphKeyWithLEDReader(led = led)
    button.whenPressed { keyReader.pressed() }
    button.whenReleased { keyReader.released() }

//    val decoder = DefaultMorseCodeDecoder(keyReader.asSequence(), 100, 1000, 10)

//    for (char in decoder.decode()) {
//        println(char)
//    }

    SleepUtil.pause()
}