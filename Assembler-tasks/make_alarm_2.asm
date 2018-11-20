code    segment
        assume  cs:code,ds:code
        org     100h
	.386
		
start:  jmp     load 

        old     	dd 0
        startTimer  	db 'Start timer:  00:00:00', 0
	stopTimer	db 'Stop timer:  00:00:00', 0
	hour		db 0
	min		db 0
	sec		db 0
                                                                                         
shortDecode proc
	push 	cx

	mov  	ah, al
        and   	al, 0Fh
	mov 	cl, 4
        ror    	ah, cl
	and   	ah, 0Fh
        add   	al, 30h
        add   	ah, 30h  

	pop 	cx		
		
	ret
shortDecode endp	

clockHandler proc
        pushf
        call  	cs:old
        push  	ds
        push  	es
	push  	ax
	push  	bx
        push  	cx
        push  	dx
	push  	di
        push  	cs
        pop   	ds

	mov     ah, 02h
        int     1Ah 

	mov 	al, ch
	call 	shortDecode
	mov 	stopTimer[13], ah
	mov 	stopTimer[14], al

	mov 	al, cl
	call 	shortDecode
	mov 	stopTimer[16], ah
	mov 	stopTimer[17], al

	mov 	al, dh
	call 	shortDecode
	mov 	stopTimer[19], ah
	mov 	stopTimer[20], al
	
        mov   	ax, 0B800h
        mov  	es, ax
        mov	di, 48
        xor    	bx, bx
        mov   	ah, 1Bh
		
	xor 	bx, bx 
b:	mov     al, stopTimer[bx]
        stosw
        inc     bx
        cmp     stopTimer[bx], 0
        jnz     b
				
	pop     di 
        pop     dx
        pop     cx
        pop     bx
        pop     ax
        pop     es
        pop     ds
		
        iret
clockHandler endp
end_clock:

load:   
	mov     ah, 02h
        int     1Ah 
	mov 	hour, ch
	mov 	min, cl
	mov	sec, dh		

	mov     ah, 07h 
        int     1Ah
	
	inc 	cl
	
	mov	ah, 06h
	int 	1Ah

	mov 	al, hour
	call 	shortDecode
	mov 	startTimer[14], ah
	mov 	startTimer[15], al

	mov 	al, min
	call 	shortDecode
	mov 	startTimer[17], ah
	mov 	startTimer[18], al

	mov 	al, sec
	call 	shortDecode
	mov 	startTimer[20], ah
	mov 	startTimer[21], al

	mov   	ax, 0B800h
        mov  	es, ax
        xor    	di, di
        xor    	bx, bx
        mov   	ah, 1Bh
		
	xor     bx, bx 
a:	mov     al, startTimer[bx]
        stosw
        inc     bx
        cmp     startTimer[bx], 0
        jnz     a  

	mov     ax,  354Ah  
        int     21h 
        mov     word ptr old, bx 
        mov     word ptr old + 2, es
        mov     ax, 254Ah
        mov     dx, offset clockHandler
        int     21h 
        mov     ax, 3100h 
        mov     dx, (end_clock - start + 10Fh) / 16 

        int     21h 
code    ends 
end     start 
