# raspimorse
Morse Code interpreter for a Raspberry Pi.

## Purpose
This project started when a friend gave me a vintage telegraph key for Christmas. 
I wanted to do something with it, and I had a Raspberry Pi that I had not used
for anything yet, so it seemed like a natural fit. 

## Building
Run `mvn package` to build. That's it.

## Running
Run `java -jar raspimorse-<version>.jar` specifying a properties file, or individual 
values for the pins you have wired up.

### Using a Properties File 
To use a properties file, create a file in standard Java properties file format, like this
```
key=17
led=22
```

Then run `java -jar raspimorse-<version>.jar --properties=my.properties`

### Specifying the Pins
To specify the pins on the command line, you will need to provide the `--key`
and `--led` options, like this `java -jar raspimorse-<version>.jar --key 17 --led 22`.

All of the command line options take shorter forms. Run with `--help` to see all of them.



