Scheduling for project planning
================================


.. note:: Under Construction / Draft status - please do not hesitate to use the form at the end of this article to ask for clarification where needed.

.. sidebar:: Golden Gate bridge by Guido Diepen

    .. image:: ../Resources/C_Mathematical_Modeling/Images/140/golden-gate.jpg



The identifier types ACTIVITIES and RESOURCES, and the scheduling intrinsic functions as part of the AIMMS constraint programming component are very useful in modeling construction projects and optimizing the makespan of those projects.

Several existing constraint programming languages use the Bridge Building example in the Ph.D Thesis of the late M. Bartusch to illustrate this, see for instance `mozart-oz <http://www.mozart-oz.org/documentation/fdt/node48.html#section.scheduling.bridge>`_ and `SAS <http://support.sas.com/documentation/cdl/en/orcpug/63973/HTML/default/viewer.htm#orcpug_clp_sect046.htm>`_.

In such a construction project, there are construction activities related to each element of a structure, such as pillars, and to the activities to manage the project itself such as delivery of materials and the building of housing to shelter the construction equipment.

.. figure:: ../Resources/C_Mathematical_Modeling/Images/140/Bridge-Building.jpg

    Bridge Building
    

A bridge consists of two abutments, several pillars in between, and bearers upon those pillars and abutments. Erecting a pillar requires several steps: excavation, pile driving (if needed), form work, concrete filling and setting, and masonry work. For an abutment, the sand filling is an additional step. A construction step is modeled as:

.. code-block:: aimms

    ACTIVITY:
       identifier      :  PillarActivity
       index domain    :  (pbs,p)
       schedule domain :  TimeLine
       text            :  "Pillar building step pbs to build pillar p"
       length          :  PillarDuration(pbs, p)

Ensuring that two building steps pbs1 and pbs2 are taken in the correct order during the building of a pillar p is modeled in the following constraint:

.. code-block:: aimms

    CONSTRAINT:
       identifier      :  pillarBuildingOrder
       index domain    :  (pbs1, pbs2, p) | PillarDuration(pbs1,p) and
                               PillarDuration(pbs2,p) and pbs1 < pbs2 and
                                ( not exists( pbs3 | pbs1 < pbs3 and
                                   pbs3 < pbs2 and PillarDuration(pbs3,p) ) )
       definition      :  cp::EndBeforeBegin(PillarActivity(pbs1,p), PillarActivity(pbs2,p)) ;

Perhaps interesting is the last condition in the index domain: "*( not exists( pbs3 | pbs1 < pbs3 and pbs3 < pbs2 and PillarDuration(pbs3,p) ) )*". This condition states that we do not want to incorporate those individual restrictions that can be derived from two other individual restrictions; if the activity *PillarActivity(pbs1,p)* already takes place before activity *PillarActivity(pbs3,p)* and activity *PillarActivity(pbs3,p)* already takes places before *PillarActivity(pbs2,p)*; then this implies that activity *PillarActivity(pbs1,p)* takes places before activity *PillarActivity(pbs2,p)* and we do not have to enforce this explicitly. Adding this condition reduces the number of rows from 161 to 86 and improves the number of branches investigated per second by CP Optimizer by roughly 15%.

Each building step pbs requires a corresponding resource, and of each resource we have only one. This can be modeled using the following sequential resource:

.. code-block:: aimms

    RESOURCE:
       identifier      :  mach
       usage           :  sequential
       index domain    :  m
       schedule domain :  Timeline
       activities      :  PillarActivity(pbs, p):
                              (requiredMachineForBuildingStep(pbs)=m) and
                              PillarDuration(pbs, p) ;
                          
Additional constraints can be added to the model easily, for instance as presented in the following AIMMS model:
[attachments include="4143"]
This model requires AIMMS 3.13 FR1 or later.

Reference:
Bartusch, M. (1983), Optimierung von Netzplänen mit Anordnungsbeziehungen bei knappen Betriebsmitteln, Ph.D. thesis, Universität Passau, Fakultät für Mathematik und Informatik.


.. include:: ../includes/form.def


