#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */


void MCU_init(void); /* Device initialization function declaration */

void main(void) {
  MCU_init(); /* call Device Initialization */
  /* put your own code here */
  
  DDRB = 0b11111111;
  PORTB = 0b11111111;



  for(;;) {
    /* _FEED_COP(); by default, COP is disabled with device init. When enabling, also reset the watchdog. */
  } /* loop forever */
  /* please make sure that you never leave main */
}
