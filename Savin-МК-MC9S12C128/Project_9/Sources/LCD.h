/**
 * Copyright (c) 2004, Freescale Semiconductor
 *
 * File name   : LCD.h
 * Project name: LCDroutines
 *
 * Author      : Dusty Nidey, Axiom Manufacturing
 * Modified by : Eduardo Montanez, Freescale 
 * Department  : Applications Engineering  
 *
 * Description : LCD routines header
 *               
 *
 * History     :
 * 10/14/2004  : LCD routines modified (EM)
 * 10/21/2004  : Added commands for LCD Init functions (EM)
 */

#include <MC9S12C128.h>     /* derivative information */

#ifndef LCD_H
#define LCD_H

/* Defines */
/* Standard LCD commands */
#define WR  0x20     /* LCD WR bit */
#define RS  0x40     /* LCD RS bit */
#define EN  0x80     /* LCD EN bit */

#define FourBit        0
#define EightBit       1
#define OneLine        0
#define TwoLines       1
#define FiveBySeven    0
#define FiveByTen      1 
#define DisplayOFF     0
#define DisplayON      1
#define CursorOFF      0
#define CursorON       1
#define BlinkOFF       0
#define BlinkON        1
#define Decrement      0
#define Increment      1
#define NoDisplayShift 0
#define DisplayShift   1

/* RAM Variables */
extern unsigned char LCDBuf;   /* holds data and status bits sent to LCD  */
extern unsigned char LCDStat;  /* holds LCD status */

/* Function Prototypes */
extern void LCDInit(void);
extern void LCDPutChar(unsigned char datval);
extern void LCDPutString(char *sptr);
extern void LCDClearDisplay(void);
extern void LCDCursorAtHome(void); 

#endif /* LCD_H */