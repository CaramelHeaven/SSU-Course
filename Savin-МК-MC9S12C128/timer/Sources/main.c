#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */

unsigned int ch = 0;



void main(void) 
{
  DDRB = 0b11110000;
  PUCR |= 0b00000010;

  TSCR2 = 0b10000010;
  TSCR1 = 0b10000000;

  EnableInterrupts;

  

  for(;;) 
  {
  } 

}
#pragma CODE_SEG __NEAR_SEG NON_BANKED 
  interrupt 16 void TOF() 
{
  TFLG2 = 0b10000000;
  
  if(ch == 0) 
  {
    PORTB = 0b01111111;
    ch =  1;
  } 
  else if(ch == 1) 
  {   
    PORTB = 0b10111111;
    ch = 2;
  } 
  else if(ch == 2) 
  {   
    PORTB = 0b11011111;
    ch = 3;
  } 
  else if(ch == 3) 
  {   
    PORTB = 0b11101111;
    ch = 0;
  }
  //#pragma CODE_SEG _DEFAULT
}
