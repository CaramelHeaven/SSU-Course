extrn ExitProcess: proc 
extrn lstrlenA: proc 
extrn WriteConsoleA: proc 
extrn ReadConsoleA: proc 
extrn GetStdHandle: proc 

; Фрагмент описания переменных

.data 
	STD_OUTPUT_HANDLE dq -11
	STD_INPUT_HANDLE dq -10
	strokaA db "A = ", 0							;
	strokaB db "B = ", 0							; строки
	strokaAB db "F = 369 + ( 3856 + A - B ) = ", 0						; пользовательского
	inv_char db "Invalid character", 0			; интерфейса
	exit_key db 0Dh, 0Ah, 'Press key, please...',0 	;
	
.data? 
	hStdInput dq ?		; дескрипторы ввода
	hStdOutput dq ?		; и вывода
	sum dq ?			; сумма a + b

; Фрагмент кода	
	
.code 

; Макросы

stackalloc macro arg ; выделение места под параметры стека и выравнивание стека
	push r15 
	mov r15, rsp 
	and spl, 0f0h 
	sub rsp, 8 * 4

	if arg 
		sub rsp, 8 * arg
	endif 
endm stackalloc 

stackfree macro ; освобождение памяти
	mov rsp, r15 
	pop r15
endm stackfree 

null_fifth_arg macro ; обнулить пятый аргумент 
	mov qword ptr [rsp + 32], 0
endm null_fifth_arg


; Основная программа 

Start proc 

	local const_1:qword ; константы
	local const_2:qword ;?

	mov const_1,171h ;по заданию
	mov const_2,0F10h ;тоже

	stackalloc 2 
	
	mov rcx, STD_OUTPUT_HANDLE ; поток вывода
	call GetStdHandle ; получим значение дескриптора потока вывода
	mov hStdOutput, rax ; значение дескриптора из RAX в hStdOutput
	
	mov rcx, STD_INPUT_HANDLE ; поток ввода
	call GetStdHandle ; получим значение дескриптора потока ввода
	mov hStdInput, rax ; значение дескриптора из RAX в hStdInput 
	
	
	mov rax, const_1
	add sum, rax
	
	mov rax, const_2
	add sum, rax
	
	lea rax, strokaA
	push rax
	; Вызов MessageBoxA

	call PrintString ; вывод "a="
	call read_signed_number ; чтение значения числа а

	cmp R10, 0
		je wrong_exit
	
	add sum, rax

	lea rax, strokaB
	push rax

	call PrintString ; вывод "b="
	call read_signed_number ; чтение значения числа b
	
	cmp R10, 0
		je wrong_exit

	add sum, rax


	lea rax, strokaAB
	push rax
	
	call PrintString ; вывод "a+b="	
	call write_signed_number ; вывод значения a+b
	jmp exit
	
wrong_exit: ; выход из программы, если введен неверный символ
	lea rax, inv_char
	push rax
	call PrintString
	call WaitInput
	call ExitProcess
	
exit: ; успешное завершение работы программы
	call WaitInput
	call ExitProcess 

Start endp

; Процедуры
	
PrintString proc uses rax rcx rdx r8 r9 str_pointer: qword ; Вывод строки 

	local bytesWritten: qword 
	
	stackalloc 1 
	mov rcx, str_pointer 
	call lstrlenA 
	mov rcx, hStdOutput 
	mov rdx, str_pointer
	mov r8, rax 
	lea r9, bytesWritten 
	null_fifth_arg
	call WriteConsoleA 
	stackfree 
	
	ret 8 
PrintString endp 
	
read_signed_number proc; Чтение знакового числа из строки 

	local readStr[64]:BYTE ; строка считанных символов
	local bytesRead:DWORD ; число прочитанных байт
	
	stackalloc 2 
	;mov rdx, 0 
	mov rcx, hStdInput 
	lea rdx, readStr 
	mov r8, 64d 
	lea r9, bytesRead 
	null_fifth_arg 
	call ReadConsoleA 
	
	mov rcx, 0 
	mov ecx, bytesRead 
	sub rcx, 2 ; избавились от символов переноса и возврата каретки
	mov readStr[rcx], 0 ; дописали в конце ноль
	mov rbx, 0 
	mov r8, 1 
	
	
new_digit: 
	dec rcx 
	cmp rcx, -1 
		je scanComplete 
	mov rax, 0 
	mov al, readStr[rcx] ; очередной символ в AL 
	cmp al, '-' 
		jne eval ; ????????????
	neg rax ; инверсия RAX
	jmp scanComplete 
eval: 			; проверка, я вляется ли цифра валидной десятичной (от 0 до 9)
	cmp al, 30h
		jl Error
	cmp al, 39h 
		jg Error 
	
	sub rax, 30h ; получаем число из кода 
	mul r8 
	add rbx, rax 
	mov rax, 10 
	mul r8 
	mov r8, rax 
	jmp new_digit 
Error: 
	mov r10, 0 
	stackfree 
	ret 8*2 
	
scanComplete: 
	mov r10, 1 
	mov rax, rbx 
	stackfree 
	ret 8*2 
	
read_signed_number endp 


write_signed_number proc uses rax rcx rdx r8 r9  ; вывод знакового числа 

	local numberStr[22]:BYTE 

	mov r8,0 
	mov rax, sum 
	cmp rax, 0 ; сравниваем число с нулем
		ja Next_Step; дописываем "-" перед числом, если отрицательное
	mov numberStr[r8], '-'
	inc r8
	neg rax
	
Next_Step:
	mov rbx, 10
	xor rcx, rcx

division: 		; деление на цифры 
	mov rdx, 0 
	div rbx 
	add rdx, 30h ; добавляем код нуля 
	push rdx 
	inc rcx 
	cmp rax, 0 
		jne division
	
stack2variable: 			; перенос из стека в переменную
		pop rdx ; берем из стека цифру
		mov numberStr[r8], dl ; заносим число в переменную со смещением, которое в r8
		inc r8 
	loop stack2variable 
	
	mov numberStr[r8], 0 ; нуль-терминатор в конце строки
	lea rax, numberStr ; адрес начала строки в RAX
	push rax 
	call PrintString ; вывод полученной строки
	stackfree 
	ret 8 

write_signed_number endp 


WaitInput proc ; ожидание ввода перед выходом 

	local readStr:BYTE 
	local bytesRead:DWORD 
	
	lea rax, exit_key 
	push rax
	call PrintString
	
	stackalloc 1 
	mov rcx, hStdInput 
	lea rdx, readStr 
	mov r8, 1 
	lea r9, bytesRead 
	null_fifth_arg
	call ReadConsoleA 
	
	stackfree 
	ret 8 
	
WaitInput endp 

end