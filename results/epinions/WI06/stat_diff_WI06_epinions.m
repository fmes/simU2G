
A=zeros(9,3);
load epinions_W01_WI06_NOoverlap;
B=epinions_W01_WI06_NOoverlap;
A(1,1)=0.1;
A(1,2)=(max(B(:,2))-min(B(:,2)))/max(B(:,2));
A(1,3)=(max(B(:,3))-min(B(:,3)))/max(B(:,3)); ;
load epinions_W02_WI06_NOoverlap;
B=epinions_W02_WI06_NOoverlap;
A(2,1)=0.2;
A(2,2)=(max(B(:,2))-min(B(:,2)))/max(B(:,2));
A(2,3)=(max(B(:,3))-min(B(:,3)))/max(B(:,3)); ;
load epinions_W03_WI06_NOoverlap;
B=epinions_W03_WI06_NOoverlap;
A(3,1)=0.3;
A(3,2)=(max(B(:,2))-min(B(:,2)))/max(B(:,2));
A(3,3)=(max(B(:,3))-min(B(:,3)))/max(B(:,3)); ;
load epinions_W04_WI06_NOoverlap;
B=epinions_W04_WI06_NOoverlap;
A(4,1)=0.4;
A(4,2)=(max(B(:,2))-min(B(:,2)))/max(B(:,2));
A(4,3)=(max(B(:,3))-min(B(:,3)))/max(B(:,3)); ;
load epinions_W05_WI06_NOoverlap;
B=epinions_W05_WI06_NOoverlap;
A(5,1)=0.5;
A(5,2)=(max(B(:,2))-min(B(:,2)))/max(B(:,2));
A(5,3)=(max(B(:,3))-min(B(:,3)))/max(B(:,3)); ;
load epinions_W06_WI06_NOoverlap;
B=epinions_W06_WI06_NOoverlap;
A(6,1)=0.6;
A(6,2)=(max(B(:,2))-min(B(:,2)))/max(B(:,2));
A(6,3)=(max(B(:,3))-min(B(:,3)))/max(B(:,3)); ;
load epinions_W07_WI06_NOoverlap;
B=epinions_W07_WI06_NOoverlap;
A(7,1)=0.7;
A(7,2)=(max(B(:,2))-min(B(:,2)))/max(B(:,2));
A(7,3)=(max(B(:,3))-min(B(:,3)))/max(B(:,3)); ;
load epinions_W08_WI06_NOoverlap;
B=epinions_W08_WI06_NOoverlap;
A(8,1)=0.8;
A(8,2)=(max(B(:,2))-min(B(:,2)))/max(B(:,2));
A(8,3)=(max(B(:,3))-min(B(:,3)))/max(B(:,3)); ;
load epinions_W09_WI06_NOoverlap;
B=epinions_W09_WI06_NOoverlap;
A(9,1)=0.9;
A(9,2)=(max(B(:,2))-min(B(:,2)))/max(B(:,2));
A(9,3)=(max(B(:,3))-min(B(:,3)))/max(B(:,3)); ;
mx=max(A(:,3));
for i=1:9; A(i,3)=mx; endfor
dlmwrite("stat_diff_WI06.dat",A, "\t", "precision", 4);
