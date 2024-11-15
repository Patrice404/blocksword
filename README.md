# Blockswords

Se placer dans le dossier et lancer les commandes suivantes:

# Compilation
    - javac -d ../build -cp ../lib/bwgenerator.jar:../lib/blocksworld.jar planning/utils/*.java planning/*.java modelling/*.java datamining/*.java cp/*.java blocksworld/*.java blocksworld/utils/*.java blocksworld/*.java blocksworld/demo/*.java
    - 
# Exécution des démos
    # Verification satisfaction de contrainte
    - java -cp ../build:../lib/blocksworld.jar  blocksworld. demo.VerificationSatisfactionConstraint1
    - java -cp ../build:../lib/blocksworld.jar  blocksworld. demo.VerificationSatisfactionConstraint2
    
    # Création de configuration
    - java -cp ../build:../lib/blocksworld.jar  blocksworld. demo.CreationConfiguration1
    - java -cp ../build:../lib/blocksworld.jar  blocksworld. demo.CreationConfiguration2
    - java -cp ../build:../lib/blocksworld.jar  blocksworld. demo.CreationConfiguration3

    # Planning
    - java -cp ../build:../lib/blocksworld.jar  blocksworld. demo.Planning1
    - java -cp ../build:../lib/blocksworld.jar  blocksworld. demo.Planning2
    - java -cp ../build:../lib/blocksworld.jar  blocksworld. demo.Planning3
    
    #Datamining
    - java -cp ../build:../lib/blocksworld.jar  blocksworld. demo.Datamining
  
