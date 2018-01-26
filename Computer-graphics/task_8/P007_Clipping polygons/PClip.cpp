#pragma once 
#include <stdafx.h> 
#include "Transform.h"
#include "PClip.h"
polygon^ PClip (polygon^ P, point Pmin, point Pmax)
{
	// ��������������� ����������
	point temp, temp2;
	polygon^ empty = gcnew polygon(0);
	
	// ����
	float xmin, ymin, xmax, ymax;
	xmin = Pmin.x; ymin = Pmin.y;
	xmax = Pmax.x; ymax = Pmax.y;	

	// 1 ���
	float i = 1; // ����� ������� ������� ������� ���������
	float n1; // ���-�� ������ � ��������� ��������������
	n1 = P->Count - 1; // ���-�� ������ � ��������� ��������������
	polygon^ P1 = gcnew polygon(0); // ������������� �� �����

	for (int j = 0; j < P->Count; j++)
	{
		P1->Add(P[j]);
	}

	// 3 ��� ����������
	float k, n2, Q0;
	point p0, Ni, Fi;
	polygon^ P2 = gcnew polygon(0);

	// 7 ��� ����������
	float Q1;

	// 8 ��� ����������
	float t;

	// 2 ���
	step2:
	while (i < 5)
	{	
	// 3 ���		
		// (a)
		if ( i == 1)
		{
			Ni.x = 1; Ni.y = 0;
			Fi.x = xmin; Fi.y = ymin;
		}
		// (b)
		else if ( i == 2)
		{
			Ni.x = 0; Ni.y = -1;
			Fi.x = xmin; Fi.y = ymax;
		}
		// (c)
		else if ( i == 3)
		{
			Ni.x = -1; Ni.y = 0;
			Fi.x = xmax; Fi.y = ymax;
		}
		// (d)
		else if ( i == 4)
		{
			Ni.x = 0; Ni.y = 1;
			Fi.x = xmax; Fi.y = ymin;
		}
		
		k = 0; // ����� �������� ����� ����������� ��������������
		
		// Q0
		temp.x = P1[n1].x - Fi.x;
		temp.y = P1[n1].y - Fi.y;
		Q0 = (temp.x * Ni.x) + (temp.y * Ni.y);

		p0 = P1[n1];
		n2 = 0; // ���������� ����� � ���������� ��������� ������������ ������� ������� ������� ���������
		//polygon^ P2 = gcnew polygon(0);
		P2->Clear();
		
		step4:
		// 4 ���
		if ( k > n1 )
		{	
		// 5 ���
			//P1 = P2;
			P1->Clear();
			for (int j = 0; j < P2->Count; j++)
			{
				P1->Add(P2[j]);
			}
			
			if (n2 == 0) // ����� �������� ������������ n1 = -1, �.�. ��� �������� � ������ �� ������� ������
			{
				n1 = n2;
			}
			else
			{
				n1 = n2-1;
			}

		// 6 ���
			if (n1 == 0)
			{
				return (empty);
			}
			else 
			{
				i++; goto step2;
			}
		}

		// 7 ���
		else
		{
			// Q1
			temp.x = P1[k].x - Fi.x;
			temp.y = P1[k].y - Fi.y;
			Q1 = (temp.x * Ni.x) + (temp.y * Ni.y);

		// 8 ���
			if ( (Q0 * Q1) < 0)
			{
				t = Q0/(Q0-Q1);
				n2++;
				
				// p0 - P1[k]
				temp.x = p0.x - P1[k].x;
				temp.y = p0.y - P1[k].y;

				// (p0 - P1[k])*t
				temp.x *= t; temp.y *= t;

				// p0 - (p0 - P1[k])*t
				temp2.x = p0.x - temp.x;
				temp2.y = p0.y - temp.y;
				
				// P2[n2] = p0 - (p0 - P1[k])*t
				P2->Add(temp2);
			}

		// 9 ���
			if ( Q1 >= 0)
			{
				n2++;
				P2->Add(P1[k]);
			}
		}

		// 10 ���
		Q0 = Q1;
		p0.x = P1[k].x;
		p0.y = P1[k].y;
		k++;
		goto step4;
	}
	return (P1);
}

point InPntGet(polygon^ p)
{
	point inpoint;
	float ybar, ybarbar;

	ybar = p[0].y;

	for (int i = 1; i < p->Count; i++) // ���������� y'
	{
		if (p[i].y < ybar) ybar = p[i].y;
	}

	for (int i = 0; i < p->Count; i++) // ���������� y''
	{
		if (p[i].y > ybar)
		{
			ybarbar = p[i].y;
			break;
		}
	}

	for (int i = 0; i < p->Count; i++) // ��������� ���������� ��� ��������
	{
		if ((p[i].y < ybarbar) && (p[i].y > ybar)) 
			ybarbar = p[i].y;
	}

	float ymid = (ybar + ybarbar) / 2;
	
	std::vector <float> help; // ��������������� ������

	for (int i = 0; i < p->Count; i++)
	{
		point p1 = p[i];
		point p2 = p[(i+1) % p->Count];
		
		if (p1.y > p2.y)
		{
			std::swap(p1.x, p2.x);
			std::swap(p1.y, p2.y);
		}
		
		if ((p1.y <= ymid && p2.y >= ymid) && (abs(p1.y - p2.y) > 0.001))
		{
			float x = p1.x + (ymid - p1.y) / (p2.y - p1.y) * (p2.x - p1.x);
			help.push_back(x);
		}
	}
	float xbar = help[0];
	float xbarbar;
	for (std::vector<float>::iterator iter = help.begin(); iter!= help.end(); ++iter)
	{
		if (xbar > *iter) xbar = *iter;
	}
	for (std::vector<float>:: iterator iter = help.begin(); iter!= help.end(); ++iter)
	{
		if (*iter != xbar){
			xbarbar = *iter;
			break;
		}
	}
	for (std::vector<float>:: iterator iter = help.begin(); iter!= help.end(); ++iter)
	{
		if ((xbarbar > *iter) && (*iter > xbar)) xbarbar = *iter;
	}
	if (help.size() == 1) xbarbar = help[0];
	inpoint.y = (int)(ymid );
	inpoint.x = (int)(xbar + xbarbar) / 2;
	return inpoint;
}

void Pfill (polygon^ P, System::Drawing::Bitmap^ image, System::Drawing::Color C)
{
	System::Drawing::Graphics^ g = System::Drawing::Graphics::FromImage(image);

	//1 ���
	System::Collections::Generic::Stack <point> Pointstack;
	Pointstack.Clear();
	
	//2 ��� (��� �������� ������)
	point inpoint; // ���������� ����� �������
	inpoint = InPntGet(P); // ��������� ���������� �����
	
	//3 ���

	image->SetPixel(inpoint.x, inpoint.y, C); // help
		
	Pointstack.Push(inpoint);

	while (Pointstack.Count != 0)
	{
		//5 ���
		point xjyj;
		xjyj = Pointstack.Pop();
		
		float xj, yj;
		xj = xjyj.x;
		yj = xjyj.y;

		point a, xyj_2, xyj_3, xyj_4;
		a.x=xj-1;
		a.y=yj;
		
		xyj_2.x=xj+1;
		xyj_2.y=yj;

		xyj_3.x=xj;
		xyj_3.y=yj-1;

		xyj_4.x=xj;
		xyj_4.y=yj+1;

		if (image->GetPixel(a.x, a.y).ToArgb() != C.ToArgb())
		{	
			image->SetPixel(a.x, a.y, C);
			Pointstack.Push(a);
		}

		if (image->GetPixel(xyj_2.x, xyj_2.y).ToArgb() != C.ToArgb())
		{	
			image->SetPixel(xyj_2.x, xyj_2.y, C);
			Pointstack.Push(xyj_2);
		}
		if (image->GetPixel(xyj_3.x, xyj_3.y).ToArgb() != C.ToArgb())
		{
			image->SetPixel(xyj_3.x, xyj_3.y, C);
			Pointstack.Push(xyj_3);
		}
		if (image->GetPixel(xyj_4.x, xyj_4.y).ToArgb() != C.ToArgb())
		{	
			image->SetPixel(xyj_4.x, xyj_4.y, C);
			Pointstack.Push(xyj_4);
		}
	}
}