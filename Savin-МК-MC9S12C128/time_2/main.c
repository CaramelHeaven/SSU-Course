#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */

unsigned int ch = 0;



void main(void) 
{
  DDRB = 0b11110000;
  PUCR |= 0b00000010;

//  TCTL4 = 0b00000001;
//  TELG1 = 0b11111111;
//  TELG2 = 0b10000000;
//  TIE   = 0b00000001;
  TSCR2 = 0b10000010;
  TSCR1 = 0b10000000;
//  CRGINT = 0b10000000;
  
	EnableInterrupts;

  

  for(;;) 
  {
  } 

}
  interrupt 16 void TOF() 
{
  TFLG2 = 0b10000000;
  if(ch == 0) 
  {
    PORTB = 0b11111111;
    ch =  1;
  } 
  else 
  {   
    PORTB = 0b00001111;
    ch = 0;
  }
}
