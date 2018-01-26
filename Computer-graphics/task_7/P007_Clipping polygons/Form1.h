#pragma once

namespace P007_Clippingpolygons {

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
		System::Collections::Generic::List<polygon^> polygons;

		bool drawNames;
		float left, right, top, bottom;
		float Wcx, Wcy, Wx, Wy;
		float Vcx, Vcy, Vx, Vy;
		void set_hope(float hope1, float hope2, float hope3, float hope4) 
		{
			Vcx = hope1;
			Vcy = hope2;
			Vx = hope3;
			Vy = hope4;
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
			this->btnOpen->Anchor = static_cast<System::Windows::Forms::AnchorStyles>((System::Windows::Forms::AnchorStyles::Top | System::Windows::Forms::AnchorStyles::Right));
			this->btnOpen->Location = System::Drawing::Point(719, 12);
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
			this->KeyDown += gcnew System::Windows::Forms::KeyEventHandler(this, &Form1::Form1_KeyDown);
			this->Resize += gcnew System::EventHandler(this, &Form1::Form1_Resize);
			this->ResumeLayout(false);

		}
#pragma endregion
	private: System::Void Form1_Load(System::Object^  sender, System::EventArgs^  e) {
				polygons.Clear();
				lines.Clear(); 
				unit (T); 
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
				
				System::Drawing::Graphics^g = e->Graphics;
				System::Drawing::Pen^ blackPen = gcnew Pen(Color::Black, 1.3);
				System::Drawing::Pen^ rectPen = gcnew Pen(Color::Yellow, 4);
				System::Drawing::Font^ drawFont = gcnew System::Drawing::Font("Arial",12);
				g->DrawRectangle(rectPen, Wcx, top, Wx, Wy);

				point Pmin, Pmax;
				 Pmin.x = Wcx;
				 Pmin.y = Wcy - Wy;
				 Pmax.x = Wcx + Wx;
				 Pmax.y = Wcy;
				 point centre;
				 for (int i = 0; i < polygons.Count; i++) {
						 polygon^ p = polygons[i];
						 polygon^ temp = gcnew polygon(0);
						 point a, b, c;
						 vec A, B, A1, B1;
						 for (int j = 0; j < p->Count; j++)
						 {
							 point2vec(p[j], B);
							 timesMatVec(T, B, B1);
							 vec2point(B1, b);
							 temp->Add(b);
						 }
						 temp = PClip(temp, Pmin, Pmax);
						 if (temp->Count != 0)
						 {
							 a = temp[temp->Count - 1];
							 for (int j = 0; j < temp->Count; j++)
							 {
								 b = temp[j];
								 g->DrawLine(blackPen, a.x, a.y, b.x, b.y);
								 a = b;
							 }
						 }
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
					
						 if (in.is_open() ) 
						 {

							 matrices.clear(); 
							 std::stack<mat> matStack; 

							 mat K; 
							 unit(K);
							 unit(T);

							 std::string str;
							 getline (in, str);

							 while (in) {
								 if ((str.find_first_not_of(" \t\r\n") != std::string::npos) && (str[0] != '#')) if ((str.find_first_not_of(" \t\r\n") != std::string::npos) && (str[0] != '#')) // проверка на "пустоту" и # (комментаторская строчка)
							{
								std::stringstream s(str); 
								std::string cmd;
								s >> cmd;

								if ( cmd == "frame" )
								{
									float cat1, cat2, cat3, cat4;
									s >> cat1 >> cat2 >> cat3 >> cat4;
									set_hope(cat1, cat2, cat3, cat4);
									frame (Vx, Vy, Vcx, Vcy, Wx, Wy, Wcx, Wcy, T); 
								}
								else if (cmd=="polygon")
								 {
									 int numpoint;
									 s>>numpoint;
									 polygon^ P = gcnew polygon(0);
									 for (int i = 0;i<numpoint;i++)
									 {
										 point p;
										 s>>p.x>>p.y;
										 P->Add(p);
									 }
									 polygons.Add(P);
								 }
								

							}
								 getline(in, str);
							 }
						 }
						
						
				 }
			this->Refresh();
			 }
	private: System::Void Form1_KeyDown(System::Object^  sender, System::Windows::Forms::KeyEventArgs^  e) {
			 Rectangle rect = Form::ClientRectangle;
			int width = rect.Width;
			int height = rect.Height;
				 mat R, T1, A, B, F, G;

				 switch(e->KeyCode)
				 {
					 /////////////////////MOVE
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
					//Z - поворот против часовой стрелки относительно начала координат;
				 case Keys::Z :
					 move(-Wcx, -(Wcy - Wy), R);
					 times(R, T, T1);
					 set(T1, T);
					 rotate(0.05, R);
					 times(R, T, T1);
					 set(T1, T);
					 move(Wcx, Wcy - Wy, R);
					 break;
					 //////////////
				 case Keys::X :
					move(-Wcx, -(Wcy - Wy), R);
					 times(R, T, T1);
					 set(T1, T);
							scale(1*1.1, R);
							 times(R, T, T1);
					 set(T1, T);
					 move(Wcx, Wcy - Wy, R);
					 break;
					 //Q - уменьшение изображения в 1.1 раза относительно начала координат;
				 case Keys::Q :
					 move(-Wcx, -(Wcy - Wy), R);
					 times(R, T, T1);
					 set(T1, T);
					 scale(1/1.1, R);
					 times(R, T, T1);
					 set(T1, T);
					 move(Wcx, Wcy - Wy, R);
					 break;
					 ////////////
				 case Keys::E :
					 move(-Wcx, -(Wcy - Wy), R);
					 times(R, T, T1);
					 set(T1, T);
					 rotate(-0.05, R);
					 times(R, T, T1);
					 set(T1, T);
					 move(Wcx, Wcy - Wy, R);
				     break;
					 //I, O, K, L  - быстрое смещение изображения вверх, вниз, влево, вправо;
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
					 //U, J - преобразование зеркального отображения относительно вертикальной и горизонтальной оси проходящей через центр окна приложения;
				  case Keys::U :
					 mirror_g(A);
					 move(-Wx -2*left ,0,B);
					 times(A,B,R);
					 break;

				 case Keys::J :
					 mirror_v(A);
					 move(0,-Wy - 2*top,B);
					 times(A,B,R);
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


				 }
				 times(R,T,T1);
				 set(T1, T);
				 this->Refresh();

			 }
private: System::Void Form1_Resize(System::Object^  sender, System::EventArgs^  e) { 
				 float oldWx = Wx, oldWy = Wy; 
				 mat R, T1; 
				 Wcx = left; 
				 Wcy = Form::ClientRectangle.Height - bottom; 
				 Wx = Form::ClientRectangle.Width - left - right; 
				 Wy = Form::ClientRectangle.Height - top - bottom; 
				 specscale((Wx/oldWx), (Wy/oldWy), Wcx, top, R); 
				 times(R,T,T1); 
				 set(T1,T); 
				 this->Refresh(); 

			 }
	};
}