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

#define M 3 // определяем константу - порядок матрицы преобразований
typedef std::tr1::array<float, M> vec; // вектор
typedef std::tr1::array<vec, M> mat; // матрица

extern mat T; // матрица совмещенного преобразования
extern std::vector<mat> matrices; // список матриц преобразований


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
void Scale_c(float S, mat &c, float zz, float xx);
void Scale_cX(float S, mat &c, float gb); 
void Scale_cY(float S, mat &c, float vf);
void mirror(mat &c);
void frame (float Vx, float Vy, float Vcx, float Vcy, float Wx, float Wy, float Wcx, float Wcy, mat &c);
void Scale_pp (float hup, float hop, float Wcx, float top, mat &c);
