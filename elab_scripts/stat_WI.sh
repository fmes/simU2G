if [[ $# != 2 ]]; then 
  echo "Usage $0 <w> <dataset>"
else

w=$1
ds=$2

d=$(`cd dirname $0`; pwd);

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
      echo "load $ds""_W0$w""_WI0$i""_NOoverlap;" >> $statf
      if [[ $i == 1 ]] ; then    
	echo "A(:,$i)=$ds""_W0$w""_WI0$i""_NOoverlap(:,1);" >> $statf
	echo "A(:,$((i+1)))=$ds""_W0$w""_WI0$i""_NOoverlap(:,$j);" >> $statf
      else
	echo "A(:,$((i+1)))=$ds""_W0$w""_WI0$i""_NOoverlap(:,$j);" >> $statf
      fi
    done

      echo "S=statistics(A(:,2:end),1)(1:5,:)';" >> $statf
      echo "SS=[[0.1:0.1:0.9]',S];" >> $statf
      echo "dlmwrite(\"stat_$l""_W0$w"".dat\",SS, \"\t\", \"precision\", 4);" >> $statf

      octave --silent $statf
      bash $d/candlestick/candle_stick.gnuplot.sh stat_$l"_W0$w"".dat" $d/candlestick/candle_stick.Wfixed.WIvar.ndef
  done
fi
