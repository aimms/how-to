Demand Forecasting
===================
.. meta::
   :keywords: Semi-continuous variables, Mixed Integer Programming model, MIP, combinationchart, table, colors, css
   :description: This AIMMS project illustrates the use of a semi-continuous variable.

.. image:: https://img.shields.io/badge/AIMMS_4.88-ZIP:_Demand_Forecasting-blue
   :target: https://github.com/aimms/demand-forecasting/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_4.88-Github:_Demand_Forecasting-blue
   :target: https://github.com/aimms/demand-forecasting

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-webui-44/uptaded-contract-allocation-example-1253


Story
-----
Imagine you have a small cookie factory. And for a few months, you stored the demand data you had each day and now, you want to use this data in a way to forecast your possible future demand. 
This analysis will create understanding in how your demand will behaviour and decrease waste. 

However, since you are not really sure which algorithm to use, your goal is to create a dashboard with all algorithm available to choose the best fit for your factory.


Language 
--------

In this example, we are using the library called `Forecasting <https://documentation.aimms.com/forecasting/index.html>`_. 
Library which have native a few forecast algorithms ready to use.  Here, we are exemplifying 6 functions. 

.. aimms:procedure:: pr_exampleExponentialSmoothing

This procedure is an example on how to set up a forecast using exponential smoothing.

.. code-block:: aimms
   :linenos:

    empty p_estimateExponentialSmoothingTrendSeasonality;

    if p_bin_useAutomaticTrendExponentialSmoothing then

        forecasting::ExponentialSmoothingTune(
            dataValues     :  p_historicData, 
            noObservations :  count(i_day | p_historicData(i_day)), 
            alpha          :  p_alphaExponentialSmoothing);

    elseif not p_alphaExponentialSmoothing then

        webui::RequestPerformWebUIDialog(
            title   :  "Settings", 
            message :  "One or more settings are empty, set a value or activate the automatic tune.", 
            actions :  s_OK, 
            onDone  :  'webui::NoOp1');
        return;
    endif;

    forecasting::ExponentialSmoothing(
        dataValues         :  p_historicData,
        estimates          :  p_estimateExponentialSmoothing,
        noObservations     :  count(i_day | p_historicData(i_day)),
        alpha              :  p_alphaExponentialSmoothing);

.. seealso::
   To more detail, check out `forecasting::ExponentialSmoothing <https://documentation.aimms.com/forecasting/forecasting_exponentialsmoothing.html>`_ 
   and `forecasting::ExponentialSmoothingTune <https://documentation.aimms.com/forecasting/forecasting_exponentialsmoothingtune.html>`_ documentations. 

.. aimms:procedure:: pr_exampleExponentialSmoothingTrend

This procedure is an example on how to set up a forecast using exponential smoothing trend.

.. code-block:: aimms
   :linenos:

    empty p_estimateExponentialSmoothingTrend;

    if p_bin_useAutomaticTrendExponentialSmoothingTrend then

        forecasting::ExponentialSmoothingTrendTune(
            dataValues     :  p_historicData, 
            noObservations :  count(i_day | p_historicData(i_day)), 
            alpha          :  p_alphaExponentialSmoothingTrend, 
            beta           :  p_betaExponentialSmoothingTrend);

    elseif not p_alphaExponentialSmoothingTrend
            or not p_betaExponentialSmoothingTrend then

        webui::RequestPerformWebUIDialog(
            title   :  "Settings", 
            message :  "One or more settings are empty, set a value or activate the automatic tune.", 
            actions :  s_OK, 
            onDone  :  'webui::NoOp1');
        return;
    endif;

    forecasting::ExponentialSmoothingTrend(
        dataValues         :  p_historicData,
        estimates          :  p_estimateExponentialSmoothingTrend,
        noObservations     :  count(i_day | p_historicData(i_day)),
        alpha              :  p_alphaExponentialSmoothingTrend,
        beta               :  p_betaExponentialSmoothingTrend);


.. seealso::
    To more detail, check out `forecasting::ExponentialSmoothingTrend <https://documentation.aimms.com/forecasting/forecasting_exponentialsmoothingtrend.html>`_ 
    and `forecasting::ExponentialSmoothingTrendTune <https://documentation.aimms.com/forecasting/forecasting_exponentialsmoothingtrendtune.html>`_ documentations.

.. aimms:procedure:: pr_exampleExponentialSmoothingTrendSeasonality

This procedure is an example on how to set up a forecast using exponential smoothing trend seasonality.

.. code-block:: aimms
   :linenos:

    empty p_estimateExponentialSmoothingTrendSeasonality;

    if p_bin_useAutomaticTrendExponentialSmoothingTrendSeasonality then

        forecasting::ExponentialSmoothingTrendSeasonalityTune(
            dataValues     :  p_historicData, 
            noObservations :  count(i_day | p_historicData(i_day)), 
            alpha          :  p_alphaExponentialSmoothingTrendSeasonality, 
            beta           :  p_betaExponentialSmoothingTrendSeasonality, 
            gamma          :  p_gammaExponentialSmoothingTrendSeasonality, 
            periodLength   :  12);

    elseif not p_alphaExponentialSmoothingTrendSeasonality 
            or not p_betaExponentialSmoothingTrendSeasonality
            or not p_gammaExponentialSmoothingTrendSeasonality then

        webui::RequestPerformWebUIDialog(
            title   :  "Settings", 
            message :  "One or more settings are empty, set a value or activate the automatic tune.", 
            actions :  s_OK, 
            onDone  :  'webui::NoOp1');

        return;
    endif;

    forecasting::ExponentialSmoothingTrendSeasonality(
        dataValues         :  p_historicData,
        estimates          :  p_estimateExponentialSmoothingTrendSeasonality(i_day),
        noObservations     :  count(i_day | p_historicData(i_day)),
        alpha              :  p_alphaExponentialSmoothingTrendSeasonality,
        beta               :  p_betaExponentialSmoothingTrendSeasonality,
        gamma              :  p_gammaExponentialSmoothingTrendSeasonality,
        periodLength       :  12);


.. seealso::
    To more detail, check out `forecasting::ExponentialSmoothingTrendSeasonality <https://documentation.aimms.com/forecasting/forecasting_exponentialsmoothingtrendseasonality.html>`_ 
    and `forecasting::ExponentialSmoothingTrendSeasonalityTune <https://documentation.aimms.com/forecasting/forecasting_exponentialsmoothingtrendseasonalitytune.html>`_ documentations.

.. aimms:procedure:: pr_exampleMovingAverage

This procedure is an example on how to set up a forecast using moving average.

.. code-block:: aimms
   :linenos:

    empty p_estimateMovingAverage;

    forecasting::MovingAverage(
        dataValues         :  p_historicData(i_day),
        estimates          :  p_estimateMovingAverage(i_day),
        noObservations     :  count(i_day | p_historicData(i_day)), 
        noAveragingPeriods :  12);

.. seealso::
   To more detail, check out `forecasting::MovingAverage <https://documentation.aimms.com/forecasting/forecasting_movingaverage.html>`_ documentation.

.. aimms:procedure:: pr_exampleWeightedMovingAverage

This procedure is an example on how to set up a forecast using weighted moving average.

.. code-block:: aimms
   :linenos:

    empty p_estimateWeightedMovingAverage;

    forecasting::WeightedMovingAverage(
        dataValues         :  p_historicData,
        estimates          :  p_estimateWeightedMovingAverage(i_day),
        noObservations     :  count(i_day | p_historicData(i_day)), 
        weights            :  p_weights,
        noAveragingPeriods :  p_numberOfWeights);


.. seealso::
   To more detail, check out `forecasting::WeightedMovingAverage <https://documentation.aimms.com/forecasting/forecasting_weightedmovingaverage.html>`_ documentation.

.. aimms:procedure:: pr_exampleSimpleLinearRegressionVCR

This procedure is an example on how to set up a forecast using linear regression.

.. code-block:: aimms
   :linenos:

    empty p_costError, p_costEstimate;

    forecasting::SimpleLinearRegressionVCR(
                    xIndepVarValue        :  p_def_machineProduction,
                    yDepVarValue          :  p_def_costOfProduction,
                    LRcoeff               :  p_coeff,
                    VariationComp         :  p_variationMeasure,
                    yEstimates            :  p_costEstimate,
                    eResiduals            :  p_costError);


.. seealso::
   To more detail, check out `forecasting::SimpleLinearRegressionVCR <https://documentation.aimms.com/forecasting/forecasting_simplelinearregression.html>`_ documentation.
   And the notational convention `here <https://documentation.aimms.com/forecasting/simple-linear-regression.html>`_.    

WebUI Features
--------------

The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Table Widget <https://documentation.aimms.com/webui/table-widget.html>`_

- `Combination Chart Widget <https://documentation.aimms.com/webui/combination-chart-widget.html>`_

- `Page Actions <https://documentation.aimms.com/webui/page-menu.html>`_ 

- `Scalar Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `Button Widget <https://documentation.aimms.com/webui/button-widget.html>`_
 

UI Styling
----------

Below described all UI modifications done on this example trough ``css`` files which can be found beneath ``MainProject/WebUI/resourses/stylesheets``. 

.. tab-set::
    .. tab-item:: colors.css

        .. code-block:: css
            :linenos:

            :root {
                --secondaryLightest: #ECECFD;
                --secondaryLight: #7883b4;
                --secondary: #4E598C;
                --primaryDark: #FF8C42;
                --primary: #FCAF58;
                --primaryLight: #F9C784;

                --bg_app-logo: 15px 50% / 50px 50px no-repeat url(/app-resources/resources/images/forecast.png);
                --spacing_app-logo_width: 60px;

                --color_border-divider_themed: var(--secondary);
                --color_text_edit-select-link: var(--secondary);
                --color_text_edit-select-link_hover: var(--primary);
                --color_bg_edit-select-link_inverted: var(--secondary);
                --color_bg_button_primary: var(--secondary);
                --color_bg_button_primary_hover: var(--secondaryLight);
                --color_text_button_secondary: var(--secondary);
                --border_button_secondary: 1px solid var(--secondary);
                --color_text_button_secondary_hover: var(--primary);
                --border_button_secondary_hover: 1px solid var(--primary);
                --color_bg_widget-header: var(--primaryLight);
                --border_widget-header: 3px solid var(--primaryDark);
            }

    .. tab-item:: body.css

        .. code-block:: css
            :linenos:

            /*Add logo on the background*/
            .scroll-wrapper--pagev2 .page-container {
                content: " ";
                background: url(img/RightBackground.png) rgb(249, 249, 249) no-repeat left/contain;
            }

            /*Changing tittle to be uppercase*/
            .title-addon {
                text-transform: uppercase;
                text-shadow: 1px 1px 2px var(--secondaryLightest);
            }
            
            /*Changing button font*/
            .ui-widget, .ui-widget button, .ui-widget input, .ui-widget select, .ui-widget textarea {
                font-family: var(--font_headers),Montserrat,Roboto,Arial,Helvetica,sans-serif; 
            }

    .. tab-item:: button.css

        .. code-block:: css
            :linenos:

            .aimms-widget .ui-button {
                text-transform: uppercase;
            }

    .. tab-item:: workflow.css

        .. code-block:: css
            :linenos:

            /*Change color of the active step*/
            .workflow-panel .step-item.current,
            .workflow-panel.collapse .step-item.current {
                box-shadow: inset 0.3125rem 0 0 var(--secondary);
            }

            /*Change color of the titles*/
            .workflow-panel .step-item.active.complete .title, 
            .workflow-panel .step-item.active.incomplete .title {
                color: var(--secondary);
            }

            /*Change color of the icons*/
            .workflow-panel .step-item.active.complete .icon, 
            .workflow-panel .step-item.active.incomplete .icon {
                color: var(--secondary);
                border: 2px solid var(--secondary);
            }

    .. tab-item:: textColor.css

        .. code-block:: css
            :linenos:

            /*Link color*/
            .ql-snow a {  
                color: var(--primaryDark);
            }

Minimal Requirements
--------------------   

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example. 
