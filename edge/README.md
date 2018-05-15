# Dv-Akka-Poc - Edge
Akka POC Demo - Edge (Http)

# usage 
run **sbt compile** in order to compile the protobuf file

running **sbt run** will run the cluster in one process on 7 ports (2551-2557) and will open an Http listener on 8080
we ran it in Google cloud using the following commands 

**sbt assembly** in order to build the assembly jar 
and than


java -jar  DvPocEdge-assembly-1.0.jar http


which will open 7 an Http Server on port 8080 on the localhost
The service will than use a default value of "worktime=400" microseconds
and default value of NumOfServices=7 to dispatch work to 7 diffrent "micro-services"


for more information : tomer.shaiman@doubleverify.com



