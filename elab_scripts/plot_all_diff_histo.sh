if [[ -z $1 ]]; then
  echo "Usage: $0 <dataset>"
else

  d=$(cd `dirname $0`; pwd);
  ds=$1

  resd=$d/../results/$ds;

  #W
  for i in `seq 1 9`; do
    (cd $resd/W0$i; bash $d/stat_W_diff.sh $i $ds; rm -f data.dat; ln -s stat_diff_W0$i.dat data.dat; gnuplot $d/plotDiffW_histo.gnuplot > plot_diff_W0$i.eps)
  done

  #WI
  for i in `seq 1 9`; do
    (cd $resd/WI0$i; bash $d/stat_WI_diff.sh $i $ds; rm -f data.dat; ln -s stat_diff_WI0$i.dat data.dat; gnuplot $d/plotDiffWI_histo.gnuplot > plot_diff_WI0$i.eps)
  done
fi
