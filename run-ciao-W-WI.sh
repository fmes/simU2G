sw=10000
nt=10
#dataset=epinions
dataset=ciao

d=$(cd `dirname $0`; pwd)

#compile code
javac $d/src/Sim.java

#cd conf/$dataset;
mkdir -p $d/results/$dataset;
dest=$d/results/$dataset;
confd=$d/conf/$dataset;

#i is W
for i in `seq 1 9`; do
  #j is WI
  for j in `seq 1 9`; do
    f="$confd/$dataset-config-WI$j-W$i";
    fout="$confd/$dataset-W0$i-WI0$j-NOoverlap.txt";
    echo "*** $f ***"
    rm -f $confd/$dataset-config.txt
    ln -sf $f $dataset-config.txt
    echo "%$f" > $fout

    java -cp $d/src -Xmx6000m -Dsw=$sw -Dnt=$nt  -Ddataset=$dataset -Doverlap=0 Sim 2>>$fout

    mkdir -p $dest/W0$i
    mkdir -p $dest/WI0$j

    cp $fout $dest/W0$i
    cp $fout $dest/WI0$j

    rm $fout
  done
done
