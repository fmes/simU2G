for i in `seq 1 9`; do (cd WI0$i; bash ../elab_W.sh $i epinions); done 
for i in `seq 1 9`; do (cd W0$i; bash ../elab_WI.sh $i epinions); done 

for i in `seq 1 9`; do (cd W0$i; bash ../plot_all.sh epinions); done 
for i in `seq 1 9`; do (cd WI0$i; bash ../plot_all.sh epinions); done 

for i in `seq 1 9`; do (cd W0$i; bash  ../stat_WI.sh $i epinions); done 
for i in `seq 1 9`; do (cd WI0$i; bash  ../stat_W.sh $i epinions); done 
