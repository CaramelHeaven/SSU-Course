#pragma once 
#include <stdafx.h> 
#include "Transform.h" 
#include "clip.h"

bool Clip (point &A, point &B, point Pmin, point Pmax)
{
	float x1,x2,y1,y2,tmin=0;
	float tmax=1;
	int i=1;
	float Pi;
	float Qi;
	while(i<=5)
	{
step2:	if(i>4)
		{

			x1=A.x+(B.x-A.x)*tmin;
			y1=A.y+(B.y-A.y)*tmin;
			x2=A.x+(B.x-A.x)*tmax;
			y2=A.y+(B.y-A.y)*tmax;
			A.x=x1;
			A.y=y1;
			B.x=x2;
			B.y=y2;
			return true;
		}
		else 
		{
			if(i==1)
			{
				Pi=A.x-B.x;
				Qi=A.x-Pmin.x;
			}

			if(i==2)
			{
				Pi=B.x-A.x;
				Qi=Pmax.x-A.x;
			}
			if(i==3)
			{
				Pi=A.y-B.y;
				Qi=A.y-Pmin.y;
			}
			if(i==4)
			{
				Pi=B.y-A.y;;
				Qi=Pmax.y-A.y;
			}
			if(Pi==0)
			{
				if(Qi<0)
				{

					return false;

				}
				else 
				{
					i=i+1;
					goto step2;
				}

			}
			if(Pi>0)
			{

				if(tmax>(Qi/Pi))
					tmax=Qi/Pi;
				else
					tmax=tmax;
			}
			else 
			{

				if(tmin>(Qi/Pi))
					tmin=tmin;
				else 
					tmin=Qi/Pi;
			}
			if(tmin>tmax)
			{

				return false;

			}
			else
			{
				i=i+1;
				goto step2;
			}
		}
	}
}
