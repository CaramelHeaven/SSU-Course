#include <stdio.h>
#include <MC9S12C128.h>
#include "derivative.h"
/*используемые функции*/
void initialize_spi(void);
void send_data(unsigned int);
void main(void) {
 int i, j;
 unsigned int data;
 initialize_spi(); /*инициализаци€ модул€ SCI*/
 data = 0xF0;
 while(1) /*передавать данные непрерывно*/
 { 
  send_data(data); 
 } 
}
/* ‘ункци€ initialize_spi производит инициализацию модул€ SPI. */ 
void initialize_spi(void) {
 SP0BR = 0х04; /*установить скорость обмена*/ 
 SP0CR1 = 0x18; /*запретить прерывани€ от SPI, назначить режим*/ 
                /*ведущего, старшим битом вперед*/ 
 SP0DR = 0x00; /*очистить регистр данных */ 
 SP0SR = 0x00; /*очистить регистр состо€ни€*/ 
 SP0CR1 = 0x58; /* разрешить SPI */ 
}
/* ‘ункци€ send_data производит инициализацию модул€ SPI. */ 
void send_data(unsigned int data) {
 unsigned int status; 
 SP0DR = data; /*задать число дл€ пересылки*/ 
 while ((SP0SR & 0x80) == 0x00) /*ожидать флага завершени€ передачи*/ 
 { 
  ;
 } 
 status = SP0SR /*прочитать регистр состо€ни€ с целью сброса флага SPIF*/ 
}