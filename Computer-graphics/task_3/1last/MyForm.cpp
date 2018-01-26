#include "Transform.h"
#include <fstream>
#include <sstream>
#include "MyForm.h"

using namespace System;
using namespace System::Windows::Forms;


[STAThread]
void Main(array<String^>^ args)
{
	Application::EnableVisualStyles();
	Application::SetCompatibleTextRenderingDefault(false);

	_1last::MyForm form;
	Application::Run(%form);
}
