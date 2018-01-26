.model small
.stack 100h
.386 ;Разрешение трансляции команд процессора 386
.data
    mas dw 2*10 dup (0) ;исходный массив
.code
 
start:
    mov ax, @data
    mov ds, ax
 
    mov ax, 0 ;обнуление ax
    mov cx, 10 ;значение счетчика цикла
 
    mov ax, 5 ; счётчик для значений массива
    mov si, 0 ;индекс начального элемента в cx
 
    mov bx, 2*10 ;смещение на следующую строку массива
 
go: ;цикл инициализации
    mov mas[si], ax ;запись в массив
    push ax
    push dx
    mul ax      ;возведение числа в квадрат
    mov mas[si+bx], ax ;сохранение квадрата числа во второй строке матрицы
    pop dx
    pop ax
    add ax, 5 ;увеличение значения следуещего элемента массива на 5
    add si, 2 ;переход к следующему элементу
loop go ;повторить цикл
 
    mov si, 0 ;переход к 1-му элементу
    mov cx, 2 ;ето строчки
 
show1: ;цикл вывода элементов массива на экран
    push cx
    mov cx, 10 ;ето столбцы количество их
 
show2:
    mov ax, mas[si]     ;значение элемента массива
                        ;помещается в AX
 
    call print ; вызов функции вывода числа в AX на экран
    add si, 2 ;переход к следующему элементу
loop show2
    pop cx
 
    mov dl, 0Dh-30h ;переход на следующую строку
    call pr
 
    mov dl, 0Ah-30h
    call pr
 
loop show1
 
    mov ax,4C00h ;завершение программы
    int 21h
 
print proc
;процедура для вывода элеманта массива на экран
    pusha
;вывод пробелов перед каждым числом
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
;вывод символа на экран
mov cx, 0
 
label1:
    mov dx, 0
    mov bx, 10 ;Заносим основание системы счисления, равное 10, в регистр BX
 
    div bx ;деление AX на BX
    mov bx, 0
 
    push dx
    inc cx
 
    cmp ax, 0 ;сравнение AX с нулём
    jnz label1 ;если условие выполняется, то переход на метку label1
 
label2:
    pop dx
    call pr ;вывод на экран символа
 
    inc bx
    loop label2 ;повторение с метки label2, пока значение CX не станет равно нулю
 
    popa
    ret
print endp
 
pr proc ;процедура, выводящая символ на экран
    mov ah, 02h
    add dl, 30h
 
    int 21h
    ret
pr endp
 
end start