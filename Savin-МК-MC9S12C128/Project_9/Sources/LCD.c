/**
 * Copyright (c) 2004, Freescale Semiconductor
 *
 * File name   : lcd.c
 * Project name: LCDroutines
 *
 * Author      : Dusty Nidey, Axiom Manufacturing
 * Modified by : Eduardo Montanez, Freescale 
 * Department  : Applications Engineering  
 *
 * Description : LCD routines via SPI interface on MCUSLK
 *               
 *
 * History     :
 * 10/14/2004  : LCD routines modified (EM)
 * 10/21/2004  : Added LCD Init functions (EM)
 */
#include "LCD.h"

/* Function Prototypes */
static void LCDDelay1(unsigned int);
static void LCDDelay2(unsigned int);
static void LCDSend(void);
static void LCDWrite4(char);
static void LCDWrite4d(char);
static void LCDWrite8(unsigned char);
static void LCDCommand(unsigned char);
static void LCDFunctionSet(char, char, char);
static void LCDDisplayControl(char, char, char);
static void LCDEntryModeSet(char, char);


/* Declarations */
unsigned char LCDBuf;   /* holds data and status bits sent to LCD */
unsigned char LCDStat;  /* holds LCD status */

#define LCD_DELAYTIME   0x100   /* adjust this value Lower for quicker LCD performance,
                                   Higher if you're seeing garbage on the display */
#define LCD_LINELENGTH  8     /*Sets the maximum number of characters printed per line.
                                   resoulution of the LCD is 8 characters*/                                   

/*
 * LCDDelay1: Simple delay loop, waits specified number of counts
 *
 * Parameters: ucount - number of wait counts 
 *
 * Return : None.
 */
void LCDDelay1(unsigned int ucount)
{
  while(ucount > 0)
  {
    --ucount;
        /* look at your compiler output and count the number of cycles used.
           add more of these if needed to fine tune the exact delay you want
           asm("nop"); */
   }
}

/*
 * LCDDelay2: Simple delay loop, LCDDelay1() * LCDDelaytime
 *
 * Parameters: mcount - number of wait counts 
 *
 * Return : None.
 */
void LCDDelay2(unsigned int mcount)
{
  while(mcount > 0)
  {
    --mcount;
    LCDDelay1(LCD_DELAYTIME);
  }
}

/*
 * LCDSend: Simple serial driver (SPI)
 *          Sends data byte in global LCDBuf
 *          Return received back value in global LCDStat
 *
 * Parameters: None. 
 *
 * Return : None.
 */
void LCDSend()
{
  LCDDelay2(1);
  LCDStat = SPISR;   /* clear status of SPI by reading it */
  SPIDR = LCDBuf;    /* send byte */

  do
  { 
    /* wait for status flag to go high */
    LCDStat = SPISR;
  } while(LCDStat < 0x80);

  LCDStat = SPIDR;    /* receive value back */
}

/*
 * LCDWrite4: Write 4 bit data to LCD port
 *          
 *
 * Parameters: lcddata - data to be written to LCD 
 *
 * Return : None.
 */
void LCDWrite4(char lcddata)
{
  /* merge lower 4 bits of LCDdata with upper 4 bits of LCDBuf (control bits) */
  LCDBuf &= 0xF0;
  LCDBuf |= lcddata;

  LCDBuf &= ~EN;  /* enable low */
  LCDSend();      /* send the data */
  LCDBuf |= EN;   /* enable high */
  LCDSend();      /* send the data */
  LCDBuf &= ~EN;  /* enable low */
  LCDSend();      /* send the data */
}

/*
 * LCDWrite4d: Write 4 bit data to LCD port with delay
 *          
 *
 * Parameters: lcddata - data to be written to LCD 
 *
 * Return : None.
 */
void LCDWrite4d(char lcddata)
{
  LCDWrite4(lcddata);
  LCDDelay1(50);
}

/*
 * LCDWrite8: Write 8 bit data to LCD port
 *            Upper 4 bits first, then lower 4 bits
 *          
 *
 * Parameters: lcdval - data to be written to LCD 
 *
 * Return : None.
 */
void LCDWrite8(unsigned char lcdval)
{
  LCDWrite4(lcdval >> 4);      /* send upper 4 bits */
  LCDWrite4(lcdval & 0x0F);    /* send lower 4 bits */
}

/*
 * LCDCommand: Write a command byte to the LCD port
 *          
 *
 * Parameters: cmdval - command to be written to LCD 
 *
 * Return : None.
 */
void LCDCommand(unsigned char cmdval)
{
    unsigned char x = cmdval;
    LCDBuf &= ~RS;    /* clear RS to select LCD Command mode */
    LCDWrite8(cmdval);
    LCDDelay2(10);
}

/*
 * LCDPutChar: Send a character to the LCD for display
 *          
 *
 * Parameters: datval - data character to be displayed on LCD 
 *
 * Return : None.
 */
void LCDPutChar(unsigned char datval)
{
  LCDBuf |= RS;      /* set RS to select LCD Data mode */
  LCDWrite8(datval); /* write a DATA byte to the LCD */
}

/*
 * LCDPutString: Send a string to the LCD for display
 *               String must end in a 0x00
 *          
 *
 * Parameters: sptr - pointer to string to be displayed on LCD
 *
 * Return : None.
 */
void LCDPutString(char *sptr)
{
  unsigned char stringlength = 1;
  
  while(*sptr)
  {
    if(*sptr == 10) 
    {   /*if \n detected goto new line */
      /*while(stringlength <= 2*LCD_LINELENGTH) {
        LCDPutChar(' ');
        ++stringlength;
      }*/
      LCDCommand(0xC0);
      ++sptr;
    }
    else 
    {        
      LCDPutChar(*sptr);
	  	++sptr;
		  //++stringlength;
    }
	}
}

/*
 * LCDFunctionSet: Determine command to set LCD function             
 *          
 *
 * Parameters: mode - select 4 bit mode (FourBit) OR 8 bit mode (EightBit)
 *             lines - select 1 line (OneLine) OR 2 lines (TwoLines)
 *             font - select 5x7 (FiveBySeven) OR 5x10 (FiveByTen) 
 *
 * Return : None.
 */
void LCDFunctionSet(char mode, char lines, char font)
{
  char tempvar1 = 0;
  
  LCDWrite4d(3);              /* Send LCD Initialization commands */
  LCDWrite4d(3);
  LCDWrite4d(3);
  LCDWrite4d(2);              
    
  if(lines == OneLine)
  {
    tempvar1 |= 0x20;
  } 
  else
  {
    tempvar1 |= 0x28;
  }

  if(font == FiveBySeven) 
  {
    tempvar1 |= 0x20;
  } 
  else
  {
    tempvar1 |= 0x24;
  }

  if(mode == EightBit)
  {
    tempvar1 |= 0x30;
  } 
  else
  {
    tempvar1 |= 0x20;
  }

  LCDCommand(tempvar1);               /* Send LCD Function command */  
}

/*
 * LCDDisplayControl: Determine command to set LCD display             
 *          
 *
 * Parameters: display - select display on (DisplayON) OR display off (DisplayOFF)
 *             cursor - select cursor on (CursorON) OR cursor off (CursorOFF)
 *             blink - select cursor blink on (BlinkON) OR cursor blink off (BlinkOFF) 
 *
 * Return : None.
 */
void LCDDisplayControl(char display, char cursor, char blink)
{
  char tempvar2 = 0;
  
  if(display == DisplayON)
  {
    tempvar2 |= 0x0C;
  } 
  else
  {
    tempvar2 |= 0x08;
  }
  
  if(cursor == CursorON)
  {
    tempvar2 |= 0x0A;
  } 
  else
  {
    tempvar2 |= 0x08;
  }
  
  if(blink == BlinkON)
  {
    tempvar2 |= 0x09;
  } 
  else
  {
    tempvar2 |= 0x08;
  }
    
  LCDCommand(tempvar2);              /* Send LCD Display command */
}

/*
 * LCDEntryModeSet: Determine command to set LCD entry mode             
 *          
 *
 * Parameters: direction - select cursor direction right (Increment)
 *                         OR cursor direction left (Decrement)
 *             shift - select display shift on (DisplayShift)
 *                     OR no diplay shift (NoDisplayShift)
 *
 * Return : None.
 */
void LCDEntryModeSet(char direction, char shift)
{
  char tempvar3 = 0;

  if(direction == Decrement)
  {
    tempvar3 |= 0x04;
  } 
  else
  {
    tempvar3 |= 0x06;
  }
  
  if(shift == DisplayShift)
  {
    tempvar3 |= 0x05;
  } 
  else
  {
    tempvar3 |= 0x04;
  }
 
  LCDCommand(tempvar3);              /* Send LCD Entry Mode command */
}

/*
 * LCDClearDisplay: Clear LCD Display             
 *          
 *
 * Parameters: None.
 *
 * Return : None.
 */
void LCDClearDisplay()
{
  LCDCommand(0x01);                   /* Clear LCD Display */  
}

/*
 * LCDCursorAtHome: LCD cursor returns to home             
 *          
 *
 * Parameters: None.
 *
 * Return : None.
 */
void LCDCursorAtHome()
{
  LCDCommand(0x02);                   /* Cursor return to home */  
}

/*
 * LCDInit: Initialize LCD port
 *          
 *
 * Parameters: None.
 *
 * Return : None.
 */
void LCDInit()
{  
  /*  Turn on SPI */
  SPICR1 = 0x52;
  SPICR2 = 0x10;            /* enable /SS */
  SPIBR = 0b00010010;             /* set up SPI baud clock rate */
  LCDBuf = (WR + EN);       /* set WR and EN bits */
  LCDSend();                /* send status to LCD */

  /* Initialize LCD */
  LCDBuf &= ~(RS + EN);     /* clear RS and EN bits (select lcd Command) */
  LCDSend();                /* send status to LCD */
  
  /* delay's are used because this lcd interface does not provide status */
  LCDDelay2(50);
  
  LCDFunctionSet(FourBit, TwoLines, FiveBySeven);
                              /* Set 4-bit mode (enter FourBit)
                                 OR 8-bit mode (enter EightBit)
                                 AND 1 line (enter OneLine)
                                 OR 2 lines (enter TwoLines)
                                 AND 5x7 character font (enter FiveBySeven)
                                 OR 5x10 character font (enter FiveByTen) */
  LCDDisplayControl(DisplayON, CursorOFF, BlinkOFF);
                              /* Set display ON (enter DisplayON)
                                 OR display OFF (enter DisplayOFF)
                                 AND cursor ON (enter CursorON)
                                 OR cursor OFF (enter Cursor OFF)
                                 AND blink of cursor ON (enter BlinkON)
                                 OR blink of cursor OFF (enter BlinkOFF) */
  LCDEntryModeSet(Increment, NoDisplayShift);
                              /* Set cursor increment (enter Increment)
                                 OR cursor decrement (enter Decrement)
                                 AND display shift (enter DisplayShift)
                                 OR no displayshift (enter NoDisplayShift) */
  
  LCDClearDisplay();          /* Clear LCD Display */
  LCDCursorAtHome();          /* Cursor return to home */
                                 
  LCDBuf = 0;                 /* Reset Lcd states to rest */
  LCDSend();                  /* send status to LCD */
}

