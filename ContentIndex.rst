
.. image:: Images/aimms-logo-s-rgb.png
				:align: center

.. raw:: html

			<br>
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
			overflow:hidden;      /* hide scroll bars */
			min-height: 100px;    /* B. change the vertical numb. of boxes */
			width: 170px;        /* C. change the horizontal numb. of boxes*/
			height: 170px;
			margin:40px;          /*  boxes separator */
			vertical-align: top; /* (top|bottom) align boxes */
			text-align:justify;
			padding:6pt;
			background-color:#EDEFF2;/*#c5c6c7;*/
			border-radius: 5px;
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
		<a href="C_Language.html">
			<div class="box">
				<h1 align="center">Language</h1>
				<p>The AIMMS programming Language in details</p>
			</div>
		</a>
		<a href="C_Deployment.html">
			<div class="box">
				<h1 align="center">Deployment</h1>
				<p>PRO and Cloud</p>
			</div>
		</a>
		<a href="C_Mathematical Modeling.html">
			<div class="box">
				<h1 align="center">Modeling</h1>
				<p>Mathematical modeling and major Optimization problems solved in AIMMS</p>
			</div>
		</a>
		<a href="C_Solvers.html">
			<div class="box">
				<h1 align="center">Solvers</h1>
				<p>What you always wanted about solvers in AIMMS</p>
			</div>
		</a>
		<a href="C_UI.html">
			<div class="box">
				<h1 align="center">UI</h1>
				<p>From WebUI to WinUI</p>
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

   C_Language
   C_Deployment
   C_Mathematical Modeling
   C_Solvers
   C_UI
   C_Other