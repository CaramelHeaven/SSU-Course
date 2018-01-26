CSEG segment
assume cs:CSEG, ds:CSEG, es:CSEG, ss:CSEG
org 100h

start:
    mov bx,8
    call Out_number
    
    int 20h
        
    Out_number proc
        add bx,30h
        mov dl,bl
    
        mov ah,02h
        int 21h
        ret
    Out_number endp

CSEG ends
end start   