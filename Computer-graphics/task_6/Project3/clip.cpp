#pragma once
#include "stdafx.h"
#include "Transform.h"
#include "clip.h"


void Clip1Left(point &A, float dx, float dy, float dxl) {
	A.x = A.x + dxl;
	A.y = A.y + dxl * dy / dx;
}

void Clip1Top(point &A, float dx, float dy, float dyt) {
	A.x = A.x + dyt * (dx / dy);
	A.y = A.y + dyt;
}

void Clip1Bottom(point &A, float dx, float dy, float dyb) {
	A.x = A.x + dyb * (dx / dy);
	A.y = A.y + dyb;
}

void Clip2Right(point A, point &B, float dx, float dy, float dxr) {
	B.x = A.x + dxr;
	B.y = A.y + dxr * (dy / dx);
}

void Clip2Top(point A, point &B, float dx, float dy, float dyt) {
	B.x = A.x + dyt * (dx / dy);
	B.y = A.y + dyt;
}

void Clip2Bottom(point A, point &B, float dx, float dy, float dyb) {
	B.x = A.x + dyb * (dx / dy);
	B.y = A.y + dyb;
}

bool clip(point &A, point &B, point Pmin, point Pmax) {
	point K;
	/* 1 */
	if (A.x > B.x) {K=A; A=B; B=K;};

	/* 2 */
	int C1 = 0;
	if (A.x < Pmin.x) C1 += 1;
	if (A.x > Pmax.x) C1 += 2;
	if (A.y < Pmin.y) C1 += 4;
	if (A.y > Pmax.y) C1 += 8;

	int C2 = 0;
	if (B.x < Pmin.x) C2 += 1;
	if (B.x > Pmax.x) C2 += 2;
	if (B.y < Pmin.y) C2 += 4;
	if (B.y > Pmax.y) C2 += 8;

	/* 3 */
	if (C1 & C2) return false;

	switch (C1) {
	/* 4 */
		case 0: {
			if (C2 == 0) return true;

			float dx = B.x - A.x;
			float dy = B.y - A.y;

			if (dy >= 0) {
				float dxr = Pmax.x - A.x;
				float dyt = Pmax.y - A.y;

				if ( (dy * dxr) < (dx * dyt) ) {
					Clip2Right(A, B, dx, dy, dxr);
				} else {
					Clip2Top(A, B, dx, dy, dyt);
				}
				return true;
			} else {
				float dxr = Pmax.x - A.x;
				float dyb = Pmin.y - A.y;

				if ( (dy * dxr) < (dx * dyb) ) {
					Clip2Bottom(A, B, dx, dy, dyb);
				} else {
					Clip2Right(A, B, dx, dy, dxr);
				}
				return true;
			}
			break;
		}

	/* 5 */
		case 1: {
			float dx = B.x - A.x;
			float dy = B.y - A.y;

			float dxl = Pmin.x - A.x;
			float dyt = Pmax.y - A.y;

			if (C2 == 0) {
				Clip1Left(A, dx, dy, dxl);
				return true;
			}

			float dyb;

			if (dy >= 0) {
				if ( (dy * dxl) > (dx * dyt) ) return false;
				
				float dxr = Pmax.x - A.x;
				float dyt = Pmax.y - A.y;

				if ( (dy * dxr) < (dx * dyt) ) {
					Clip2Right(A, B, dx, dy, dxr);
				}
				else {
					Clip2Top(A, B, dx, dy, dyt);
				}

				dx = B.x - A.x;
				dy = B.y - A.y;
				dxl = Pmin.x - A.x;

				Clip1Left(A, dx, dy, dxl);
				return true;
			} else {
				float dxl = Pmin.x - A.x;
				dyb = Pmin.y - A.y;
				
				if ( (dy * dxl) < (dx * dyb) ) return false;
				
				float dxr = Pmax.x - A.x;
				dyb = Pmin.y - A.y;

				if ( (dy * dxr) < (dx * dyb) ) {
					Clip2Bottom(A, B, dx, dy, dyb);
				} else {
					Clip2Right(A, B, dx, dy, dxr);
				}

				dx = B.x - A.x;
				dy = B.y - A.y;
				dxl = Pmin.x - A.x;

				Clip1Left(A, dx, dy, dxl);
				return true;
			}
			break;
		}	
	
	/* 6 */
		case 4: {
			float dx = B.x - A.x;
			float dy = B.y - A.y;
			float dxr = Pmax.x - A.x;
			float dyb = Pmin.y - A.y;

			if (C2 == 0) {
				Clip1Bottom(A, dx, dy, dyb);
				return true;
			}

			if (dy > 0) {
				if ( (dy * dxr) < (dx * dyb) ) return false;
				
				float dyt = Pmax.y - A.y;

				if ( (dy * dxr) < (dx * dyt) ) {
					Clip2Right(A, B, dx, dy, dxr);
				} else {
					Clip2Top(A, B, dx, dy, dyt);
				}
				
				dx = B.x - A.x;
				dy = B.y - A.y;
				dyb = Pmin.y - A.y;

				Clip1Bottom(A, dx, dy, dyb);
				return true;
			} else {
				return false;
			}
		}

	/* 7 */
		case 5: {
			float dx = B.x - A.x;
			float dy = B.y - A.y;

			if (dy > 0) {
				float dxl = Pmin.x - A.x;
				float dyt = Pmax.y - A.y;

				if ( (dy * dxl) > (dx * dyt) ) return false;

				float dxr = Pmax.x - A.x;
				float dyb = Pmin.y - A.y;

				if ( (dy * dxr) < (dx * dyb) ) return false;

				if ( (dyb * dxr) < (dxl * dyt) ) {
					if ( (dy * dxl) < (dx * dyb) ) {
						Clip1Bottom(A, dx, dy, dyb);
						
						if (B.x > Pmax.x) {
							dx = B.x - A.x;
							dy = B.y - A.y;
							dxr = Pmax.x - A.x;

							Clip2Right(A, B, dx, dy, dxr);
						}
						return true;
					}

					Clip1Left(A, dx, dy, dxl);

					if (C2 == 0) return true;

					if ( ((dy * dxr) < (dx * dyt)) && (C2 != 0) ) {
						dx = B.x - A.x;
						dy = B.y - A.y;
						dxr = Pmax.x - A.x;
						
						Clip2Right(A, B, dx, dy, dxr);
						return true;
					}

					dx = B.x - A.x;
					dy = B.y - A.y;
					dyt = Pmax.y - A.y;

					Clip2Top(A, B, dx, dy, dyt);
					return true;
				} else {
					if ( (dy * dxr) < (dx * dyt) ) {
						Clip1Bottom(A, dx, dy, dyb);

						if (B.x > Pmax.x) {
							dx = B.x - A.x;
							dy = B.y - A.y;
							dxr = Pmax.x - A.y;
							Clip2Right(A, B, dx, dy, dxr);
						}	

						return true;
					}

					if ( (dy * dxl) < (dx * dyb) ) {
						Clip1Bottom(A, dx, dy, dyb);

						if (C2 != 0) {
							dx = B.x - A.x;
							dy = B.y - A.y;
							dyt = Pmax.y - A.y;
		
							Clip2Top(A, B, dx, dy, dyt);
						}
						return true;
					}
					
					Clip1Left(A, dx, dy, dxl);
					
					if (C2 == 0) return true;
					
					dx = B.x - A.x;
					dy = B.y - A.y;
					dyt = Pmax.y - A.y;

					Clip2Top(A, B, dx, dy, dyt);
					return true;
				}
			} else 
				return false;
			break;
		}

	/* 8 */
		case 8: {
			float dx = B.x - A.x;
			float dy = B.y - A.y;

			float dxr = Pmax.x - A.x;
			float dyt = Pmax.y - A.y;

			if (C2 == 0) {
				Clip1Top(A, dx, dy, dyt);
				return true;
			}

			if (dy < 0) {
				if ( (dy * dxr) > (dx * dyt) ) return false;
				
				float dyb = Pmin.y - A.y;
				
				if ( (dy * dxr) > (dx * dyb) ) {
					Clip2Right(A, B, dx, dy, dxr);
				} else {
					Clip2Bottom(A, B, dx, dy, dyb);
				}

				dx = B.x - A.x;
				dy = B.y - A.y;
				dyt = Pmax.y - A.y;

				Clip1Top(A, dx, dy, dyt);
				return true;
			} else 
				return false;
			break;
		}

	/* 9 */
		case 9: {
			float dx = B.x - A.x;
			float dy = B.y - A.y;

			if (dy < 0) {
				float dxr = Pmax.x - A.x;
				float dyt = Pmax.y - A.y;

				if ( (dy * dxr) > (dx * dyt) ) return false;
				
				float dxl = Pmin.x - A.x;
				float dyb = Pmin.y - A.y;

				if ( (dy * dxl) < (dx * dyb) ) return false;

				if ( (dyt * dxr) > (dxl * dyb) ) {
					if ( (dy * dxl) > (dx * dyt) ) {
						Clip1Top(A, dx, dy, dyt);
		
						if (B.x > Pmax.x) {
							dx = B.x - A.x;
							dy = B.y - A.y;
							dxr = Pmax.x - A.x;
							
							Clip2Right(A, B, dx, dy, dxr);
						}
						return true;
					}
					Clip1Left(A, dx, dy, dxl);
					
					if (C2 == 0) return true;
					
					if ( (dy * dxr) > (dx * dyb) ) {
						dx = B.x - A.x;
						dy = B.y - A.y;
						dxr = Pmax.x - A.x;

						Clip2Right(A, B, dx, dy, dxr);
						return true;
					}

					dx = B.x - A.x;
					dy = B.y - A.y;
					dyb = Pmin.y - A.y;
					
					Clip2Bottom(A, B, dx, dy, dyb);
					return true;
				} else {
					if ( (dy * dxr) > (dx * dyb) ) {
						Clip1Top(A, dx, dy, dyt);
					
						if (B.x > Pmax.x) {
							dx = B.x - A.x;
							dy = B.y - A.y;
							dxr = Pmax.x - A.x;

							Clip2Right(A, B, dx, dy, dxr);
						}
						return true;
					}

					if ( (dy * dxl) > (dx * dyt) ) {
						Clip1Top(A, dx, dy, dyt);

						if (C2 != 0) {
							dx = B.x - A.x;
							dy = B.y - A.y;
							dyb = Pmin.y - A.y;

							Clip2Bottom(A, B, dx, dy, dyb);
						}
						return true;
					}
					Clip1Left(A, dx, dy, dxl);

					if (C2 != 0) {
						dx = B.x - A.x;
						dy = B.y - A.y;
						dyb = Pmin.y - A.y;

						Clip2Bottom(A, B, dx, dy, dyb);
					}
					return true;
				}
			} else 
				return false;
		}
	}
}
