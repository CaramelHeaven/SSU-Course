CSEG segment
assume cs:CSEG, ds:CSEG, es:CSEG, ss:CSEG
org 100h

start:
    mov ax,6
    mov dl,al
    
    PUSH ax
    
    call Out_number
    
    call Out_space
    
    mov bx,4
    mov dl,bl
    call Out_number
    
    call New_line
    PUSH bx
    
    POP BX
    POP AX
    
    xchg ax,bx

    mov dl,al
    call Out_number
    
    call Out_space
    
    mov dl,bl
    call Out_number
    
    int 20h

Out_space proc
    mov dl,20h
    mov ah,02h
        
    int 21h
    ret
Out_space endp

Out_number proc
    add dl,30h
    mov ah,02h
    
    int 21h
    ret
Out_number endp

New_line proc
    mov ah, 02h
    mov dl, 13
    
    int 21h
    mov dl, 10
    
    int 21h 
    ret
New_line endp

CSEG ends
end start