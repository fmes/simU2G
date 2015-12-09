elab_dir=$(cd `dirname $0`; pwd)

resd=$elab_dir/../results/epinions;
cd $resd;

for i in `seq 1 9`; do 
  (cd WI0$i; bash $elab_dir/process_W.sh $i epinions);
  (cd W0$i; bash $elab_dir/process_WI.sh $i epinions);
 
  (cd W0$i; bash  $elab_dir/stat_WI.sh $i epinions); 
  (cd WI0$i; bash $elab_dir/stat_W.sh $i epinions); 

  (cd W0$i; bash $elab_dir/plot_all_diff.sh epinions); 
  (cd WI0$i; bash $elab_dir/plot_all_diff_histo.sh epinions);
done 
