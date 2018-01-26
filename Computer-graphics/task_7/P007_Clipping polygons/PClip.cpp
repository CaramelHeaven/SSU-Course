#pragma once 
#include <stdafx.h> 
#include "Transform.h"
#include "PClip.h"
point Angel(point A, point B)
{
	point res;
	res.x = A.x - B.x;
	res.y = A.y - B.y;
	return res;
}

point AAA(point A, point B)
{
	point res;
	res.x = A.x + B.x;
	res.y = A.y + B.y;
	return res;
}

float Mult(point A, point B)
{
	return (A.x * B.y) - (B.x * A.y);
}

point qwe(point A, float skalaar)
{
	A.x *= skalaar;
	A.y *= skalaar;
	return A;
}
polygon^ PClip (polygon^ P, point Pmin, point Pmax){
	int i = 1;
	int n1 = P->Count;
	int n2;
	polygon^ P1 = gcnew polygon(0);
	P1 = P;
	
	point N, F;
	int k;
	float Q0, Q1;
	point p0;
	float t;
	point f;
	f.x = Pmax.x;
	f.y = Pmin.y;
 while(i <= 4){
		
		switch(i){
		case 1:
			N.x = 1;
			N.y = 0;
			F.x = Pmin.x;
			F.y = Pmin.y;
			break;
		case 2:
			N.x = 0;
			N.y = -1;
			F.x = Pmin.x;
			F.y = Pmax.y;
			break;
		case 3:
			N.x = -1;
			N.y = 0;
			F.x = Pmax.x;
			F.y = Pmax.y;
			break;
		case 4:
			N.x = 0;
			N.y = 1;
			F.x = Pmax.x;
			F.y = Pmin.y;
			break;
		}
		k = 1;
		Q0 = (P1[n1 - 1].x - F.x) * (f.x - F.x) + (P1[n1 - 1].y - F.y) * (f.y - F.y);
		p0.x = P1[n1 - 1].x;
		p0.y = P1[n1 - 1].y;
		n2 = 0;
		polygon^ P2 = gcnew polygon(0);
		while (k <= n1){
	
			
		Q1 = (P1[k - 1].x - F.x) * (f.x - F.x) + (P1[k - 1].y - F.y) * (f.y - F.y);

		if (Q0 * Q1 < 0){
			t = Q0 / (Q0 - Q1);
			
			point temp;
				temp.x = p0.x - (p0.x - P1[k - 1].x) * t;
				temp.y = p0.y - (p0.y - P1[k - 1].y) * t;
				P2->Add(temp);
			n2++;
		}

		if (Q1 >= 0){
			
			point temp;
				temp.x = P1[k - 1].x;
				temp.y = P1[k - 1].y;
				P2->Add(temp);
			n2++;
		}
		Q0 = Q1;
		p0.x = P1[k - 1].x;
		p0.y = P1[k - 1].y;
		k++;
		}
		P1 = P2;
		n1 = n2;
		f.x = F.x;
		f.y = F.y;
		if (n1 == 0)
			return P1;
		else
			i++;

	}
	return P1;
}