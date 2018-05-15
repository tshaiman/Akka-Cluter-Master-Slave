# Dv-Akka-Poc - Cluster 
Akka POC Demo (Cluster/Workers)

# usage 
run **sbt compile** in order to compile the protobuf file

running **sbt run** will run the cluster in one process on 7 ports (2551-2557) and will open an Http listener on 8080
we ran it in Google cloud using the following commands 

**sbt assembly** in order to build the assembly jar 
and than

java -jar  DvPocCluster-assembly-1.0.jar cluster 2551

java -jar  DvPocCluster-assembly-1.0.jar cluster 2552

java -jar  DvPocCluster-assembly-1.0.jar cluster 2553

java -jar  DvPocCluster-assembly-1.0.jar cluster 2554

java -jar  DvPocCluster-assembly-1.0.jar cluster 2555

java -jar  DvPocCluster-assembly-1.0.jar cluster 2556

java -jar  DvPocCluster-assembly-1.0.jar cluster 2557


which will open 7 worker nodes on the cluster.
each node will perform a work of 400 microseconds in 20% of the requests
and a work of 50 microseconds in 80% of the requests.

for more information : tomer.shaiman@doubleverify.com
