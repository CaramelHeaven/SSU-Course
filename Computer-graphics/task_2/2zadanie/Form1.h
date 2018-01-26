#pragma once

namespace My2zadanie {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	/// <summary>
	/// Summary for Form1
	/// </summary>
	public ref class Form1 : public System::Windows::Forms::Form
	{
	public:
		Form1(void)
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
		~Form1()
		{
			if (components)
			{
				delete components;
			}
		}

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
			this->SuspendLayout();
			// 
			// Form1
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->ClientSize = System::Drawing::Size(284, 262);
			this->Name = L"Form1";
			this->Text = L"Form1";
			this->Paint += gcnew System::Windows::Forms::PaintEventHandler(this, &Form1::Form1_Paint);
			this->ResumeLayout(false);

		}
#pragma endregion
	private: System::Void Form1_Paint(System::Object^  sender, System::Windows::Forms::PaintEventArgs^  e) {
				 Graphics^ g=e->Graphics;
				 //g->Clear(Color::Aquamarine);
				 Rectangle rect=Form::ClientRectangle;
				 Rectangle smallRect;

				 Pen^ BlackPen=gcnew Pen(Color::Black);
				 BlackPen->Width=2;
				 g->DrawLine (BlackPen, 80, 40, 80, 120);//start kvardat
				 g->DrawLine (BlackPen, 80, 120, 180, 120);
				 g->DrawLine (BlackPen, 180, 120, 180, 40);
				 g->DrawLine (BlackPen, 180, 40, 80, 40);
				 g->DrawLine (BlackPen, 100, 120, 100, 140);//KONEZ KVADRATA
				 g->DrawLine (BlackPen, 100, 140, 40, 140);
				 g->DrawLine (BlackPen, 40, 140, 40, 160);//start kvadrat
				 g->DrawLine (BlackPen, 40, 160, 280, 160);
				 g->DrawLine (BlackPen, 280, 160, 280, 140);
				 g->DrawLine (BlackPen, 280, 140, 160, 140);
				 g->DrawLine (BlackPen, 160, 140, 160, 120);//the end stol
				 g->DrawLine (BlackPen, 60, 160, 60, 280);//start nojka
				 g->DrawLine (BlackPen, 60, 280, 80, 280);
				 g->DrawLine (BlackPen, 80, 280, 80, 160);//the ent nojka
				 g->DrawLine (BlackPen, 180, 160, 180, 280);//start 2 nojka
				 g->DrawLine (BlackPen, 180, 280, 260, 280);
				 g->DrawLine (BlackPen, 260, 280, 260, 160);
				 g->DrawLine (BlackPen, 260, 280, 260, 160);//the end 2nojka
				 g->DrawLine (BlackPen, 200, 200, 200, 220);//start processor
				 g->DrawLine (BlackPen, 220, 20, 220, 140);
				 g->DrawLine (BlackPen, 220, 20, 260, 20);
				 g->DrawLine (BlackPen, 260, 20, 260, 140);//the end proc
				 g->DrawLine (BlackPen, 230, 40, 250, 40);//plushka
				 g->DrawLine (BlackPen, 100, 140, 160, 140);
			 }
	};
}

