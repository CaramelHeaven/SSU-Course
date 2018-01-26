#pragma once
#include "stdafx.h"

namespace Project3 {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	/// <summary>
	/// Сводка для Form1
	///
	/// Внимание! При изменении имени этого класса необходимо также изменить
	///          свойство имени файла ресурсов ("Resource File Name") для средства компиляции управляемого ресурса,
	///          связанного со всеми файлами с расширением .resx, от которых зависит данный класс. В противном случае,
	///          конструкторы не смогут правильно работать с локализованными
	///          ресурсами, сопоставленными данной форме.
	/// </summary>
	public ref class Form1 : public System::Windows::Forms::Form
	{
	public:
		Form1(void)
		{
			InitializeComponent();
			//
			//TODO: добавьте код конструктора
			//
		}

	protected:
		/// <summary>
		/// Освободить все используемые ресурсы.
		/// </summary>
		~Form1()
		{
			if (components)
			{
				delete components;
			}
		}
	
	private:
		System::Collections::Generic::List<line> lines;
	private: 
		void DrawFigure(Graphics^ g, Pen^ pen)
		{
			int a=27;
			int b=30;
			int c=6;
			int d=5;
			g->DrawRectangle(pen, -20, -30, 40, 60);

				 g->DrawLine (pen, 80/c-a, 40/d-b, 80/c-a, 120/d-b);//start kvardat
				 g->DrawLine (pen, 80/c-a, 120/d-b, 180/c-a, 120/d-b);
				 g->DrawLine (pen, 180/c-a, 120/d-b, 180/c-a, 40/d-b);
				 g->DrawLine (pen, 180/c-a, 40/d-b, 80/c-a, 40/d-b);
				 g->DrawLine (pen, 100/c-a, 120/d-b, 100/c-a, 140/d-b);//KONEZ KVADRATA
				 g->DrawLine (pen, 100/c-a, 140/d-b, 40/c-a, 140/d-b);
				 g->DrawLine (pen, 40/c-a, 140/d-b, 40/c-a, 160/d-b);//start kvadrat
				 g->DrawLine (pen, 40/c-a, 160/d-b, 280/c-a, 160/d-b);
				 g->DrawLine (pen, 280/c-a, 160/d-b, 280/c-a, 140/d-b);
				 g->DrawLine (pen, 280/c-a, 140/d-b, 160/c-a, 140/d-b);
				 g->DrawLine (pen, 160/c-a, 140/d-b, 160/c-a, 120/d-b);//the end stol
				 g->DrawLine (pen, 60/c-a, 160/d-b, 60/c-a, 280/d-b);//start nojka
				 g->DrawLine (pen, 60/c-a, 280/d-b, 80/c-a, 280/d-b);
				 g->DrawLine (pen, 80/c-a, 280/d-b, 80/c-a, 160/d-b);//the ent nojka
				 g->DrawLine (pen, 180/c-a, 160/d-b, 180/c-a, 280/d-b);//start 2 nojka
				 g->DrawLine (pen, 180/c-a, 280/d-b, 260/c-a, 280/d-b);
				 g->DrawLine (pen, 260/c-a, 280/d-b, 260/c-a, 160/d-b);
				 g->DrawLine (pen, 260/c-a, 280/d-b, 260/c-a, 160/d-b);//the end 2nojka
				 g->DrawLine (pen, 200/c-a, 200/d-b, 200/c-a, 220/d-b);//start processor
				 g->DrawLine (pen, 220/c-a, 20/d-b, 220/c-a, 140/d-b);
				 g->DrawLine (pen, 220/c-a, 20/d-b, 260/c-a, 20/d-b);
				 g->DrawLine (pen, 260/c-a, 20/d-b, 260/c-a, 140/d-b);//the end proc
				 g->DrawLine (pen, 230/c-a, 40/d-b, 250/c-a, 40/d-b);//plushka
				 g->DrawLine (pen, 100/c-a, 140/d-b, 160/c-a, 140/d-b);
		}
		bool drawNames;
		float left, right, top, bottom;
		float Wcx, Wcy, Wx, Wy;
		float Vcx, Vcy, Vx, Vy;
		void Set_cat(float vcx, float vcy, float vx, float vy)
	{
		Vcx = vcx;
		Vcy = vcy;
		Vx = vx;
		Vy = vy;
	}
	private: System::Windows::Forms::OpenFileDialog^  openFileDialog;
	private: System::Windows::Forms::Button^  btnOpen;

	private:
		/// <summary>
		/// Требуется переменная конструктора.
		/// </summary>
		System::ComponentModel::Container ^components;

#pragma region Windows Form Designer generated code
		/// <summary>
		/// Обязательный метод для поддержки конструктора - не изменяйте
		/// содержимое данного метода при помощи редактора кода.
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
			this->openFileDialog->Filter = L"Текстовые файлы (*.txt)|*.txt|Все файлы (*.*)|*.*";
			this->openFileDialog->Title = L"Открыть файл";
			this->openFileDialog->FileOk += gcnew System::ComponentModel::CancelEventHandler(this, &Form1::openFileDialog_FileOk);
			// 
			// btnOpen
			// 
			this->btnOpen->Anchor = static_cast<System::Windows::Forms::AnchorStyles>((System::Windows::Forms::AnchorStyles::Bottom | System::Windows::Forms::AnchorStyles::Right));
			this->btnOpen->Location = System::Drawing::Point(729, 422);
			this->btnOpen->Name = L"btnOpen";
			this->btnOpen->Size = System::Drawing::Size(75, 23);
			this->btnOpen->TabIndex = 0;
			this->btnOpen->Text = L"Открыть";
			this->btnOpen->UseVisualStyleBackColor = true;
			this->btnOpen->Click += gcnew System::EventHandler(this, &Form1::btnOpen_Click);
			// 
			// Form1
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->ClientSize = System::Drawing::Size(806, 448);
			this->Controls->Add(this->btnOpen);
			this->KeyPreview = true;
			this->MinimumSize = System::Drawing::Size(400, 400);
			this->Name = L"Form1";
			this->Text = L"Form1";
			this->Load += gcnew System::EventHandler(this, &Form1::Form1_Load);
			this->Paint += gcnew System::Windows::Forms::PaintEventHandler(this, &Form1::Form1_Paint);
			this->Resize += gcnew System::EventHandler(this, &Form1::Form1_Resize);
			this->KeyDown += gcnew System::Windows::Forms::KeyEventHandler(this, &Form1::Form1_KeyDown);
			this->ResumeLayout(false);

		}
#pragma endregion
	private: System::Void Form1_Load(System::Object^  sender, System::EventArgs^  e) {
				lines.Clear(); // очистка списка отрезков
				unit (T); // создание матрицы преобразования
				left =50;
				right=50;
				top=50;
				bottom=50;
				Wcx = left;
				Wcy = Form::ClientRectangle.Height - bottom;
				Wx = Form::ClientRectangle.Width - left - right;
				Wy = Form::ClientRectangle.Height - top - bottom;
			 }

	private: System::Void Form1_Paint(System::Object^  sender, System::Windows::Forms::PaintEventArgs^  e) {
				System::Drawing::Graphics^ g = e->Graphics;

				System::Drawing::Pen^ blackPen = gcnew Pen(Color::Black, 1.3);
				System::Drawing::Pen^ rectPen = gcnew Pen(Color::Blue, 4);
				System::Drawing::Font^ DrawF = gcnew System::Drawing::Font("Arial", 6);
				SolidBrush^ DrawB = gcnew SolidBrush(Color::Red);

				Rectangle rect = System::Drawing::Rectangle(Wcx, top, Wx, Wy);
				g->DrawRectangle(rectPen, rect);
				g->Clip = gcnew System::Drawing::Region(rect);


				point Pmin, Pmax;

				/*Pmin.x = Wcx;
				Pmin.y = Wcy-Wy;
				Pmax.x = Wcx + Wx;
				Pmax.y = Wcy;*/

				for (int i = 0; i < matrices.size(); i++) {
	
					g->Transform = gcnew System::Drawing::Drawing2D::Matrix(T[0][0], T[1][0],
																		T[0][1], T[1][1],
																		T[0][2], T[1][2]);
					mat С;
					times(T, matrices[i], С);
					g->Transform = gcnew System::Drawing::Drawing2D::Matrix(С[0][0], С[1][0],
					С[0][1], С[1][1],
					С[0][2], С[1][2]);

				
					DrawFigure(g, blackPen);
				}
				
			 }

	private: System::Void openFileDialog_FileOk(System::Object^  sender, System::ComponentModel::CancelEventArgs^  e) {
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

					
					/*std::string str2;
					getline (in, str2);
					std::stringstream s2(str2);
					float temp1, temp2, temp3, temp4;
					s2 >> temp1 >> temp2 >> temp3 >> temp4;
					Set(temp1, temp2, temp3, temp4);
					
					this->Refresh();*/
					

						 if (in.is_open() ) 
						 {

							 matrices.clear(); // очистка списка матриц
							 std::stack<mat> matStack; // стек матриц

							 mat K; 
							 unit(K);
							 unit(T);

							 std::string str;
							 getline (in, str);

							 while (in) {
								 if ((str.find_first_not_of(" \t\r\n") != std::string::npos) && (str[0] != '#')) if ((str.find_first_not_of(" \t\r\n") != std::string::npos) && (str[0] != '#')) // проверка на "пустоту" и # (комментаторская строчка)
							{
								std::stringstream s(str); //считывание координат и имени
								std::string cmd;
								s >> cmd;

								if ( cmd == "frame" )
								{
									float temp1, temp2, temp3, temp4;
									s >> temp1 >> temp2 >> temp3 >> temp4;
									Set_cat(temp1, temp2, temp3, temp4); // установка Vcx, Vcy, Vx, Vy
									frame (Vx, Vy, Vcx, Vcy, Wx, Wy, Wcx, Wcy, T); // начальное преобразование с кадрированием и преобразованием пользовательских координат в экранные
								}
								else if ( cmd == "figure" )
								{
									matrices.push_back(K);
								}
								else if ( cmd == "pushTransform" )
								{
									matStack.push(K);
								}
								else if ( cmd == "popTransform" )
								{
									K = matStack.top();
									matStack.pop();
								}
								else if ( cmd == "translate" )
								{
									float Tx, Ty;
									s >> Tx >> Ty;
									mat C, C1;
									move(Tx, Ty, C);
									times(K, C, C1);
									K = C1;
								}
								else if ( cmd == "scale" )
								{
									float S;
									s >> S;
									mat C, C1;
									scale(S, C);
									times(K, C, C1);
									K = C1;
								}
								else if ( cmd == "rotate" )
								{
									float Phi;
									s >> Phi;
									float pi = 3.1415926535;
									float PhiR = Phi * (pi / 180);
									mat C, C1;
									rotate(PhiR, C);
									times(K, C, C1);
									K = C1;
								}

							}
								 getline(in, str);
							 }
						 }
						
						
				 }
						/*mat R, T1, T2;
						move(0, -400, R);
						times(R,T,T1);
						mirror(R);
						times(R,T1,T2);
						set(T2, T);
						frame (Vx, Vy, Vcx, Vcy, Wx, Wy, Wcx, Wcy, T);*/
						this->Refresh();
			 }
	private: System::Void Form1_KeyDown(System::Object^  sender, System::Windows::Forms::KeyEventArgs^  e) {
			 mat  A, B, R, T1;
			 Rectangle rect = Form::ClientRectangle;
			 switch(e->KeyCode) {
				 case Keys::W :
					 move(0, -1, R);
					 break;

				 case Keys::S :
					 move(0, 1, R);
					 break;

				 case Keys::A :
					 move(-1, 0, R);
					 break;

				 case Keys::D :
					 move(1, 0, R);
					 break;

				 case Keys::Z :
					 move(-Wcx, -(Wcy - Wy), R);
					 times(R, T, T1);
					 set(T1, T);
					 rotate(0.05, R);
					 times(R, T, T1);
					 set(T1, T);
					 move(Wcx, Wcy - Wy, R);
					 break;

				 case Keys::X :
					move(-Wcx, -(Wcy - Wy), R);
					 times(R, T, T1);
					 set(T1, T);
							scale(1*1.1, R);
							 times(R, T, T1);
					 set(T1, T);
					 move(Wcx, Wcy - Wy, R);
					 break;

				 case Keys::Q :
					 move(-Wcx, -(Wcy - Wy), R);
					 times(R, T, T1);
					 set(T1, T);
					 scale(1/1.1, R);
					 times(R, T, T1);
					 set(T1, T);
					 move(Wcx, Wcy - Wy, R);
					 break;

				 case Keys::E :
					 move(-Wcx, -(Wcy - Wy), R);
					 times(R, T, T1);
					 set(T1, T);
					 rotate(-0.05, R);
					 times(R, T, T1);
					 set(T1, T);
					 move(Wcx, Wcy - Wy, R);
				     break;

				 case Keys::O :
					 move(0, -10, R);
					 break;

				 case Keys::K :
					 move(0, 10, R);
					 break;

				 case Keys::I :
					 move(-10, 0, R);
					 break;

				 case Keys::L :
					 move(10, 0, R);
					 break;

				 case Keys::U :
					 Window_new(A);
					 move(-Wx -2*left ,0,B);
					 times(A,B,R);
					 break;

				 case Keys::J :
					 Mir_cX(R, rect.Height); 
					 break;

				 case Keys::C	 :
					 Rotate_c(0.05, R, rect.Width, rect.Height); 
					 break;

				 case Keys::V :
					Rotate_c(-0.05, R, rect.Width, rect.Height); 
					 break;

				 case Keys::R :
					 Scale_c((1/1.1), R, rect.Width, rect.Height); 
					 break;

				 case Keys::Y :
					 Scale_c(1.1, R, rect.Width, rect.Height); 
					 break;

				 case Keys::T :
					Scale_cX((1/1.1), R, rect.Width); 
					 break;

				 case Keys::F :
					 Scale_cX(1.1, R, rect.Width); 
					 break;

				 case Keys::G :
					 Scale_cY((1/1.1), R, rect.Height);
					 break;

				 case Keys::H :
					 Scale_cY(1.1, R, rect.Height); 
					 break;

				 case Keys::P :
					 if (drawNames)
					 {
						drawNames = false;
					 }
					 else
					 {
					 	drawNames = true;
					 }
					 unit(R);
					 break;

				case Keys::Escape :
					
					unit(R);	
					frame (Vx, Vy, Vcx, Vcy, Wx, Wy, Wcx, Wcy, T);
					break;

				default :
					unit(R);
					this->Refresh();
			 }
			 times(R,T,T1);
			 set(T1, T);
			 this->Refresh();
			 }



private: System::Void Form1_Resize(System::Object^  sender, System::EventArgs^  e) {
			float Old_Wx = Wx, Old_Wy = Wy; // старые значения параметров прямоугольника
			mat R, T1;
			 
			Wcx = left;
			Wcy = Form::ClientRectangle.Height - bottom;
			Wx = Form::ClientRectangle.Width - left - right;
			Wy = Form::ClientRectangle.Height - top - bottom;

			Scale_pp((Wx/Old_Wx), (Wy/Old_Wy), Wcx, top, R); // resize изображения при resize окна
			times(R,T,T1);
			set(T1,T);


			this->Refresh();
		 }
};
}

