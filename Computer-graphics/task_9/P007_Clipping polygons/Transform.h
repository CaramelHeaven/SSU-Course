#include <array>
#include <vector>

value struct point //��������� �����
{
float x, y;
};

value struct point3D //���������� �����
{
float x, y, z;
};

value struct color
{
	int R, G, B;
};

value struct line //������� � ��������� ��-��
{
point start, end;
System::String^ name;
};

#define M 3 // ���������� ��������� - ������� ������� ��������������
typedef std::tr1::array<float, M> vec; // ������
typedef std::tr1::array<vec, M> mat; // �������

#define N 4
typedef std::tr1::array<float, N> vec3D; // ���������� ����� � ���������� �����������
typedef std::tr1::array<vec3D, N> mat3D; // ������� ����������� ��������������

typedef System::Collections::Generic::List <point> polygon; // �������������
typedef System::Collections::Generic::List <point3D> polygon3D; // ���������� �������������
typedef System::Collections::Generic::List <point3D> triangle3D; // ���������� �����������

typedef System::Collections::Generic::List <color> colors;

extern mat T; // ������� ������������ ��������������
extern mat3D T3D;
extern std::vector<mat> matrices; // ������ ������ ��������������

void times(mat a, mat b, mat &c);  // ��������� ������� � �� ������� b, ������ � ������� c
void timesMatVec(mat a, vec b, vec &c); // ��������� ������� � �� ������ b, ������ � ������ c
void set(mat a, mat &b); // �������� ������� � � ������� b

void point2vec(point a, vec &b); // �������������� ���������� ��������� �����, �������� ��������� ���� point � ����������, �������� �������� vec
void vec2point(vec a, point &b); // �������� ����������� �������������

void makeHomogenVec(float x, float y, vec &c); // �������������� ��������� � � � � ������ ���������� ���������
void unit(mat &a); // ������������� ������� � ��������� �������

void move(float Tx, float Ty, mat &c); // �������
void rotate(float phi, mat &c); // �������
void scale(float S, mat &c); // ���������������

void mirrorcentery(mat &c, float w); // ���������� ��������� �� ��������� ������������ ������ ����
void mirrorcenterx(mat &c, float h); // ���������� ��������� �� ����������� ������������ ������ ����
void rotatecenter(float phi, mat &c, float w, float h); // ������� ������������ ������
void scalecenter(float S, mat &c, float w, float h); // ��������������� ������������ ������
void scalecenterx(float S, mat &c); // ��������������� ������������ �������������� ��� (�����������)
void scalecentery(float S, mat &c); // ��������������� ������������ ������������ ��� (�����������)
void mirror(mat &c); // ���������� ��������� �� ��������� ������������ ������ ���������
void frame (float Vx, float Vy, float Vcx, float Vcy, float Wx, float Wy, float Wcx, float Wcy, mat &c); // ������������
void scalepicresize (float quo1, float quo2, float Wcx, float top, mat &c); // resize ����������� ��� resize ����

// 3D -->
void times(mat3D a, mat3D b, mat3D &c);
void timesMatVec(mat3D a, vec3D b, vec3D &c);
void set(mat3D a, mat3D &b);

void point2vec(point3D a, vec3D &b);
void vec2point(vec3D a, point3D &b);

void makeHomogenVec(float x, float y, float z, vec3D &c);

void unit(mat3D &a);
void move(float Tx, float Ty, float Tz, mat3D &c);
void rotate(point3D n, float phi, mat3D &c);
void scale(float Sx, float Sy, float Sz, mat3D &c);

void LookAt (point3D eye, point3D center, point3D up, mat3D &c);
void Ortho (float Vx, float Vy, float near, float far, mat3D &c);
void Frustrum (float Vx, float Vy, float near, float far, mat3D &c);
void Perspective (float fovy, float aspect, float near, float far, mat3D &c);

void set (point3D a, point &b); // ������� �� ���������� ������� ��������� � ��������� ��� �����