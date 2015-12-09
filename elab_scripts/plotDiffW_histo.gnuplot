set terminal eps enhanced monochrome font "arial,20"
set boxwidth 0.9 absolute
set style fill solid 1.00 
set key inside right top vertical Right noreverse noenhanced autotitle nobox
set style histogram clustered gap 1 title textcolor lt -1
set datafile missing '-'
set style data histograms
set xtics border in scale 0,0 nomirror rotate by -45  autojustify
set xtics  norangelimit
set xtics   ()
set xlabel "WI"
set ylabel "MAS reduction (ratio)"

min(a,b) = (a < b) ? a : b
max(a,b) = (a < b) ? b : a

#determine min and max
stats 'data.dat' using 2;
min1=STATS_min;
max1=STATS_max;
stats 'data.dat' using 3;
min2=STATS_min;
max2=STATS_max;
mn=min(min1,min2);
mx=max(max1,max2);
set yrange [mn: mx+mx/7];

#set title "US immigration from Northern Europe\nPlot selected data columns as histogram of clustered boxes" 
#set yrange [ 0.00000 : 300000. ] noreverse nowriteback
#x = 0.0
#i = 22
## Last datafile plotted: "immigration.dat"
plot 'data.dat' using 2:xtic(1) ti "COMP" fs pattern 1 , '' u 3 ti "SIM"  fs pattern 2
