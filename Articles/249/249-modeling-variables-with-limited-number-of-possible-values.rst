Modeling variables with limited number of possible values =================================================================
.. meta::   :description: Linearizing trick to model x in S = limited set of values.   :keywords: algebraic modeling, linear constraints, linear programming, integer programming .. note::    This article was originally posted to the AIMMS Tech Blog.    
.. <link>https://berthier.design/aimmsbackuptech/2012/08/17/modeling-variables-with-limited-number-of-possible-values/</link>
.. <pubDate>Fri, 17 Aug 2012 08:36:52 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=1695</guid>
 .. sidebar:: :math:`{\huge x \in \{ 1.5, 2.5, 4.5 \}}`     Modeling discrete values.At User Support, we've seen the question appear a couple of times on the `AIMMS Google group <https://groups.google.com/forum/#!forum/aimms>`_, though every time explained in different words.We have also seen a similar kind of question a number of times as a support question to our support department. Although always formulated a bit different, the bottom line of the question always boils down to the following: 
How to model a variable that can only take a couple of values, for example: .. math:: x \in \{1.5 , 2.5, 4.5\}  
Often people will see that you can use binary variables to ensure exactly one of the values is selected, but then the remaining problem always is how to get the chosen option as a variable value again.
So if you have the situation as given above, to have exactly one of the values selected, you can introduce the following three binary variables:
.. math:: x_{1.5} = \left\{ \begin{array}{ll} 1 & \mathrm{if\ } x=1.5; \\ 0 & \mathrm{otherwise.}\end{array} \right.
.. math:: x_{2.5} = \left\{ \begin{array}{ll} 1 & \mathrm{if\ } x=2.5; \\ 0 & \mathrm{otherwise.}\end{array} \right.
.. math:: x_{4.5} = \left\{ \begin{array}{ll} 1 & \mathrm{if\ } x=4.5; \\ 0 & \mathrm{otherwise.}\end{array} \right.To ensure only one value gets selected, we add the following constraint:
.. math:: x_{1.5} + x_{2.5} + x_{4.5} = 1
To solve the remaining problem of how to get the option that was chosen as a variable value again, we can use an easy modeling trick: just sum the multiplications of the binary variables with their corresponding values. You can do so by introducing the following new equality constraint (or use everything right of the = sign in the definition attribute of variable :math:`x`):.. math:: x = 1.5 * x_{1.5} + 2.5 * x_{2.5} + 4.5 * x_{4.5}
Because exactly one of the variables will take the value of 1, the sum will result in the variable :math:`x` taking the value of the chosen option. All the other terms in the sum will have value 0, as the corresponding binary variables will have the value 0.
.. include:: /includes/form.def