// P007_Clipping polygons.cpp: ������� ���� �������.

#include "stdafx.h"
#include <array>
#include <vector>
#include <stack>
#include "Transform.h"
#include "PClip.h"
#include <fstream>
#include <sstream>
#include "Form1.h"

using namespace P007_Clippingpolygons;

[STAThreadAttribute]
int main(array<System::String ^> ^args)
{
	// ��������� ���������� �������� Windows XP �� �������� �����-���� ��������� ����������
	Application::EnableVisualStyles();
	Application::SetCompatibleTextRenderingDefault(false); 

	// �������� �������� ���� � ��� ������
	Application::Run(gcnew Form1());
	return 0;
}
