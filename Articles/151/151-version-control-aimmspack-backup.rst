Version Control in AIMMS Projects
======================================

.. meta::
   :description: Best practices for using version control in AIMMS projects.
   :keywords: version control, AIMMS, git, VCS, source control, Sourcetree

Introduction
------------

Effective version control is essential for managing AIMMS projects, enabling collaboration, tracking changes, and maintaining project integrity. 
With AIMMS supporting text-based source files from AIMMS 4, up to the latest version, it is easier than ever to integrate a Version Control System (VCS) such as Git into your workflow.

This article provides best practices for using version control in AIMMS projects, including setup instructions using `SourceTree <https://www.sourcetreeapp.com/>`_, a user-friendly Git client.

Why Use Version Control?
-------------------------

Version control offers numerous benefits, including:

* **Tracking Changes**: Maintain a detailed history of changes, allowing easy review and rollback.
* **Collaboration**: Multiple developers can work on the same project without conflicts.
* **Branching and Merging**: Experiment with new features in separate branches and merge them once finalized.
* **Backup and Recovery**: Protect your work against accidental loss or corruption.

Setting Up Git with SourceTree for AIMMS Projects
--------------------------------------------------

Follow these steps to set up Git version control for your AIMMS project using `SourceTree <https://www.sourcetreeapp.com/>`_:

1. Install Git and SourceTree
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

* Download and install `Git for Windows <https://git-scm.com/download/win>`_
* Download and install `SourceTree <https://www.sourcetreeapp.com/>`_

2. Initialize a Git Repository for Your AIMMS Project
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

* Open `SourceTree <https://www.sourcetreeapp.com/>`_.
* Click :menuselection:`Clone/New > Create New Repository`.
* Select the folder where your AIMMS project is stored.
* Click :menuselection:`Create` to initialize a Git repository in that folder.

3. Configure Remote Repository (Optional, for Collaboration)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To collaborate with others, push your project to a remote Git repository (e.g., GitHub, GitLab, Bitbucket):

* Create a repository on GitHub/GitLab/Bitbucket.
* In SourceTree, go to :menuselection:`Repository Settings > Remotes`.
* Click :menuselection:`Add` and enter the remote repository URL.
* Click :menuselection:`Push` to upload your local repository to the remote server.

4. Add and Commit Files
~~~~~~~~~~~~~~~~~~~~~~~~~

* In SourceTree, go to the :menuselection:`File Status` tab.
* You will see untracked files (your AIMMS project files).
* Select all files and click :menuselection:`Stage Selected`.
* Enter a commit message (e.g., "Initial commit") and click :menuselection:`Commit`.

Best Practices for Managing AIMMS Projects in Git
-------------------------------------------------

* **Use Meaningful Commit Messages**: Clearly describe what each commit changes.
* **Ignore Unnecessary Files**: Add a ``.gitignore`` file to exclude unnecessary files (e.g., temporary AIMMS files).
* **Work in Branches**: Use branches for feature development and bug fixes.
* **Regularly Push Changes**: Keep your remote repository updated to avoid losing progress.
* **Review Before Merging**: Use pull requests or merge requests to review code before merging branches.

Backup Strategies Without Using a VCS
-------------------------------------

If you do not use a VCS, consider:

- **Zip Archives**: Create zip backups of your AIMMS project folder.
- **Automated Backup Scripts**: Use command-line scripts to generate time-stamped zip backups.

.. important::

   ``Aimmspack`` files are not suitable for version control, once you create your aimmspack, your files are encrypted, preventing meaningful tracking of changes. 
   ``Aimmspack`` files primarily serve two main purposes in AIMMS:

   * **Deployment**: Packaging all development sources into a single file.
   * **Encryption**: Protecting intellectual property.

Conclusion
----------

Integrating Git with AIMMS projects improves collaboration, tracking, and project management. 
By using `SourceTree <https://www.sourcetreeapp.com/>`_, developers can manage AIMMS source files easily and benefit from a robust version control workflow.

