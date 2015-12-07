set terminal eps enhanced monochrome font "arial,20"
#set output 'comp_sim.eps'
set datafile missing '-'
#set title "Average QoS =|T|)"
set key center right
#set yrange [0:1.0]
#set xrange [0.4:1.0]
set ylabel "MAS"
set xlabel "time-window"
#set logscale y
set grid
plot 'data.dat' using 1:2 title "COMP" with linespoint lt 1, '' using 1:3 title "SIM" with linespoint lt 3
#'' using 1:4 title "maxRep" with linespoint
