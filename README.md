Gugëll Guesser është një aplikacion i krijuar në Java, i cili ka si funksion gjetjen e rrugës më të shkurtër duke marrë parasysh të dhënat e një dataset-i, që në këtë rast përmban rrugët e Cërrikut dhe distancat e tyre me njëra-tjetrën.

Klasa Graph --> Klasa që përmban dhe algoritmin që bën të mundur gjetjen e rrugës më të shkurtër për të shkuar nga një pikë fillestare drejt një destinacioni përfundimtar. Algoritmi i përdorur është A*. I cili përdor priority queue për t’i dhënë prioritet nodes bazuar në koston e tyre. Algoritmi gjithashtu ruan një set me visited nodes, priority queue dhe parent map, që ruajne lidhjen mes nodes dhe rrugës më të shkurtër. Kthen rrugën më të shkurtër mes starting node dhe goal node.

Klasa Node --> Klasa Node përfshin metoda për shtimin e lidhjeve, llogaritjen e heuristic value dhe ngarkimin e të dhënave nga file-t CSV. Klasa shton lidhjet midis nodes, llogarit një vlerë heuristike (një vlerë e vlerësuar e distancës deri te qëllimi) në bazë të koordinatave, ngarkon nodes dhe connections nga filet CSV. Një static map që lidh emrat e rrugëve me nodes korresponduese. Kjo map përdoret për të marrë një node sipas emrit të rrugës. Përdor një buffered reader për të lexuar të dhënat nga file-t.

Klasa Connection --> Klasa bën të mundur lidhjet e klasës Node dhe gjithashtu përcaktimin e distancave mes nodes.

Klasa Style --> Kjo klasë bën të mundur stilizimin e UI duke përdorur CSS, projektuar për të punuar me JavaFX me përdorimin e prefikseve -fx.

Klasa Main --> Kjo klasë shfrytëzon klasat dhe metodat e përcaktuara më herët për të përcaktuar rrugën më të shkurtër në mes të dy vendeve në një hartë. Hap një ndërfaqe Google Maps në një WebView përcaktuar në një dritare të re. Konstrukton URL-në për Google Maps duke përdorur adresat e formatuara. Dhe gjithashtu stilizimi i UI.
