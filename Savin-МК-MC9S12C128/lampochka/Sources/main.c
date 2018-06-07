#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */



void main(void) {
     
  /* put your own code here */
  DDRB  = 0b11111111;
  PORTB = 0b11111111;


	EnableInterrupts;


  for(;;) {
        
 
  } /* loop forever */
  /* please make sure that you never leave main */
}
