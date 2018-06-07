#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */



void main(void) {
  /* put your own code here */
  DDRB  = 0b1000101;
  


	EnableInterrupts;


  for(;;) {
    PORTB = 0b1000101;
    _FEED_COP(); /* feeds the dog */
  } /* loop forever */
  /* please make sure that you never leave main */
}
