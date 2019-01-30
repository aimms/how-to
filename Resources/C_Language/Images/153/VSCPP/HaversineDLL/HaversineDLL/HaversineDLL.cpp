// HaversineDLL.cpp : Defines the exported functions for the DLL application.
//
//#include "stdafx.h"
#define _USE_MATH_DEFINES
#include <math.h>


#ifndef __DLLEXPORT__
#ifdef _MSC_VER
  #define __DLLEXPORT__ __declspec(dllexport)
  #define __DLLIMPORT__ __declspec(dllimport)
  #define __DLLLOCAL__
#else
    #define __DLLEXPORT__ __attribute__ ((visibility("default")))
    #define __DLLIMPORT__ __attribute__ ((visibility("default")))
    #define __DLLLOCAL__ __attribute__ ((visibility("hidden")))
  #ifndef WINAPI
    #define WINAPI
  #endif
#endif
#endif

#ifdef __cplusplus
#define DLL_EXPORT_PROTO(type) extern "C" __DLLEXPORT__ type WINAPI
#else
#define DLL_EXPORT_PROTO(type) extern __DLLEXPORT__ type WINAPI
#endif


// to build on linux:
//  compile:
//    /opt/gcc61/bin/g++ -m64 -fPIC -fvisibility=hidden -std=c++14 -c -O2 HaversineDLL.cpp -o HaversineDLL.o
//  link:
//    /opt/gcc61/bin/g++ -o HaversineDLL.so HaversineDLL.o -lm -shared -fPIC
//  check no missing symbols (aka it's standalone):
//    ldd -r HaversineDLL.so
//  should result in:
//        linux-vdso.so.1 =>  (0x00007fffca7e6000)
//        libstdc++.so.6 => /usr/lib/x86_64-linux-gnu/libstdc++.so.6 (0x00007f4157267000)
//        libm.so.6 => /lib/x86_64-linux-gnu/libm.so.6 (0x00007f4156f61000)
//        libgcc_s.so.1 => /lib/x86_64-linux-gnu/libgcc_s.so.1 (0x00007f4156d4b000)
//        libc.so.6 => /lib/x86_64-linux-gnu/libc.so.6 (0x00007f4156982000)
//        /lib64/ld-linux-x86-64.so.2 (0x00007f415776c000)
//   (note no missing symbols)



DLL_EXPORT_PROTO(double) Haversine(double lat1, double lon1, double lat2, double lon2);



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
