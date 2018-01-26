#pragma once  
#include "Transform.h" 
#include "Clip.h"

bool clip(point &A, point &B, point Pmin, point Pmax)
{
	float x1, x2, y1, y2, Tmin = 0;
	float Tmax = 1;
	int i = 1;
	float Pi,Qi;
	while (i <= 5)
	{
	step1:	if (i>4)
	{

		x1 = A.x + (B.x - A.x)*Tmin;
		y1 = A.y + (B.y - A.y)*Tmin;
		x2 = A.x + (B.x - A.x)*Tmax;
		y2 = A.y + (B.y - A.y)*Tmax;
		A.x = x1;
		A.y = y1;
		B.x = x2;
		B.y = y2;
		return true;
	}
			else
			{
				if (i == 1)
				{
					Pi = A.x - B.x;
					Qi = A.x - Pmin.x;
				}

				if (i == 2)
				{
					Pi = B.x - A.x;
					Qi = Pmax.x - A.x;
				}
				if (i == 3)
				{
					Pi = A.y - B.y;
					Qi = A.y - Pmin.y;
				}
				if (i == 4)
				{
					Pi = B.y - A.y;;
					Qi = Pmax.y - A.y;
				}
				if (Pi == 0)
				{
					if (Qi<0)
					{

						return false;

					}
					else
					{
						i = i + 1;
						goto step1;
					}

				}
				if (Pi > 0)
				{
					if (Tmax > (Qi / Pi))
						Tmax = Qi / Pi;
					else
						Tmax = Tmax;
				}
				else
				{
					if (Tmin > (Qi / Pi))
						Tmin = Tmin;
					else
						Tmin = (Qi / Pi);
				}
				if (Tmin>Tmax)
				{

					return false;

				}
				else
				{
					i = i + 1;
					goto step1;
				}
			}
	}
}
