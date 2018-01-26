#pragma once
#include "stdafx.h"
#include "Transform.h"
#include <math.h>

mat T;
mat3D T3D;
std::vector <mat> matrices(0);

void times(mat a, mat b, mat &c)
{
	for(int i = 0; i < M; i++) 
	{
		for(int j = 0; j < M; j++) 
		{
			float skalaar = 0;
			for(int k = 0; k < M; k++)
				skalaar += a[i][k] * b[k][j];
			c[i][j] = skalaar;
		}
	}
}

void times(mat3D a, mat3D b, mat3D &c) // 3D
{
	for(int i = 0; i < N; i++) 
	{
		for(int j = 0; j < N; j++) 
		{
			float skalaar = 0;
			for(int k = 0; k < N; k++)
				skalaar += a[i][k] * b[k][j];
			c[i][j] = skalaar;
		}
	}
}

void timesMatVec(mat a, vec b, vec &c) 
{
	for(int i = 0; i < M; i++) 
	{
		float skalaar = 0;
		for(int j = 0; j < M; j++)
			skalaar += a[i][j] * b[j];
		c[i] = skalaar;
	}
}

void timesMatVec(mat3D a, vec3D b, vec3D &c) // 3D
{
	for(int i = 0; i < N; i++) 
	{
		float skalaar = 0;
		for(int j = 0; j < N; j++)
			skalaar += a[i][j] * b[j];
		c[i] = skalaar;
	}
}

void set(mat a, mat &b) 
{
	for(int i = 0; i < M; i++)
		for (int j = 0; j < M; j++)
			b[i][j] = a[i][j];
}

void set(mat3D a, mat3D &b) // 3D
{
	for(int i = 0; i < N; i++)
		for (int j = 0; j < N; j++)
			b[i][j] = a[i][j];
}

void point2vec(point a, vec &b)
{
	b[0] = a.x; b[1] = a.y; b[2] = 1;
}

void point2vec(point3D a, vec3D &b) // 3D
{
	b[0] = a.x; b[1] = a.y; b[2] = a.z; b[3] = 1;
}

void vec2point(vec a, point &b) 
{
	b.x = ((float)a[0])/a[2];
	b.y = ((float)a[1])/a[2];
}

void vec2point(vec3D a, point3D &b) // 3D
{
	b.x = ((float)a[0])/a[3];
	b.y = ((float)a[1])/a[3];
	b.z = ((float)a[2])/a[3];
}

void makeHomogenVec(float x, float y, vec &c)
{
	c[0] = x; c[1] = y; c[2] = 1;
}

void makeHomogenVec(float x, float y, float z, vec3D &c) // 3D
{
	c[0] = x; c[1] = y; c[2] = z; c[3] = 1;
}

void unit(mat &a) 
{
	for (int i = 0; i < M; i++) 
	{
		for (int j = 0; j < M; j++) 
		{
			if (i == j) a[i][j] = 1;
		else a[i][j] = 0;
		}
	}
}

void unit(mat3D &a) // 3D
{
	for (int i = 0; i < N; i++) 
	{
		for (int j = 0; j < N; j++) 
		{
			if (i == j) a[i][j] = 1;
		else a[i][j] = 0;
		}
	}
}

void move(float Tx, float Ty, mat &c)
{
	unit (c);
	c[0][M-1] = Tx;
	c[1][M-1] = Ty;
}

void move(float Tx, float Ty, float Tz, mat3D &c) // 3D
{
	unit (c);
	c[0][N-1] = Tx;
	c[1][N-1] = Ty;
	c[2][N-1] = Tz;
}

void rotate(float phi, mat &c) 
{
	unit (c);
	c[0][0] = cos(phi); c[0][1] = -sin(phi);
	c[1][0] = sin(phi); c[1][1] = cos(phi);
}

void rotate(point3D n, float phi, mat3D &c) // 3D
{
	c[0][0] = (n.x * n.x - 1) * (1 - cos(phi)) + 1;		c[0][1] = n.x * n.y * (1 - cos(phi)) - n.z * sin(phi);		c[0][2] = n.y * sin(phi) + n.x * n.z * (1 - cos(phi));		c[0][3] = 0;
	c[1][0] = n.z * sin(phi) + n.x * n.y * (1 - cos(phi));		c[1][1] = (n.y * n.y - 1) * (1 - cos(phi)) + 1;		c[1][2] = n.y * n.z * (1 - cos(phi)) - n.x * sin(phi);		c[1][3] = 0;
	c[2][0] = n.x * n.z * (1 - cos(phi)) - n.y * sin (phi);		c[2][1] = n.x * sin(phi) + n.y * n.z * (1 - cos(phi));		c[2][2] = (n.z * n.z - 1) * (1 - cos(phi)) + 1;		c[2][3] = 0;
	c[3][0] = 0;	c[3][1] = 0;	c[3][2] = 0;	c[3][3] = 1;
}

void scale(float S, mat &c)
{
	unit (c);
	c[0][0] = S; c[1][1] = S;
}

void scale(float Sx, float Sy, float Sz, mat3D &c) // 3D
{
	unit (c);
	c[0][0] = Sx; c[1][1] = Sy; c[2][2] = Sz;
}

void mirrorcentery(mat &c, float w){
	unit (c);
	c[0][0] = -1; c[0][2]=w;
}

void mirrorcenterx(mat &c, float h){
	unit (c);
	c[1][1] = -1; c[1][2]=h;
}

void rotatecenter(float phi, mat &c, float w, float h) 
{
	unit (c);
	c[0][0] = cos(phi); c[0][1] = -sin(phi); c[0][2]=(w/2)*(1-cos(phi))+(h/2)*sin(phi);
	c[1][0] = sin(phi); c[1][1] = cos(phi); c[1][2]=(h/2)*(1-cos(phi))-(w/2)*sin(phi);
}

void scalecenter(float S, mat &c, float w, float h)
{
	unit (c);
	c[0][0] = S; c[0][2]=(w/2)*(1-S);
	c[1][1] = S; c[1][2]=(h/2)*(1-S);
}

void scalecenterx(float S, mat &c)
{
	unit (c);
	c[0][0] = S;
}

void scalecentery(float S, mat &c)
{
	unit (c);
	c[1][1] = S;
}

void mirror(mat &c){
	unit (c);
	c[1][1] = -1;
}

void frame (float Vx, float Vy, float Vcx, float Vcy, float Wx, float Wy, float Wcx, float Wcy, mat &c)
{
	unit (c);
	c[0][0] = Wx/Vx; /*c[0][1]*/ c[0][2] = Wcx-((Vcx*Wx)/Vx);
	/*c[1][0]*/ c[1][1] = -Wy/Vy; c[1][2] = Wcy+((Vcy*Wy)/Vy);
}

void scalepicresize (float quo1, float quo2, float Wcx, float top, mat &c)
{
	unit (c);
	c[0][0]=quo1; c[0][2]=Wcx*(1-quo1);
	c[1][1]=quo2; c[1][2]=top*(1-quo2);
}

// 3D -->
void LookAt (point3D eye, point3D center, point3D up, mat3D &c) //3
{
	// eye = S, center = P, u = up
	point3D vPS, e1, e2, e3;

	// s - p
	vPS.x = eye.x - center.x;
	vPS.y = eye.y - center.y;
	vPS.z = eye.z - center.z;

	float vPSLength;
	vPSLength = sqrt(vPS.x * vPS.x + vPS.y * vPS.y + vPS.z * vPS.z);

	e3.x = vPS.x / vPSLength;
	e3.y = vPS.y / vPSLength;
	e3.z = vPS.z / vPSLength;

	// [u x e3]
	point3D uxe3;	
	uxe3.x = up.y * e3.z - up.z * e3.y;
	uxe3.y = up.z * e3.x - up.x * e3.z;
	uxe3.z = up.x * e3.y - up.y * e3.x;

	float uLength;
	uLength = sqrt(up.x * up.x + up.y * up.y + up.z * up.z);

	e1.x = uxe3.x / uLength;
	e1.y = uxe3.y / uLength;
	e1.z = uxe3.z / uLength;

	// [e3 x e1]
	e2.x = e3.y * e1.z - e3.z * e1.y;
	e2.y = e3.z * e1.x - e3.x * e1.z;
	e2.z = e3.x * e1.y - e3.y * e1.x;

	c[0][0] = e1.x;		c[0][1] = e1.y;		c[0][2] = e1.z;		c[0][3] = -e1.x * eye.x - e1.y * eye.y - e1.z * eye.z;
	c[1][0] = e2.x;		c[1][1] = e2.y;		c[1][2] = e2.z;		c[1][3] = -e2.x * eye.x - e2.y * eye.y - e2.z * eye.z;
	c[2][0] = e3.x;		c[2][1] = e3.y;		c[2][2] = e3.z;		c[2][3] = -e3.x * eye.x - e3.y * eye.y - e3.z * eye.z;
	c[3][0] = 0;		c[3][1] = 0;		c[3][2] = 0;		c[3][3] = 1;
}

void Ortho (float Vx, float Vy, float near, float far, mat3D &c)
{
	unit(c);
	c[0][0] = 2 / Vx;
	c[1][1] = 2 / Vy;
	c[2][2] = 2 / (far-near); c[2][3] = (far + near) / (far - near);
}

void Frustrum (float Vx, float Vy, float near, float far, mat3D &c)
{
	unit(c);
	c[0][0] = near * (2 / Vx);
	c[1][1] = near * (2 / Vy);
	c[2][2] = (far + near) / (far - near); c[2][3] = (2 * far * near) / (far - near);
	c[3][2] = -1; c[3][3] = 0;
}

void Perspective (float fovy, float aspect, float near, float far, mat3D &c)
{
	unit(c);
	// ctg = 1/tg
	c[0][0] = (1 / aspect) * (1 / tan(fovy / 2));
	c[1][1] = 1 / tan(fovy / 2);
	c[2][2] = (far + near) / (far - near); c[2][3] = (2 * far * near) / (far - near);
	c[3][2] = -1; c[3][3] = 0;
}

void set (point3D a, point &b)
{
	b.x = a.x; b.y = a.y;
}
