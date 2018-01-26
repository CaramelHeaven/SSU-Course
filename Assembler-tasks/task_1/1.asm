stak segment stack 'stack'      
db 256 dup (?)                  
stak ends                       
data segment 'data'             
	Hello db 'Hello, World!$'       
data ends                       
code segment 'code'             
	assume CS:code,DS:data,SS:stak  
                                
start:                          

	mov AX,data                     
	mov DS,AX                       
	mov AH,09h                      
	mov DX,offset Hello             
	int 21h                         
	mov AX,4C00h                    
	int 21h        
                 
code ends                       
end start                       