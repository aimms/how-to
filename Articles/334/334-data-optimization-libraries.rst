Data for Optimization Libraries
================================

Vraag: kan ik beter namen gebruiken als set en parameter, of beter een soort voorbeeld en dat ik dat Cities, distance en demand gebruik?
Antwoord: Bij Chris zijn subset programmaatje gebruikte hij City etc.



Een AIMMS bibliotheek kan worden toegevoegd aan andere projecten. Dit is handig bij vaker voorkomende problemen, zoals het Capacitated Vehicle Routing Problem (CVRP). Dan hoeven verschillende gebruikers niet telkens opnieuw over hetzelfde probleem na te denken. Een CVRP bibliotheek kan dan worden toegevoegd en zonder al te veel acties worden aangeroepen.

Het is dan wel belangrijk dat een bibliotheek zo min mogelijk input vereist en dat de data automatisch in de goede sets/parameters terecht komt. Een voorbeeldje voor het overzetten van data in een bibliotheek is hier te vinden:
(link to: PracticeLibrary)

Wat je uiteindelijk wilt bereiken is de mogelijkheid om in je model enkel deze zin toe te hoeven voegen en dat het dan werkt. 
pl::TransferData(s_Costumers,p_Demand,p_Distance); 

Misschien moet er ook nog een output argument komen voor de uitleg?
Maar dat is wel onhandig, want dan moet je ook echt een hele procedure maken dat er in de library iets gebeurt.
Dat hoeft natuurlijk niet helemaal een Mathematical Program te zijn, kan ook de meest simpele output ooit zijn.


 

- bibliotheek -> je wilt dat het in andere modellen zonder al te veel moeite te gebruiken is
- je wilt eigenlijk alleen dat input argumenten mee gegeven hoeven worden en dat relevante output argumenten er weer uitkomen (zo min mogelijk acties)
- Hoe moet je de data overzetten?
- Hier is een voorbeeldje gemaakt

- library interface etc (maak een public section zodat je meteen alles in 1 keer in de interface kunt zetten)
- daarna moet je de data in de bibliotheek krijgen 
- bij sets is het niet mogelijk om dat zomaar te kopieren plakken 
- (Iets zeggen over dat je verschillende opmaken kunt hebben van de Costumer set. Bijvoorbeeld namen van costumers, of gewoon nummering)
	vraag: is dat ook de grootste reden waarom je niet zomaar kunt kopieren/plakken?
	extra note: Als beide sets een subset zijn van een andere set kan het wel

- Dus om de data in de bibliotheek te krijgen: stukje code
- uitleggen wat er gebeurd 



te verwerken onderwerpen:
- library interface
- iets van dat de procedure private is en je de data public wilt krijgen 








English version:

An AIMMS library can be added to other projects. This is useful for more common problems, such as the Capacitated Vehicle Routing Problem (VRRP). That way different users don't all have to think about the same problem. (A CVRP library can simply be added and without too many actions the CVRP will be calculated).

