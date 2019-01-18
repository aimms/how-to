
AIMMS Knowledge Center
======================================

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
            text-align:center;
            padding:10pt;
            background-color:#E4F0FE /*#EDEFF2;#c5c6c7;*/
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
            /*font-style: italic;*/
        }
        
    </style>
    
    <div id="content">
        <a href="C_Language/index.html">
            <div class="box">
                <h1 align="center">Language</h1>
                <p>Expressing a model in the AIMMS programming language</p>
            </div>
        </a>
        <a href="C_Cloud_PRO/index.html">
            <div class="box">
                <h1 align="center">Deployment</h1>
                <p>Using the PRO and Cloud platforms and deploying your applications</p>
            </div>
        </a>
        <a href="C_Connectivity/index.html">
            <div class="box">
                <h1 align="center">Data Connection</h1>
                <p>Connecting AIMMS to other sources with extensions, links, and libraries</p>
            </div>
        </a>
        <a href="C_Mathematical_Modeling/index.html">
            <div class="box">
                <h1 align="center">Modeling</h1>
                <p>Converting real problems into optimization models, selecting solvers</p>
            </div>
        </a>
        <a href="C_UI/index.html">
            <div class="box">
                <h1 align="center">UI</h1>
                <p>Building and styling applications in WebUI and WinUI</p>
            </div>
        </a>
        <a href="C_Licensing/index.html">
            <div class="box">
                <h1 align="center">Licensing</h1>
                <p>Setting up developer, academic, and network licenses</p>
            </div>
        </a>
    </div>

-----------------------------------------------------------

.. toctree::
   :maxdepth: 1
   :titlesonly:
   :hidden:

   C_Language/index
   C_Cloud_PRO/index
   C_Connectivity/index
   C_Mathematical_Modeling/index
   C_UI/index
   C_Licensing/index
   