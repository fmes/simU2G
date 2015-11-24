if [[ $# != 2 ]]; then
  echo "Usage $0 <w> <dataset>"
else

w=$1
ds=$2

statf="stat_diff_W0"$w"_$ds".m
echo "" > $statf
echo "A=zeros(9,3);" >> $statf;

for i in `seq 1 9`; do
  echo "load $ds""_W0$w""_WI0$i""_NOoverlap;" >> $statf;
  echo "B=$ds""_W0$w""_WI0$i""_NOoverlap;" >> $statf;
  echo "A($i,1)=0.$i;" >> $statf;
  echo "A($i,2)=(max(B(:,2))-min(B(:,2)))/max(B(:,2));" >> $statf #COMP
  echo "A($i,3)=(max(B(:,3))-min(B(:,3)))/max(B(:,3)); ;" >> $statf #SIM
done

  echo "dlmwrite(\"stat_diff""_W0$w"".dat\",A, \"\t\", \"precision\", 4);" >> $statf

  octave --silent $statf
#  bash `dirname $0`/candlestick/candle_stick.gnuplot.sh stat_$l"_W0$w"".dat" `dirname $0`/candlestick/candle_stick.Wfixed.WIvar.ndef
fi
