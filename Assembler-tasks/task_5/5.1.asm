.model small
.stack 100h
.186
.data
;����������� ����������:
    ;row - ������, � ������� ��������� ������, ��������� �������� ����� 4
    ;col - �������, � ������� ��������� ������, ��������� �������� ����� 24
    ;mode - ����� ������, ��������� �������� �� ���������� (����� ?)
    ;char_ctr - ������� ���2-��������, ��������� �������� ����� 0
        row db 4
        col db 24
        mode db (?)
        char_ctr db 0
.code
 
Start:
    mov ax,@data
    mov ds,ax
 
;Code main programm

        call B10MODE
        call C10CLEAR       

c1:
        call D10CURSOR
        call E10DISPlAY
        
        cmp char_ctr,255
        jz c2

        inc char_ctr
        add col,2
        
        cmp col,56
        jne c1
        
        add row,1
        mov col,24
        jne c1
c2:      
        mov ah,10h
        int 16h
 
        mov ax,4C00h
        int 21h
 
B10MODE proc    ;���������/��������� �����������
 
    mov ah,0Fh
 
    int 10h
    
    mov mode,al
 
    mov ah,00h
    mov al,03h
 
    int 10h
 
    ret
    B10MODE endp
 
C10CLEAR proc      ;������� ������ 
    pusha
 
    mov ah,06h
    mov dx,184Fh
 
    int 10h
 
    mov ah,0
    mov ah,06h ;�������?
    mov al,10h ;������ 10
    
    mov BH,01h ;������ ���, ����� �������
    mov cx, 0424h ;���������� ������ �������� ����
 
    mov dx,1954h ; ������ ������ � ��, ����������������� ��� ����� �����!
 
    int 10h
 
    popa
    
    ret 
    C10CLEAR endp
 
D10CURSOR proc     ;��������� ������� 
 
    pusha
 
    mov ah,02h
    mov bh,0
    mov dh,row
    mov dl, col
    int 10h
        
        popa
 
    ret
    D10CURSOR endp
 
E10DISPLAY proc    ;����� ������� �� �����
 
        pusha
        mov ah,0Ah
        mov al,char_ctr
        mov bh,0
        mov cx,1
        int 10h
        
        popa
        
    ret 
    E10DISPLAY endp
 
end start