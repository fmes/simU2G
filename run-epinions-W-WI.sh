sw=10000
nt=10
dataset=epinions
#dataset=ciao

#compile code
javac src/Sim.java

cd conf/$dataset;
mkdir -p results/$dataset;

#i is W
for i in `seq 1 9`; do
  #j is WI
  for j in `seq 1 9`; do
    f="$dataset-config-WI$j-W$i";
    fout="$dataset-W0$i-WI0$j-NOoverlap.txt";
    echo "*** $f *** "
    rm -f $dataset-config.txt
    ln -s $f $dataset-config.txt
    echo "%$f" > $fout
    java -cp src -Xmx6000m -Dsw=$sw -Dnt=$nt  -Ddataset=$dataset -Doverlap=0 Sim 2>>$fout

    mkdir -p results/$dataset/W0$i
    mkdir -p results/$dataset/WI0$j

    cp $fout results/$dataset/W0$i
    cp $fout results/$dataset/WI0$j

    rm $fout
  done
done
