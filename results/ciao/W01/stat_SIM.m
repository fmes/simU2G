
A=zeros(11,10);
load ciao_W01_WI01_NOoverlap;
A(:,1)=ciao_W01_WI01_NOoverlap(:,1);
A(:,2)=ciao_W01_WI01_NOoverlap(:,3);
load ciao_W01_WI02_NOoverlap;
A(:,3)=ciao_W01_WI02_NOoverlap(:,3);
load ciao_W01_WI03_NOoverlap;
A(:,4)=ciao_W01_WI03_NOoverlap(:,3);
load ciao_W01_WI04_NOoverlap;
A(:,5)=ciao_W01_WI04_NOoverlap(:,3);
load ciao_W01_WI05_NOoverlap;
A(:,6)=ciao_W01_WI05_NOoverlap(:,3);
load ciao_W01_WI06_NOoverlap;
A(:,7)=ciao_W01_WI06_NOoverlap(:,3);
load ciao_W01_WI07_NOoverlap;
A(:,8)=ciao_W01_WI07_NOoverlap(:,3);
load ciao_W01_WI08_NOoverlap;
A(:,9)=ciao_W01_WI08_NOoverlap(:,3);
load ciao_W01_WI09_NOoverlap;
A(:,10)=ciao_W01_WI09_NOoverlap(:,3);
S=statistics(A(:,2:end),1)(1:5,:)';
SS=[[0.1:0.1:0.9]',S];
dlmwrite("stat_SIM_W01.dat",SS, "\t", "precision", 4);
