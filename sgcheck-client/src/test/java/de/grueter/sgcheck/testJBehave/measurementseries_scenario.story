Narrative: 

Scenario: Es wird geprueft ob eine Messreihe angelegt werden kann
Given eine oder mehrere Messreihen befinden sich in der Liste
When eine neue Messreihe wird angelegt
Then sollte die Anzahl der Listeneintraege groesser 1 sein
Then sollte die Messreihe mit der ID existieren

Scenario: Es wird zweimal die gleiche Messreihe angelegt
Given eine Messreihe mit der ID $id befindet sich in der Liste
When eine Messreihe mt der gleichen ID $id angelegt wird
Then soll eine Fehlermeldung ausgegeben werden

Examples:
|id|
|666|
|999|