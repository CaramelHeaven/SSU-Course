#include <array>
#include <vector>

value struct point //двумерная точка
{
float x, y;
};

value struct point3D //трехмерная точка
{
float x, y, z;
};

value struct color
{
	int R, G, B;
};

value struct line //отрезок в двумерном пр-ве
{
point start, end;
System::String^ name;
};

#define M 3 // определяем константу - порядок матрицы преобразований
typedef std::tr1::array<float, M> vec; // вектор
typedef std::tr1::array<vec, M> mat; // матрица

#define N 4
typedef std::tr1::array<float, N> vec3D; // трехмерная точка в однородных координатах
typedef std::tr1::array<vec3D, N> mat3D; // матрица трехмерного преобразования

typedef System::Collections::Generic::List <point> polygon; // многоугольник
typedef System::Collections::Generic::List <point3D> polygon3D; // трехмерный многоугольник
typedef System::Collections::Generic::List <point3D> triangle3D; // трехмерный треугольник

typedef System::Collections::Generic::List <color> colors;

extern mat T; // матрица совмещенного преобразования
extern mat3D T3D;
extern std::vector<mat> matrices; // список матриц преобразований

void times(mat a, mat b, mat &c);  // умножение матрицы а на матрицу b, запись в матрицу c
void timesMatVec(mat a, vec b, vec &c); // умножение матрицы а на вектор b, запись в вектор c
void set(mat a, mat &b); // перепись матрицы а в матрицу b

void point2vec(point a, vec &b); // преобразование декартовых координат точки, заданных структрой типа point в однородные, заданные вектором vec
void vec2point(vec a, point &b); // обратное предыдущему преобразовние

void makeHomogenVec(float x, float y, vec &c); // преобразование координат х и у в вектор однородных координат
void unit(mat &a); // пребразование матрицы в единичную матрицу

void move(float Tx, float Ty, mat &c); // перенос
void rotate(float phi, mat &c); // поворот
void scale(float S, mat &c); // масштабирование

void mirrorcentery(mat &c, float w); // зеркальное отражение по вертикали относительно центра окна
void mirrorcenterx(mat &c, float h); // зеркальное отражение по горизонтали относительно центра окна
void rotatecenter(float phi, mat &c, float w, float h); // поворот относительно центра
void scalecenter(float S, mat &c, float w, float h); // масштабирование относительно центра
void scalecenterx(float S, mat &c); // масштабирование относительно горизонтальной оси (центральной)
void scalecentery(float S, mat &c); // масштабирование относительно вертикальной оси (центральной)
void mirror(mat &c); // зеркальное отражение по вертикали относительно начала координат
void frame (float Vx, float Vy, float Vcx, float Vcy, float Wx, float Wy, float Wcx, float Wcy, mat &c); // кадрирование
void scalepicresize (float quo1, float quo2, float Wcx, float top, mat &c); // resize изображения при resize окна

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

void set (point3D a, point &b); // переход из трехмерной системы координат к двумерную для точки