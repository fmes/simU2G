set terminal eps enhanced monochrome font "arial,20"
set datafile missing '-'
set key center right
#set yrange [0:1.0]
#set xrange [0.4:1.0]
set ylabel "Ratio of MAS decreasing"
set xlabel "WI"
#set logscale y
set grid
plot 'data.dat' using 1:2 title "COMP" with linespoint lt 1, '' using 1:3 title "SIM" with linespoint lt 3
#'' using 1:4 title "maxRep" with linespoint
