polygon^ PClip (polygon^ P, point Pmin, point Pmax);
void Pfill (polygon^ P, System::Drawing::Bitmap^ image, System::Drawing::Color C);
//void vec_diff(point P1, point Fi, point &temp); // разность векторов
point InPntGet(polygon^ p); // получение внутренней точки