.model small
.stack 100h
.186
.data
        row db 4
        col db 10
        mode db (?)
        char_ctr  db 'M' ; - start M
        Color db 9
.code
 
Start:
    mov ax,@data
    mov ds,ax
 
;Code main programm
 
        call B10MODE
 
        mov cx,6
c1:
        call D10CURSOR
        call E10DISPlAY
 
        add row,1
  
        inc char_ctr
        inc Color
 
        loop c1
 
        mov ah,10h
        int 16h
 
        mov ax,4C00h
        int 21h
 
B10MODE proc    ;Получение/установка видеорежима
 
    mov ah,0Fh
 
    int 10h
 
    mov mode,al
 
    mov ah,00h
    mov al,03h
    int 10h
 
 
    ret
B10MODE endp

D10CURSOR proc     ;Установка курсора
 
    pusha
 
    mov ah,02h
    
    mov dh,row
    mov dl,col
    int 10h
 
    popa
 
    ret
D10CURSOR endp
 
E10DISPLAY proc    ;Вывод символа на экран
 
        pusha
 
        mov ah,09h
        mov al,char_ctr
  
        mov bl,Color
        
        mov cx,7
        int 10h
 
        popa
 
    ret
E10DISPLAY endp
 
end start