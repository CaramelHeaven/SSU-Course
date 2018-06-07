#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */

/*--------------------------------------------------------------------*/

/* filename: SPI.c */

/* МAIN PROGRAМ: Эта программа реализует непрерывную посылку кода */

/* символа "S" с скоростью 9600 бод в 8-разрядном формате (кадр 10 бит)*/

/* с битом паритета */

/*--------------------------------------------------------------------*/

#include <stdio.h>

/*используемые функции*/

void initialize_spi(void);

void send_data(unsigned int);

void main(void) 
{

  
  int i = 0, j = 0;

  unsigned int data;

  initialize_spi(); /*инициализация модуля SCI*/

  data = 0b01010010;

  for(;;) /*передавать данные непрерывно      */

  { 

    send_data(data); 

  } 
    
}   

/* Функция initialize_spi производит инициализацию модуля SPI. */ 

void initialize_spi(void) 
{

  SPIBR = 0x04; /*установить скорость обмена*/ 

  SPICR1 = 0x18; /*запретить прерывания от SPI, назначить режим*/ 

  /*ведущего, старшим битом вперед*/ 

  SPIDR = 0x00; /*очистить регистр данных */ 

  SPISR = 0x00; /*очистить регистр состояния*/ 

  SPICR1 = 0x58; /* разрешить SPI */ 
  
  PERM = 0b00111111;

}

/* Функция send_data производит инициализацию модуля SPI. */ 

void send_data(unsigned int data) 
{

  unsigned int status; 

  SPIDR = data; /*задать число для пересылки*/ 

  while ((SPISR & 0x80) == 0x00) /*ожидать флага завершения передачи*/ 

  { 

    ;

  } 

status = SPISR /*прочитать регистр состояния с целью сброса флага SPIF*/ 


;
}