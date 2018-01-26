#pragma once

namespace _1last {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	/// <summary>
	/// Summary for MyForm
	/// </summary>
	public ref class MyForm : public System::Windows::Forms::Form
	{
	public:

		MyForm(void)
		{
			InitializeComponent();
			//
			//TODO: Add the constructor code here
			//
		}

	protected:
		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		~MyForm()
		{
			if (components)
			{
				delete components;
			}
		}
	private:
		System::Collections::Generic::List<line>lines;
		float left, right, top, bottom;
		float Wcx, Wcy, Wx, Wy;
		float Vcx, Vcy, Vx, Vy;
		// function сюда входят кэты- потом меняются на хоупы эти значения сэйвим
		void set_hope(float hope1, float hope2, float hope3, float hope4) 
		{
			Vcx = hope1;
			Vcy = hope2;
			Vx = hope3;
			Vy = hope4;
		}
		bool drawNames;
		// enter old and save their
		float VcyOld, VcxOld, VxOld, VyOld;
		float kold, lold;
		float app, qpp;
	
	private: System::Windows::Forms::OpenFileDialog^  openFileDialog;
	private: System::Windows::Forms::Button^  btnOpen;

	private:
		/// <summary>
		/// Required designer variable.
		/// </summary>
		System::ComponentModel::Container ^components;


#pragma region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		void InitializeComponent(void)
		{
			this->openFileDialog = (gcnew System::Windows::Forms::OpenFileDialog());
			this->btnOpen = (gcnew System::Windows::Forms::Button());
			this->SuspendLayout();
			// 
			// openFileDialog
			// 
			this->openFileDialog->DefaultExt = L"txt";
			this->openFileDialog->FileName = L"openFileDialog1";
			this->openFileDialog->Filter = L"Текстовые файлы(*.txt)|*.txt|Все файлы (*.*)|*.*";
			this->openFileDialog->Title = L"Открыть файл";
			// 
			// btnOpen
			// 
			this->btnOpen->Anchor = static_cast<System::Windows::Forms::AnchorStyles>((System::Windows::Forms::AnchorStyles::Top | System::Windows::Forms::AnchorStyles::Right));
			this->btnOpen->BackColor = System::Drawing::SystemColors::InactiveBorder;
			this->btnOpen->Location = System::Drawing::Point(414, -1);
			this->btnOpen->Name = L"btnOpen";
			this->btnOpen->Size = System::Drawing::Size(70, 23);
			this->btnOpen->TabIndex = 0;
			this->btnOpen->Text = L"Открыть";
			this->btnOpen->UseVisualStyleBackColor = false;
			this->btnOpen->Click += gcnew System::EventHandler(this, &MyForm::btnOpen_Click);
			// 
			// MyForm
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->ClientSize = System::Drawing::Size(484, 261);
			this->Controls->Add(this->btnOpen);
			this->KeyPreview = true;
			this->MinimumSize = System::Drawing::Size(100, 293);
			this->Name = L"MyForm";
			this->Text = L"MyForm";
			this->Load += gcnew System::EventHandler(this, &MyForm::MyForm_Load);
			this->Paint += gcnew System::Windows::Forms::PaintEventHandler(this, &MyForm::MyForm_Paint);
			this->KeyDown += gcnew System::Windows::Forms::KeyEventHandler(this, &MyForm::MyForm_KeyDown);
			this->Resize += gcnew System::EventHandler(this, &MyForm::MyForm_Resize);
			this->ResumeLayout(false);

		}

#pragma endregion
	private: System::Void MyForm_Load(System::Object^  sender, System::EventArgs^  e) {
		unit(T);
		lines.Clear();
		// zadaem znachenia left, bottom... because чтобы было нормальное окно
		left = 50;
		bottom = 50;
		top = 50;
		right = 350;
		Wcx = left;
		Wcy = Form::ClientRectangle.Height - bottom;
		Wx = Form::ClientRectangle.Width - left - right;
		Wy = Form::ClientRectangle.Height - top - bottom;
		drawNames != drawNames;
		Vcx = -5;
		Vcy = -5;
		Vx = 10;
		Vy = 10;
		qpp = 15;
		app = 15;
		VcyOld = Vcy; VcxOld = Vcx; VxOld = Vx; VyOld = Vy; kold= app; lold = qpp;
	}
	private: System::Void MyForm_Paint(System::Object^  sender, System::Windows::Forms::PaintEventArgs^  e) {
		Graphics^ g = e->Graphics;
		Pen^ blackPen = gcnew Pen(Color::Red);
		Pen^ rectBlue = gcnew Pen(Color::Blue);
		blackPen->Width = 2;
		rectBlue->Width = 5;
		System::Drawing::Font^ drawFont = gcnew System::Drawing::Font("Arial", 12);
		SolidBrush^ drawBrush = gcnew SolidBrush(Color::Red);
		// отмечаем границы
		g->DrawRectangle(rectBlue, Wcx, top, Wx, Wy);
		point Pmin, Pmax;
		Pmin.x = left;//Wcx
		Pmin.y = top;//Wcy+Wy
		Pmax.x = Form::ClientRectangle.Width - right;//Wcx+Wx;
		Pmax.y = Form::ClientRectangle.Height - bottom;//Wcy;
		Pen^ setPen = gcnew Pen(Color::Black);
		setPen->Width = 1;

		float doge;//setka
		point vLines_start, vLines_end;


		for (int i = 1; i<qpp + 1; i++)
		{
			doge= (Vx / (qpp + 1))*i + Vcx;
			vLines_start.x = vLines_end.x = Wcx + ((doge - Vcx) / Vx)*Wx; 
			vLines_start.y = Pmin.y;  
			vLines_end.y = Pmax.y;

			g->DrawLine(setPen, vLines_start.x, vLines_start.y, vLines_end.x, vLines_end.y);
			g->DrawString(doge.ToString("F"), drawFont, drawBrush, vLines_start.x - 15, Form::ClientRectangle.Height - Wy - bottom - top);
		}

		point gLines_start, gLines_end;

		for (int i = 1; i<app + 1; i++)
		{
			doge = (Vy / (app + 1))*i + Vcy; 
			gLines_start.y = gLines_end.y = Wcy - (((doge - Vcy) / Vy)*Wy); 
			gLines_start.x = Pmin.x;  
			gLines_end.x = Pmax.x;

			g->DrawLine(setPen, gLines_start.x, gLines_start.y, gLines_end.x, gLines_end.y);
			g->DrawString(doge.ToString("F"), drawFont, drawBrush, Form::ClientRectangle.Width - Wx - right - left + 10, gLines_start.y);
		}
		Rectangle rect = Form::ClientRectangle;

		point a, b;

		float X1,Y1;
		a.x = Wcx; X1 = Vcx;

		bool visible1, visible2;

		if (fexists(X1))
		{
			visible1 = true;
			Y1 = f(X1);
			a.y = Wcy - ((Y1 - Vcy) / Vy)*Wy;
		}
		else visible1 = false;

		while (a.x < Wcx + Wx)
		{
			X1 = (a.x + 1 - Wcx) / Wx*Vx + Vcx;
			if (fexists(X1))
			{
				visible2 = true;
				Y1 = f(X1);
				b.y = Wcy - ((Y1 - Vcy) / Vy)*Wy;
			}
			else visible2 = false;
			b.x = a.x + 1;

			if (visible1 && visible2) {
				if (clip(a, b, Pmin, Pmax))
					g->DrawLine(blackPen, a.x, a.y, b.x, b.y);
			}

			b.x = a.x + 1;
			a.x = b.x; 
			a.y = b.y; 
			visible1 = visible2;
		}
		//the end alg.
	}
	private: System::Void btnOpen_Click(System::Object^  sender, System::EventArgs^  e) {
		//if (this->openFileDialog->ShowDialog() ==
		//	System::Windows::Forms::DialogResult::OK) {
		//	wchar_t fileName[1024];
		//	for (int i = 0; i < openFileDialog->FileName->Length; i++)
		//		fileName[i] = openFileDialog->FileName[i];
		//	fileName[openFileDialog->FileName->Length] = '\0';
		//	std::ifstream in;
		//	in.open(fileName);
		//	//cat - считывающий всх всу вх ву + юзаем функцию
		//	std::string readcat;
		//	getline(in, readcat);
		//	std::stringstream s2(readcat);
		//	float cat1, cat2, cat3, cat4;
			//s2 >> cat1 >> cat2 >> cat3 >> cat4;
	//		set_hope(cat1, cat2, cat3, cat4);
		//	this->Refresh();
		//	if (in.is_open()) {
		//		lines.Clear();
		//		unit(T);
		//		std::string str;
		//		getline(in, str);
		//		while (in)
		//		{
		//			if ((str.find_first_not_of(" \t\r\n") != std::string::npos)
		//				&& (str[0] != '#')) {
		//				std::stringstream s(str);
		//				line l;
		//				s >> l.start.x >> l.start.y >> l.end.x >> l.end.y;
		//				l.start.y = 360 - l.start.y;
		//				l.end.y = 360 - l.end.y;
		//				std::string linename;
		//				s >> linename;
		//				l.name = gcnew String(linename.c_str());
		///				lines.Add(l);
		//			}
		//			getline(in, str);
		//		}
		//		//frame(Vcy, Vcx, Vx, Vy, Wcy, Wcx, Wx, Wy,mat c);
		//		void frame(float Vcy, float Vcx, float Vx, float Vy, float Wcy, float Wcx, float Wx, float Wy, mat c);
		//	}
			//mat T1;
			//set(T1, T);
			//frame(Vcy,Vcx,Vx,Vy,Wcy, Wcx,Wx,Wy,T);
		//	this->Refresh();
		//}
	}
	private: System::Void MyForm_KeyDown(System::Object^  sender, System::Windows::Forms::KeyEventArgs^  e) {
		Rectangle rect = Form::ClientRectangle;

		float Wp = (1 / Wx)*Vx;
		float Hp = (1 / Wy)*Vy;
		switch (e->KeyCode)
		{
		case Keys::Z:
			if (app>0) app--;
			break;
		case Keys::C:
			app++;
			break;
		case Keys::I:
			Vx = Vx-Wp;
			Vcx = Vcx+Wp/2;
			break;
		case Keys::O:
			Vx = Vx+Wp;
			Vcx = Vcx - Wp / 2;
			break;
		case Keys::K:
			Vy = Vy+ Hp;
			Vcy = Vcy - Hp / 2;
			break;
		case Keys::L:
			Vy = Vy -Hp;
			Vcy = Vcy + Hp / 2;
			break;
		case Keys::F:
			Vcy = Vcy + 1;
			break;
		case Keys::T:
			Vcy = Vcy - 1;
			break;
		case Keys::W:
			Vcy =Vcy+ Hp;
			break;
		case Keys::S:
			Vcy = Vcy+Hp;
			break;
		case Keys::D:
			Vcx = Vcx-Wp;
			break;
		case Keys::A:
			Vcx =Vcx+ Wp;
			break;
		case Keys::G:
			Vcx =Vcx -1;
			break;
		case Keys::H:
			Vcx =Vcx+ 1;
			break;
		case Keys::Q:
			if (qpp>0) qpp--;
			break;
		case Keys::E:
			qpp++;
			break;
		case Keys::Escape:
			Vcx = VcxOld;
			Vcy = VcyOld;
			Vx = VxOld;
			Vy = VyOld;
			app = kold;
			qpp = lold;
			break;

		}

		this->Refresh();
	}
private: System::Void MyForm_Resize(System::Object^  sender, System::EventArgs^  e) 
{
	Rectangle rect = Form::ClientRectangle;
	// save oldWx and oldWy + so we changing their
	//float oldWx = Wx, oldWy = Wy;
	//mat R, T1;
	Wcx = left;
	Wcy = Form::ClientRectangle.Height - bottom;
	Wx = Form::ClientRectangle.Width - left - right;
	Wy = Form::ClientRectangle.Height - top - bottom;
	//new_scale((Wx / oldWx), (Wy / oldWy), Wcx, top, R);
	//times(R, T, T1);
	//set(T1, T);
	this->Refresh();
}
};
}
