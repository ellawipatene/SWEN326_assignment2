#include <avr/io.h>
#include <util/delay.h>
#include "tinyboy.h"

#define SCREEN_WIDTH_D8 (SCREEN_WIDTH >> 3)
#define SCREEN_MID_X (SCREEN_WIDTH >> 4)
#define SCREEN_MID_Y (SCREEN_HEIGHT >> 1)

void display_ul() {
  for(int y=0;y<SCREEN_HEIGHT;++y) {
    for(int x=0;x<(SCREEN_WIDTH>>3);++x) {
      if(x < SCREEN_MID_X && y < SCREEN_MID_Y) {
	display_write(0xFF);
      } else {
	display_write(0x0);
      }
    }
  }
}


void display_ll() {
  for(int y=0;y<SCREEN_HEIGHT;++y) {
    for(int x=0;x<(SCREEN_WIDTH>>3);++x) {
      if(x < SCREEN_MID_X && y >= SCREEN_MID_Y) {
	display_write(0xFF);
      } else {
	display_write(0x0);
      }
    }
  }
}

void display_ur() {
  for(int y=0;y<SCREEN_HEIGHT;++y) {
    for(int x=0;x<(SCREEN_WIDTH>>3);++x) {
      if(x >= SCREEN_MID_X && y < SCREEN_MID_Y) {
	display_write(0xFF);
      } else {
	display_write(0x0);
      }
    }
  }
}

void display_lr() {
  for(int y=0;y<SCREEN_HEIGHT;++y) {
    for(int x=0;x<(SCREEN_WIDTH>>3);++x) {
      if(x >= SCREEN_MID_X && y >= SCREEN_MID_Y) {
	display_write(0xFF);
      } else {
	display_write(0x0);
      }
    }
  }
}


void display_clear() {
  for(int y=0;y<SCREEN_HEIGHT;++y) {
    for(int x=0;x<(SCREEN_WIDTH>>3);++x) {
      display_write(0x0);
    }
  }
}

int main (void){
  DDRB = 0b00001111;
  PORTB = 0b00000000;
  //
  display_ll();
  while((read_buttons() & BUTTON_UP) == 0) {
    // Keep looping :)
  }
  display_ul();
  while((read_buttons() & BUTTON_RIGHT) == 0) {
    // Keep looping :)
  }
  display_ur();
  while((read_buttons() & BUTTON_DOWN) == 0) {
    // Keep looping :)
  }
  display_lr();
  while((read_buttons() & BUTTON_LEFT) == 0) {
    // Keep looping :)
  }
  display_ll();
  while((read_buttons() & BUTTON_UP) == 0) {
    // Keep looping :)
  }  
  display_ul();
  while((read_buttons() & BUTTON_RIGHT) == 0) {
    // Keep looping :)
  }
  display_ur();  
  while((read_buttons() & BUTTON_LEFT) == 0) {
    // Keep looping :)
  }
  display_ul();
  while((read_buttons() & BUTTON_DOWN) == 0) {
    // Keep looping :)
  }
  display_ll();  
  // Done
}
