#pragma once
#include "stdafx.h"
#include <iostream>
#include <vector>
#include "Transform.h"
#include <math.h>

mat T;

std::vector<mat> matrices(0);

void times(mat a, mat b, mat &c) {
	for(int i = 0; i < M; i++) {
		for(int j = 0; j < M; j++) {
			float skalaar = 0;
			for(int k = 0; k < M; k++)
				skalaar += a[i][k] * b[k][j];
			c[i][j] = skalaar;
		}
	}
}

void timesMatVec(mat a, vec b, vec &c) {
	for(int i = 0; i < M; i++) {
		float skalaar = 0;
		for(int j = 0; j < M; j++)
			skalaar += a[i][j] * b[j];
		c[i] = skalaar;
	}
}


void set(mat a, mat &b) {
	for(int i = 0; i < M; i++)
		for (int j = 0; j < M; j++)
			b[i][j] = a[i][j];
}

void point2vec(point a, vec &b) {
	b[0] = a.x; b[1] = a.y; b[2] = 1;
}

void vec2point(vec a, point &b) {
	b.x = ((float)a[0])/a[2];
	b.y = ((float)a[1])/a[2];
}

void makeHomogenVec(float x, float y, vec &c){
	c[0] = x; c[1] = y; c[2] = 1;
}

void unit(mat &a) {
	for (int i = 0; i < M; i++) {
		for (int j = 0; j < M; j++) {
			if (i == j) a[i][j] = 1;
			else a[i][j] = 0;
		}
	}
}

void move(float Tx, float Ty, mat &c) {
	unit (c);
	c[0][M-1] = Tx;
	c[1][M-1] = Ty;
}

void rotate(float phi, mat &c) {
	unit (c);
	c[0][0] = cos(phi); c[0][1] = -sin(phi);
	c[1][0] = sin(phi); c[1][1] = cos(phi);
}

void scale(float S, mat &c) {
	unit (c);
	c[0][0] = S; c[1][1] = S;
}

void Window_new(mat &c){
	unit (c);
	c[0][0] = -1;
	c[1][1] = -1;
}
void mirror_g(mat &c)
{
	unit(c);
	c[0][0] = -1;
	
}

void mirror_v(mat &c)
{
	unit(c);
	c[1][1] = -1;
}
void Rotate_c(float phi, mat &c, float aa, float ss) {
unit (c);
c[0][0] = cos(phi); c[0][1] = -sin(phi); c[0][2]=(aa/2)*(1-cos(phi))+(ss/2)*sin(phi);
c[1][0] = sin(phi); c[1][1] = cos(phi); c[1][2]=(ss/2)*(1-cos(phi))-(aa/2)*sin(phi);
}
void Scale_c(float S, mat &c, float zz, float xx){
unit (c);
c[0][0] = S; c[0][2]=(zz/2)*(1-S);
c[1][1] = S; c[1][2]=(xx/2)*(1-S);
}

void Scale_cX(float S, mat &c, float gb){
unit (c);
c[0][0] = S; c[0][2]=(gb/2)*(1-S);
}

void Scale_cY(float S, mat &c, float vf){
unit (c);
c[1][1] = S; c[1][2]=(vf/2)*(1-S);
}

void frame (float Vx, float Vy, float Vcx, float Vcy, float Wx, float Wy, float Wcx, float Wcy, mat &c)
{
	unit (c);
	c[0][0] = Wx/Vx; 
	c[0][2] = Wcx-((Vcx*Wx)/Vx); 
	c[1][1] = -Wy/Vy; 
	c[1][2] = Wcy+((Vcy*Wy)/Vy); 
}
void specscale (float quone, float quotwo, float Wcx, float top, mat &c) 
{ 
unit (c); 
c[0][0]=quone; c[0][2]=Wcx*(1-quone); 
c[1][1]=quotwo; c[1][2]=top*(1-quotwo); 
}
