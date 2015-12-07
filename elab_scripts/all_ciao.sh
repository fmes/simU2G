elab_dir=$(cd `dirname $0`; pwd)

cd results/ciao;

for i in `seq 1 9`; do 
  (cd WI0$i; bash $elab_dir/process_W.sh $i ciao);
  (cd W0$i; bash $elab_dir/process_WI.sh $i ciao);
 
  (cd W0$i; bash  $elab_dir/stat_WI.sh $i ciao); 
  (cd WI0$i; bash $elab_dir/stat_W.sh $i ciao); 

  (cd W0$i; bash $elab_dir/plot_all_diff.sh ciao); 
  (cd WI0$i; bash $elab_dir/plot_all_diff_histo.sh ciao);
done 
