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
		<a href="http://aimms.com">
			<div class="box">
				<h1 align="center">Modeling</h1>
				<p>Mathematical modeling and major Optimization problems solved in AIMMS</p>
			</div>
		</a>
		<a href="http://aimms.com">
			<div class="box">
				<h1 align="center">Solvers</h1>
				<p>What you always whanted about solvers in AIMMS</p>
			</div>
		</a>
		<a href="http://aimms.com">
			<div class="box">
				<h1 align="center">Language</h1>
				<p>The AIMMS programming Language in details</p>
			</div>
		</a>
		<a href="http://aimms.com">
			<div class="box">
				<h1 align="center">UI</h1>
				<p>From Web to WinUI</p>
			</div>
		</a>
		<a href="http://aimms.com">
			<div class="box">
				<h1 align="center">Deployment</h1>
				<p>PRO and Cloud</p>
			</div>
		</a>
		<a href="http://aimms.com">
			<div class="box">
				<h1 align="center">Other</h1>
				<p>Licensing, connectivity, AIMMS policies...</p>
			</div>
		</a>
	</div>



.. carroussel is fine but not now
	.. raw:: html
		
			<h1 align="center">Welcome to How-Do-I AIMMS documentation !</h1>

			<div>
			  <title>Bootstrap Example</title>
			  <meta charset="utf-8">
			  <meta name="viewport" content="width=device-width, initial-scale=1">
			  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
			  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
			  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
			  <style>
			  .carousel-inner > .item > img,
			  .carousel-inner > .item > a > img {
				  width: 70%;
				  margin: auto;
			  }
			  </style>
			</div>
			<div>

			<div class="container">
			  <br>
			  <div id="myCarousel" class="carousel slide" data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
				  <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				  <li data-target="#myCarousel" data-slide-to="1"></li>
				  <li data-target="#myCarousel" data-slide-to="2"></li>
				  <li data-target="#myCarousel" data-slide-to="3"></li>
				</ol>

				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">

				  <div class="item active">
					<img src="_static/aimma-display.jpg" alt="AIMMS" width="460" height="345">
					<div class="carousel-caption">
					  <h3>AIMMS</h3>
					</div>
				  </div>

				  <div class="item">
					<img src="_static/math.jpg" alt="AIMMS" width="460" height="345">
					<div class="carousel-caption">
					  <h3>AIMMS</h3>
					  <p>The atmosphere in AIMMS has a touch of Florence and Venice.</p>
					</div>
				  </div>
				
				  <div class="item">
					<img src="_static/hqdefault.jpg" alt="Contraint_Programming" width="460" height="345">
					<div class="carousel-caption">
					  <h3>Contraint Programming</h3>
					</div>
				  </div>
				  
				  <div class="item">
					<img src="_static/Portal-with-groups.png" alt="Flower" width="460" height="345">
					<div class="carousel-caption">
					  <h3>Flowers</h3>
					  <p>PRO Portal with groups</p>
					</div>
				  </div>
				  
				  <div class="item">
					<img src="_static/Screenshot_13.png" alt="Flower" width="460" height="345">
					<div class="carousel-caption">
					  <h3>Screenshot</h3>
					  <p>AIMMS Screenshot</p>
					</div>
				  </div>
			  
				</div>

				<!-- Left and right controls -->
				<a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
				  <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				  <span class="sr-only">Previous</span>
				</a>
				<a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
				  <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				  <span class="sr-only">Next</span>
				</a>
			  </div>
			</div>
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