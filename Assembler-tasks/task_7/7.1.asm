.model tiny

.code
org 100h

start:
  LEA dx, binary
  call print

  call print_binary_code
  call new_line

  call set_equipment
  call get_equipment

  call get_memory


	mov AX,4C00h
  int 21h

;----------------Get binary number or numbers-----------------
set_equipment PROC
  int 11h
	mov bx,2
;------------------ 1
	mov dx,0
	div bx
	mov bit_0,dl
;------------------ 2
  mov dx,0
  div bx
  mov bit_1, dl
;------------------ 3 ??
	mov dx, 0
	div bx
	mov bit_2_3, dl

	mov dx, 0
	div bx
	mov cl, 1
	shl dx, cl
	add dl, bit_2_3
	mov bit_2_3, dl
;------------------ 4 and 5
	mov dx, 0
	div bx
	mov bit_4_5, dl

	mov dx, 0
	div bx
	mov cl, 1
	shl dx, cl
	add dl, bit_4_5
	mov bit_4_5, dl
;------------------ 6 and 7
	mov dx, 0
	div bx
	mov bit_6_7, dl

	mov dx, 0
	div bx
	mov cl, 1
	shl dx, cl
	add dl, bit_6_7
	mov bit_6_7, dl
;------------------ get 8 ????
	mov dx, 0
	div bx
	mov bit_8, dl
;------------------ get 9, 10, 11
  mov dx, 0
	div bx
	mov bit_9_10_11, dl

	mov dx, 0
	div bx
	mov cl, 1
	shl dx, cl
	add dl, bit_9_10_11
	mov bit_9_10_11, dl

	mov dx, 0
	div bx
	mov cl, 2
	shl dx, cl
	add dl, bit_9_10_11
	mov bit_9_10_11, dl
;------------------  get 12
	mov dx, 0
	div bx
	mov bit_12, dl
;------------------ get 13
	mov dx, 0
	div bx
	mov bit_13, dl
;------------------ get 14 and 15
	mov dx, 0
	div bx
	mov cl, 1
	shl dx, cl
	mov bit_14_15, dl

	mov dx, 0
	div bx
	add dl, bit_14_15
	mov bit_14_15, dl

  ret
set_equipment endp

;------------------------------------------------------------------------------

print PROC
  mov ah,09h
  int 21h

  ret
print endp

;------------------------------------------------------------------------------

get_equipment PROC
;------------------
  LEA dx,disk_dev
  call print
  mov dl, bit_0

  call compare
  call new_line
;------------------
  LEA dx, math_coprocessor
  call print
  mov dl,bit_1

  call compare
  call new_line
;------------------
  LEA dx, VM_4_5
  call print
  mov dl, bit_4_5
  mov al,dl

  call checkVideo

  call new_line
;------------------
  LEA dx, floppyDrives
  call print
  mov ax,0
  mov al,bit_6_7

  inc ax

  call floppy_num
  call new_line
;------------------
  LEA dx, adapter_RS_232
  call print
  mov ax,0
  mov al,bit_9_10_11

  call floppy_num
  call new_line
;------------------
  LEA dx, game_adapter
  call print
  mov dl,bit_12

  call compare
  call new_line
;------------------
  LEA dx, serial_printer
  call print
  mov dl,bit_13

  call compare
  call new_line
;------------------
  LEA dx, connected_printers
  call print
  mov ax,0
  mov al, bit_14_15

  call floppy_num
  call new_line

  ret
get_equipment endp

;------------------------------------------------------------------------------

get_memory PROC
  LEA dx, memory
  call print

  int 12h
  call floppy_num

  LEA dx,end_m
  call print

  ret
get_memory endp

;------------------------------------------------------------------------------

checkVideo PROC
  cmp al, 3
  jnz m_00
  LEA dx, videoMode_11
  call print
  jmp exit_f
m_00:
  cmp al,2
  jnz m_10
  LEA dx, videoMode_10
  call print
  jmp exit_f
m_10:
  cmp al,1
  jnz m_20
  LEA dx, videoMode_01
  call print
  jmp exit_f
m_20:
  LEA dx,videoMode_00
  call print

exit_f:

  ret
checkVideo endp

;------------------------------------------------------------------------------

floppy_num PROC
  mov bx,10
  mov cx,0
m0:
  mov dx,0
  div bx
  push dx
  inc cx
  cmp ax,0
  jnz m0

m2:
  pop dx
  call print_number
  loop m2

  ret
floppy_num endp

;------------------------------------------------------------------------------

print_number PROC
  add dl, 30h
  mov ah,02h
  int 21h

  ret
print_number endp

;---------Here is print binary code which had gotten with help int 11h---------

print_binary_code PROC
  int 11h
  mov bx,ax
  mov cx,16
ob1:
  shl bx,1
  jc ob2

  mov dl,'0'
  jmp ob3

ob2:
  mov dl,'1'
ob3:
  mov ah,02h
  int 21h
  loop ob1

	ret
print_binary_code endp

;------------------------------------------------------------------------------

compare PROC
  cmp dl,0
  jz n_use

use:
  LEA dx,use_txt
  mov ah,09h
  int 21h
  jmp out_in

n_use:
  LEA dx,n_use_txt
  mov ah,09h
  int 21h

out_in:
  ret
compare endp

;------------------------------------------------------------------------------

new_line PROC
	mov ah, 02h
  mov dl, 13
  int 21h

  mov dl, 10
  int 21h

  ret
new_line endp

;------------------------------------------------------------------------------

;===== Data =====
use_txt db 'is used$'
n_use_txt db 'not used$'

bit_0 db ?
bit_1 db ?
bit_2_3 db ?
bit_4_5 db ?
bit_6_7 db ?
bit_8 db ?
bit_9_10_11 db ?
bit_12 db ?
bit_13 db ?
bit_14_15 db ?

end_m db ' Kb$'
memory db 'Remaining memory: $'
connected_printers db 'The number of connected printers: $'
serial_printer db 'Serial printer: $'
game_adapter db 'Game adapter: $'
adapter_RS_232 db 'Number of successive joint adapters RS-232: $'
binary db 'Binary code: $'
disk_dev db 'Disk device: $'
math_coprocessor db 'Math Coprocessor: $'
VM_4_5 db 'Video Mode: $'
floppyDrives db 'Floppy drive: $'

videoMode_00 db 'not used$'
videoMode_01 db '40x25 plus color$'
videoMode_10 db '80x25 plus color$'
videoMode_11 db '80х25 black and white mode$'
end start
