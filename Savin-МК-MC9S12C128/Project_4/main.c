#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */

void main(void) {
  
  DDRB = 0b11111111;
  PORTB = 0b11100000;   /*������ 4-�� �������� */
  
 /*
 PORT  00000000     7654 - ��������
 BIT   76543210     3210 - ������ (�� ��, ��� ����������)
 */

	for(;;) 
	{
	    if(PP_BIT0 == 1) 
	    {
	        PORTB = 0b11110000;
	    }  
	}
}
