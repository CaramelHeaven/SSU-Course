value struct point {
	float x, y;
};

value struct line {
	point start, end;
	System::String^ name;
};

#define M 3
typedef float vec[M];
typedef float mat[M][M];

extern mat T;

void times(mat a, mat b, mat c);
void timesMatVec(mat a, vec b, vec c);
void set(mat a, mat b);
void point2vec(point a, vec b);
void vec2point(vec a, point &b);
void makeHomogenVec(float x, float y, vec c);
void unit(mat a);
void move(float Tx, float Ty, mat c);
void rotate(float phi, mat c);
void scale(float S, mat c);
void window(mat c);
void window_h(mat c);
void scale_Y(float S, mat c);
void scale_X(float S, mat c);
void scale2(float Sx, float Sy, mat c);
void frame(float Vx, float Vy, float Vcx, float Vcy, float Wx, float Wy, float Wcx, float Wcy, mat c);
void new_scale(float Wx_oldWx, float Wy_oldWy, float Wcx, float top, mat c);
void window_hframe(mat c);