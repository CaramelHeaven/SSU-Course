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
	System::Collections::Generic::List<line> lines; // ������: ����� �������� ��� �����������

	bool drawNames; // ��������������� ����������: ���� true - ������� ����� ��������, � ��������� ������ - �� ��������

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

	// 3D -->
	point3D eye, center;
	float near, far;
	float fovy;
	point3D up;
	bool prOrtho;

	point3D eyebar, centerbar;
	float nearbar, farbar;
	float fovybar;
	point3D upbar;
	float aspectbar;

	float rotatephi;

	private: System::Windows::Forms::OpenFileDialog^  openFileDialog;
	private: System::Windows::Forms::Button^  btnOpen;

	private: System::Collections::Generic::List<polygon^> polygons; // ������ ���������� �� ��������������
	private: System::Collections::Generic::List<polygon3D^> polygons3D;
	private: System::Collections::Generic::List<triangle3D^> triangles3D;


	private: System::Collections::Generic::List<color> colors; // ����� ���������������

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
			// openFileDialog1
			// 
			this->openFileDialog->DefaultExt = L"txt";
			this->openFileDialog->FileName = L"openFileDialog";
			this->openFileDialog->Filter = L"��������� ����� (*.txt)|*.txt|��� ����� (*.*)|*.*";
			this->openFileDialog->Title = L"������� ����";
			this->openFileDialog->FileOk += gcnew System::ComponentModel::CancelEventHandler(this, &Form1::openFileDialog_FileOk);
			// 
			// btnOpen
			// 
			this->btnOpen->Anchor = static_cast<System::Windows::Forms::AnchorStyles>((System::Windows::Forms::AnchorStyles::Bottom | System::Windows::Forms::AnchorStyles::Right));
			this->btnOpen->Location = System::Drawing::Point(912, 465);
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
			this->ClientSize = System::Drawing::Size(999, 500);
			this->Controls->Add(this->btnOpen);
			this->KeyPreview = true;
			this->MinimumSize = System::Drawing::Size(16, 39);
			this->Name = L"Form1";
			this->Text = L"Task_9";
			this->Load += gcnew System::EventHandler(this, &Form1::Form1_Load);
			this->Paint += gcnew System::Windows::Forms::PaintEventHandler(this, &Form1::Form1_Paint);
			this->KeyDown += gcnew System::Windows::Forms::KeyEventHandler(this, &Form1::Form1_KeyDown);
			this->Resize += gcnew System::EventHandler(this, &Form1::Form1_Resize);
			this->ResumeLayout(false);

		}
#pragma endregion
	private: System::Void Form1_Load(System::Object^  sender, System::EventArgs^  e) 
			 {
			    polygons.Clear(); // ������� ������ ���������� �� ��������������
				polygons3D.Clear();
				triangles3D.Clear();

				lines.Clear(); // ������� ������ ��������
				unit (T); // �������� ������� ��������������
				unit (T3D);
				
				left = 50; // ������������� ���������� �������� �� ����� ������
				right = 50; 
				top = 50; 
				bottom = 50;

				Wcx = left; // ������������� ���������� ��������������
				Wcy = Form::ClientRectangle.Height - bottom;
				Wx = Form::ClientRectangle.Width - left - right;
				Wy = Form::ClientRectangle.Height - top - bottom;

				//drawNames = false;
				colors.Clear();
				
				// 3D -->
				/*eye.x = 0; eye.y = 0; eye.z = 0;
				center.x = 0; center.y = 0; center.z = 100;
				near = 20;
				far = 100;
				fovy = 90;
				up.x = 0; up.y = 0; up.z = 1;*/
				prOrtho = false;

				// �� ��������
				/*eyebar.x = eye.x; eyebar.y = eye.y; eyebar.z = eye.z;
				centerbar.x = center.x; centerbar.y = center.y; centerbar.z = center.z;
				nearbar = near;
				farbar = farbar;
				fovybar = 90;
				upbar.x = up.x; upbar.y = up.y; upbar.z = up.z;*/

				aspectbar = Wx / Wy;
				rotatephi = 1;

			 }
	private: System::Void Form1_Paint(System::Object^  sender, System::Windows::Forms::PaintEventArgs^  e) 
			 {
				System::Drawing::Graphics^g = e->Graphics;

				System::Drawing::Pen^ blackPen = gcnew Pen(Color::Black, 3);

				System::Drawing::Pen^ rectPen = gcnew Pen(Color::Yellow, 4);
				
				System::Drawing::Font^ drawFont = gcnew System::Drawing::Font("Arial",12);
				SolidBrush^ drawBrush = gcnew SolidBrush(Color::Red);

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
				
				/*for (int i = 0; i < polygons.Count; i++) // ���� �� ���������� ���������������
				{
					// ���������� �� ������ ��������� ����� ������� ����� (����� �������������� ������������� �� 0 �� p->Count - 1; ������ ����� ���������� � ����� c ������� p->Count - 1 � ������������� � ����� � ������� 0)
					// polygon^ p = Pclip(polygons[i], Pmin, Pmax);
					System::Drawing::Pen^ rgbPen = gcnew Pen(Color::FromArgb(colors[i].R, colors[i].G, colors[i].B), 2); // �������� � ����� pen�� ��� ������� �� ���������������

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

					p1 = Pclip(p1, Pmin, Pmax); // �������� ��������� ��������� �������������� � ��� ���������������� �������
					if (p1->Count != 0) // �������� �� ������� ��������������, �.�. �������� �� ��, ��������� �� �� ��������� �� ��������� ������������� �������
					 { 
						 // ����� ������������ p1 - ���������������� ��������� � �����������
						 c = p1[p1->Count - 1];
						 for(int j = 0; j < p1->Count; j++)
						 {
							 g ->DrawLine(rgbPen, c.x, c.y, p1[j].x, p1[j].y);
							 c = p1[j];
						 }
					 }
				}*/

				//3D -->

				// 1
				mat3D V;
				LookAt(eyebar, centerbar, upbar, V);

				// 2
				mat3D U;
				Vy = 2 * nearbar * tan(fovybar / 2);
				Vx = aspectbar * Vy;
				
				if (prOrtho)
				{
					Ortho(Vx, Vy, nearbar, farbar, U);
				}
				
				else
				{
					Perspective(fovybar, aspectbar, nearbar, far, U);
				}

				// 3
				mat3D R, help;
				times(U, V, help);
				times(help, T3D, R);

				// 4
				mat F;
				frame(2, 2, -1, -1, Wx, Wy, Wcx, Wcy, F);

				
				// 5
				for (int i = 0; i < triangles3D.Count; i++) // ���� �� ���������� ���������������
				{
					System::Drawing::Pen^ rgbPen = gcnew Pen(Color::FromArgb(colors[i].R, colors[i].G, colors[i].B), 2); // �������� � ����� pen�� ��� ������� �� ���������������
					
					triangle3D^ p = triangles3D[i];
					polygon^ p1 = gcnew polygon(0);

					point3D Abar;
					point Abarbar, Abarbarbar;
					
					vec3D A, B, A1, B1;
					point2vec(p[p->Count - 1], A); // point A to vec A
					timesMatVec(R, A, A1); // R * A = A1 
					vec2point(A1, Abar); // A1 -> Abar

					set(Abar, Abarbar); // Abar to 2D = Abarbar

					vec A2D, B2D, A2D1, B2D1;
					point2vec(Abarbar, A2D); // point Abarbar to vec A2D
					timesMatVec(F, A2D, A2D1); // F * A2D = A2D1
					vec2point(A2D1, Abarbarbar); // A2D1 -> Abarbarbar

					p1->Add(Abarbarbar); // ���������� ��������� �����
				
					for (int j = 0; j < p->Count; j++) // ���������� �� ��������� ������
					{
						point2vec(p[j], B); // point A to vec B
						timesMatVec(R, B, B1); // R * B = B1 
						vec2point(B1, Abar); // B1 -> Abar

						set(Abar, Abarbar); // Abar to 2D = Abarbar

						point2vec(Abarbar, B2D); // point Abarbar to vec B2D
						timesMatVec(F, B2D, B2D1); // F * B2D = B2D1
						vec2point(B2D1, Abarbarbar); // B2D1 -> Abarbarbar

						p1->Add(Abarbarbar); // ���������� ��������� �����
					}

					point c; // ��������������� �����

					p1 = Pclip(p1, Pmin, Pmax); // �������� ��������� ��������� �������������� � ��� ���������������� �������
					if (p1->Count != 0) // �������� �� ������� ��������������, �.�. �������� �� ��, ��������� �� �� ��������� �� ��������� ������������� �������
					 { 
						 // ����� ������������ p1 - ���������������� ��������� � �����������
						 c = p1[p1->Count - 1];
						 for(int k = 0; k < p1->Count; k++)
						 {
							 g ->DrawLine(rgbPen, c.x, c.y, p1[k].x, p1[k].y);
							 c = p1[k];
						 }
					 }
				}
			 }
	private: System::Void openFileDialog_FileOk(System::Object^  sender, System::ComponentModel::CancelEventArgs^  e) {
		 }
	private: System::Void btnOpen_Click(System::Object^  sender, System::EventArgs^  e) 
			 {
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
								
								/*else if ( cmd == "polygon" )
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
								}*/

								else if ( cmd == "triangle" )
								{

									triangle3D^ P = gcnew triangle3D(0); // �������� ��������������
									for (int i = 0; i<3; i++) // ���������� ��������� ������
									{
										point3D p;
										s >> p.x >> p.y >> p.z;
										P->Add(p);
									}
									triangles3D.Add(P); // ��������� ����������� ������������� � ������
									
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

								else if ( cmd == "color" ) // ��������� �����
								{
									int r, g, b;
									s >> r >> g >> b;
									color RGB;
									RGB.R = r; RGB.G = g; RGB.B = b;
									colors.Add(RGB);
									isEqual = false;
								}

								else if( cmd == "lookat" )
								{
									point3D e;
									s >> e.x >> e.y >> e.z;
									eye.x = e.x; eye.y = e.y; eye.z = e.z;
									
									point3D c;
									s >> c.x >> c.y >> c.z;
									center.x = c.x; center.y = c.y; center.z = c.z;

									point3D u;
									s >> u.x >> u.y >> u.z;
									up.x = u.x; up.y = u.y; up.z = u.z;
								}

								else if( cmd == "screen" )
								{
									float fv, n, f;
									s >> fv >> n >> f;
									fovy = fv; near = n; far = f;
								}
							}
							getline (in, str);
						}
					}
				 }

			 
			 LookAt(center, eye, up, T3D);
			
			 // �������������� ����� eye' ��� eye ����� �������������� T
			 point3D help;
			 vec3D evec, evec2;
			 
			 point2vec(eye, evec);
			 timesMatVec(T3D, evec, evec2);
			 vec2point(evec2, help);

			 eyebar.x = help.x; eyebar.y = help.y; eyebar.z = help.z;		 
			 upbar.x = 0; upbar.y = 1; upbar.z = 0;		 
			 centerbar.x = 0; centerbar.y = 0; centerbar.z = 0;

			 fovybar = fovy; nearbar = near; farbar = far;
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
			 this->Refresh();			 
			 }
	private: System::Void Form1_KeyDown(System::Object^  sender, System::Windows::Forms::KeyEventArgs^  e) 
			 {
				mat3D R1, T1; // ������� �������������� � ������� ��� ������ ����������� ������������ ������
				
				point3D oX, oZ;
				oX.x = 1; oX.y = 0; oX.z = 0;
				oZ.x = 0; oZ.y = 0; oZ.z = 1;

				float pi, degree; // ���� ��������
				pi = 3.1415;
				degree = (rotatephi*pi)/180;

				Rectangle rect = Form::ClientRectangle;
				switch(e->KeyCode)
				{
					case Keys::A : // ������� [���]
					rotate(upbar, -degree, R1);
					break;

					case Keys::D : // ������� [���]
					rotate(upbar, degree, R1);
					break;

					case Keys::W : 
					rotate(oX, degree, R1);
					break;

					case Keys::S :
					rotate(oX, -degree, R1);
					break;

					case Keys::Q :
					rotate(oZ, -degree, R1);
					break;

					case Keys::E :
					rotate(oZ, degree, R1);
					break;

					case Keys::Z :
					nearbar++;
					unit(R1);
					break;

					case Keys::X :
					nearbar--;
					unit(R1);
					break;

					case Keys::C :
					fovybar+=0.017; // 1 ������ ~ 0.017 ���
					unit(R1);
					break;

					case Keys::V :
					fovybar-=0.017;
					unit(R1);
					break;

					case Keys::O :
					rotatephi*=1.1;
					unit(R1);
					break;

					case Keys::L :
					rotatephi/=1.1;
					unit(R1);
					break;

					case Keys::Escape:
					LookAt(center, eye, up, T3D);
			
					 // �������������� ����� eye' ��� eye ����� �������������� T
					 point3D help;
					 vec3D evec, evec2;
			 
					 point2vec(eye, evec);
					 timesMatVec(T3D, evec, evec2);
					 vec2point(evec2, help);

					 fovybar = fovy; nearbar = near;
					 unit(R1);
					 break;


					/*case Keys::Z :
					scale((1/1.1), R); // ��������������� � 1.1 ���
					break;
					
					case Keys::U :
					mirrorcentery(R, rect.Width);
					break;
					case Keys::J :
					mirrorcenterx(R, rect.Height);			
					break;

					case Keys::R :
					rotatecenter(0.05, R, rect.Width, rect.Height);		
					break;
					case Keys::Y :
					rotatecenter(-0.05, R, rect.Width, rect.Height);			
					break;

					case Keys::C :
					scalecenter((1/1.1), R, rect.Width, rect.Height);			
					break;
					case Keys::V :
					scalecenter(1.1, R, rect.Width, rect.Height);			
					break;

					case Keys::I :
					scalecenterx((1/1.1), R);			
					break;
					case Keys::O :
					scalecenterx(1.1, R);			
					break;
					case Keys::K :
					scalecentery((1/1.1), R);			
					break;
					case Keys::L :
					scalecentery(1.1, R);			
					break;

					*/
					case Keys::P :
					prOrtho = !prOrtho;
					unit(R1);
					break;

					/*case Keys::Escape :
					unit(R);
					frame (Vx, Vy, Vcx, Vcy, Wx, Wy, Wcx, Wcy, T);
					break;*/

					default :
					unit(R1);
				}
				times(R1,T3D,T1);
				set(T1, T3D);
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
