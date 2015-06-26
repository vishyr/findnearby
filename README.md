# findnearby
Finds cities matching the name provided and also get points of interests nearby

Clone the repo

$git clone https://github.com/vishyr/findnearby.git

To build, issue command

$cd findnearby

$mvn clean package

Add execute permission to runMe.sh

$chmod +x runMe.sh

To run 

$./runMe.sh london

Note
----
The username I have used is "foobar" because the given username "demo" gets rate limited within very few tries.

Sample Output
-------------
2015-06-25 18:45:33 INFO  FindNearby:42 - Getting city list for input paris

2015-06-25 18:45:35 INFO  FindNearby:54 - For the city of Paris-Gare de Lyon, we have the following nearby points of interest

{

  "countryCode" : "FR",
  
  "lang" : "en",
  
  "feature" : "landmark",
  
  "title" : "Paris-Gare de Lyon",
  
  "countryName" : "France",
  
  "lat" : "48.84469",
  
  "lng" : "2.37407",
  
  "toponymName" : "Lyon Station",
  
  "fcode" : "MTRO",
  
  "distance" : "0.04317"
  
}

2015-06-25 18:45:36 INFO  FindNearby:54 - For the city of Paris, we have the following nearby points of interest

{

  "countryCode" : "FR",
  
  "lang" : "en",
  
  "feature" : "city",
  
  "title" : "Paris",
  
  "countryName" : "France",
  
  "lat" : "48.8567",
  
  "lng" : "2.35101",
  
  "toponymName" : "Bureau de Poste de Paris Hotel De Ville",
  
  "fcode" : "PO",
  
  "distance" : "0.01507"
  
}

.

.

and so on..

2015-06-25 18:45:42 INFO  FindNearby:73 - Done!

