# simU2G

### Run Training and tests for CIAO and EPINIONS datasets 

* Scripts:
  * run-epinions-W-WI.sh and run-ciao-W-WI.sh will run the software and will left results in results/ciao and results/epinions. 
  * Results will be left in directories W0x ans WI0x. 

* Requirements: 
  * Directories conf/ciao and conf/epinions must exist and contain configurations 
    for "simulations".
  * Directory src must exist and contain source file Sim.java.
  * The following files must be in the root directory (just unzip file "trust_rating.zip"):
    * ciao-rating-sorted.txt
    * ciao-trust.txt
    * epinions-rating-sorted.txt
    * epinions-trust.txt

###Process and plot results: 

* Scripts: 
  * elab_script/all_ciao.sh will process all the results related to CIAO dataset
  * elab_script/all_epinions.sh will process all the results related to EPINIONS dataset
  * data files (*.dat) and eps plots will be left in directories results/{ciao|epinions}/{WI0x,W0x}

* Requirements:
  * Directory results/ciao/WI0x, results/ciao/W0x
  * Directory results/epinions/WI0x, results/epinions/W0x
