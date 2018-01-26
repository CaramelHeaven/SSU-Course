#include <array>
#include <vector>

value struct point 
{
	float x, y;
};
value struct line
{
	point start, end;
	System::String^ name;
};

#define M 3

typedef std::tr1::array<float, M> vec;
typedef std::tr1::array<vec, M> mat;
typedef System::Collections::Generic::List<point> polygon;
//typedef float vec[M];
//typedef float mat[M][M];

extern mat T;
extern std::vector<mat> matrices;

void times(mat a, mat b, mat &c);
void timesMatVec(mat a, vec b, vec &c);
void set(mat a, mat &b);
void point2vec(point a, vec &b);
void vec2point(vec a, point &b);
void makeHomogenVec(float x, float y, vec &c);
void unit(mat &a);
void move(float Tx, float Ty, mat &c);
void rotate(float phi, mat &c);
void scale(float S, mat &c);
void Window_new(mat &c);
void Mir_cX(mat &c, float asd);
void Rotate_c(float phi, mat &c, float aa, float ss);
void mirror_v(mat &c);
void mirror_g(mat &c);
void Scale_c(float S, mat &c, float zz, float xx);
void Scale_cX(float S, mat &c, float gb); 
void Scale_cY(float S, mat &c, float vf);
void frame (float Vx, float Vy, float Vcx, float Vcy,float Wx, float Wy, float Wcx, float Wcy,mat &c);
void specscale (float quone, float quotwo, float Wcx, float top, mat &c);