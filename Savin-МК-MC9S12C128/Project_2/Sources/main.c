#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */

#define delay(us); for(delay=us/2;delay!=0;delay--) asm NOP;
//Макроопределение кода создания программной
//задержки. Микросекунды переводятся в машинные
//циклы.

void MCU_init(void); /* Device initialization function declaration */

void main(void) {
  MCU_init(); /* call Device Initialization */
  /* put your own code here */
  
 

 
  for(;;)
  {
    /* _FEED_COP(); by default, COP is disabled with device init. When enabling, also reset the watchdog. */
 

     PORTB = 0b11111111;
  
  
  } /* loop forever */
  /* please make sure that you never leave main */
}
