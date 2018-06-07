#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */


void MCU_init(void); /* Device initialization function declaration */

void main(void) {
 DDRA = 0b0000101;
 PORTA = 0b0000101; /* call Device Initialization */
  /* put your own code here */
  




  for(;;) {
    /* _FEED_COP(); by default, COP is disabled with device init. When enabling, also reset the watchdog. */
  } /* loop forever */
  /* please make sure that you never leave main */
}
