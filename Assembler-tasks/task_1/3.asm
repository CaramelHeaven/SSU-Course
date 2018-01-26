.model tiny               
                          
.code                     
org 100h                                   

start:
	mov AH,09h
	mov DX,offset Hello
	int 21h
	mov AX,4C00h
	int 21h
;===== Data =====
Hello db 'Hello, World!$'
end start 