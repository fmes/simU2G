
A=zeros(11,10);
load epinions_W01_WI06_NOoverlap;
A(:,1)=epinions_W01_WI06_NOoverlap(:,1);
A(:,2)=epinions_W01_WI06_NOoverlap(:,3);
load epinions_W02_WI06_NOoverlap;
A(:,3)=epinions_W02_WI06_NOoverlap(:,3);
load epinions_W03_WI06_NOoverlap;
A(:,4)=epinions_W03_WI06_NOoverlap(:,3);
load epinions_W04_WI06_NOoverlap;
A(:,5)=epinions_W04_WI06_NOoverlap(:,3);
load epinions_W05_WI06_NOoverlap;
A(:,6)=epinions_W05_WI06_NOoverlap(:,3);
load epinions_W06_WI06_NOoverlap;
A(:,7)=epinions_W06_WI06_NOoverlap(:,3);
load epinions_W07_WI06_NOoverlap;
A(:,8)=epinions_W07_WI06_NOoverlap(:,3);
load epinions_W08_WI06_NOoverlap;
A(:,9)=epinions_W08_WI06_NOoverlap(:,3);
load epinions_W09_WI06_NOoverlap;
A(:,10)=epinions_W09_WI06_NOoverlap(:,3);
S=statistics(A(:,2:end),1)(1:5,:)';
SS=[[0.1:0.1:0.9]',S];
dlmwrite("stat_SIM_WI06.dat",SS, "\t", "precision", 4);
