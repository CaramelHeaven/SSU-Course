#pragma once

namespace P007_Clippingpolygons {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	/// <summary>
	/// ������ ��� Form1
	///
	/// ��������! ��� ��������� ����� ����� ������ ���������� ����� ��������
	///          �������� ����� ����� �������� ("Resource File Name") ��� �������� ���������� ������������ �������,
	///          ���������� �� ����� ������� � ����������� .resx, �� ������� ������� ������ �����. � ��������� ������,
	///          ������������ �� ������ ��������� �������� � ���������������
	///          ���������, ��������������� ������ �����.
	/// </summary>
	public ref class Form1 : public System::Windows::Forms::Form
	{
	public:
		Form1(void)
		{
			InitializeComponent();
			//
			//TODO: �������� ��� ������������
			//
		}

	protected:
		/// <summary>
		/// ���������� ��� ������������ �������.
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
	System::Collections::Generic::List<color> colors;

		bool drawNames; // ��������������� ����������: ���� true - ������� ����� ��������, � ��������� ������ - �� ��������

		int R, G, B; // "���������� �����"
		int num;

		float left, right, top, bottom; // ���������� �� �������������� (�����) �� �����, ������, ������� � ������ ������� ����
		float Wcx, Wcy, Wx, Wy; // ������������� �� ������
		float Vcx, Vcy, Vx, Vy; // ������������� � ��������� ���������
		void Set(float vcx, float vcy, float vx, float vy) // ��������� Vcx, Vcy, Vx, Vy
	{
		Vcx = vcx;
		Vcy = vcy;
		Vx = vx;
		Vy = vy;
	}
	private: System::Windows::Forms::OpenFileDialog^  openFileDialog;
	private: System::Windows::Forms::Button^  btnOpen;
			 
	private: 
		System::Collections::Generic::List<polygon^> polygons;

	private:
		/// <summary>
		/// ��������� ���������� ������������.
		/// </summary>
		System::ComponentModel::Container ^components;

#pragma region Windows Form Designer generated code
		/// <summary>
		/// ������������ ����� ��� ��������� ������������ - �� ���������
		/// ���������� ������� ������ ��� ������ ��������� ����.
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
			this->openFileDialog->FileName = L"openFileDialog";
			this->openFileDialog->Filter = L"��������� ����� (*.txt)|*.txt|��� ����� (*.*)|*.*";
			this->openFileDialog->Title = L"������� ����";
			this->openFileDialog->FileOk += gcnew System::ComponentModel::CancelEventHandler(this, &Form1::openFileDialog_FileOk);
			// 
			// btnOpen
			// 
			this->btnOpen->Anchor = static_cast<System::Windows::Forms::AnchorStyles>((System::Windows::Forms::AnchorStyles::Top | System::Windows::Forms::AnchorStyles::Right));
			this->btnOpen->Location = System::Drawing::Point(719, 12);
			this->btnOpen->Name = L"btnOpen";
			this->btnOpen->Size = System::Drawing::Size(75, 23);
			this->btnOpen->TabIndex = 0;
			this->btnOpen->Text = L"�������";
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
				R = 0; G = 0; B = 0; // ���� �� ��������� - ������
				num = 0;
				Wcy = Form::ClientRectangle.Height - bottom;
				Wx = Form::ClientRectangle.Width - left - right;
				Wy = Form::ClientRectangle.Height - top - bottom;
				colors.Clear();
			 }

	private: System::Void Form1_Paint(System::Object^  sender, System::Windows::Forms::PaintEventArgs^  e) {
				
				//System::Drawing::Graphics^g = e->Graphics;
				Bitmap^ image1 = gcnew Bitmap( this->ClientRectangle.Width, this->ClientRectangle.Height);
				Graphics^ g = Graphics::FromImage(image1); // ���������� ���������� g ���������� �����������

				System::Drawing::Pen^ blackPen = gcnew Pen(Color::Black, 3);

				System::Drawing::Pen^ rectPen = gcnew Pen(Color::Brown, 4);
				
				System::Drawing::Font^ drawFont = gcnew System::Drawing::Font("Arial",12);
				SolidBrush^ DrawB = gcnew SolidBrush(Color::Red);

				g->DrawRectangle(rectPen, Wcx, top, Wx, Wy);
				/*������ �������� DrawRectangle (������ ������������� - ����) � �������� top, � �� Wcy. ���� ��������� � ��������
				������� � �������� ���������� ����������
				���������� �������� ������ ����, ����� ��� ��������� Wcx � Wcy � ����������
				������ ������� ���� */

				point Pmin, Pmax; /* xmin � xmax ��� ���������� ��������� ��������� �������� 
				(� ������: ���� �������� �� �����, �� ��� ������� ����� ���� � ������ ������ 
				- � �������� ������� ���������, �����������) */
				
				Pmin.x = Wcx;
				Pmin.y = Wcy-Wy;
				Pmax.x = Wcx + Wx;
				Pmax.y = Wcy;
				
				/*for (int i = 0; i < lines.Count; i++) 
				{
					vec A, B;
					point2vec(lines[i].start, A);
					point2vec(lines[i].end, B);

					vec A1, B1;
					timesMatVec(T,A,A1);
					timesMatVec(T,B,B1);

					point a, b;
					vec2point(A1, a);
					vec2point(B1, b);
					
					if (clip(a, b, Pmin, Pmax)) // ��������� �������� (���� clip == false, �� ������� ��������� �������, � ��������� ������ - �� ���� ��������, ���� ��������� �����)
					g->DrawLine(blackPen, a.x, a.y, b.x, b.y);

					if (drawNames==true) // ���������� ���� ��������
					{
						g->DrawString(lines[i].name, drawFont,drawBrush, (a.x+((b.x-a.x)/2)), (a.y+((b.y-a.y)/2)));
					}
				}*/
				
				for (int i = 0; i < polygons.Count; i++) // ���� �� ���������� ���������������
				{
					// ���������� �� ������ ��������� ����� ������� ����� (����� �������������� ������������� �� 0 �� p->Count - 1; ������ ����� ���������� � ����� c ������� p->Count - 1 � ������������� � ����� � ������� 0)
					// polygon^ p = Pclip(polygons[i], Pmin, Pmax);
					System::Drawing::Pen^ rgbPen = gcnew Pen(Color::FromArgb(colors[i].R, colors[i].G, colors[i].B), 5); // �������� � ����� pen�� ��� ������� �� ���������������

					polygon^ p = polygons[i];
					polygon^ p1 = gcnew polygon(0);
					point a, b;
					vec A, B, A1, B1;
					point2vec(p[p->Count - 1], A);
					timesMatVec(T, A, A1);
					vec2point(A1, a);
				
					for (int j = 0; j < p->Count; j++) // ���������� �� ��������� ������
					{
						point2vec(p[j], B);
						timesMatVec(T, B, B1);
						vec2point(B1, b);
						p1->Add(b); // ���������� � ������������� p1 ��������������� ��������� �����
						//g -> DrawLine(blackPen, a.x, a.y, b.x, b.y);
						//a = b;
					}

					p1->Add(a); // ���������� ��������� �����

					point c; // ��������������� �����

					p1 = PClip(p1, Pmin, Pmax); // �������� ��������� ��������� �������������� � ��� ���������������� �������
					if (p1->Count != 0) // �������� �� ������� ��������������, �.�. �������� �� ��, ��������� �� �� ��������� �� ��������� ������������� �������
					 { 
						 // ����� ������������ p1 - ���������������� ��������� � �����������
						 c = p1[p1->Count - 1];
						 for(int j = 0; j < p1->Count; j++)
						 {
							 g ->DrawLine(rgbPen, c.x, c.y, p1[j].x, p1[j].y);
							 c = p1[j];
						 }
					Color clr = Color::FromArgb(colors[i].R, colors[i].G, colors[i].B);
					Pfill(p1, image1, clr);
					 }
					//image1->SetPixel(p1[0].x-50, 320, Color::Red);
					//image1->SetPixel(p1[0].x, ymax+51, Color::Red);
					//image1->SetPixel(p1[0].x, ymax+52, Color::Red);
					//image1->SetPixel(p1[0].x, ymax+53, Color::Red);
					//Color pixelColor = image1->GetPixel(200, 200);

				}

				//image1->SetPixel(200, 200, Color::Red);
				//Color pixelColor = image1->GetPixel(200, 200);
				//System::Drawing::Pen^ newPen = gcnew Pen(pixelColor, 3);
				//g ->DrawLine(newPen, 0, 0 , 50, 50);

				g = e->Graphics; // ����������� �������� g
				g->DrawImage(image1,0,0); // ����� �����������
				delete image1; // ��� ��������

			 }
	private: System::Void openFileDialog_FileOk(System::Object^  sender, System::ComponentModel::CancelEventArgs^  e) {
		 }
	private: System::Void btnOpen_Click(System::Object^  sender, System::EventArgs^  e) {
				  bool isEqual;
				 isEqual = true;
				 if (this->openFileDialog->ShowDialog() == System::Windows::Forms::DialogResult::OK)
				 {
					wchar_t fileName[1024]; // "�������" �� ��������� ������� ����� �����
					for (int i = 0; i < openFileDialog->FileName->Length; i++)
					fileName[i] = openFileDialog->FileName[i];
					fileName[openFileDialog->FileName->Length] = '\0';

					std::ifstream in; // �������� �����
					in.open(fileName);					
					
					if ( in.is_open() ) 
					{
						unit(T);

						std::string str;
						getline (in, str);
						
						while (in) 
						{
							if ((str.find_first_not_of(" \t\r\n") != std::string::npos) && (str[0] != '#')) // �������� �� "�������" � # (��������������� �������)
							{
								std::stringstream s(str); //���������� ��������� � �����
								std::string cmd;
								s >> cmd;

								if ( cmd == "frame" )
								{
									float temp1, temp2, temp3, temp4;
									s >> temp1 >> temp2 >> temp3 >> temp4;
									Set(temp1, temp2, temp3, temp4); // ��������� Vcx, Vcy, Vx, Vy
									frame (Vx, Vy, Vcx, Vcy, Wx, Wy, Wcx, Wcy, T); // ��������� �������������� � ������������� � ��������������� ���������������� ��������� � ��������
								}
								if ( cmd == "polygon" )
								{
									int numpoint; // ����� ������
									s >> numpoint;

									polygon^ P = gcnew polygon(0); // �������� ��������������
									for (int i = 0; i<numpoint; i++) // ���������� ��������� ������
									{
										point p;
										s >> p.x >> p.y;
										P->Add(p);
									}
									polygons.Add(P); // ��������� ����������� ������������� � ������
									
									// ����������� �������� ���������� ����� ��� ������� ��������������
									if (colors.Count == 0)
									{
										color default;
										default.R = 0; default.G = 0; default.B = 0;
										colors.Add(default);
									}

									else if (colors.Count > 0)
									{
										if (isEqual)
										{
											colors.Add(colors[colors.Count-1]);
										}

										isEqual = true;
									}
								}

								if ( cmd == "color" ) // ��������� �����
								{
									int r, g, b;
									s >> r >> g >> b;
									color RGB;
									RGB.R = r; RGB.G = g; RGB.B = b;
									colors.Add(RGB);
									isEqual = false;
								}
							}
							getline (in, str);
						}
					}
				 }
			 this->Refresh();

			 /*Rectangle rect = Form::ClientRectangle; // �������������� �� �����
			 mat R, T1;
			 move(-Vcx, -Vcy, R);
			 times(R,T,T1);
			 scale(Wx/Vx, R);
			 times(R, T1, T);
			 scale(Wy/Vy, R);
			 times(R, T, T1);
			 mirrorcenterx(R, rect.Height);
			 times(R, T1, T);
			 move(Wcx,Wcy,R);
			 times(R,T,T1);
			 set(T1,T);*/

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
					//Z - ������� ������ ������� ������� ������������ ������ ���������;
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
					 //Q - ���������� ����������� � 1.1 ���� ������������ ������ ���������;
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
					 //I, O, K, L  - ������� �������� ����������� �����, ����, �����, ������;
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
					 //U, J - �������������� ����������� ����������� ������������ ������������ � �������������� ��� ���������� ����� ����� ���� ����������;
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
				 float oldWx = Wx, oldWy = Wy; // ������ �������� ���������� ��������������
			 mat R, T1;

			 Wcx = left; // refresh ��� resize ��������������
			 Wcy = Form::ClientRectangle.Height - bottom;
			 Wx = Form::ClientRectangle.Width - left - right;
			 Wy = Form::ClientRectangle.Height - top - bottom;

			 scalepicresize((Wx/oldWx), (Wy/oldWy), Wcx, top, R); // resize ����������� ��� resize ����
			 times(R,T,T1);
			 set(T1,T);

			 /*move(Wcx, top, R); // resize ����������� ��� resize ���� ��������
			 times(R, T, T1);
			 scale((Wx/oldWx),R);
			 times(R,T1,T);
			 scale((Wy/oldWy),R);
			 times(R,T,T1);
			 move(-Wcx,-top,R);
			 times(R,T1,T);*/

			 this->Refresh();
			 }
	};
}