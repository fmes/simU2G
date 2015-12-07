#process and plot data with variable W

if [[ $# != 2 ]]; then 
  echo "Usage: <Ws> <dataset>"
else

ds=$2
w=$1
d=$(cd `dirname $0`; pwd);

for i in `seq 1 9`; do
  f=$ds-W0$i-WI0$w-NOoverlap.txt
  grep -v config $f > $f.elab.1
  grep -v MAS $f.elab.1 > $f.elab
  echo -e "%T\tCOMP\t\tSIM" > $f.elab.txt
  cat $f.elab | awk 'FNR<=11 {v[FNR,1]=$w; v[FNR,2]=$3} FNR>11 {v[FNR-11,3]=$3;} END{for(i=1;i<=11;i++) printf("%d\t%f\t%f\n",v[i,1],v[i,2],v[i,3])}' >> $f.elab.txt
  rm $f.elab $f.elab.1
  
  #store results
  newf="`echo $f | sed -e 's/-/_/g; s/\.txt//'`"
  mv $f.elab.txt $newf;

  #plot results
  ln -s $f data.dat;
  gnuplot $d/plot.gnuplot > $f.eps
done
fi
