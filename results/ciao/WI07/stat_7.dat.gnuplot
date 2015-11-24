set terminal postscript enhanced 18
set xrange[0:1]
set xlabel 'Ws'
set ylabel 'Similarity'
set key ins vert center top spacing 1.0
set output 'stat_7.dat.eps'
set bars 4.0
set style fill empty
plot 'stat_7.dat.elab' using 1:3:2:6:5:xticlabel(1) with candlesticks lt -1 lw 1 title 'Quartiles' whiskerbars,'' using 1:4:4:4:4:xticlabels(1) with candlesticks lt -1 lw 4 title 'Median'
