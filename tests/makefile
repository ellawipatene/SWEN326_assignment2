# Makefile for programming the ATtiny85 via micronucleous USB
# bootloader
DEVICE      = attiny85
CLOCK      = 16000000UL
PROGRAMMER = -c usbtiny 
AVRDUDE = avrdude $(PROGRAMMER) -p $(DEVICE)
COMPILE = avr-gcc -Wall -Os -std=c11 -DF_CPU=$(CLOCK) -mmcu=$(DEVICE)

TARGETS = blocks_1.hex \
	blocks_2.hex \
	blocks_3.hex \
	blocks_4.hex \
	blocks_5.hex \
	blocks_6.hex \
	blocks_7.hex \
	blocker_1.hex \
	blocker_2.hex \
	numbers_1.hex \
	tetris.hex \
	snake.hex 

all: $(TARGETS)

%.s : %.c
	$(COMPILE) -S -o $@ $<

%.o : %.c
	$(COMPILE) -c -o $@ $<

%.elf: %.o
	$(COMPILE) -o $@ $< 

%.hex: %.elf
	avr-objcopy -j .text -j .data -O ihex $< $@
	avr-size --format=avr --mcu=$(DEVICE) $<

clean:
	rm -f *.hex *.elf *.o
