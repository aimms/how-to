Data for Optimization Libraries
================================

Een AIMMS bibliotheek kan worden toegevoegd aan andere projecten. Dit is handig bij vaker voorkomende problemen, zoals het Transport Probleem. Dan hoeven verschillende gebruikers niet telkens opnieuw over hetzelfde probleem na te denken. Een bestaande bibliotheek van een Transport Probleem kan gewoon worden toegevoegd aan een model. Er zijn enkel een aantal input argumenten nodig en de relevante uitkomsten worden teruggegeven. 

In de bibliotheek moet een manier zijn waarbij de input data wordt overgezet naar data in de bibliotheek. Hier is een voorbeeld van een Transport Probleem bibliotheek waarbij dat gebeurt (Link naar Transport Bibliotheek).

Bij een transport probleem zijn er een aantal sources. Vanuit die sources moeten goederen worden geleverd aan sinks. Iedere source heeft een supply en iedere sink heeft een demand. De routes tussen de sources en de sinks hebben allemaal hun eigen kosten voor het vervoeren van één unit. De vraag is wat de goedkoopste manier is om alle goederen te leveren die de sinks nodig hebben. 

- je hebt dus deze input argumenten (geef de benaming hoe ze ook in de bibliotheek staan)
- je wilt de volgende output argumenten (geef de benaming hoe ze ook in de bibliotheek staan)
- dat zijn de gegevens die getransfered moeten worden 

- In de bibliotheek zijn alle parameters etc waar de data naar gekopieerd moet worden. 


- in de bibliotheek maak je een procedure die de data gaat transferren
- in het hoofdmodel hoef je alleen maar 1 zin te typen die de procedure aanroept en alle argumenten meegeeft
- de procedure in de bibliotheek heeft argumenten waar je de gegevens aan meegeeft
- aangezien dat in een procedure is, zijn de gegevens niet public
- Nu wil je de gegevens kopieren naar de public argumenten
- eerst maak je de sets leeg (voor de zekerheid)

- als je bijvoorbeeld gegevens van een parameter wilt kopieren/plakken kan dat een stuk makkelijker
- bij een set kun je niet zomaar s_library := s_local doen. dat komt omdat .....


- stukje code uitleggen 










- pl::TransferData(s_Sources, s_Sinks, p_Supply, p_Demand, p_TransportCost, v_TotalTransportCost, v_Transport);
















-> ergens in de tekst verwerken waarom je sets niet zomaar kunt kopieren/plakken. 
	extra note: Als beide sets een subset zijn van een andere set kan het wel
-> iets over dat je het op deze manier doet omdat mensen niet iets in de bibliotheek moeten hoeven bewerken.
-> library interface
-> iets van dat de procedure private is en je de data public wilt krijgen 
-> stukje code 
