Narrative: 

Scenario: Es wird geprueft ob eine Messreihe angelegt werden kann
Given eine oder mehrere Messreihen befinden sich in der Liste
When eine neue Messreihe wird angelegt
Then sollte die Anzahl der Listeneintraege groesser 1 sein
Then sollte die Messreihe mit der ID existieren