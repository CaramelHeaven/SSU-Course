#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */

/*--------------------------------------------------------------------*/

/* filename: SPI.c */

/* �AIN PROGRA�: ��� ��������� ��������� ����������� ������� ���� */

/* ������� "S" � ��������� 9600 ��� � 8-��������� ������� (���� 10 ���)*/

/* � ����� �������� */

/*--------------------------------------------------------------------*/

#include <stdio.h>

/*������������ �������*/

void initialize_spi(void);

void send_data(unsigned int);

void main(void) 
{

  
  int i = 0, j = 0;

  unsigned int data;

  initialize_spi(); /*������������� ������ SCI*/

  data = 0b01010010;

  for(;;) /*���������� ������ ����������      */

  { 

    send_data(data); 

  } 
    
}   

/* ������� initialize_spi ���������� ������������� ������ SPI. */ 

void initialize_spi(void) 
{

  SPIBR = 0x04; /*���������� �������� ������*/ 

  SPICR1 = 0x18; /*��������� ���������� �� SPI, ��������� �����*/ 

  /*��������, ������� ����� ������*/ 

  SPIDR = 0x00; /*�������� ������� ������ */ 

  SPISR = 0x00; /*�������� ������� ���������*/ 

  SPICR1 = 0x58; /* ��������� SPI */ 
  
  PERM = 0b00111111;

}

/* ������� send_data ���������� ������������� ������ SPI. */ 

void send_data(unsigned int data) 
{

  unsigned int status; 

  SPIDR = data; /*������ ����� ��� ���������*/ 

  while ((SPISR & 0x80) == 0x00) /*������� ����� ���������� ��������*/ 

  { 

    ;

  } 

status = SPISR /*��������� ������� ��������� � ����� ������ ����� SPIF*/ 


;
}