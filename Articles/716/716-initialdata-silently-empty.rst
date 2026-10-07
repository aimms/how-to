``InitialData`` Silently Leaves a Parameter Empty
=================================================

.. meta::
   :description: InitialData is worked before any procedure runs, so a parameter over a set that MainInitialization fills has nothing to assign to and comes out empty. This is why the same declaration works over one set and not another.
   :keywords: InitialData, IndexDomain, MainInitialization, PostMainInitialization, initialization order, empty parameter, definition, startup

Two parameters, the same ``InitialData``, and only one of them has values at startup.

Example
-------

Take two sets. ``s_Periods`` gets its elements from its own definition, while ``s_Products`` is filled by a
procedure called from ``MainInitialization``:

.. code-block:: aimms
   :linenos:

   Set s_Periods {
       Index      : i_t;
       Definition : data { t1, t2, t3 };
   }

   Set s_Products {
       Index : i_p;
   }

   Procedure MainInitialization {
       Body : {
           s_Products := data { productA, productB, productC };
       }
   }

Now declare one parameter over each set, both with the same ``InitialData``:

.. code-block:: aimms
   :linenos:

   Parameter p_PeriodFactor {
       IndexDomain : i_t;
       InitialData : 1;              ! 1 for t1, t2 and t3
   }

   Parameter p_ProductFactor {
       IndexDomain : i_p;
       InitialData : 1;              ! empty at startup
   }

After the project opens, ``p_PeriodFactor`` holds the value 1 for every period, but ``p_ProductFactor`` has no
data at all, even though ``s_Products`` now contains three elements.

Nothing distinguishes the two declarations. What differs is when each index set gets its contents.

The Order of Initialization
----------------------------

#. Definitions and ``InitialData`` attributes are worked first, in declaration order, before any procedure runs.
#. ``MainInitialization`` and ``PostMainInitialization`` run afterwards.

So when ``p_ProductFactor`` is initialized in step 1, ``s_Products`` is still empty, because it is only filled
inside ``MainInitialization``. There is nothing for the ``InitialData`` value to be assigned to. The parameter is
not initialized and then cleared; it is initialized over an empty domain, which produces no data at all.

``s_Periods`` works because it has its elements before step 1. Here they come from its own definition, but a
literal or data read at load time would work the same way.

No error is reported, because nothing went wrong. The assignment covered every tuple in the domain, and there were
none.

What To Do Instead
-------------------

**Assign it in a procedure**, after the set is filled. If the set is populated in ``MainInitialization``, the
assignment belongs after that point, and ``PostMainInitialization`` is the natural home:

.. code-block:: aimms
   :linenos:

   Procedure PostMainInitialization {
       Body : {
           p_ProductFactor(i_p) := 1;
       }
   }

**Or give the parameter a definition** rather than initial data, if the value is genuinely constant. A definition
is re-evaluated when its domain changes, so it follows the set instead of predating it:

.. code-block:: aimms
   :linenos:

   Parameter p_ProductFactor {
       IndexDomain : i_p;
       Definition  : 1;
   }

The one thing not to do is move the declaration, hoping declaration order will fix it. Declaration order decides
the sequence *within* step 1; it cannot move an ``InitialData`` attribute after a procedure.

.. seealso::

   * :doc:`../351/351-app-initialization-termination-with-libraries`
