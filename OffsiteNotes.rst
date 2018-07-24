.. |date| date:: %Y-%m-%d %H:%M:%S

:orphan:

.. warning::

    Nobody can reach this article, unless they know the URL. We are a transparent company, but there is some limits :)

Offsite Notes and remarks
++++++++++++++++++++++++++++

The following notes are separated in several sections, representing every "focus topics" we covered, roughly every circles + a few major initiatives. 

The offsite was about going through all those topics, 1 by 1. Presentations were shared upfront, such that we could actually spend our time asking question / sharing reactions instead of sleeping on a 50 slides presentation. 

Please mind note blocks are my own opinion or updates since time has passed by. Meaning the rest is really my notes, a bit reformulated and enriched sometimes, but representing pretty closely what was said (without being exhaustive. I selected my topic of interest. I'm a dictator with my brain, and my notes. And my circle)

.. contents:: Topics covered
	:local:

|


SC Navigator
===============
Chris G is in lead, Peter N was also making some interventions from time to time.

**Development**

* Development is focused on 3 core apps, which build the SC Navigator "`Platform`" :
	
	* S&OP (ready)
	* Network Design (ready)
	* Inventory Optimization (not ready yet)
* Database will be an issue. Costumers are asking for secure connection to their already existing database. But this is not very urgent for now.
	
**Sales Machine**

* SCN **Sales Machine is the biggest bottleneck for the moment**. Marketing + Engineering are awesome, but no deal closed.
* :math:`{Sales Machine} = Chris_G + Paul_N` for the moment
* Current status is 7 prospects, interested in the Network Optimization tool. (S&OP contains no) they are working hard on it, there official expectation is to close 3 of them.

Chris G Quote:
	*"Costumers have patience, they are used to the digitalization of the SupplyChain: SC Navigator is actually a journey in the Supply Chain Digitalization. That's why we are going to sell. They sign up for a trip, not a feature"* Patrick vGent strongly agreed.

* According to ChrisG, the key difference when you try to sell SC Nav + AIMMS is that it is still quite an abstract product, for quite a long time since now. That's a burden. 

.. note::
	
	SC Navigator team (ChrisG) announced 1 SCN Platform sold per month, in their new business planning starting now (roughly July, even earlier). (before it was a lot more, leading our Financial status to be very optimistic). There is still no SC navigator sold, at the very moment when I'm writing this report (|date|)

**Costumer Support involvement: Can we help you guys in any way ?**

CS will be needed, but no pressure so far. So, no thanks. SCN needs more sales/demo in SupplyChain field. We need SupplyChain consultants.

**Transactional selling**

There was a discussion about how we could end up doing "transactional sales" : we are not selling cups en masse. I didn't understand what this was about, ask PatrickvG or PatrickD for more info


Costumer Success
==================

**SuccessMatrix**

* Costumer Success made a Success Matrix that they shared in their presentation. 

.. math::
	
	SuccessMatrix = {What\: it\: takes\: to\: make\: a\: Costumer\: Success}.

.. image:: SuccessMatrix.png


* Partner training is needed, as we can see on this SuccessMatrix. 

**Costumer Success is important now**

* We see 2 major impediments for a real costumer success:

1. Product Stability 
2. Partner training/support + interaction with the R&D

* As Costumer Success, we need Project Delivery, meaning good Partner + good Product + good Costumer

	* Kill escalations
	* Happy renewal

* As of now, Costumer Success has defined a way to evaluate the risk of a project, depending on many factors, including if Cloud/on premise, PRO or not, Partner involved, etc. One tension is that on high risk project, the only way to deal with expectation is to involve supermen, like Marcel R or Peter N. But they are only 2 people actually, and we have more costumer projects. Thus we need more super[CostumerSupport]Men. 

	=> One proposal was that Marcel R will take Costumer Support guys under he's wing to learn, on the VION project specifically.

.. note::
		
		This has not happened yet. Marcel R is still alone for now.

Costumer Success Proposal
==========================

This proposal is about fighting for renewal. This was, in my opinion, the most tensed discussion of the Offsite, resulting in MarcelR's involvement, acknowledgement and hypothetical work with CS people (discussed above).

.. note::

	Since then, Marcel has dived into Fuji, Heineken, etc. and realized that our costumer or our partners are not that stupid: AIMMS is a big part of the problem. His main action since then was to actually prioritize bug fix in order to regain Fuji project, and make a few adjustments to the model (+ incorporating CDM when needed, because he's the only one to understand).

* Please read the proposal. Herein below some selected parts:

.. topic:: Underlying Tensions

	1.	Customers have not been successful implementing AIMMS projects recently.
	2.	We strive for lasting customer success, but we do not “put our money where our mouth” is
	3.	R&D operates at too remotely from customers

| 
	
.. topic:: CostumerSuccess Proposal

	**Proposal**

	1.	For each KEY project we involve a partner to do a risk assessment to determine our support plan
	2.	Roles will be assigned by CS6 based on the risk areas and resources will need to come from CS and R&D 
	3.	The level of pro-active project involvement  will be as indicated in the below table

	+------------------------+--------------------------------------------------------------------------------------------+
	| **Risk Factor**        | **Pro-active AIMMS project involvement**                                                   |
	+------------------------+--------------------------------------------------------------------------------------------+
	| LOW  < 0.8             | *       Monthly project updates with Partner by CS6 and customer check-in by AM            |
	+------------------------+--------------------------------------------------------------------------------------------+
	| MEDIUM > 0.8 and < 1.3 | *       Bi-Weekly review with PROJECT TEAM                                                 |
	|                        | *       One day of hands-on AIMMS support per 2 weeks within the risk areas of the project |
	+------------------------+--------------------------------------------------------------------------------------------+
	| HIGH > 1.3             | *       One-day workshop @ AIMMS for PARTNER on project risk areas in early project phase  |
	|                        | *       Weekly updates with PROJECT TEAM                                                   |
	|                        | *       One day of hands-on AIMMS support per week within risk areas                       |
	|                        | *       Bi-weekly updates by TAM/CS6 rep. in GCC                                           |
	+------------------------+--------------------------------------------------------------------------------------------+

.. comment: http://www.tablesgenerator.com/text_tables

|

Selected quotes from the discussion:
	* *Our Product is not mature at the level of our Partner talk*
	* *We are overprotecting R&D* against *We should not involve R&D on client side*

	
Partners
==========

* Wipro: silent, used to be exciting
* BCG: staling but still a large amount of sandbox licenses. There is an opportunity of tripartite agreement with ORTEC (BCG as strategic consultant).

**Trainings**

* Kick-started KPMG/BCG
* Advanced training to be given
* Engage with partners in costumer project

Costumer Support
=================

Only one remark/question from Jan Willem:
Do we have any conversion from Odoo Ticket in HowTo article ? 
Answer is a little yes

Marketing
==========

* the bot was not successful but gave us a lot of academic satisfaction about license activation (thanks to Khang)

* There was a big discussion about "MQL" (Marketing Qualified Lead) and "SQL" (Sales Qualified Lead), mainly concerning SCNavigator. 

.. note:: 

	* Marketing is at 75% devoted to SC Navigator
	* The Marketing/Sales funnel is as follows: Nobody=>MQL=>SQL=>Opportunity=>Client. Basically, the goal of marketing is to transform a Nobody into an SQL, then Sales take over to transform a SQL into a Client. Currently we have 7 Opportunities for SCNavigator.


R&D
======

* R&D is doing Holacracy because of holacracy, they nobody really believes in it. Neither in their Scrum process and agile methodology.

.. note::

    That's a bummer, in a software company supposed to deliver a programming language...
    
* They are working on making more logging and info, for people to analyse and help costumer (support).


Product Hardened
==================

.. note::

    Gijs started it

* "We have the feeling foundations are not good enough"
* reliability vs bug fixing ? Is this 
* Why not measure Telemetry data from Laith ? (LinLin strongly agrees as well)
* The number of bug reports is very high. Is that normal ? What can we do about it ?
* It would be good to have a KPI only about stability, not consequenced bugs
	
.. note:: As a result, R&D KPIs changed a bit. Joris mentioned R&D Cloud effort to report about downtimes, which they consider as a direct stability KPI.

UX 2.0 (Gertjan)
==================

* Obviously, ,because of SC Navigator step back in sales, we have less money for UX 2.0

.. note:: interestingly, nothing emerged about the stability that needed to needed to be fixed as well, before releasing new stuff.

Thus, the question turned into:
* How to improve SCNav and S&OP app with the current tech available ?
* Gertjan would like (already did ) to ask Heineken, Ecolab, etc. to fund UX 2.0

.. note:: 

    This seems currently totally inappropriate, looking at the amount of bugs and lack of stability: we deliver a product that is not "good", and on top of that we want them to pay for improvements. It feels we ask them to pay for more bugs ! 

Cloud
========

* the estimation of downtimes is somehow biased, non costumer centric for several reasons:
    * KPI is [nb incident/hours in month]. However, many hours (during the night) are totally irrelevant for the costumer. Thus this KPI is too positive.
    * downtimes after an incident are estimated, 

* so far, costumer downtimes were due to many different layers in AIMMS. Inter-relations are fairly complex to anticipate.

Holacracy (Megan)
===================

* Energize AIMMSians: things stagnated, thus a digest was made

* Salary determination is outside Holacracy, in GCC circle. Is this what it should be ?

* Holacracy priority is to energize rep link of different circles

* R&D is low in Holacracy implementation. Kim was assigned to the R&D circle, to get people energized.

BSS Circle
============

**Jolanda's Business App**
Jolanda is moving her "Business app"(financial planning) from an Excel to AIMMS. It is not yet complete. Data is there but the multi year layer not yet
* What about using SC Navigator S&OP ??=> Jolanda will contact ChrisG

**Recruitment procedure**
Everybody more or less agreed that we had several miss hired people. Notably Ted Francisco, that was supposed to help Christophe on Supply Chain knowledge and sales, and that finally end-up leaving after realizing he had (also) poor skills.
It turned out several alert signals were raised by many people about Ted Francisco, but they were lost in the procedure, or not integrated unfortunately. 
"We didn't stick to the process" as Pauline and Megan agreed upon. 
* => no references
* => no consensus/feedback around the office, but
* => "he could bridge the gap"  Simulation vs Optimisation 

**Data Protection**

... euh, whatever. this is not clear at all to me.

GCC
======

* a big tension was raised by Kim about our company KPI, which is a financial number not representing at all our work, success and failures. Gijs agreed and assigned Kim to the project of determining a new KPIs, and our new purpose. Yes, there is a project for that in the GCC circle...

.. note::

    You may ask Kim to be part of the "purpose framework brainstorming sessions", to think about it all together.
    
Funding project (Gijs)
========================

* Hold on: We have to wait for some SC Navigator sales, or some costumer success in order to go back to search for investors. It is currently difficult for Giks to share a good story with potential investors, because AIMMS results are pretty bad.

* A question was raised, How is it decided if any dollar is invested ? Gijs answered:
    => SC navigator + sales and marketing first
    
    => R&D underlying product next. (but we already have overspent on R&D so far)

* and some precision about the financial matrix update Gijs also shared during the offsite debrief: the 2 following basic and useful ideas.

    * free cash flow you can see in the financial matrix is representing actually what can we invest in our own development. This is not a lot.
    * subscriptions are making us live, are extremely valuable, provided we renew... You see the trick ? Basic economics. Please reflect on it (until now, we were selling our software as perpetual license.)
    
.. note::

    We could almost say that subscriptions are actually saving us from bankruptcy due to SC Nav poor result and R&D spendings.
    
Conclusion 
============================

All of those discussions came out with several tensions Patrick van Gent shared by e-mail. In my opinion those tensions are not the underlying tensions. The underlying tensions came up in our circle track. AIMMS is in a very particular crisis situation in my opinion, we can't really make a list of tensions like we did in the offsite: everything is linked. We need a new vision.


