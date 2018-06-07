/*
** ###################################################################
**     This code is generated by the Device Initialization Tool.
**     It is overwritten during code generation.
**     USER MODIFICATION ARE PRESERVED ONLY INSIDE EXPLICITLY MARKED SECTIONS.
**
**     Project   : Project_1
**     Processor : MC9S12C128CFU16
**     Version   : Component 02.001, Driver 01.05, CPU db: 2.87.410
**     Datasheet : MC9S12C128 Rev 01.23 05/2007
**     Date/Time : 13.03.2018, 10:51
**     Abstract  :
**         This module contains device initialization code 
**         for selected on-chip peripherals.
**     Contents  :
**         Function "MCU_init" initializes selected peripherals
**
**     Copyright : 1997 - 2010 Freescale Semiconductor, Inc. All Rights Reserved.
**     
**     http      : www.freescale.com
**     mail      : support@freescale.com
** ###################################################################
*/

#ifndef __Project_1_H
#define __Project_1_H 1

/* Include shared modules, which are used for whole project */


/* User declarations and definitions */
/*   Code, declarations and definitions here will be preserved during code generation */
/* End of user declarations and definitions */
#pragma CODE_SEG DEFAULT

extern void MCU_init(void);
/*
** ===================================================================
**     Method      :  MCU_init (component MC9S12C32_80)
**
**     Description :
**         Device initialization code for selected peripherals.
** ===================================================================
*/



/*lint -save  -e765 Disable MISRA rule (8.10) checking. */
#pragma CODE_SEG __NEAR_SEG NON_BANKED
__interrupt void isr_default(void);
/*
** ===================================================================
**     Interrupt handler : isr_default
**
**     Description :
**         User interrupt service routine. 
**     Parameters  : None
**     Returns     : Nothing
** ===================================================================
*/
#pragma CODE_SEG DEFAULT



#pragma CODE_SEG __NEAR_SEG NON_BANKED
__interrupt void isrVspi(void);
/*
** ===================================================================
**     Interrupt handler : isrVspi
**
**     Description :
**         User interrupt service routine. 
**     Parameters  : None
**     Returns     : Nothing
** ===================================================================
*/
#pragma CODE_SEG DEFAULT



/*lint -restore Enable MISRA rule (8.10) checking. */



/* END Project_1 */

#endif
/*
** ###################################################################
**
**     This file was created by Processor Expert 3.02 [04.44]
**     for the Freescale HCS12 series of microcontrollers.
**
** ###################################################################
*/
