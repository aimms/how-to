Reclaim Memory Between Data Instances
=====================================

.. meta::
   :description: Empty, Cleanup, CleanDependents and Rebuild each do something precise and different; this explains which does what and gives the pattern for clearing all model data between scenarios while leaving the interface state intact.
   :keywords: Empty, Cleanup, CleanDependents, Rebuild, GarbageCollectStrings, AllIdentifiers, inactive element, element space, memory management, scenario loop, MemoryInUse, IdentifierMemory, memory leak

An application that loads and discards large data instances in sequence - running many scenarios in one session -
needs to release the memory of one before reading the next. Four statements do related but distinctly different
things, and using the wrong one leaves data behind.


What Each Statement Does
-------------------------

Take a set ``s_locations`` with index ``i`` and parameters ``p_supply(i)`` and ``p_flow(i,j)``.

``Empty s_locations;``
    Clears the set *and* every identifier depending on it - so ``p_supply`` and ``p_flow`` as well.

    A second form takes a subset of :any:`AllIdentifiers`. With ``s_ids`` defined as ``{ p_supply, p_flow }``,
    ``Empty s_ids;`` clears the data of those two parameters - not the contents of ``s_ids`` itself.

``Cleanup p_supply;``
    Removes *inactive* data. If ``s_locations`` once held ``{a, b}`` and now holds ``{b}`` while ``p_supply``
    still carries ``{a:1, b:2}``, then ``a:1`` is inactive: it belongs to an element no longer in the set.
    ``Cleanup`` drops it from ``p_supply``.

``CleanDependents s_locations;``
    Does the same across every dependent identifier **and** removes the name ``'a'`` from the set's element
    space. That second part matters: without it the element number stays allocated, which is why element
    numbering accumulates through a session.

``Rebuild;``
    Drops unused index permutations. If ``p_flow``'s main permutation is ``[i,j]`` and a ``[j,i]`` copy also
    exists - kept because it was efficient for some earlier computation - ``Rebuild`` removes the copy.

:any:`GarbageCollectStrings` is the counterpart for the string table.

What "Removing" Actually Means
-------------------------------

None of these return memory to the operating system. They move it to AIMMS's own reserves, which are then used
first the next time execution needs memory.

That is the behavior you want between data instances - the memory is reused rather than requested again - but it
does mean the process size seen from outside will not drop, and that is not evidence the clearing failed.

Clearing a Data Instance
-------------------------

The goal is usually to clear everything belonging to the model while leaving the application's own state - the
interface, dialogs, meta-information - untouched. That distinction has to be made explicitly, because AIMMS has no
notion of it.

Separate the identifiers by section, and build a subset of :any:`AllIdentifiers` from those sections:

.. code-block:: aimms

    s_mainModelIds  := sectionBusinessLogic;
    s_businessLogic := s_mainModelIds + s_lib1Ids + s_lib2Ids;

Then clearing an instance is three steps:

.. code-block:: aimms

    ! 1. Generated mathematical programs hold memory of their own.
    while card( AllGeneratedMathematicalPrograms ) do
        ep_gmp := first( AllGeneratedMathematicalPrograms );
        GMP::Instance::Delete( ep_gmp );
        AllGeneratedMathematicalPrograms -= ep_gmp;
    endwhile;

    ! 2. The model's own data.
    Empty s_businessLogic;

    ! 3. Strings no longer referenced by anything.
    GarbageCollectStrings();

Step 1 is the one most often missed. A generated mathematical program is not reached by ``Empty`` on the
identifiers it was built from, so without deleting the instances a scenario loop accumulates one matrix per
iteration.

Checking for Memory Leaks
--------------------------

Two functions let you verify that the clearing actually works:

* :aimms:func:`MemoryInUse` returns the amount of memory used by AIMMS.
* :aimms:func:`IdentifierMemory` returns the memory in use for a single identifier.

Record :aimms:func:`MemoryInUse` at the end of each iteration, after the instance has been cleared. Because
cleared memory goes back to AIMMS's reserves and is reused, the value should level off after the first few
iterations. If it keeps growing from one iteration to the next, memory is leaking.

The leak can come from two places:

* **The application itself**: some identifiers keep their data over iterations, for instance because they are
  not in ``s_businessLogic`` and are therefore never emptied. Calling :aimms:func:`IdentifierMemory` on the
  suspects, or checking their :any:`Card`, shows which of them keep growing.
* **AIMMS itself**: if every identifier is cleared and memory still grows, the leak is inside AIMMS. That is a
  bug, and should be reported to AIMMS Support together with a project that reproduces it.

:doc:`../134/134-Monitoring-Memory-Use` shows a complete pattern for logging memory over repeated runs, and
:doc:`../170/170-memory-in-use` covers the other tools for investigating memory use.

.. seealso::

   * :doc:`../170/170-memory-in-use`
   * :doc:`../134/134-Monitoring-Memory-Use`
   * :doc:`../125/125-execution-efficiency`
   * :doc:`../612/612-reduce-memory-use`

.. spelling:word-list::

   meta