CSEG segment
assume cs:CSEG, ds:CSEG, es:CSEG, ss:CSEG
;.386
org 100h

start:
    mov ax, 469
    mov bx,10
    
    mov cx,0
    
tag_1:
    mov dx,0
    div bx ;ax/bx
    
    push dx
    inc cx
    
    cmp ax,0
    
    jnz tag_1
    
tag_2:
    pop dx
    call writeln
    
    loop tag_2
    
    int 21h
    
writeln proc
    push ax
    mov ah,02h
    add dl,30h
    
    int 21h
    
    pop ax
    ret
writeln endp

CSEG ends
end start