.. raw:: html

		<div class="navbar">
		  	<a href="http://aimms.com/english/about-us">About Us</a>
		  	<a href="http://aimms.com/english/developers">Developers</a>
		  	<a href="http://aimms.com/english/customers">Customers</a>
		  	<a href="http://aimms.com/english/resources">Resources</a>
		  	<a href="http://aimms.com/english/software-solutions/software">Software & Solutions</a>
  			<a class="navbarimg" href="http://www.aimms.com">
  			<img src="_images/favicon.png"  width="80" height="63" border="0"></a>
  		</div>
  		
.. figure:: Images/aimms-logo-s-rgb.png
				:scale: 70 %
				:align: center
				

.. raw:: html

			<h1 align="center">Welcome to How-To AIMMS documentation !</h1>
			<br>
			<br>


.. raw:: html

	<style>
		#content {
			width:100%;
			max-width:1000px;    /* A. max horizontal number of boxes =~ int(A/B) */
			margin:0 auto;
			text-align:left;    /* (left|center) align last boxes and the set vert. line */
			/*background-color:#AAA; */
		}
		.box {
			display: inline-block;
			overflow:auto;    
			min-height: 100px;    /* B. change the vertical numb. of boxes */
			width: 170px;        /* C. change the horizontal numb. of boxes*/
			height: 170px;
			margin:40px;          /*  boxes separator */
			vertical-align: top; /* (top|bottom) align boxes */
			text-align:justify;
			padding:6pt;
			background-color: #c5c6c7;
			border-radius: 10px;
			margin: 10px 10px 50px;
			flex: 1 0 270px;
			min-width: 270px; 
		}
		
		.box:hover{
			opacity: 0.7;
			cursor: pointer;
			transition: all .2s ease-in-out;
		}
		
		.box h1{
			border-bottom: 1px solid black;
			margin-left: 10px !important;
			margin-right: 10px !important;
			margin-bottom:25px !important;
		}
		
		.box:hover h1{
			opacity: 1;
			/*color: #000081;*/
		}
		
		.box p{
			color : black ;
			font-style: italic;
		}
		
	</style>
	
	<div id="content">
		<a href="C_Mathematical Modeling.html">
			<div class="box">
				<h1 align="center">Modeling</h1>
				<p>Mathematical modeling and major Optimization problems solved in AIMMS</p>
			</div>
		</a>
		<a href="C_Solvers.html">
			<div class="box">
				<h1 align="center">Solvers</h1>
				<p>What you always whanted about solvers in AIMMS</p>
			</div>
		</a>
		<a href="C_Language.html">
			<div class="box">
				<h1 align="center">Language</h1>
				<p>The AIMMS programming Language in details</p>
			</div>
		</a>
		<a href="C_UI.html">
			<div class="box">
				<h1 align="center">UI</h1>
				<p>From Web to WinUI</p>
			</div>
		</a>
		<a href="C_Deployment.html">
			<div class="box">
				<h1 align="center">Deployment</h1>
				<p>PRO and Cloud</p>
			</div>
		</a>
		<a href="C_Other.html">
			<div class="box">
				<h1 align="center">Other</h1>
				<p>Licensing, connectivity, AIMMS policies...</p>
			</div>
		</a>
	</div>

-----------------------------------------------------------

Table Of Content
=================

.. toctree::
   :maxdepth: 2
   :titlesonly:

   C_Deployment
   C_Language
   C_UI
   C_Mathematical Modeling
   C_Other
   C_Solvers


Indices and tables
==================

* :ref:`genindex`
* :ref:`modindex`
* :ref:`search`