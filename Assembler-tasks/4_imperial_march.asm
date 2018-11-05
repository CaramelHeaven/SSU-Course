code    segment                          ; определение кодового сегмента
        assume  cs:code,ds:code          ; CS и DS указывают на сегмент кода
        org     100h                     ; размер PSP для COM программы
		.386
		
start:  jmp     load                     ; переход на нерезидентную часть
        old     	dd 0                    ; адрес старого обработчика 
        counterNotes	db 0
	durCount	db 0
	arrayOfFrequencies	dw 440, 440, 440, 349, 523, 440, 349, 523, 440, 15000, 440, 440, 440, 349, 523, 440, 349, 523, 440, 15000, 659, 569, 569, 692, 523, 415, 349, 523, 440, 15000, 880, 622, 587, 554, 523, 440, 523, 659, 15000, 880, 440, 440, 880, 830, 740, 698, 740, 12000, 455, 622, 587, 554, 523, 294, 523, 12000, 349, 415, 349, 523, 440, 349, 523, 440
	freq		dw 0
	init		db 0
		
		
		
Beep proc 
	push 	ax 				;сохранить регистры
	push 	bx
	push 	dx
	mov	ax, freq
	mov 	bx,ax 			;частота
	mov 	ax,34DDh
	mov 	dx,12h 			;(dx,ax)=1193181

	cmp 	dx,bx 			;если bx < 18Гц, то выход
	jnb 	Done 			;чтобы избежать переполнения

	div 	bx 				;ax=(dx,ax)/bx
	mov 	bx,ax 			;счетчик таймера

	in 	al,61h 			;порт РВ
	or 	al,3 			;установить биты 0-1
	out 	61h,al
	mov 	al,00001011b 	;управляющее слово таймера:

	;канал 2, режим 3, двоичное слово
	mov 	dx,43h
	out 	dx,al 			;вывод в регистр режима
	dec 	dx
	mov 	al,bl
	out 	dx,al 			;младший байт счетчика
	mov 	al,bh
	out 	dx,al 			;старший байт счетчика
Done:
	pop 	dx 			;восстановить регистры
	pop 	bx
	pop 	ax

	ret
Beep endp

noBeep proc 
	push 	ax
		
	in 	al,61h 			;порт РВ
	and 	al,not 3 		;сброс битов 0-1
	out 	61h, al
		
	pop 	ax
		
	ret
noBeep endp

handlerSound   proc                             ; процедура обработчика прерываний от таймера
        pushf                            ; создание в стеке структуры для IRET
        call    cs:old                   ; вызов старого обработчика прерываний
        push    ds                       ; сохранение модифицируемых регистров
        push    es
	push    ax
        push    cx
        push    dx
	push    di
        push    cs
        pop     ds
		
	mov 	al, init
	cmp 	al, 1
	je	baseHandler
	xor 	bx, bx
	mov 	init, 1
		
baseHandler:
	mov 	al, counterNotes
	cmp 	al, 65
	je	ArrayIndexOutOfBoundsException
		
	mov 	al, durCount
	cmp 	al, 6
	je	makeDuration
				
	mov	ax, arrayOfFrequencies[bx]
	mov	freq, ax
	call 	Beep
	jmp 	exit

makeDuration:
	mov	durCount, 0
	add 	bx, 2
	inc 	counterNotes
	jmp	exit

ArrayIndexOutOfBoundsException:
	call 	noBeep

exit:
	inc	durCount
	   
	pop     di                       ; восстановление модифицируемых регистров
        pop     dx
        pop     cx
        pop     ax
        pop     es
        pop     ds
		
        iret                             ; возврат из обработчика
handlerSound   endp                             ; конец процедуры обработчика
end_handler:                               ; метка для определения размера резидентной
                                         ; части программы
load:   

	mov     ax,  351Ch               ; получение адреса старого обработчика
        int     21h                      ; прерываний от таймера
        mov     word ptr old, bx        ; сохранение смещения обработчика
        mov     word ptr old + 2, es    ; сохранение сегмента обработчика
        mov     ax, 251Ch               ; установка адреса нашего обработчика
        mov     dx, offset handlerSound       ; указание смещения нашего обработчика
        int     21h                      ; вызов DOS
        mov     ax, 3100h               ; функция DOS завершения резидентной программы
        mov     dx, (end_handler - start + 10Fh) / 16 ; определение размера резидентной
                                                    ; части программы в параграфах
        int     21h                      ; вызов DOS
code    ends                             ; конец кодового сегмента
        end     start                    ; конец программы
