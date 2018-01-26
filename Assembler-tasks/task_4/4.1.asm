.model small
.stack 100h
.386 ;���������� ���������� ������ ���������� 386
.data
    mas dw 2*10 dup (0) ;�������� ������
.code
 
start:
    mov ax, @data
    mov ds, ax
 
    mov ax, 0 ;��������� ax
    mov cx, 10 ;�������� �������� �����
 
    mov ax, 5 ; ������� ��� �������� �������
    mov si, 0 ;������ ���������� �������� � cx
 
    mov bx, 2*10 ;�������� �� ��������� ������ �������
 
go: ;���� �������������
    mov mas[si], ax ;������ � ������
    push ax
    push dx
    mul ax      ;���������� ����� � �������
    mov mas[si+bx], ax ;���������� �������� ����� �� ������ ������ �������
    pop dx
    pop ax
    add ax, 5 ;���������� �������� ���������� �������� ������� �� 5
    add si, 2 ;������� � ���������� ��������
loop go ;��������� ����
 
    mov si, 0 ;������� � 1-�� ��������
    mov cx, 2 ;��� �������
 
show1: ;���� ������ ��������� ������� �� �����
    push cx
    mov cx, 10 ;��� ������� ���������� ��
 
show2:
    mov ax, mas[si]     ;�������� �������� �������
                        ;���������� � AX
 
    call print ; ����� ������� ������ ����� � AX �� �����
    add si, 2 ;������� � ���������� ��������
loop show2
    pop cx
 
    mov dl, 0Dh-30h ;������� �� ��������� ������
    call pr
 
    mov dl, 0Ah-30h
    call pr
 
loop show1
 
    mov ax,4C00h ;���������� ���������
    int 21h
 
print proc
;��������� ��� ������ �������� ������� �� �����
    pusha
;����� �������� ����� ������ ������
    pusha
    mov bx, 0
 
label0:
    inc bx
 
    mov dx, 0
    mov cx, 10
    div cx
    cmp ax, 1
    jnc label0
    neg bx
    add bx, 6
    mov cx, bx
 
lab:
    mov dl, 0D0h
    call pr
loop lab
    popa
;����� ������� �� �����
mov cx, 0
 
label1:
    mov dx, 0
    mov bx, 10 ;������� ��������� ������� ���������, ������ 10, � ������� BX
 
    div bx ;������� AX �� BX
    mov bx, 0
 
    push dx
    inc cx
 
    cmp ax, 0 ;��������� AX � ����
    jnz label1 ;���� ������� �����������, �� ������� �� ����� label1
 
label2:
    pop dx
    call pr ;����� �� ����� �������
 
    inc bx
    loop label2 ;���������� � ����� label2, ���� �������� CX �� ������ ����� ����
 
    popa
    ret
print endp
 
pr proc ;���������, ��������� ������ �� �����
    mov ah, 02h
    add dl, 30h
 
    int 21h
    ret
pr endp
 
end start