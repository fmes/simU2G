
A=zeros(11,10);
load epinions_W09_WI01_NOoverlap;
A(:,1)=epinions_W09_WI01_NOoverlap(:,1);
A(:,2)=epinions_W09_WI01_NOoverlap(:,2);
load epinions_W09_WI02_NOoverlap;
A(:,3)=epinions_W09_WI02_NOoverlap(:,2);
load epinions_W09_WI03_NOoverlap;
A(:,4)=epinions_W09_WI03_NOoverlap(:,2);
load epinions_W09_WI04_NOoverlap;
A(:,5)=epinions_W09_WI04_NOoverlap(:,2);
load epinions_W09_WI05_NOoverlap;
A(:,6)=epinions_W09_WI05_NOoverlap(:,2);
load epinions_W09_WI06_NOoverlap;
A(:,7)=epinions_W09_WI06_NOoverlap(:,2);
load epinions_W09_WI07_NOoverlap;
A(:,8)=epinions_W09_WI07_NOoverlap(:,2);
load epinions_W09_WI08_NOoverlap;
A(:,9)=epinions_W09_WI08_NOoverlap(:,2);
load epinions_W09_WI09_NOoverlap;
A(:,10)=epinions_W09_WI09_NOoverlap(:,2);
S=statistics(A(:,2:end),1)(1:5,:)';
SS=[[0.1:0.1:0.9]',S];
dlmwrite("stat_COMP_W09.dat",SS, "\t", "precision", 4);
