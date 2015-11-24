sw=100000
nt=10
dataset=epinions
#dataset=ciao

for i in `seq 1 9`; do
  for j in `seq 1 9`; do
    f="$dataset-config-WI$j-W$i";
    fout="$dataset-W0$i-WI0$j-NOoverlap.txt"
    echo "*** $f *** "
    rm -f $dataset-config.txt
    ln -s $f $dataset-config.txt
    echo "%$f" > $fout
    java -Xmx6000m -Dsw=$sw -Dnt=$nt  -Ddataset=$dataset -Doverlap=0 Sim 2>>$fout
  done
done
