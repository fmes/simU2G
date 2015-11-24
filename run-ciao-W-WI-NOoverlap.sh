sw=10000
nt=10
#dataset=epinions
dataset=ciao

for i in `seq 1 9`; do
  for j in `seq 1 9`; do
    f="ciao-config-WI$j-W$i";
    fout="ciao-W0$i-WI0$j-NOoverlap.txt"
    echo "*** $f *** "
    rm -f ciao-config.txt
    ln -s $f ciao-config.txt
    echo $f > $fout
    java -Xmx6000m -Dsw=$sw -Dnt=$nt  -Ddataset=$dataset -Doverlap=0 Sim 2>>$fout
  done
done
