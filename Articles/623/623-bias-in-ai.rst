Bias in AI
=============

.. meta::
   :keywords: python, dex, openapi, docker, bias
   :description: This examples connects AIMMS to Python. 

.. image:: https://img.shields.io/badge/AIMMS_24.5-ZIP:_Bias_in_AI-blue
   :target: https://github.com/aimms/bias-in-ai/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_24.5-Github:_Bias_in_AI-blue
   :target: https://github.com/aimms/bias-in-ai

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-support-updates-67/bias-in-ai-example-1642

.. image:: images/project-1920-high.gif
    :align: center

|

Story
----------

.. https://www.kaggle.com/code/var0101/introduction-to-ai-ethics
.. https://www.kaggle.com/code/alexisbcook/identifying-bias-in-ai/tutorial
.. https://www.kaggle.com/code/alexisbcook/identifying-bias-in-ai

The combination of machine learning and everyday applications is at the heart of modern tech advancements. 
From predicting trends to powering self-driving cars, machine learning reshapes how we use data to make smarter choices. 
But, hidden beneath its brilliance is a complex issue - bias within these algorithms.

In this example, we illustrate bias, by creating an AIMMS front-end to an existing Python application.
The Python application is from
`Kaggle <https://www.kaggle.com/>`_ who teaches about 
`bias <https://www.kaggle.com/code/alexisbcook/identifying-bias-in-ai/tutorial>`_ in the context of 
`ethics <https://www.kaggle.com/code/var0101/introduction-to-ai-ethics>`_.

The AIMMS application uses the following steps:

#.  Get a comment from a user to determine its toxicity

#.  Read in training data

#.  Select two columns from this training data: the comment and the toxicity

#.  Pass the training data and the user entered comment to a Python service

#.  The Python service returns whether it considers the user comment to be toxic or not.

.. note:: 

    This app demonstrates bias, which can be observed by entering comments like:

    #.  ``black`` which is marked **toxic**, and  

    #.  ``white`` which is marked **not toxic**.

A few remarks on the choice of this example:

*   This example is about checking whether there is bias in your data.  
    At first, this may seem far fetched for Decision Support applications.
    However, basing a decision on data that is not representative of your market is not a good idea!

*   A practical aspect of this example is that the communication between two processes is relatively simple: a row of objects and a few scalars - that is all.
    In practice, there is often significantly more detail to the structure of the data communication; 
    however, that extra detail in structure will not make the flow of information easier to understand.


Language
-----------

Machine Learning Core
^^^^^^^^^^^^^^^^^^^^^^^^

The core of the Python app is based on materials from `Bias in AI (Kaggle) <https://www.kaggle.com/code/alexisbcook/identifying-bias-in-ai/tutorial>`_.
For the Machine Learning core it uses `scikit-learn <https://scikit-learn.org/stable/>`_, in particular:

#.  `train_test_split <https://scikit-learn.org/stable/modules/generated/sklearn.model_selection.train_test_split.html#sklearn.model_selection.train_test_split>`_: Split arrays or matrices into random train and test subsets.

#.  `CountVectorizer <https://scikit-learn.org/stable/modules/generated/sklearn.feature_extraction.text.CountVectorizer.html#sklearn.feature_extraction.text.CountVectorizer>`_: Convert a collection of text documents to a matrix of token counts.

#.  `LogisticRegression <https://scikit-learn.org/stable/modules/generated/sklearn.linear_model.LogisticRegression.html#sklearn.linear_model.LogisticRegression>`_: Create Logistic Regression (aka logit, MaxEnt) classifier.

How these utilities work in detail is outside the scope of this article.

.. seealso::
    Here you can find specific explanations about how to `create and connect a Python Service using AIMMS <https://how-to.aimms.com/Articles/599/599-Integrating-Python-with-AIMMS.html>`_. 

WebUI Features
---------------

The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Table Widget <https://documentation.aimms.com/webui/table-widget.html>`_

- `Button Widget <https://documentation.aimms.com/webui/button-widget.html>`_

- `Scalar Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `Page Actions <https://documentation.aimms.com/webui/page-menu.html>`_ 

- `Treemap Widget <https://documentation.aimms.com/webui/tree-map-widget.html>`_ 

- `CSS Annotations <https://documentation.aimms.com/webui/css-styling.html#data-dependent-styling>`_



UI Styling
---------------

Below described all UI modifications done on this example trough ``css`` files which can be found beneath ``MainProject/WebUI/resourses/stylesheets``. 

.. tab-set::
    .. tab-item:: annotation.css

        .. code-block:: css
            :linenos:

            .annotation-not-toxic {
                background: #FF3636;
            }

            .annotation-toxic {
                background: #57C126;
            }

            .annotation-toxic-emoji input.boolean-cell-editor-contents,
            .annotation-not-toxic-emoji input.boolean-cell-editor-contents,
            .annotation-crossed-emoji input.boolean-cell-editor-contents{
                visibility: hidden;
                display: block;
            }

            .annotation-toxic-emoji.cell {
                background: white url(img/poison.png) no-repeat 50%/contain;
                background-size: auto 70% ;
            }

            .annotation-not-toxic-emoji.cell {
                background: white url(img/like.png) no-repeat 50%/contain;
                background-size: auto 70% ;
            }

            .annotation-crossed-emoji.cell {
                background: white url(img/crossed.png) no-repeat 50%/contain;
                background-size: auto 70% ;
            }
        
    .. tab-item:: custom.css

        .. code-block:: css
            :linenos:

            .title-addon {
                text-shadow: 1px 1px 0px var(--secondary90Transparent);
            }


    .. tab-item:: theming.css

        .. code-block:: css
            :linenos:

            :root {
                --primaryDark: #7188dd;
                --primaryDarker: #3351C5;
                --primary90Transparent: #3350c546;
                --secondary: #e9bc38;
                --secondary90Transparent: #e9bd387c;

                --bg_app-logo: 15px 50% / 45px no-repeat url(/app-resources/resources/images/bias.png); /*app logo*/
                --spacing_app-logo_width: 60px;
                --color_border_app-header-divider: var(--secondary); /*line color after header*/
                --color_bg_app-canvas: url(/app-resources/resources/images/RightBackground.png) rgb(249, 249, 249) no-repeat left/contain; /*background color*/

                --color_bg_workflow_current: var(--primaryDarker); /*bg color when step is selected*/
                --color_workflow_active: var(--primaryDarker); /*font and icon color when step is active*/

                --color_bg_widget-header: var(--primaryDarker); /*widget header background color*/
                --border_widget-header: 2px solid var(--secondary); /*line color after widget header*/

                --color_text_edit-select-link: var(--primaryDark);
                --color_text_widget-header: white;

                --color_bg_button_primary: var(--primaryDarker);
                --color_bg_button_primary_hover: var(--primaryDark);
            }



Minimal Requirements
----------------------

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example. 
However, you will need Python 3.11 installed. Pycharm is recommended but not required.   

Release Notes
--------------------

`v1.1 <https://github.com/aimms/bias-in-ai/releases/tag/1.1>`_ (03/10/2024)
    Upgrading AIMMS version and WebUI library version.

`v1.0 <https://github.com/aimms/bias-in-ai/releases/tag/1.0>`_ (29/02/2024)
    First version launched!

.. spelling:word-list::

   logit