
.. image:: Images/aimms-logo-s-rgb.png
				:align: center

.. raw:: html

			<br>
			<h1 align="center">Welcome to AIMMS Knowledge Center!</h1>
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
		<a href="C_Language/index.html">
			<div class="box">
				<h1 align="center">Language</h1>
				<p>AIMMS programming language</p>
			</div>
		</a>
		<a href="C_Deployment/index.html">
			<div class="box">
				<h1 align="center">Deployment</h1>
				<p>PRO and Cloud platforms</p>
			</div>
		</a>
		<a href="C_Mathematical_Modeling/index.html">
			<div class="box">
				<h1 align="center">Modeling</h1>
				<p>Mathematical modeling and optimization</p>
			</div>
		</a>
		<a href="C_Solvers/index.html">
			<div class="box">
				<h1 align="center">Solvers</h1>
				<p>Solvers in AIMMS</p>
			</div>
		</a>
		<a href="C_UI/index.html">
			<div class="box">
				<h1 align="center">UI</h1>
				<p>WebUI and WinUI apps</p>
			</div>
		</a>
		<a href="C_Licensing/index.html">
			<div class="box">
				<h1 align="center">Licensing</h1>
				<p>Licensing setup</p>
			</div>
		</a>
		<a href="Tech_Blog/index.html">
			<div class="box">
				<h1 align="center">Tech Blog Archive</h1>
				<p>Popular topics from our blog</p>
			</div>
		</a>
	</div>

-----------------------------------------------------------

Table Of Contents
=================

.. toctree::
   :maxdepth: 2
   :titlesonly:

   C_Language/index
   C_Deployment/index
   C_Mathematical_Modeling/index
   C_Solvers/index
   C_UI/index
   C_Licensing/index
   Tech_Blog/index