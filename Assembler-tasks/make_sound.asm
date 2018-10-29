.model small
.stack 100h
.186
.data
        	massiveSounds db 1, 1, 1, 1, 1, 3, 3, 1, 3, 1, 3, 3, 1, 1, 1, 1, 1
		freq dw 500
.code
 
Start:
		mov 	ax, @data
		mov 	ds, ax
		
		xor 	bx, bx
		call 	sos_require
 
        mov ax,4C00h
        int 21h

sos_require proc
	push	ax
	push	dx
	push 	cx
		
	mov 	cx, 17
oneBeep:		
	call 	MakeSound
	push 	cx
	xor 	cx, cx
	mov	cl, massiveSounds[bx]
sound:		
	push 	cx
	mov 	cx, 2h
	mov 	ah, 86h
	int 	15h
	pop 	cx
	loop 	sound
	pop 	cx
	inc 	bx
			
	call 	DisableSound
	push 	cx
	xor 	cx, cx
	mov	cl, massiveSounds[bx]
noSound:
	push 	cx
	mov 	cx, 2h
	mov 	ah, 86h
	int 	15h
	pop 	cx
	loop 	noSound
		pop 	cx
	inc 	bx
	loop 	oneBeep
		
	pop 	cx
	pop 	dx
	pop 	ax

	ret
sos_require endp
 
MakeSound proc 
	push 	ax 
	push 	bx
	push 	dx
	
	mov 	bx,freq 	
	mov 	ax,34DDh
	mov 	dx,12h 	

	cmp 	dx,bx 
	jnb 	exit 

	div 	bx
	mov 	bx,ax

	in 	al,61h 
	or 	al,11b 
	out 	61h,al
	mov 	al,00001011b 	

	mov 	dx,43h
	out 	dx,al
	dec 	dx
	mov 	al,bl
	out 	dx,al 	
	mov 	al,bh
	out 	dx,al 
exit:
	pop 	dx 
	pop 	bx
	pop 	ax

	ret
MakeSound endp

DisableSound proc 
	push 	ax
		
	in 	al, 61h 
	and 	al, not 11b
	out 	61h, al
		
	pop 	ax
		
	ret
DisableSound endp
 
end start
