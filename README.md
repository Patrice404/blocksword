# Blockswords

Se placer dans le dossier src et lancer les commandes suivantes:
# Compilation
    - javac -d ../build -cp ../lib/bwgenerator.jar:../lib/blocksworld.jar planning/utils/*.java planning/*.java modelling/*.java datamining/*.java cp/*.java blocksworld/utils/*.java blocksworld/*.java  blocksworld/demo/*.java
# Exécution des démos
    # Vérification satisfaction de contrainte
    - java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar blocksworld.demo.VerificationSatisfactionConstraint1
    - java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar blocksworld.demo.VerificationSatisfactionConstraint2
    
    # Création de configuration
    - java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar   blocksworld.demo.CreationConfiguration1
    - java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar   blocksworld.demo.CreationConfiguration2
    - java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar   blocksworld.demo.CreationConfiguration3

    # Planning
    - java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar   blocksworld.demo.Planning1
    - java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar   blocksworld.demo.Planning2
    
    # Datamining
    - java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar  blocksworld.demo.Datamining
  
# Source des emojis 
trello.com
