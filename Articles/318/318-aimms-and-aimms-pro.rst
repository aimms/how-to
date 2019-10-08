Version dependencies between AIMMS and AIMMS PRO
==============================================================

There is a dependency between AIMMS IDE versions and AIMMS PRO versions. 
By extending interfaces, it is occasionally needed that if one is upgraded, also the other needs to be upgraded.
In addition, some functionality requires improvements in both AIMMS and in AIMMS PRO. 
The version dependencies are kept minimal, but there have been some that could not be avoided.

These version dependencies are listed below:

#.  ``pro::storage::ExistsObject`` and ``pro::storage::ExistsBucket`` require at least AIMMS PRO 2.33 and AIMMS 4.69

#.  If you want to deploy AIMMS 4.66 or higher, you will need AIMMS PRO 2.28 or newer.

#.  **Solver Lease**: Requires at least AIMMS PRO 2.25 and AIMMS 4.57

#.  If you want to use AIMMS versions 4.38 or higher, you should use PRO 2.15 or higher. 
    Lower PRO versions are incompatible with these AIMMS versions.

