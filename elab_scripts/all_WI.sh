for i in `seq 1 9`; do (cd WI0$i; bash ../arrange.sh; bash elab_W.sh $i; bash stat_W.sh $i); done
