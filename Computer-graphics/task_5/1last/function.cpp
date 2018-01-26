#include "function.h"
#include <cmath>
float f(float x)
{
	if(x!=0)
	{
		return 1/x;
	}
}

bool fexists(float x)
{
	if (abs(x) < 0.0001)
	{
		return false;
	}
	else
		return true;
}