#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */

#define delay(ms); for(i = 0; i < ms; i++) asm NOP;
unsigned int i;

void main(void) 
{
  DDRB = 0b11110000;
  
  PUCR |= 0b00000010;

  ATDCTL2 = 0b10000011;
  ATDCTL3 = 0b00011000;
  ATDCTL4 = 0b10000000;
  ATDCTL5 = 0b00100101;
  ATDDIEN = 0b00100000;
   
  delay(100)
	EnableInterrupts;

  
  for(;;) 
  {
  
  } 
}   

interrupt 22 void TOF() 
{
  PORTB = ATDDR0H;
  ATDCTL5 = 0b00100101;
}  
