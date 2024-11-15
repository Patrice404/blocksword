# blockwords
Tp file rouge du cours IAAD

# Pour le Package modelling
compilation :
javac -d ../build -cp ../lib/modellingtests.jar modelling/*.java 

# Pour le Package planning
compilation:
javac -d ../build -cp ../lib/planningtests.jar planning/utils/*.java planning/*.java

# Pour le Package cp
compilation:
javac -d ../build -cp ../lib/cptests.jar cp/*.java

# Pour le Package datamining
compilation: 
javac -d ../build -cp ../lib/dataminingtests.jar datamining/*.java

# Pour le Packag blockwords
compilation: 
javac -d ../build -cp ../lib/blocksworldtests.jar blocksworld/*.java blocksworld/demo/*.java

