
A=zeros(11,10);
load epinions_W04_WI01_NOoverlap;
A(:,1)=epinions_W04_WI01_NOoverlap(:,1);
A(:,2)=epinions_W04_WI01_NOoverlap(:,3);
load epinions_W04_WI02_NOoverlap;
A(:,3)=epinions_W04_WI02_NOoverlap(:,3);
load epinions_W04_WI03_NOoverlap;
A(:,4)=epinions_W04_WI03_NOoverlap(:,3);
load epinions_W04_WI04_NOoverlap;
A(:,5)=epinions_W04_WI04_NOoverlap(:,3);
load epinions_W04_WI05_NOoverlap;
A(:,6)=epinions_W04_WI05_NOoverlap(:,3);
load epinions_W04_WI06_NOoverlap;
A(:,7)=epinions_W04_WI06_NOoverlap(:,3);
load epinions_W04_WI07_NOoverlap;
A(:,8)=epinions_W04_WI07_NOoverlap(:,3);
load epinions_W04_WI08_NOoverlap;
A(:,9)=epinions_W04_WI08_NOoverlap(:,3);
load epinions_W04_WI09_NOoverlap;
A(:,10)=epinions_W04_WI09_NOoverlap(:,3);
S=statistics(A(:,2:end),1)(1:5,:)';
SS=[[0.1:0.1:0.9]',S];
dlmwrite("stat_SIM_W04.dat",SS, "\t", "precision", 4);
