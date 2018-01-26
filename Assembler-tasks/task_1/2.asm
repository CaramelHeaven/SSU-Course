.model small                 
                             
.stack 100h                  
.data                        
	Hello db 'Hello, World!$'
.code                        
start:                       
                             
                             
	mov AX, @data                
	mov DS,AX                    
	mov AH,09h
	mov DX,offset Hello
	int 21h
	mov AX,4C00h
	int 21h
end start 