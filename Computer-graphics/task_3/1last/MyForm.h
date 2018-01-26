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
			this->btnOpen->BackColor = System::Drawing::SystemColors::InactiveBorder;
			this->btnOpen->Location = System::Drawing::Point(634, 222);
			this->btnOpen->Name = L"btnOpen";
			this->btnOpen->Size = System::Drawing::Size(75, 23);
			this->btnOpen->TabIndex = 0;
			this->btnOpen->Text = L"Открыть";
			this->btnOpen->UseVisualStyleBackColor = false;
			this->btnOpen->Click += gcnew System::EventHandler(this, &MyForm::btnOpen_Click);
			// 
			// MyForm
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->ClientSize = System::Drawing::Size(721, 257);
			this->Controls->Add(this->btnOpen);
			this->KeyPreview = true;
			this->Name = L"MyForm";
			this->Text = L"MyForm";
			this->Load += gcnew System::EventHandler(this, &MyForm::MyForm_Load);
			this->Paint += gcnew System::Windows::Forms::PaintEventHandler(this, &MyForm::MyForm_Paint);
			this->KeyDown += gcnew System::Windows::Forms::KeyEventHandler(this, &MyForm::MyForm_KeyDown);
			this->ResumeLayout(false);

		}
#pragma endregion
	private: System::Void MyForm_Load(System::Object^  sender, System::EventArgs^  e) {
		unit(T);
		lines.Clear();
	}
	private: System::Void MyForm_Paint(System::Object^  sender, System::Windows::Forms::PaintEventArgs^  e) {
		Graphics^ g = e->Graphics;


		Pen^ blackPen = gcnew Pen(Color::Black);
		blackPen->Width = 2;

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
			g->DrawLine(blackPen, a.x, a.y, b.x, b.y);
		}
		/*for (int i = 0; i < lines.Count; i++) {
			g->DrawLine(blackPen, lines[i].start.x, lines[i].start.y, lines[i].end.x, lines[i].end.y);
		}*/
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
			if (in.is_open()) {
				lines.Clear();
				unit(T);
				std::string str;
				getline(in, str);
				while (in) {
					if ((str.find_first_not_of(" \t\r\n") != std::string::npos)
						&& (str[0] != '#')) {
						std::stringstream s(str);
						line l;
						s >> l.start.x >> l.start.y >> l.end.x >> l.end.y;
						l.start.y = 320 - l.start.y;
						l.end.y = 320 - l.end.y;
						std::string linename;
						s >> linename;
						l.name = gcnew String(linename.c_str());
						lines.Add(l);
					}
					getline(in, str);
				}
			}
			this->Refresh();
		}
	}
	private: System::Void MyForm_KeyDown(System::Object^  sender, System::Windows::Forms::KeyEventArgs^  e) {
		Rectangle rect = Form::ClientRectangle;
		int width = rect.Width;
		int height = rect.Height;
		mat R, T1, A, B;
		switch (e->KeyCode) {
		case Keys::W:
			move(0, -1, R);
			break;
		case Keys::S:
			move(0, 1, R);
			break;
		case Keys::A:
			move(-1, 0, R);
			break;
		case Keys::D:
			move(1, 0, R);
			break;
		case Keys::E:
			rotate(0.05, R);
			break;
		case Keys::Z:
			rotate(-0.05, R);
			break;
		case Keys::Q:
			scale(1 / 1.1, R);
			break;
		case Keys::I:
			move(0, -15, R);
			break;
		case Keys::O:
			move(0, 15, R);
			break;
		case Keys::K:
			move(-15, 0, R);
			break;
		case Keys::L:
			move(15, 0, R);
			break;
		case Keys::C:
			move(-rect.Width/2, -rect.Height/2, R);
			times(R, T, T1);
			set(T1, T);
			rotate(-0.5, R);
			times(R, T, T1);
			set(T1, T);
			move(rect.Width / 2, rect.Height / 2, R);
			break;
		case Keys::V:
			move(-rect.Width / 2, -rect.Height / 2, R);
			times(R, T, T1);
			set(T1, T);
			rotate(0.5, R);
			times(R, T, T1);
			set(T1, T);
			move(rect.Width / 2, rect.Height / 2, R);
			break;
		case Keys::U:
			window(A);
			times(A, T, T1);
			set(T1, T);
			move(rect.Width, 0,  R);
			break;
		case Keys::J:
			window_h(A);
			move(0, -rect.Height, B);
			times(A, B, R);
			break;
		case Keys::R:
			move(-width / 2,-height / 2, R);
			times(R, T, T1);
			set(T1, T);
			scale(1 / 1.1, R);
			times(R, T, T1);
			set(T1, T);
			move(width / 2, height / 2, R);
			break;
		case Keys::Y:
			move(-width / 2, -height / 2, R);
			times(R, T, T1);
			set(T1, T);
			scale(1.1, R);
			times(R, T, T1);
			set(T1, T);
			move(width / 2, height / 2, R);
			break;
		case Keys::Escape:
			unit(T);
			unit(R);
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
	};
}
