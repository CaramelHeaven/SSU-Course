#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */

void main(void) {
  
  DDRB = 0b11111111;
  PORTB = 0b11100000;   /*первые 4-ре лампочки */
  
 /*
 PORT  00000000     7654 - лампочки
 BIT   76543210     3210 - кнопки (не те, что нажимаются)
 */

	for(;;) 
	{
	    if(PP_BIT0 == 1) 
	    {
	        PORTB = 0b11110000;
	    }  
	}
}
