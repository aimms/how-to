.. |GitFolder| image:: Images/GitFolder.png
				:scale: 70 %
				:target: https://gitlab.aimms.com/Chris/aimms-how-to
.. |PythonPath| image:: Images/PythonPath.png
				:scale: 70 %
.. |SphinxBuild| image:: Images/SphinxBuild.png
				:scale: 70 %


.. warning::
	This page is hidden, so nobody can access it except if one knows the url. You are a lucky one.

How to use this, man ?
======================

1/ Install Python 2.7, because Python is life
--------------------------------------------------

1. Double click the ``python-2.7.14.msi`` file  from here : https://www.python.org/downloads/release/python-2714/, 
2. Python 2.7 will be installed in ``C:\Python27``, because Python is life
3. Add python to your environment variables (thus you will be able to access it directly in the command prompt) by using https://superuser.com/questions/143119/how-to-add-python-to-the-windows-path (first answer)

.. note::
	
	You should normally be able to call Python directly from the command prompt now, by typing ``python``. If not, call me. It should look like this:
		
		|PythonPath|

2/ Install Sphinx package
--------------------------------------------------

* Install the Sphinx package (+ all depedencies) using the command "``python -m pip install sphinx``" in the command prompt (search *command* in windows menu search)

.. note::

	Along the way, we will use more and more "extensions" to Sphinx (google analytics, last posts addon, sexy pictures, etc.). Those are actually Python packages that you will install exactly as Sphinx package.

3/ Get the Git folder source code
--------------------------------------------------	
* Now, synchronise the git we've already created. ``git@gitlab.aimms.com:Chris/aimms-how-to.git`` . you should end up with a bunch of files. If you can't access it, please reach out.
* Here is what the folder looks like (without the non green-synchronised file): 
	|GitFolder|

4/ Build the documentation
--------------------------------------------------
* So far, this git folder only contains the "source" code (the .rst files, the conf.py, make files and potentially some css styling files... you know the drill) So we need to generate the HTML pages from this. To finally generate the HTML pages do the following:
	1. open the git folder
	2. open a command prompt **from this folder**
	3. run ``make html`` command. Voila:
		|SphinxBuild|
	4. find the HTML files in `_build\\html` (from this folder)
	5. those should be exactly the same than those on https://how-to.aimms.com/ 

.. note::
	
		Don't bother about the warning in red.. If however you have other red text in the console, please bother.

5/ Create your own How-to
---------------------------

The best way to create your own HowTo is by far to check the source (.rst file) from this doc, or any doc found online. However, here are some very small stuff to get you onboard:

* In the git folder, copy-paste the :blue:`Generate Random Numbers.rst` , and rename it :red:`MyHowTo.rst`
* Open this file in notepad++, and start reading/modifing it
* Open a command prompt from the current folder (containing the **make** files)
* run ``make html``
* Go to `_build\\html` : you should have a new html called `MyHowTo.html` in there

6/ Protocol development work on HDI
-----------------------------------

In this section, with "you", I mean all the authors that are working on a particular "answer".

This section has a dual purpose.

* It explains how GIT allows you to safely work on an answer, until it is ready to published.  
  With "safely", I mean without disturbing the work by others, or be disturbed by the work of others.

* Where to place the actual examples discussed in your answer, such that they can be easily picked up by WebUI integration tests.

#. Start a new branch, say ``NeedsClarification``

	#. Do not forget to save the changes you want to keep and are not yet committed.
	
	#. In HDI we will follow the convention that each branch is "based on" the branch named "master". To achieve this:
	
		#. Switch to master (origin\remote\master actually) if you are not there already.
	
		#. Pull
	
	#. Now we will actually create the branch:
	
		#. Via Tortoise git (the context menu) select "Create Branch..."
	
		#. Fill it in as follows: 
		
			.. image:: Resources/Other/HowToMan/Images/HowToManCreateBranchNeedsClarification.PNG
		
		#. Switch to this branch:
	
			.. image:: Resources/Other/HowToMan/Images/HowToManSwitchToBranchNeedsClarification.PNG

		#. Add a empty text file in the git root: ``NeedsClarification.rst`` (git add).

		#. Commit and push using tortoise git.  The commit line in the context menu should include the name of your branch and your commit dialog should look like this:
		
			.. image:: Resources/Other/HowToMan/Images/HowToManFirstCommitNeedsClarification.PNG
			
		   Your git log should look something like:
			
			.. image:: Resources/Other/HowToMan/Images/HowToManGitLogAfterFirstCommitNeedsClarification.PNG

	#. Integrate with the other answer by editing ContentIndex.rst, section toctree.

#. Continue to work on ``NeedsClarification`` until you are satisfied yourself with the answer.

	#. Please put downloads to be tested regularly in the folder ``Resources\Deployment\NeedsClarification\Downloads``.
	   The purpose of this "protocol" is to enable the framework to collect all downloadable examples, and thus build a suite of tests that can be tested using the integration test tools of the WebUI team. 
	   Here the ``AIMMSPRO`` in the above path is the name of the part to which the answer belongs. 
	   For now, the list of parts is the following: { Deployment, Language, Licensing, Modeling, Other, Solver, UI }. 
	   Obviously, when 'other' get overfull, we'll need to reorganize ;-); but we'll handle that luxury problem when we get there.
	
#. Share your branch with your referee 

	Tasks of the referee:
	
	#. Fix small spelling/grammer errors - I do not see the point in spending time writing e-mails for typos. 
	
	#. Point out logical errors, irrelevant pieces of text, missing definitions etc. 
	   As a referee, do not fix those; the author(s) are responsible for the answer, not you as a referee.
	
	#. The introduction is particularly important as it contains the motivation for the reader to read on. 
	   My (Chris) rule of thumb is:
	
		#. Provide something related that is true without question, related and important.
		
		#. Provide something that makes the reader wonder if everything is ok.
		
		#. Provide something small that naturally leads to the question in the title.

#. Work comments of the referee, iterate - until you are satisfied

	Note that it is the author's decision when to publish, not the referee's.

#. Merge work back to master, notify Arthur that the answer is ready to be published.

	#. Ensure that everything is committed in your branch and push.
	
	#. You'll need to merge back to the proper point in the source tree: namely the latest of the branch Master.
	
		#. Switch to master
		
		#. Pull
		
	#. You'll handle merge conflicts in your branch
	
		#. Switch back to feature branch
		
		#. Merge "master" in the feature branch
		
		#. Resolve any conflicts - typically there will be one in ContentIndex.rst - just make sure that your line is added.
		
		#. Commit and push again
		
	#. And finally ..
		
		#. Switch again to master
		
		#. Now you can merge your feature branch to master
		
		#. Commit and Push.
	

.. raw:: html
	<style>
	@import url(https://fonts.googleapis.com/css?family=Roboto:400,300,600,400italic);
	* {
	  margin: 0;
	  padding: 0;
	  box-sizing: border-box;
	  -webkit-box-sizing: border-box;
	  -moz-box-sizing: border-box;
	  -webkit-font-smoothing: antialiased;
	  -moz-font-smoothing: antialiased;
	  -o-font-smoothing: antialiased;
	  font-smoothing: antialiased;
	  text-rendering: optimizeLegibility;
	}

	body .container {
	  font-family: "Roboto", Helvetica, Arial, sans-serif;
	  font-weight: 100;
	  font-size: 12px;
	  line-height: 30px;
	  color: #777;
	  background: #4CAF50;
	}

	.container {
	  max-width: 400px;
	  width: 100%;
	  margin: 0 auto;
	  position: relative;
	}

	#contact input[type="text"],
	#contact input[type="email"],
	#contact input[type="tel"],
	#contact input[type="url"],
	#contact textarea,
	#contact button[type="submit"] {
	  font: 400 12px/16px "Roboto", Helvetica, Arial, sans-serif;
	}

	#contact {
	  background: #F9F9F9;
	  padding: 25px;
	  margin: 150px 0;
	  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
	}

	#contact h3 {
	  display: block;
	  font-size: 30px;
	  font-weight: 300;
	  margin-bottom: 10px;
	}

	#contact h4 {
	  margin: 5px 0 15px;
	  display: block;
	  font-size: 13px;
	  font-weight: 400;
	}

	fieldset {
	  border: medium none !important;
	  margin: 0 0 10px;
	  min-width: 100%;
	  padding: 0;
	  width: 100%;
	}

	#contact input[type="text"],
	#contact input[type="email"],
	#contact input[type="tel"],
	#contact input[type="url"],
	#contact textarea {
	  width: 100%;
	  border: 1px solid #ccc;
	  background: #FFF;
	  margin: 0 0 5px;
	  padding: 10px;
	}

	#contact input[type="text"]:hover,
	#contact input[type="email"]:hover,
	#contact input[type="tel"]:hover,
	#contact input[type="url"]:hover,
	#contact textarea:hover {
	  -webkit-transition: border-color 0.3s ease-in-out;
	  -moz-transition: border-color 0.3s ease-in-out;
	  transition: border-color 0.3s ease-in-out;
	  border: 1px solid #aaa;
	}

	#contact textarea {
	  height: 100px;
	  max-width: 100%;
	  resize: none;
	}

	#contact button[type="submit"] {
	  cursor: pointer;
	  width: 100%;
	  border: none;
	  background: #4CAF50;
	  color: #FFF;
	  margin: 0 0 5px;
	  padding: 10px;
	  font-size: 15px;
	}

	#contact button[type="submit"]:hover {
	  background: #43A047;
	  -webkit-transition: background 0.3s ease-in-out;
	  -moz-transition: background 0.3s ease-in-out;
	  transition: background-color 0.3s ease-in-out;
	}

	#contact button[type="submit"]:active {
	  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.5);
	}

	.copyright {
	  text-align: center;
	}

	#contact input:focus,
	#contact textarea:focus {
	  outline: 0;
	  border: 1px solid #aaa;
	}

	::-webkit-input-placeholder {
	  color: #888;
	}

	:-moz-placeholder {
	  color: #888;
	}

	::-moz-placeholder {
	  color: #888;
	}

	:-ms-input-placeholder {
	  color: #888;
	}	
	</style>
	<div class="container">  
	  <form id="contact" action="https://formspree.io/your@email.com" method="post">
		<h3>Colorlib Contact Form</h3>
		<h4>Contact us for custom quote</h4>
		<fieldset>
		  <input placeholder="Your name" type="text" tabindex="1" required autofocus>
		</fieldset>
		<fieldset>
		  <input placeholder="Your Email Address" type="email" tabindex="2" required>
		</fieldset>
		<fieldset>
		  <input placeholder="Your Phone Number (optional)" type="tel" tabindex="3" required>
		</fieldset>
		<fieldset>
		  <input placeholder="Your Web Site (optional)" type="url" tabindex="4" required>
		</fieldset>
		<fieldset>
		  <textarea placeholder="Type your message here...." tabindex="5" required></textarea>
		</fieldset>
		<fieldset>
		  <button name="submit" type="submit" id="contact-submit" data-submit="...Sending">Submit</button>
		</fieldset>
		<p class="copyright">Designed by <a href="https://colorlib.com" target="_blank" title="Colorlib">Colorlib</a></p>
	  </form>
	</div>