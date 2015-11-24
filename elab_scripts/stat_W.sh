if [[ $# != 2 ]]; then 
  echo "Usage $0 <w> <dataset>"
else

w=$1
ds=$2

for l in "COMP" "SIM"; do
  statf="stat_$l.m"
  echo "" > $statf
  echo "A=zeros(11,10);" >> $statf;
  if [[ $l == "COMP" ]]; then
    j=2; #column COMP
  else
    j=3; #column SIM
  fi

  for i in `seq 1 9`; do 
    echo "load $ds""_W0$i""_WI0$w""_NOoverlap;" >> $statf
    if [[ $i == 1 ]] ; then    
      echo "A(:,$i)=$ds""_W0$i""_WI0$w""_NOoverlap(:,1);" >> $statf
      echo "A(:,$((i+1)))=$ds""_W0$i""_WI0$w""_NOoverlap(:,$j);" >> $statf
    else
      echo "A(:,$((i+1)))=$ds""_W0$i""_WI0$w""_NOoverlap(:,$j);" >> $statf
    fi
  done

    echo "S=statistics(A(:,2:end),1)(1:5,:)';" >> $statf
    echo "SS=[[0.1:0.1:0.9]',S];" >> $statf
#  echo "save(\"-ascii\", \"stat_$l.dat\", \"SS\");" >> $statf
    echo "dlmwrite(\"stat_$l""_WI0$1.dat\",SS, \"\t\", \"precision\", 4);" >> $statf

    octave --silent  $statf
    bash `dirname $0`/candlestick/candle_stick.gnuplot.sh stat_$l"_WI0"$w"".dat `dirname $0`/candlestick/candle_stick.Wvar.WIfixed.ndef
  done

fi
