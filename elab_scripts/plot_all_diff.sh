if [[ -z $1 ]]; then
  echo "Usage: $0 <dataset>"
else

  d=$(cd `dirname $0` && pwd);

  ds=$1
  #W
  for i in `seq 1 9`; do
    (cd W0$i; bash $d/stat_W_diff.sh $i $ds; rm -f data.dat; ln -s stat_diff_W0$i.dat data.dat; gnuplot $d/plotDiffW.gnuplot > plot_diff_W0$i.eps)
  done

  #WI
  for i in `seq 1 9`; do
    (cd WI0$i; bash $d/stat_WI_diff.sh $i $ds; rm -f data.dat; ln -s stat_diff_WI0$i.dat data.dat; gnuplot $d/plotDiffWI.gnuplot > plot_diff_WI0$i.eps)
  done

fi
