// HaversineDLL.cpp : Implements the exported Haversine function for the DLL application.

#include "stdafx.h"
#include "HaversineDLL.h"

static double toRadians(double angle)
{
	return M_PI * angle / 180.0;
}
DLL_EXPORT_PROTO(double) Haversine(double lat1, double lon1, double lat2, double lon2)
{
	double R = 6372.8; // In kilometers
	double dLat = toRadians(lat2 - lat1);
	double dLon = toRadians(lon2 - lon1);
	lat1 = toRadians(lat1);
	lat2 = toRadians(lat2);

	double a = sin(dLat / 2) * sin(dLat / 2) + sin(dLon / 2) * sin(dLon / 2) * cos(lat1) * cos(lat2);
	double c = 2 * asin(sqrt(a));
	return R * 2 * asin(sqrt(a));
}
