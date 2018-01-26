CSEG segment
assume cs:CSEG, ds:CSEG, es:CSEG, ss:CSEG
.386
org 100h

start:
    mov eax, 70000
    mov bx,10
    
    mov cx,0
    
tag_1:
    mov edx,0
    div bx ;ax/bx
    
    push edx
    inc cx ;это просто счетчик
    
    cmp eax,0
    
    jnz tag_1
    
tag_2:
    pop edx
    call writeln
    
    loop tag_2
    
    int 21h
    
writeln proc
    push eax
    mov ah,02h
    add dl,30h
    
    int 21h
    
    pop eax
    ret
writeln endp

CSEG ends
end start