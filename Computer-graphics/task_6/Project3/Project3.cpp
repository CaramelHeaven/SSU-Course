// Project3.cpp: главный файл проекта.
#include "stdafx.h"
#include <fstream>
#include <sstream>
#include <array>
#include <vector>
#include <stack>
#include "Transform.h"
#include "clip.h"
#include "Form1.h"

using namespace Project3;

[STAThreadAttribute]
int main(array<System::String ^> ^args)
{
	// Включение визуальных эффектов Windows XP до создания каких-либо элементов управления
	Application::EnableVisualStyles();
	Application::SetCompatibleTextRenderingDefault(false); 

	// Создание главного окна и его запуск
	Application::Run(gcnew Form1());
	return 0;
}
