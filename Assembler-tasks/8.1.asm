.model small
.stack 100h
.186
.data
;Определение переменных:
    ;row - строка, в которой находится курсор, начальное значение равно 4
    ;col - столбец, в котором находится курсор, начальное значение равно 24
    ;mode - номер режима, начальное значение не определено (равно ?)
    ;char_ctr - счетчик АСС2-символов, начальное значение равно 0

        row db 4
        col db 24
        mode db (?)
.code

Start:
    mov ax,@data
    mov ds,ax

;Code main programm

        call B10MODE
        call C10CLEAR

        mov bx,0
        LEA cx, end_of_code

c1:
        mov al, CS:BX
        push bx
        mov ah, 0
        mov bl,16
        div bl

        call getASCII
        call D10CURSOR
        call printLetter

        mov al,ah
        call getASCII
        add col,1

        call D10CURSOR
        call printLetter

        add col,2

        cmp col,56
        jl c2
        mov col,24
        inc row

c2:
        pop bx
        inc bx
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

printLetter PROC
  pusha

  mov ah,0Ah
  mov bh,00h
  mov cx,1
  int 10h

  popa
  ret
printLetter endp

C10CLEAR proc      ;Очистка экрана
    pusha

    mov ah,06h
    mov dx,184Fh

    int 10h

    mov al,20 ;строки 10

    mov BH,03h ;черный фон, синие символы
    mov ch, 0				; Строка левого верхнего угла
	  mov cl, 0				; Столбец левого верхнего угла
	  mov dh, 20			; Строка правого нижнего угла
	  mov dl, 70				; Столбец правого нижнего угла


    int 10h

    popa

    ret
    C10CLEAR endp

D10CURSOR proc     ;Установка курсора

    pusha

    mov ah,02h
    mov bh,0
    mov dh,row
    mov dl, col
    int 10h

        popa

    ret
    D10CURSOR endp

getASCII PROC
  add al,30h
  cmp al,'9'
  jle exit
  add al,7

exit:

  ret
getASCII endp

end_of_code db (?)

end start
