CSEG segment
assume cs:CSEG, ds:CSEG, es:CSEG, ss:CSEG
org 100h

Begin:

Next_key:
    mov ah,10h
    int 16h
    cmp al,'F'
    jz F_pressed
    cmp al,'f'
    jz F_pressed
    jmp Next_key
    
F_pressed:
    mov ah,9
    mov dx,offset Mess
    int 21h
    int 20h
    
Mess db 'u pressed f or F suka blyat!$'
CSEG ends
end Begin