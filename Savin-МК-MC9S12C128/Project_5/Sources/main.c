#include <stdio.h>
#include <MC9S12C128.h>
#include "derivative.h"
/*������������ �������*/
void initialize_spi(void);
void send_data(unsigned int);
void main(void) {
 int i, j;
 unsigned int data;
 initialize_spi(); /*������������� ������ SCI*/
 data = 0xF0;
 while(1) /*���������� ������ ����������*/
 { 
  send_data(data); 
 } 
}
/* ������� initialize_spi ���������� ������������� ������ SPI. */ 
void initialize_spi(void) {
 SP0BR = 0�04; /*���������� �������� ������*/ 
 SP0CR1 = 0x18; /*��������� ���������� �� SPI, ��������� �����*/ 
                /*��������, ������� ����� ������*/ 
 SP0DR = 0x00; /*�������� ������� ������ */ 
 SP0SR = 0x00; /*�������� ������� ���������*/ 
 SP0CR1 = 0x58; /* ��������� SPI */ 
}
/* ������� send_data ���������� ������������� ������ SPI. */ 
void send_data(unsigned int data) {
 unsigned int status; 
 SP0DR = data; /*������ ����� ��� ���������*/ 
 while ((SP0SR & 0x80) == 0x00) /*������� ����� ���������� ��������*/ 
 { 
  ;
 } 
 status = SP0SR /*��������� ������� ��������� � ����� ������ ����� SPIF*/ 
}