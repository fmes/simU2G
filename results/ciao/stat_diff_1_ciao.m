
A=zeros(9,3);
load ciao_W01_WI01_NOoverlap;
B=ciao_W01_WI01_NOoverlap;
A(1,1)=1
A(1,2)=max(B(:,2)-min(B(:,2));
A(1,3)=max(B(:,3)-min(B(:,3));
load ciao_W01_WI02_NOoverlap;
B=ciao_W01_WI02_NOoverlap;
A(2,1)=2
A(2,2)=max(B(:,2)-min(B(:,2));
A(2,3)=max(B(:,3)-min(B(:,3));
load ciao_W01_WI03_NOoverlap;
B=ciao_W01_WI03_NOoverlap;
A(3,1)=3
A(3,2)=max(B(:,2)-min(B(:,2));
A(3,3)=max(B(:,3)-min(B(:,3));
load ciao_W01_WI04_NOoverlap;
B=ciao_W01_WI04_NOoverlap;
A(4,1)=4
A(4,2)=max(B(:,2)-min(B(:,2));
A(4,3)=max(B(:,3)-min(B(:,3));
load ciao_W01_WI05_NOoverlap;
B=ciao_W01_WI05_NOoverlap;
A(5,1)=5
A(5,2)=max(B(:,2)-min(B(:,2));
A(5,3)=max(B(:,3)-min(B(:,3));
load ciao_W01_WI06_NOoverlap;
B=ciao_W01_WI06_NOoverlap;
A(6,1)=6
A(6,2)=max(B(:,2)-min(B(:,2));
A(6,3)=max(B(:,3)-min(B(:,3));
load ciao_W01_WI07_NOoverlap;
B=ciao_W01_WI07_NOoverlap;
A(7,1)=7
A(7,2)=max(B(:,2)-min(B(:,2));
A(7,3)=max(B(:,3)-min(B(:,3));
load ciao_W01_WI08_NOoverlap;
B=ciao_W01_WI08_NOoverlap;
A(8,1)=8
A(8,2)=max(B(:,2)-min(B(:,2));
A(8,3)=max(B(:,3)-min(B(:,3));
load ciao_W01_WI09_NOoverlap;
B=ciao_W01_WI09_NOoverlap;
A(9,1)=9
A(9,2)=max(B(:,2)-min(B(:,2));
A(9,3)=max(B(:,3)-min(B(:,3));
dlmwrite("stat_diff_W01.dat",A, "\t", "precision", 4);
