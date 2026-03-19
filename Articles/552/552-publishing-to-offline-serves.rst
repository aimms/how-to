Publishing to Offline Servers
==============================

.. meta::
   :description: Explains how to bundle AIMMS Repository Libraries inside an aimmspack so that PRO On-Premise servers without internet access can publish and run applications.
   :keywords: aimmspack, offline server, AIMMS PRO On-Premise, Library Repository, local libs, publishing, deployment

The `AIMMS Repository Library <https://documentation.aimms.com/library-repository.html>`_ 
places the library resources on the `url <https://library-repository.aimms.com/aimmslibs.all>`_.

When the AIMMS PRO On-Premise server does not have access to this url, then the repository libraries can be included
in the .aimmspack by checking the ``_local_libs`` entry:

.. image:: images/including-local-libs.png
    :align: center

|

By putting these libraries in the ``.aimmspack``, the AIMMS PRO On-Premise server no longer needs to download the AIMMS Libraries in the AIMMS repository them when the ``.aimmspack`` is published.

.. spelling:word-list::
    url
