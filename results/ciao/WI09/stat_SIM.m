
A=zeros(11,10);
load ciao_W01_WI09_NOoverlap;
A(:,1)=ciao_W01_WI09_NOoverlap(:,1);
A(:,2)=ciao_W01_WI09_NOoverlap(:,3);
load ciao_W02_WI09_NOoverlap;
A(:,3)=ciao_W02_WI09_NOoverlap(:,3);
load ciao_W03_WI09_NOoverlap;
A(:,4)=ciao_W03_WI09_NOoverlap(:,3);
load ciao_W04_WI09_NOoverlap;
A(:,5)=ciao_W04_WI09_NOoverlap(:,3);
load ciao_W05_WI09_NOoverlap;
A(:,6)=ciao_W05_WI09_NOoverlap(:,3);
load ciao_W06_WI09_NOoverlap;
A(:,7)=ciao_W06_WI09_NOoverlap(:,3);
load ciao_W07_WI09_NOoverlap;
A(:,8)=ciao_W07_WI09_NOoverlap(:,3);
load ciao_W08_WI09_NOoverlap;
A(:,9)=ciao_W08_WI09_NOoverlap(:,3);
load ciao_W09_WI09_NOoverlap;
A(:,10)=ciao_W09_WI09_NOoverlap(:,3);
S=statistics(A(:,2:end),1)(1:5,:)';
SS=[[0.1:0.1:0.9]',S];
dlmwrite("stat_SIM_WI09.dat",SS, "\t", "precision", 4);
