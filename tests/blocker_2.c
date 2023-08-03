#include <avr/io.h>
#include <util/delay.h>
#include "tinyboy.h"

#define WIDTH (SCREEN_WIDTH >> 3)
#define HEIGHT (SCREEN_HEIGHT >> 3)

// =========================================================
// STATE
// =========================================================

#define NROCKS 4

int player_x;
int player_y;
int rock_x[4] = {1,2,6,4};
int rock_y[4] = {1,4,2,4};

// =========================================================
// IO Functions
// =========================================================

#define WHITE 0x00
#define GREY  0b10000001
#define BLACK 0xFF

int getRock(int x, int y) {
  for(int i=0;i!=NROCKS;++i) {
    if(rock_x[i] == x && rock_y[i] == y) {
      return i;
    }
  }
  return -1;
}

void refresh() {
  for(int i=0;i<HEIGHT;++i) {
    for(int k=0;k<8;++k) {
      for(int j=0;j<WIDTH;++j) {
	if(i == player_y && j == player_x) {
	  display_write(BLACK);
	} else if(getRock(j,i) >= 0) {
	  if(k == 0 || k == 7) {
	    display_write(BLACK);
	  } else {
	    display_write(GREY);
	  }
	} else {
	  display_write(WHITE);
	}
      }
    }
  }
}

int withinBounds(int x, int y) {
  return (x >= 0 && x < WIDTH) && (y >= 0 && y < HEIGHT);
}

void setup() {
  // set SCLK, MOSI, MISO, SS to be output
  DDRB = 0b00001111;
  PORTB = 0b00000000;
  //
  player_x = 2;
  player_y = 2;
}

int main() {
  // Setup stuff
  setup();
  // Run
  while(1) {
    int dx = 0;
    int dy = 0;
    // Read user input
    int buttons = read_buttons();    
    //
    switch(buttons) {
    case BUTTON_UP:
      dy = -1;
      break;
    case BUTTON_DOWN:
      dy = +1;
      break;
    case BUTTON_LEFT:
      dx = -1;
      break;
    case BUTTON_RIGHT:
      dx = +1;
      break;
    }
    //
    int nx = player_x + dx;
    int ny = player_y + dy;    
    // Attempt to make the move
    if(withinBounds(nx,ny)) {
      // Check whether pushing rock
      int r = getRock(nx,ny);
      //
      if(r >= 0) {
	// Am attempting to move this rock
	int rx = rock_x[r] + dx;
	int ry = rock_y[r] + dy;    	
	// Attemp to push rock
	if(withinBounds(rx,ry) && getRock(rx,ry) < 0) {
	  // All looks good.
	  player_x = nx;
	  player_y = ny;
	  rock_x[r] = rx;
	  rock_y[r] = ry;
	} else {
	  // cannot push rock
	}
      } else {
	// No rock being pushed
	player_x = nx;
	player_y = ny;
      }
      // Refresh Display
      refresh();    
    }
  }
}
