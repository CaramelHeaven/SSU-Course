.model tiny

.code
org 100h

start:

  call current_time

  call set_time

  mov AX,4C00h
  int 21h

;------------------------------------------------------------------------------

current_time proc

  mov ah,02h
  int 1Ah

  mov sec, DH
  mov min, CL
  mov hour, CH

  LEA dx, time
  call print

  mov ah, 0
  mov al, hour
  mov bx, 16
  call changeSystem

  call dev_digit

  mov ah,0
  mov al, min
  mov bx, 16

  call changeSystem
  call dev_digit

  mov ah,0
  mov al, sec
  mov bx, 16

  call changeSystem
  call dev_digit

  call new_line

  ret
current_time endp

;------------------------------------------------------------------------------

set_time proc

  LEA dx, info
  call print

  mov ah,08h
  int 21h

  mov dl,al
  mov ah,02h
  int 21h

  cmp dl, 'y'
  jnz no

yes:
  call new_line
  LEA dx, answer_yes
  call print
;-----------------------HOURS
  call new_line
  LEA dx, h
  call print

  mov ax,0
  call numbers_set ;ввод два символа в час

  sub dl,30h

  cmp dl, 24h
  JGE error

  mov al, dl
  mov set_hour,al
;------------------------MINUTES
  call new_line
  LEA dx, m
  call print

  mov ax,0
  call numbers_set

  sub dl,30h

  cmp dl,60h
  JGE error

  mov al,dl
  mov min,al

;----------------------SECONDS
  call new_line
  LEA dx,s
  call print

  mov ax,0
  call numbers_set

  sub dl,30h

  cmp dl,60h
  JGE error

  mov al,dl
  mov sec,al

;---------------------SET TIME
  mov CH,set_hour
  mov CL,min
  mov DH,sec
  mov dl,0

  mov ah,03h
  int 1Ah

  jmp exit
;---------------------
no:
  call new_line
  LEA dx,answer_no
  call print
;---------------------
error:
  call new_line
  LEA dx, error_tx
  call print
;---------------------
exit:

  ret
set_time endp

numbers_set PROC
  mov ah,01h
  int 21h

  mov cl,4
  mov dl,al
  shl dl,cl

  mov ah,01h
  int 21h

  add dl,al

  ret
numbers_set endp

;---------------------------------ОСТАЛЬНОЕ-----------------------------

new_line PROC
	mov ah, 02h
  mov dl, 13
  int 21h

  mov dl, 10
  int 21h

  ret
new_line endp

;-----------------------------------------------------------------------------

;ПЕРЕВОД ИЗ 16 в 10
changeSystem PROC
  mov cx,0
m0:
  mov dx,0
  div bx
  push dx
  inc cx
  cmp ax,0
  jnz m0

  cmp cx,1
  jnz m1
  mov dx,0
  call print_number
m1:
  pop dx
  call print_number
  loop m1

  ret
changeSystem endp

;------------------------------------------------------------------------------

dev_digit proc
	mov dx, 3Ah
	mov ah, 02h
	int 21h

	ret
dev_digit endp

;------------------------------------------------------------------------------

print PROC
  mov ah,09h
  int 21h

  ret
print endp

;------------------------------------------------------------------------------

print_number PROC
  add dl, 30h
  mov ah,02h
  int 21h

  ret
print_number endp

;------------------------------------------------------------------------------

get_num PROC
  mov bx,10
  mov cx,0
mq0:
  mov dx,0
  div bx
  push dx
  inc cx
  cmp ax,0
  jnz mq0

mq1:
  pop dx
  call print_number
  loop mq1

  ret
get_num endp

;------------------------------------------------------------------------------


;===== Data =====

sec db ?
min db ?
hour db ?
set_hour db ?
helper db ?


s db 'Seconds: $'
m db 'Minutes: $'
h db 'Hours: $'
answer_no db 'The new time is not set. $'
answer_yes db 'Okay, enter a new time, please: $'
info db 'Would u like to set a new time here? (y / n) $'
time db 'The current time is: $'
completed db 'The work with program is completed'
error_tx db 'You wrote incorrect time'
qq db ' $'
end start
