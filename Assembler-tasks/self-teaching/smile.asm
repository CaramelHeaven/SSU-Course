CSEG segment
assume cs:CSEG, ds:CSEG, es:CSEG, ss:CSEG
org 100h

Begin:
    mov ax,0B800h
    mov es,ax
    mov di,0
    mov al,1
    mov ah,31
    mov cx,2000
    
Next_face:
    mov es:[di],ax
    add di,2
    loop Next_face
    
    mov ah,10h
    int 16h
    int 20h
CSEG ends
end Begin