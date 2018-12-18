Benders Decomposition in CPLEX 
================================


.. note:: Under Construction / Draft status - please do not hesitate to use the form at the end of this article to ask for clarification where needed.



The latest version of CPLEX, version 12.7, supports Benders decomposition. Benders decomposition is an approach to solve mathematical programming problems with a decomposable structure, including stochastic programming (SP) problems (it is also known as the L-shaped method). Computational results by IBM, see this <a href="http://www.slideshare.net/xnodet/ibm-cplex-optimization-studio-127-benders-modeling-assistance-etc?cm_mc_uid=18650205168313994945525&amp;cm_mc_sid_50200000=1480695733">slide show</a> by Xavier Nodet, show that Benders decomposition is faster than traditional branch-and-cut for 5% of their nontrivial MIP models. That number might not seem impressive but for certain type of MIP problems Benders decomposition is much faster than other methods.



Using the Benders algorithm in CPLEX can be very easy, at least for MIP problems. It only requires you to set the CPLEX option 'Benders strategy' to 'Full' in which case CPLEX will assign all integer variables to the master problem and all continuous variables to the subproblem. CPLEX might further decompose the subproblem since its Benders algorithm can handle multiple subproblems.

<span style="font-weight: 400;"><img class="alignright wp-image-5076size-thumbnail" src="http://techblog.aimms.com/wp-content/uploads/sites/5/2016/12/nrd.jpg" alt="Network Ring Design" width="200" height="200" />You can also specify the decomposition of the variables yourself, and in case of an LP or a stochastic model this is a necessary step. To tell CPLEX that you want to use your own decomposition you should set the option 'Benders strategy' to 'User' or "Worker' (there is a difference between both settings, see the AIMMS Help for details). To specify the decomposition, you should use the function GMP::Benders::SetDecomposition. This is demonstrated in the new AIMMS example <a href="https://aimms.com/english/developers/resources/examples/practical-examples/network-ring-design/">Network Ring Design</a> in which a 2-stage stochastic integer program is solved using the Benders algorithm of CPLEX.</span>

The Benders algorithm of CPLEX 12.7 is the third built-in Benders decomposition algorithm available in AIMMS, after the <a href="http://download.aimms.com/aimms/download/manuals/AIMMS3LR_StochasticProgramming.pdf">stochastic Benders algorithm</a> and the <a href="http://download.aimms.com/aimms/download/manuals/AIMMS3LR_BendersDecomposition.pdf">automatic Benders decomposition algorithm</a>. (These two algorithms can be used with any linear solver while obviously CPLEX Benders can only be used with CPLEX.) To clarify which algorithm is suitable for which problem type, I have created the following table.
<table style="border: 2px solid #cccccc;">
<tbody>
<tr>
<td style="border: 1px solid #cccccc;"></td>
<td style="text-align: center; border: 1px solid #FFFFFF; background-color: #c8c8c8;">MIP</td>
<td style="text-align: center; border: 1px solid #FFFFFF; background-color: #c8c8c8;">2-stage SP</td>
<td style="text-align: center; border: 1px solid #FFFFFF; background-color: #c8c8c8;">Multi-stage SP</td>
<td style="text-align: center; border: 1px solid #FFFFFF; background-color: #c8c8c8;">2-stage integer SP</td>
</tr>
<tr>
<td style="border: 1px solid #FFFFFF; background-color: #c8c8c8; padding-left: 6px;">CPLEX Benders</td>
<td style="text-align: center; border: 1px solid #cccccc;">✔</td>
<td style="text-align: center; border: 1px solid #cccccc;">✔</td>
<td style="text-align: center; border: 1px solid #cccccc;">?</td>
<td style="text-align: center; border: 1px solid #cccccc;">✔</td>
</tr>
<tr>
<td style="border: 1px solid #FFFFFF; background-color: #c8c8c8; padding-left: 6px;">Automatic Benders</td>
<td style="text-align: center; border: 1px solid #cccccc;">✔</td>
<td style="border: 1px solid #cccccc;"></td>
<td style="border: 1px solid #cccccc;"></td>
<td style="border: 1px solid #cccccc;"></td>
</tr>
<tr>
<td style="border: 1px solid #FFFFFF; background-color: #c8c8c8; padding-left: 6px;">Stochastic Benders</td>
<td style="border: 1px solid #cccccc;"></td>
<td style="text-align: center; border: 1px solid #cccccc;">✔</td>
<td style="text-align: center; border: 1px solid #cccccc;">✔</td>
<td style="border: 1px solid #cccccc;"></td>
</tr>
</tbody>
</table>
`` ``
For the 2-stage integer SP it is required that all integer variables are assigned to the first stage.

I added the question mark in the Multi-stage SP column for CPLEX Benders for the following reason. CPLEX Benders does not do nested decomposition like the stochastic Benders algorithm, but for 3-stage SP problems it could still be a good choice (technical remark: in which case a second stage node in the scenario tree combined with its child nodes in the third stage define a subproblem in the decomposition).

I do not have extensive test results that compare the performance of CPLEX Benders with one of the other Benders algorithms available in AIMMS. I only compared CPLEX Benders with the automatic Benders decomposition algorithm for a few MIP problems for which I know that Benders decomposition works very well. One of those problems is the Network Design problem which can be found as an example on our <a href="http://aimms.com/english/developers/resources/examples/practical-examples/network-design">website</a>. For this problem the automatic Benders decomposition algorithm already does a great job, but CPLEX Benders is even faster. If you download this example then you can see for yourself (after setting the CPLEX option 'Benders strategy' to 'Full').

CPLEX 12.7 is available in <a href="http://aimms.com/english/developers/downloads/download-aimms/">AIMMS 4.30</a>.


.. include:: ../includes/form.def


