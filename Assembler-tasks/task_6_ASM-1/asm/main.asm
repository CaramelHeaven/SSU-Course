extrn ExitProcess: PROC
extrn lstrlenA: PROC
extrn WriteConsoleA: PROC
extrn ReadConsoleA: PROC
extrn GetStdHandle: PROC
extrn MessageBoxA: PROC

.data
caption db 'This is windows', 0
message db 'asas', 0

.code

Start PROC
	sub rsp, 28h

	mov rcx, 0
	lea rdx, message
	lea r8, caption
	mov r9d, 0

	call MessageBoxA

	mov ecx, 0

	call ExitProcess

Start ENDP
End