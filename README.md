# Table of Contents
* [Clone and Navigate the repo on your computer](#clone-and-navigate-the-repo-on-your-computer)
* [Build Locally the HTML documentation](#build-locally-the-html-documentation)
* [The Pipeline (optional)](#the-pipeline)
* [The Staging environnement](#the-staging-environnement)
* [Style guide](#style-guide)
* [Prereq's to build a PDF version (optional)](#prereqs-to-build-a-pdf-version-optional)

Clone and Navigate the repo on your computer
---------------------------------------------

If you make it here, it means you have access right to the How-To git repo. Thus you may "clone" it somewhere on your computer ([like this](https://docs.github.com/en/github/creating-cloning-and-archiving-repositories/cloning-a-repository). This will copy all files from the server onto your computer.

Most of the content is located in the [Articles](/Articles) folder, where each article is inside a folder with a number, containing the article `RST` file and the attached resources (images, AIMMS project zip files, etc.). This number corresponds to a giltab ticket, where you can find more context and progress.

The folder structure is meant to organize the content into sections on the website. You may find references to articles part of the [Articles](/Articles) folder in every index file. Putting articles in their dedicated [Articles](/Articles) folder enables us to display articles in several sections if needed.

Build Locally the HTML documentation
--------------------------------------

**Requirements:**
 - [Python 3.X](https://www.python.org/downloads/)
 - [Sphinx package](http://www.sphinx-doc.org/en/master/) (run `python3 -m pip install sphinx`)
 - [Sphinx Spelling package](https://sphinxcontrib-spelling.readthedocs.io/en/latest/) (run `python3 -m pip install sphinxcontrib.spelling`)
 - [Sphinx AIMMS theme](https://gitlab.com/ArthurdHerbemont/sphinx-aimms-theme) (run `python3 -m pip install sphinx-aimms-theme`)
 - [AIMMS code blocks for PDF](https://gitlab.com/ArthurdHerbemont/aimms-pygments-style) (run `python3 -m pip install aimms-pygments-style`) 

Depending on previous installations of python, the above command may be `py` or `python`, instead of `python3`.

After installing all the above requirements, please go to the location of your previously cloned documentation folder:
 * Open a console prompt from this location, using ``ATL+D`` and typing ``cmd`` in the URL of your file explorer (Windows)
 * run ``make html`` (the first time, it may take some time, around 20 secs. progress is shown in your console)
 > 💡 You may also run `python3 -msphinx . _build/html` (to be sure to use a specific python version). [More docs](https://www.sphinx-doc.org/en/master/man/sphinx-build.html)

<details>
<summary>
<b>Click me to show more info on console output 👇</b>
</summary>

* As you may see at the bottom of the wonderfully colored prompt, **your html pages are in `_build\html` folder**, located in the current working directory (the same as always). You may check the build by opening any of those.
* The red text are warnings (any error would actually break the building process, as in AIMMS): **Those warnings should be avoided**. Most of the time, this is due to a misuse of sphinx. You may correct them yourself, because your are awesome. Or let them be because your don't understand them. In any case, through your development please mind that **you should avoid to create any new warnings** (ask around if you don't understand)
* Be aware to make title underline longer than the title itself 
* ⚠️ file names are case sensitive on linux, and not on windows. Thus, your build may break on gitlab/github, and not locally on your computer. 

</details>

> **💡1:** GitLab CI is following exactly the same process when building the documentation in the pipeline. This is defined in the [.gitlab-ci.yml](.gitlab-ci.yml) file. More details below

> **⚠️2:** When pushing to the **master branch only**, the repo is built and **pushed (merged) to [how-to.aimms.com](https://how-to.aimms.com/)**.

> **⚠️3:** If any warning is raised on gitlab, **the pipeline fails**

Run spell checking locally
--------------------------------------

After installing all the above requirements, please go to the location of your previously cloned documentation folder:
 * Open a console prompt from this location, using ``ATL+D`` and typing ``cmd`` in the URL of your file explorer (Windows)
 * run `python3 -msphinx -b spelling . _build/spelling` (depending on you python this could be just `py` or `python` instead of `python3`; the first time, it may take some time, around 20 secs. progress is shown in your console).

<details>
<summary>
<b>Click me to show more info on console output 👇</b>
</summary>

* The console will log information on processing the spell checks. If any errors were encountered, you will find the `WARNING: Found X misspelled words` line at the end of the log (where X is the number of errors encountered).
* Scroll through the console until you find a line similar to `[..]\aimms-how-to\Articles\12\12-generate-random-numbers.rst:10: Spell check: disribution:  [..] disribution [..]`
* This identifies the files with errors (in the example above 12-generate-random-numbers.rst), the line with the error (in the example above line 10) and the spell error (in the example above disribution)
* Sphinx will also create files with information on the spelling errors in the _build/spelling folder. Each failed rst file will have a corresponding spelling file.
* Be aware that CI/CD will only allow deploy if the spelling presents no errors/warnings.
* For reasons unkown, the spellcheck in gitlab (Linux environment) and on your local environment (probably Windwos) differs. In this case, you could get a clean check locally but not in the deploy process.

</details>


The Pipeline
-

Every push to gitlab remote will run a pipeline. This pipeline first "Test" stage contains 3 different jobs as defined in [.gitlab-ci.yml](.gitlab-ci.yml)

![image](https://gitlab.aimms.com/aimms/documentation/-/wikis/uploads/cc98f149f3858630e6a760a35c9e0c98/image.png)

| job name | description | condition |
| ------ | ------ | ----- |
| ``build`` | builds the docs using the latest sphinx version | ❌ If any warning is raised, the job and pipeline fails |
| ``linkcheck`` | checks every external link **and** anchor | ⚠️ If any link **or** anchor is broken, the job fails, but this job is **allowed to fail** |
| ``spellcheck`` | checks the spelling of every word |  ❌ If any spelling is broken the job and pipeline fails |

<details>
<summary>
 <b>If <code>build</code> fails on gitlab, but not locally, what should I do ? 👇</b>
</summary>

1. look at the error/warning in the pipeline
1. Upgrade your sphinx version and sphinx-aimms-theme version (`python -mpip --upgrade sphinx`)
1. Linux filenames are case sensitive. Double check
</details>

<details>
<summary>
<b>If <code>linkcheck</code> fails on gitlab, but not locally, what should I do ? 👇</b>
</summary>

1. if `build` also fails, go to fix ``build`` first
1. look at the error/warning in the pipeline
   1. fix your broken link
   1. fix expired link, cause website no more reachable - Thanks for your help !
1. Fix links locally using `make linkcheck` or `python -msphinx -b linkcheck . _build/html`
1. upgrade your sphinx version and sphinx-aimms-theme version (`python -mpip --upgrade sphinx sphinx-aimms-theme`)
1. re-run the job in gitlab: **some links might be temporarily not reachable**
1. If there is a link you want to **ignore**, put it 
``` 
``example.com`` 
```
</details>

<details>
<summary>
<b>If <code>spellcheck</code> fails on gitlab, what should I do ? 👇</b>
</summary>

1. look at the error/warning in the pipeline
   1. fix your spelling errors
1. upgrade your sphinx version, sphinx spelling and sphinx-aimms-theme version (`python -mpip --upgrade sphinx sphinxcontrib.spelling sphinx-aimms-theme`)
1. If there is a word you want to **ignore**, include the following directive in your article

```
.. spelling::

    word1
	word2
```
</details>

**When pushing to the master branch**

A push to master will run the pipeline and, if the `Test` stage is successful, it will copy the docs to [how-to.aimms.com](https://how-to.aimms.com/)

If the pipeline fails, no copy will happen, thus website stays unchanged

**Notes**
1. If there is a link you want to **ignore**, put it 
``` 
``example.com`` 
```

The Staging environnement
-

Thanks to [this part of the in gitlab-ci.yml](.gitlab-ci.yml#L39), every branch "NameOfMyBranch" will create a staging website at ``https://how-to.aimms.com/staging/NameOfMyBranch``, except the master branch.

This can be particularily useful to share a draft of your new article.

This folder is hidden to search engines (through the [robot.txt](.robot.txt#L23)), meaning nobody can access it, except if one knows the link.

**Warning**: As soon as there is a commit to the master branch, the staging subfolder is cleaned (removed). This ensures we are not overloading the server.
To re-generate your branch website, just re-run your branch ``build_and_staging`` job !


Style guide
-

**Guidelines**

1. PUBLISHING PROCESS - Create a Create a new branch for editing, and merge to develop branch when ready. It will be reviewed and published weekly. Please don't work in the master branch except in urgent cases (use your judgment).

2. IMAGES - When using screenshots, leave plenty of space around the area you want to show so the image can be edited and edges can be beautified. Use markup sparingly.

3. IMAGE LOCATION - Keep icons in the icon folder (they can be reused for many docs). Specific images should be in their own ``images`` folder in the numbered folder for that article, eg ``Articles/233/images``. 

4. FILE NAME CONVENTIONS - Use the Gitlab ticket number for the "id number" of your article and give it a short but descriptive file name. (Occasionally there can be more than one article under the same ticket number, but generally try to make a new ticket for each article.)

5. RST CONVENTIONS - Always follow the same code conventions for headings, images, etc. The code is flexible, but we want docs to be consistent.

6. LINKING TO ARBITRARY ANCHORS - Titles may change, headings may change. Avoid link to headings by their title, instead create references (anchors) in the code using :ref: - if the title of a heading changes, links in other docs won't be broken.

7. LINKING TO OTHER FILES - Use :doc: to link to other files in the same repo. It will automatically pull the up-to-date title.

8. LINKING TO FUNCTION REFERENCE - Use `:aimms:set:`AllIdentifiers`` or `:any:`AllIdentifiers``. This will create a link to the documentation for that function.



**Quick reference**

`== Title (level 1) marker`

`-- Heading (level 2) marker`

`^^ Subheading (level 3) marker`

`.. Commented text`

`.. code-block:: aimms`

`*Italics*`

`**Bold**`

` ``Monospace font`` `

`#. Numbered list item`

`* Unordered list item`

`` `External Link <www.url.com>`_``

See also: 
http://www.sphinx-doc.org/en/master/usage/restructuredtext/basics.html

**Useful directives and roles in reST**

*Table of contents or index*

`.. toctree::`

Use the sub-options :max-depth: or :titlesonly: appropriately, otherwise all sub-headings will show up in the table of contents leading to a long long list. Below two are equivalent.

```
.. toctree::
   :maxdepth: 1
```

```
.. toctree::
   :titlesonly:
```

*Image*

.. image:: /relativeFilePath/image.png

http://docutils.sourceforge.net/docs/ref/rst/directives.html#image 

*Code blocks, numbered*

     .. code-block::
        :linenos:

*Substitution*

Can be useful for images, but any object or text string you may want to single-source for use in multiple docs. 

Name the substitution in the header

	.. |image-name| image:: /Images/image-name.png

Call the substitution in the document

	|image-name|

http://docutils.sourceforge.net/docs/ref/rst/restructuredtext.html#substitution-definitions


*Download*

Add a downloadable file to your page

	:download:`this example script <downloads/example.py>`.

*Relative file path*

The given filename is usually relative to the current file. (../ represents go up 
a level, ../../ represents go up two levels, etc.)

http://www.sphinx-doc.org/en/master/usage/restructuredtext/roles.html#referencing-downloadable-files


*Relative link*

To link to another reST file in the same repo, you can use the :doc: role

	:doc:`file-name`
or

	:doc:`../file-name`


The link displays the title within the given document, or specify text to display explicitly

        :doc:`explicit title <filePath/file-name>`

http://www.sphinx-doc.org/en/master/usage/restructuredtext/roles.html#cross-referencing-documents


*Cross-reference*

Like an anchor, but can be referenced by name from any other doc in the repo by name (no file path needed). Used above a section header.

Name the anchor

	.. _my-reference-label:

	Section to cross-reference
	--------------------------

	This is the text of the section.

Refer to the anchor

	See :ref:`my-reference-label`.

http://www.sphinx-doc.org/en/master/usage/restructuredtext/roles.html#role-ref


*Include*

Use this to add contents of an entire file in the repo to your document as a snippet. (We use this in How-to for the feedback form at the bottom.)

	.. include:: inclusion.txt

http://docutils.sourceforge.net/docs/ref/rst/directives.html#include


Prereq's to build a PDF version (optional) 
-------------------------------------------

You can use the ``make latexpdf`` command to locally create a .pdf from the .rst source files.

First, make sure you installed Latex - https://miktex.org/howto/install-miktex

Then, to get the AIMMS code to look right, you need to run this:
   
   ``python -m pip install aimms-pygments-style``
 
   This will install an extension enabling latex to find the AIMMS style sheet define in the following open source repo 
   https://gitlab.com/ArthurdHerbemont/aimms-pygments-style. Please contribute if you think you can improve it ! :)
