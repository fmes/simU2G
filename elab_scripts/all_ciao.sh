for i in `seq 1 9`; do (cd WI0$i; bash ../elab_W.sh $i ciao); done 
for i in `seq 1 9`; do (cd W0$i; bash ../elab_WI.sh $i ciao); done 

for i in `seq 1 9`; do (cd W0$i; bash ../plot_all.sh ciao); done 
for i in `seq 1 9`; do (cd WI0$i; bash ../plot_all.sh ciao); done 

for i in `seq 1 9`; do (cd W0$i; bash  ../stat_WI.sh $i ciao); done 
for i in `seq 1 9`; do (cd WI0$i; bash  ../stat_W.sh $i ciao); done 
