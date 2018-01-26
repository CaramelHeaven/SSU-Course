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
	}
	private: System::Void MyForm_Paint(System::Object^  sender, System::Windows::Forms::PaintEventArgs^  e) {
		Graphics^ g = e->Graphics;
		Rectangle rect = Form::ClientRectangle;
		Pen^ blackPen = gcnew Pen(Color::Black);
		Pen^ rectBlue = gcnew Pen(Color::Blue);
		blackPen->Width = 2;
		rectBlue->Width = 2;
		System::Drawing::Font^ drawFont = gcnew System::Drawing::Font("Arial", 12);
		SolidBrush^ drawBrush = gcnew SolidBrush(Color::Red);
		// отмечаем границы
		g->DrawRectangle(rectBlue, Wcx, top, Wx, Wy);
		point Pmin, Pmax;
		Pmin.x = Wcx;
		Pmin.y = Wcy - Wy;
		Pmax.x = Wcx + Wx;
		Pmax.y = Wcy;

		for (int i = 0; i < lines.Count; i++) {
			vec A, B;
			point2vec(lines[i].start, A);
			point2vec(lines[i].end, B);
			vec A1, B1;
			timesMatVec(T, A, A1);
			timesMatVec(T, B, B1);
			point a, b;
			vec2point(A1, a);
			vec2point(B1, b);

			if(clip(a,b,Pmin,Pmax))
			g->DrawLine(blackPen, a.x, a.y, b.x, b.y);
			{
				if (clip(a, b, Pmin, Pmax))
				{
					if (drawNames == true)
					{
						g->DrawString(lines[i].name, drawFont, drawBrush, (a.x + ((b.x - a.x) / 2)), (a.y + ((b.y - a.y) / 2)));
					}
				}
			}
		}
	}
	private: System::Void btnOpen_Click(System::Object^  sender, System::EventArgs^  e) {
		if (this->openFileDialog->ShowDialog() ==
			System::Windows::Forms::DialogResult::OK) {
			wchar_t fileName[1024];
			for (int i = 0; i < openFileDialog->FileName->Length; i++)
				fileName[i] = openFileDialog->FileName[i];
			fileName[openFileDialog->FileName->Length] = '\0';
			std::ifstream in;
			in.open(fileName);
			//cat - считывающий всх всу вх ву + юзаем функцию
			std::string readcat;
			getline(in, readcat);
			std::stringstream s2(readcat);
			float cat1, cat2, cat3, cat4;
			s2 >> cat1 >> cat2 >> cat3 >> cat4;
			set_hope(cat1, cat2, cat3, cat4);
			this->Refresh();
			if (in.is_open()) {
				lines.Clear();
				unit(T);
				std::string str;
				getline(in, str);
				while (in)
				{
					if ((str.find_first_not_of(" \t\r\n") != std::string::npos)
						&& (str[0] != '#')) {
						std::stringstream s(str);
						line l;
						s >> l.start.x >> l.start.y >> l.end.x >> l.end.y;
						l.start.y = 360 - l.start.y;
						l.end.y = 360 - l.end.y;
						std::string linename;
						s >> linename;
						l.name = gcnew String(linename.c_str());
						lines.Add(l);
					}
					getline(in, str);
				}
				//frame(Vcy, Vcx, Vx, Vy, Wcy, Wcx, Wx, Wy,mat c);
				void frame(float Vcy, float Vcx, float Vx, float Vy, float Wcy, float Wcx, float Wx, float Wy, mat c);
			}
			//mat T1;
			//set(T1, T);
			//frame(Vcy,Vcx,Vx,Vy,Wcy, Wcx,Wx,Wy,T);
			this->Refresh();
		}
	}
	private: System::Void MyForm_KeyDown(System::Object^  sender, System::Windows::Forms::KeyEventArgs^  e) {
		Rectangle rect = Form::ClientRectangle;
		int width = rect.Width;
		int height = rect.Height;
		mat R, T1, A, B, F, G;
		switch (e->KeyCode) {
			// MOVE MOVE MOVE MOVE
		case Keys::W:
			move(0, -15, R);
			break;
		case Keys::S:
			move(0, 15, R);
			break;
		case Keys::A:
			move(-15, 0, R);
			break;
		case Keys::D:
			move(15, 0, R);
			break;
			// ROTATE ROTATE ROTATE
		//case Keys::E:
			//
			//break;
		//case Keys::Z:
		//	rotateCenter(Wx/ 2, Wy / 2, -0.05, R);
			//break;
		//case Keys::Q:
			// SCALE ScALE SCALE
		//	new_scale_center(rect.Width / 2, rect.Height / 2, 1 / 1.1, R);
			//break;
		case Keys::I:
			move(0, -100, R);
			break;
		case Keys::O:
			move(0, 100, R);
			break;
		case Keys::K:
			move(-100, 0, R);
			break;
		case Keys::L:
			move(100, 0, R);
			break;
			// WINDOW WINDOW WINDOW WINDOW
		case Keys::C:
			move(-Wx/2-left, -Wy/2-top, R);
			times(R, T, T1);
			set(T1, T);
			rotate(-0.5, R);
			times(R, T, T1);
			set(T1, T);
			move(Wx / 2+left, Wy / 2+top, R);
			break;
		case Keys::V:
			move(-Wx / 2 - left, -Wy / 2 - top, R);
			times(R, T, T1);
			set(T1, T);
			rotate(0.5, R);
			times(R, T, T1);
			set(T1, T);
			move(Wx / 2 + left, Wy / 2 + top, R);
			break;
		case Keys::U:
			window(A);
			times(A, T, T1);
			set(T1, T);
			move(Wx, 0,  R);
			break;
		case Keys::J:
			window_h(A);
			move(0, -Wy-top, B);
			times(A, B, R);
			break;
		case Keys::R:
			move(-Wx / 2 - left, -Wy / 2 - top, R);
			times(R, T, T1);
			set(T1, T);
			scale(1 / 1.1, R);
			times(R, T, T1);
			set(T1, T);
			move(Wx / 2 + left, Wy / 2 + top, R);
			break;
		case Keys::Y:
			move(-Wx / 2 - left, -Wy / 2 - top, R);
			times(R, T, T1);
			set(T1, T);
			scale(1.1, R);
			times(R, T, T1);
			set(T1, T);
			move(Wx / 2 + left, Wy / 2 + top, R);
			break;
		case Keys::T:
			move(0, -height/2, R);
			times(R, T, T1);
			set(T1, T);
			scale_Y(1 / 1.1, R);
			times(R, T, T1);
			set(T1, T);
			move(0, height / 2, R);
			break;
		case Keys::G:
			move(0, -height/2, R);
			times(R, T, T1);
			set(T1, T);
			scale_Y(1.1, R);
			times(R, T, T1);
			set(T1, T);
			move(0, height / 2, R);
			break;
			// В ИСХОДКУ В ИСХОДКУ В ИСХОДКУ В ИСХОДКУ В ИСХОДКУ В ИСХОДКУ
		case Keys::Escape:
			unit(R);
			unit(T);
			//unit(T);
			window_h(A);
			move(0, -Wy - top, B);
			times(A, B, R);
			frame(Vx, Vy, Vcx, Vcy, Wx, Wy, Wcx, Wcy, T);
			//window_h(A);
			//move(0, -Wy - top, B);
			//times(A, B, R);
			break;
			//for изменения значения параметра drawNames; false and true
		case Keys::P:
			if (drawNames)
			{
				drawNames=false;
			}
			else
			{
				drawNames = true;
			}
			unit(R);
			break;
		case Keys::F:
			move(0, -height/2, R);
			times(R, T, T1);
			set(T1, T);
			scale_X(1 / 1.1, R);
			times(R, T, T1);
			set(T1, T);
			move(0, height / 2, R);
			break;
		case Keys::H:
			move(0, -height/2, R);
			times(R, T, T1);
			set(T1, T);
			scale_X(1.1, R);
			times(R, T, T1);
			set(T1, T);
			move(0, height / 2, R);
			break;
		default:
			unit(R);
		}
		times(R, T, T1);
		set(T1, T);
		this->Refresh();
	}
private: System::Void MyForm_Resize(System::Object^  sender, System::EventArgs^  e) 
{
	// save oldWx and oldWy + so we changing their
	float oldWx = Wx, oldWy = Wy;
	mat R, T1;
	Wcx = left;
	Wcy = Form::ClientRectangle.Height - bottom;
	Wx = Form::ClientRectangle.Width - left - right;
	Wy = Form::ClientRectangle.Height - top - bottom;
	new_scale((Wx / oldWx), (Wy / oldWy), Wcx, top, R);
	times(R, T, T1);
	set(T1, T);
	this->Refresh();
}
};
}
