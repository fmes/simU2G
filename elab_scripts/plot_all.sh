ds=$1
#Ws
for i in `seq 1 9`; do 
  cd W0$i;
  bash 
  ln -s $f  data.dat
  gnuplot `dirname $0`/plot.gnuplot > `echo $f | sed -e 's/\.elab\.txt//'`.eps
done

#WI
