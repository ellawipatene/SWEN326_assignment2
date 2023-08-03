#include <avr/io.h>
#include <util/delay.h>
#include "tinyboy.h"

#define CODE_X ((DISPLAY_WIDTH>>1)-2)
#define CODE_Y ((DISPLAY_HEIGHT>>1)-1)

// =======================================
// Sprites
// =======================================

uint8_t digit_sprites_top[][4] = {
 {
  0,0,0,0 // all off
 },				  
 { // zero
  0b0000,
  0b0100,
  0b1010,
  0b1010,
 },
 { // one
  0b0000,
  0b0100,
  0b1100,
  0b0100,
 },
 { // two
  0b0000,
  0b1100,
  0b0010,
  0b0100,
 },
 { // three
  0b0000,
  0b1100,
  0b0010,
  0b0110,
 },
 { // four
  0b0000,
  0b1000,
  0b1000,
  0b1010,
 },
 { // five
  0b0000,
  0b1110,
  0b1000,
  0b1110,
 },
 { // six
  0b0000,
  0b1110,
  0b1000,
  0b1110,
 },
 { // seven
  0b0000,
  0b1110,
  0b0010,
  0b0100,
 },
 { // eight
  0b0000,
  0b1110,
  0b1010,
  0b1110,
 },
 { // nine
  0b0000,
  0b1110,
  0b1010,
  0b1110,
 }
};

uint8_t digit_sprites_bottom[][4] = {
 {
  0,0,0,0 // all off
 },
 { // zero
  0b1010,
  0b0100,
  0b0000,
  0b0000,   
 },
 { // one
  0b0100,  
  0b1110,
  0b0000,
  0b0000,
 },
 { // two
  0b1000,  
  0b1110,
  0b0000,
  0b0000,
 },
 { // three
   0b0010,  
   0b1100,
   0b0000,
   0b0000,   
 },
 { // four
   0b1110,  
   0b0010,
   0b0000,
   0b0000,
 },
 { // five
   0b0010,  
   0b1110,
   0b0000,
   0b0000,   
 },
 { // six
  0b1010,  
  0b1110,
  0b0000,
  0b0000,
 },
 { // seven
  0b0100,  
  0b0100,
  0b0000,
  0b0000,
 },
 { // eight
  0b1010,  
  0b1110,
  0b0000,
  0b0000,
 },
 { // nine
  0b0010,  
  0b1110,
  0b0000,
  0b0000,
 }
};

void draw_code(int x, int y, int code) {
  int base = 10000;
  //
  for(int i=0;i!=5;++i) {
    int d = code / base;
    display_draw(x,y,d+1);
    display_draw(x,y+1,d+1);    
    code = code - (d * base);
    base = base / 10;
    x = x + 1;
  }
}

void draw_blank(int x, int y, int bbase) {
  int base = 10000;
  //
  for(int i=0;i!=5;++i) {
    if(bbase == base) {
      display_draw(x,y,0);
      display_draw(x,y+1,0);
    }
    base = base / 10;
    x = x + 1;
  }
}

void setup() {
  // set SCLK, MOSI, MISO, SS to be output
  DDRB = 0b00001111;
  PORTB = 0b00000000;
}

int value = 0;
int base = 1;

int get_digit(int code, int b) {
  int base = 1000;
  //
  for(int i=0;i!=4;++i) {
    int d = code / base;
    if(base == b) {
      return d;
    }
    code = code - (d * base);
    base = base / 10;
  }
  // Should be impossible to get here
  return 0;
}

void update_value() {
  int b = read_buttons();
  //
  if(b & BUTTON_DOWN) {
    if(get_digit(value,base) == 0) {
      value = value + (9 * base);
    } else {
      value = value - base;
    }
  } else if(b & BUTTON_UP) {
    if(get_digit(value,base) == 9) {
      value = value - (9 * base);
    } else {    
      value = value + base;
    }
  } else if(base > 1 && (b & BUTTON_RIGHT)) {
    base = base / 10;
  } else if(base < 1000 && (b && BUTTON_LEFT)) {
    base = base * 10;
  }
}

void code_loop(int code) {
  // draw the code
  draw_code(CODE_X,CODE_Y,code);
  //
  int count = 0;
  //
  while(value != code) {
    // read buttons and change value
    update_value();
    //
    if(count == 0) {
      // draw the code
      draw_code(CODE_X,CODE_Y,value);
    } else if(count == 2) {
      draw_blank(CODE_X,CODE_Y,base);
    }
    //
    display_refresh_partial(0,CODE_Y+1,digit_sprites_top);
    display_refresh_partial(CODE_Y+1,DISPLAY_HEIGHT,digit_sprites_bottom);    
    //
    count = (count + 1) % 4;
    //
  }
}

int main() {
  // Setup stuff
  setup();
  //
  code_loop(111);
}
