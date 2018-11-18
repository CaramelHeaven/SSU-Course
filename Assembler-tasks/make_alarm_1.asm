code	segment                          ; определение кодового сегмента
assume  cs:code,ds:code          ; CS и DS указывают на сегмент кода
org     100h                     ; размер PSP для COM программы
.386
		
start:  
	jmp		load                     ; переход на нерезидентную часть
        old     	dd 0                    ; адрес старого обработчика 
        UIAlarmTime    	db ' 00:00:00 ', 0     ; шаблон для вывода текущего времени
	UICurrentTime		db '00:00:00 ', 0
	temp		db 0
	cur 		db 0
	startedToAlarmUser	db 1
	count 		db 60
	UIAlarmLabel 	db 'One minute alarm. ', 0
	clear		db '                     ', 0

decodeASCII  proc
	push 	cx

	mov     ah, al
        and     al, 0Fh

	mov 	cl, 4
        ror     ah, cl
	and     ah, 0Fh

        add     al, 30h
        add     ah, 30h
        mov     UIAlarmTime[bx + 1], ah
        mov     UIAlarmTime[bx + 2], al
        add     bx, 3

	pop cx	

        ret
decodeASCII endp

alarmDecodeHandler proc
	xor 	ah, ah
	mov 	bl, 10
	div 	bl
	
        add	ah, 30h 
	mov     UICurrentTime[7], ah

	xor 	ah, ah
	mov 	bl, 10
	div 	bl

	add     ah, 30h
	mov     UICurrentTime[6], ah

	ret
alarmDecodeHandler endp

clockHandler   proc                             ; процедура обработчика прерываний от таймера
        pushf                            ; создание в стеке структуры для IRET
        call    cs:old                   ; вызов старого обработчика прерываний
        push    ds                       ; сохранение модифицируемых регистров
        push    es
	push    ax
	push    bx
        push    cx
        push    dx
	push    di
        push    cs
        pop     ds

        mov     ah, 02h                 ; функция BIOS для получения текущего времени
        int     1Ah                      ; прерывание BIOS

        xor     bx, bx                  ; настройка BX на начало шаблона
        mov     al, ch                  ; в AL - часы
        call    decodeASCII                   ; вызов процедуры заполнения шаблона - часы
        mov     al, cl                  ; в AL - минуты
        call    decodeASCII                   ; вызов процедуры заполнения шаблона - минуты
        mov     al, dh                  ; в AL - секунды
	mov 	cur, dh
        call    decodeASCII                   ; вызов процедуры заполнения шаблона - секунды 
		
	mov 	al, cur
	mov 	ah, temp
		
	cmp 	al, ah
	je 	exit
		
	cmp 	startedToAlarmUser, 1
	jnz 	alarmUser
		
	cmp 	count, 0
	jne 	number
	mov 	startedToAlarmUser, 0
	jmp 	exit
		
number:	
	mov 	al, cur
	mov 	ah, temp
		
	cmp 	al, ah
	je 	exit

	dec 	count
	mov 	al, cur
	mov 	temp, al
	mov 	al, count

	call 	alarmDecodeHandler

	mov     ax, 0B800h              ; настройка AX на сегмент видеопамяти
        mov     es, ax                  ; запись в ES значения сегмента видеопамяти
        xor     di, di                  ; настройка DI на начало сегмента видеопамяти
        xor     bx, bx                  ; настройка BX на начало шаблона
        mov     ah, 1Bh                 ; атрибут выводимых символов
		
updateNewDateToCounterClock:
	mov     al, UIAlarmTime[bx]            ; цикл для записи символов шаблона в видеопамять
        stosw                            ; запись очередного символа и атрибута
        inc     bx                       ; инкремент указателя на символ шаблона
        cmp     UIAlarmTime[bx], 0             ; пока не конец шаблона,
        jnz     updateNewDateToCounterClock ; продолжать запись символов
		
	xor     bx, bx 

updateCurrentTime:	
	mov     al, UICurrentTime[bx]            ; цикл для записи символов шаблона в видеопамять
        stosw                            ; запись очередного символа и атрибута
        inc     bx                       ; инкремент указателя на символ шаблона
        cmp     UICurrentTime[bx], 0             ; пока не конец шаблона,
        jnz     updateCurrentTime        ; продолжать запись символов					
		
	jmp 	exit
alarmUser:	
	mov 	startedToAlarmUser, 0

	mov     ax, 0B800h              ; настройка AX на сегмент видеопамяти
        mov     es, ax                  ; запись в ES значения сегмента видеопамяти
        xor     di, di                  ; настройка DI на начало сегмента видеопамяти
        xor     bx, bx                  ; настройка BX на начало шаблона
        mov     ah, 1Bh                 ; атрибут выводимых символов
		
	xor     bx, bx 
showAlarmText:	
	mov     al, UIAlarmLabel[bx]            ; цикл для записи символов шаблона в видеопамять
        stosw                            ; запись очередного символа и атрибута
        inc     bx                       ; инкремент указателя на символ шаблона
        cmp      UIAlarmLabel[bx], 0             ; пока не конец шаблона,
        jnz     showAlarmText                     ; продолжать запись символов
exit:	
	pop     di                       ; восстановление модифицируемых регистров
        pop     dx
        pop     cx
        pop     bx
        pop     ax
        pop     es
        pop     ds
		
        iret       
		                      ; возврат из обработчика
clockHandler   endp                             ; конец процедуры обработчика
end_clock:                               ; метка для определения размера резидентной
                                         ; части программы
load:  
	mov     ax,  351Ch               ; получение адреса старого обработчика
        int     21h                      ; прерываний от таймера
        mov     word ptr old, bx        ; сохранение смещения обработчика
        mov     word ptr old + 2, es    ; сохранение сегмента обработчика
        mov     ax, 251Ch               ; установка адреса нашего обработчика
        mov     dx, offset clockHandler        ; указание смещения нашего обработчика
        int     21h                      ; вызов DOS
        mov     ax, 3100h               ; функция DOS завершения резидентной программы
        mov     dx, (end_clock - start + 10Fh) / 16 ; определение размера резидентной
                                                    ; части программы в параграфах
        int     21h                      ; вызов DOS
code    ends                             ; конец кодового сегмента
        end     start                    ; конец программы
