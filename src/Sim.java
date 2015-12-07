 import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Random;
import java.io.*;
 
    class SN {
    public static Integer sWindows;
    public static int numCategory;
    public static int numBehaviours;
    public static int numUsers;
    public static int numGroups;	//numero di gruppi da usare nelle simulazioni random
    public static int KMAX;	//numero massimo di membri di un gruppo
    public static int NMAX;	//numero massimo di gruppi a cui un utente puo' associarsi
    public static int NREQ;	//numero massimo di richieste che un utente puo' fare a altri gruppi
    public static int KMIN;	//numero minimo di membri di un gruppo
    public static int NMIN;	//numero minimo di gruppi a cui un utente puo' associarsi
    public static int coldStartRel;
    public static int coldStartRep;
    public static int coldStartSim;
    //U2G-O
     
	// i coefficienti alfa e delta sono simulati cambiando
	//ad ogni epoca in modo random gli interessi, secondo la seguente formula
	//new_int=old_int+(sign)*delta*rand
	//essendo sign generato random (+1 o -1) e delta un coefficiente che limita la variazione
    public static double delta;	//attualmente non utilizzato
    public static double alfa;	//pesa l'importanza della reliability vs la reputation
    public static int psi;	//attualmente non usato
    public static double tau;
    public static int eta;	//attualmente non usato
    public static double pi;
    public static double WI;	//valore di default
    public static double WA;	//valore di default
    public static double WB;	//valore di default
    public static double WD;	//valore di default
    public static int RATING[][];	//matrice che contiene il file dei rating
    public static int ACCESS[];	//array che contiene le preferenze sugli accessi
    public static int numRowsRating = -1;	//numero di righe della matrice di rating da prendere in considerazione - 1
    public static int maxRowsRating = -1;	//numero effettivo di righe della matrice dei rating
    public ArrayList < User > users;
    public ArrayList < Group > groups;
    public char rel[][];
    public SN(ArrayList < User > u, ArrayList < Group > g, char[][]r) {
	users = new ArrayList < User > (u);
	groups = new ArrayList < Group > (g);
	rel = new char[r.length][r.length];
	for (int i = 0; i < r.length; i++)
	    for (int j = 0; j < r.length; j++)
		rel[i][j] = r[i][j];
    } public int getNumUsers() {
	return numUsers;
    }
    public int getNumGroups() {
	return groups.size();
    }
    public char[][] getRel() {
	return rel;
    }
    
	//il metodo rel(u,v) restituisce la reliability normalizzata di v rispetto ad u
    public double rel(int u, int v) {
	return rel[u][v] / 255;
    }
    public void stampa(PrintStream p) {
	
	    //for(int i=0;i<users.size();i++)
	    //      users.get(i).stampa(p);
	    for (int i = 0; i < groups.size(); i++)
	     {
	    p.println("gruppo " + i);
	    p.println("numero membri:" +
		       groups.get(i).getMembers().size());
	    for (int j = 0; j < groups.get(i).getMembers().size(); j++)
		 {
		if (groups.get(i).getMembers().size() != 0)
		    p.println("membro #" + j + ":" +
			      groups.get(i).getMembers().get(j).getPos());
		}
	    }
    }
     
	//sezione aggiunta per IDCS
    public int dist(int a, int b) {
	
	    //calcola la distanza, in termini di archi, tra il nodo a e il nodo b nel grafo del trust
	    //fermandosi ad una profondita' pari a 3
	    //se il nodo b non e' raggiungibile da a in al piu' due passi, per convenzione restituisce 4;
	    if (a == b)
	    return 0;
	if (rel[a][b] != 0)
	    return 1;
	for (int v = 1; v < rel[0].length; v++)
	    if ((rel[a][v] != 0) && (v != b) && (rel[v][b] != 0))
		return 2;
	for (int v = 1; v < rel[0].length; v++)
	    for (int z = 1; z <= rel[0].length; z++)
		if ((rel[a][v] != 0) && (v != b) && (z != b)
		     && (rel[v][z] != 0) && (rel[z][b] != 0))
		    return 3;
	 return 4;
    }
     public double recommendation(int a, int b) {
	return rel(a, b) / dist(a, b);
    }
     public double st(int a, int b) {
	
	    //calcola la local reputation tra il nodo a e il nodo b nel grafo del trust
	    //fermandosi ad una profondita' pari a 2
	    //se b non e' conosciuto da a, ne' dai suoi amici di primo e secondo livello
	    //il risultato restituito e' 0
	    //e' definita per b diverso da a, e per rel(a,b)==0
	double s = 0;
	int cont = 0;
	for (int v = 1; v < rel[0].length; v++)
	    if ((rel[a][v] != 0) && (v != b)) {
		if (rel[v][b] != 0)
		    s = s + 1;
		cont++;
	    }
	for (int v = 1; v < rel[0].length; v++)
	    for (int z = 1; z < rel[0].length; z++)
		if ((rel[a][v] != 0) && (v != b) && (z != b)
		     && (rel[v][z] != 0)) {
		    if ((rel[z][b] != 0))
			s = s + rel(z, b) / 2;
		    cont++;
		}
	
	    //System.out.println(cont);
	    return s / cont;
    }
      public double dim_loc(int a, int b) {
	
	    //calcola la numerosita' della local network tra il nodo a e il nodo b nel grafo del trust
	    //fermandosi ad una profondita' pari a 2
	    //se b non e' conosciuto da a, ne' dai suoi amici di primo e secondo livello
	    //il risultato restituito e' 0
	    //e' definita per b diverso da a, e per rel(a,b)==0
	int cont = 0;
	for (int v = 1; v < rel[0].length; v++)
	    if ((rel[a][v] != 0) && (v != b)) {
		cont++;
	    }
	for (int v = 1; v < rel[0].length; v++)
	    for (int z = 1; z < rel[0].length; z++)
		if ((rel[a][v] != 0) && (v != b) && (z != b)
		     && (rel[v][z] != 0)) {
		    cont++;
		}
	
	    //System.out.println(cont);
	    return cont;
    }
      public double ast(int a) {
	int sum = 0;
	
	    //System.out.println(a);
	    for (int i = 1; i < numUsers; i++) {
	    sum += st(i, a);
	   } return sum;
    }
     public double helpfulness(int a) {
	double sum = 0, cont = 0;
	
	    //System.out.println(a);
	    for (int i = 0; i < numRowsRating; i++) {
	    if (RATING[i][0] == a) {
		sum += SN.RATING[i][4];
		cont++;
	    }
	 }
	if (cont != 0)
	    return sum / (5 * cont);
	else
	    return 0;
    }
     
	///////////////////////////////////////////////////////////////////////////////
	/* END IDCS */ 
     public double userDissimilarity(int a, int b) {
//	System.out.println("userDissimilarity(), "+a+", "+b);
	    //a e b sono indici di user nella lista degli users
	double CI = 0;
	int CA;
	double CB = 0;
	double d;
  
	//If we have no data about users interests, the dissimilarity will assum the cold start value
/*
	int noDataI=1;
	for (int i = 0; i < SN.numCategory; i++)
	  if(users.get(a).getProfile().getInterests()[i] != 0 || users.get(b).getProfile().getInterests()[i] != 0){
	      noDataI=0;
	      break;
	  }

	if(noDataI==1)
	    CI = 1-SN.coldStartSim;	

	else{*/
	  for (int i = 0; i < SN.numCategory; i++)
	    CI += Math.abs(users.get(a).getProfile().getInterests()[i] -
			 users.get(b).getProfile().getInterests()[i]);

	    CI = CI / SN.numCategory;
//	}

	if (users.get(a).getProfile().getAccess() ==
	     users.get(b).getProfile().getAccess())
	    CA = 0;
	
	else
	    CA = 1;
	for (int i = 0; i < SN.numBehaviours; i++)
	    if (users.get(a).getProfile().getBehaviours()[i] !=
		 users.get(b).getProfile().getBehaviours()[i])
		CB++;
	CB = CB / SN.numBehaviours;
	
	
	//calcola d con i parametri di default uguali per tutti gli users
	d = WI * CI + WA * CA + WB * CB;
	
	//calcola d con i parametri propri dello user a
	//double wi=users.get(a).getWI(),wa=users.get(a).getWA(),wb=users.get(a).getWB();
	//d=wi*CI+wa*CA+wb*CB;
	return d;
    }
      public double groupDissimilarity(int a, int b) {
	
	    //a e' l'indice di uno user nella lista degli users
	    //b e' l'indice di un gruppo nella lista dei gruppi
	double CI = 0;
	int CA;
	double CB = 0;
	double d;
	for (int i = 0; i < SN.numCategory; i++)
	    CI +=
		Math.abs(users.get(a).getProfile().getInterests()[i] -
			 groups.get(b).getProfile().getInterests()[i]);
	CI = CI / SN.numCategory;
	if (users.get(a).getProfile().getAccess() ==
	     groups.get(b).getProfile().getAccess())
	    CA = 0;
	
	else
	    CA = 1;
	for (int i = 0; i < SN.numBehaviours; i++)
	    if (users.get(a).getProfile().getBehaviours()[i] !=
		 groups.get(b).getProfile().getBehaviours()[i])
		CB++;
	CB = CB / SN.numBehaviours;
	d = WI * CI + WA * CA + WB * CB;
	return d;
    }
       public ArrayList < Member > groups(int u) {
	
	    //restituisce un arraylist contenente le posizioni nell'array dei groups
	    //dei gruppi di cui l'utente u e' membro
	    //essendo u l'indice che identifica la posizione di u nell'arrayList degli users
	    ArrayList < Member > risultato = new ArrayList < Member > ();
	Group g;
	Member m;
	for (int i = 0; i < groups.size(); i++) {
	    g = groups.get(i);
	    for (int j = 0; j < g.getMembers().size(); j++)
		if (g.getMembers().get(j).getPos() == u) {
		    m = new Member(i);
		    risultato.add(m);
		 }
	}
	return risultato;
      }
     
	//calcola le reputazioni di tutti gli user e le assegna al campo
	//reputation. Se uno user non ha ricevuto nessuno valutazione di reliability
	//la sua reputation e' convenzionalmente posta a -1
    public void computeReputation() {
	int rep;
	for (int i = 0; i < users.size(); i++) {
	    rep = 0;
	    for (int j = 0; j < users.size() && j != i; j++)
		rep += rel(j, i);
	    rep = rep / (users.size() - 1);
	    users.get(i).setRep(rep);
    } }  public double u2u_trust(int u, int v) {
	
	    //calcola la reputation col valore alfa di default uguale per tutti gli user
	    return ((alfa * rel(u, v) +
		     (1 - alfa) * users.get(v).getRep()) / 100);
	
	    //calcola la reputation col valore alfa personale dello user u
	    //double a=users.get(u).getAlfa();
	    //return((a*rel(u,v)+(1-a)*users.get(v).getRep())/100);
    }
     public double u2g_trust(int u, int g) {
	
	    // calcola il valore di trust (normalizzato tra 0 e 1)
	    // tra un utente la cui posizione nell'array degli users e' "u"
	    // e un gruppo la cui posizione nell'array dei gruppi e' "g"
	double trust = 0;
	int v;
	for (int i = 0; i < groups.get(g).getMembers().size(); i++) {
	    v = groups.get(g).getMembers().get(i).getPos();
	    trust += u2u_trust(u, v);
	} if (groups.get(g).getMembers().size() != 0)
	    trust = trust / groups.get(g).getMembers().size();
	
	else
	    trust = coldStartRel;	//l'utente non ha informazioni sulla fiducia da dare a un gruppo vuoto
	return (trust);
    }
     public double g2u_trust(int g, int u) {
	
	    // calcola il valore di trust (normalizzato tra 0 e 1)
	    // tra un gruppo la cui posizione nell'array dei gruppi e' "g"
	    // e uno user la cui posizione nell'array degli user e' "u"
	double trust = 0;
	int v;
	for (int i = 0; i < groups.get(g).getMembers().size(); i++) {
	    v = groups.get(g).getMembers().get(i).getPos();
	    trust += u2u_trust(v, u);
	} if (groups.get(g).getMembers().size() != 0)
	    trust = trust / groups.get(g).getMembers().size();
	
	else
	    trust = users.get(u).getRep();	// se g e' vuoto, allora assegna al trust di u la reputazione di u            
	return (trust);
    }
      
      //Compactness  - w*sim + (1-w)*trust
     public double u2u_cohesion(int u, int v) {
	
	    // calcola il valore di cohesion (normalizzato tra 0 e 1)
	    // tra due utenti le cui posizioni nell'array degli users sono "u" e "v"
	    //usando il parametro WD di default
	    return (WD * (1 - userDissimilarity(u, v)) + 
	      (1 - WD) * u2u_trust(u, v));
	
	    //usando il parametro WD personale di u
	    //double w=users.get(u).getWD();
	    //return(w*(1-userDissimilarity(u,v))+(1-w)*u2u_trust(u,v));
    }
     public double u2g_cohesion(int u, int g) {
	
	    // calcola il valore di cohesion (normalizzato tra 0 e 1)
	    // tra un utente la cui posizione nell'array degli users e' "u"
	    // e un gruppo la cui posizione nell'array dei gruppi e' "g"
	    
	    //controllo
	    //usando il parametro WD di default
	    return (WD * (1 - groupDissimilarity(u, g)) +
		    (1 - WD) * u2g_trust(u, g));
	
	    //usando il parametro WD personale di u
	    //double w=users.get(u).getWD();
	    //return(w*(1-groupDissimilarity(u,g))+(1-w)*u2g_trust(u,g));
    }
     public double g2u_cohesion(int g, int u) {
	
	    // calcola il valore di cohesion (normalizzato tra 0 e 1)
	    // tra un gruppo la cui posizione nell'array dei gruppi e' "g"
	    // e uno user la cui posizione nell'array degli user e' "u"
	    return (WD * (1 - groupDissimilarity(u, g)) +
		    (1 - WD) * g2u_trust(g, u));
    }
      public double cohesion(int g) {
	double c = 0;
	int n = groups.get(g).getMembers().size();
	int cont = 0;
	for (int j = 0; j < n; j++)
	    for (int k = 0; k < n; k++)
		if (groups.get(g).getMembers().get(j).getPos() !=
		     groups.get(g).getMembers().get(k).getPos()) {
		    c +=
			u2u_cohesion(groups.get(g).getMembers().get(j).
				     getPos(),
				     groups.get(g).getMembers().get(k).
				     getPos());
		    cont++;
		}
	if (cont != 0)
	    c = c / cont;
	 
	    //System.out.println(MAC);
	    return (c);
    }

      //Mean Average Dissimilarity 
      public double MAD(int epoc, String key) {
	//System.out.println("MAD() -- " + key);
	double MAD = 0;
	for (int i = 0; i < groups.size(); i++) {
	    double AD = 0;
	    int n = groups.get(i).getMembers().size();
	    int cont = 0;
	    for (int j = 0; j < n; j++)
		for (int k = 0; k < n; k++)
		    if (groups.get(i).getMembers().get(j).getPos() !=
			 groups.get(i).getMembers().get(k).getPos()) {
			AD +=
			    userDissimilarity(groups.get(i).getMembers().
					      get(j).getPos(),
					      groups.get(i).getMembers().
					      get(k).getPos());
			cont++;
		    }
	    if (cont != 0)
		AD = AD / cont; // AD of a group
	    //System.out.print(AD+"\t");
	    MAD += AD;
	 }
//	System.out.println();
	return (MAD / groups.size());
    }

    //Dissimilarity - Mean square error
    public double DAD(int epoca, String key) {
	double D = 0;
	double MAD = MAD(epoca, key);
	for (int i = 0; i < groups.size(); i++) {
	    //System.out.println("DAD(), " + i);
	    double AD = 0;
	    int n = groups.get(i).getMembers().size();
	    int cont = 0;
	    for (int j = 0; j < n; j++)
		for (int k = 0; k < n; k++)
		    if (groups.get(i).getMembers().get(j).getPos() !=
			 groups.get(i).getMembers().get(k).getPos()) {
			AD +=
			    userDissimilarity(groups.get(i).getMembers().
					      get(j).getPos(),
					      groups.get(i).getMembers().
					      get(k).getPos());
			cont++;
		    }
	    if (cont != 0)
		AD = AD / cont;
	    D += (AD - MAD) * (AD - MAD);
	}
	return (Math.sqrt(D / (groups.size())));
    }

    //Similarity - Mean
    public double MAS(int epoca, String key) {
	return 1-MAD(epoca, key);
    }

    //Medium Average Compactness 
     public double MAC() {
	double MAC = 0;
	for (int i = 0; i < groups.size(); i++) {
	    //System.out.println("MAC(), " + i);
	    double AC = 0;
	    int n = groups.get(i).getMembers().size();
	    int cont = 0;
	    for (int j = 0; j < n; j++)
	      for (int k = 0; k < n; k++)
		  if (groups.get(i).getMembers().get(j).getPos() !=
		      groups.get(i).getMembers().get(k).getPos()) {
			AC +=
			    u2u_cohesion(groups.get(i).getMembers().get(j).
					 getPos(),
					 groups.get(i).getMembers().get(k).
					 getPos());
			cont++;
		    }
	    if (cont != 0)
		AC = AC / cont;
	    MAC += AC;
	    
		//System.out.println(MAC);
	}
	 return (MAC / groups.size());
    }

     public double DAC() {
	double D = 0;
	double MAC = MAC();
	for (int i = 0; i < groups.size(); i++) {
	    double AC = 0;
	    int n = groups.get(i).getMembers().size();
	    int cont = 0;
	    for (int j = 0; j < n; j++)
		for (int k = 0; k < n; k++)
		    if (groups.get(i).getMembers().get(j).getPos() !=
			 groups.get(i).getMembers().get(k).getPos()) {
			AC +=
			    u2u_cohesion(groups.get(i).getMembers().get(j).
					 getPos(),
					 groups.get(i).getMembers().get(k).
					 getPos());
			cont++;
		    }
	    if (cont != 0)
		AC = AC / cont;
	    D += (AC - MAC) * (AC - MAC);
	}
	return (Math.sqrt(D / (groups.size())));
    }
      public double Pearson() {
	double mx = 0, my = 0;
	double cov = 0, sx = 0, sy = 0;
	for (int i = 0; i < users.size(); i++) {
	    for (int j = 0; j < users.size(); j++) {
		mx += (1 - userDissimilarity(i, j));
		my += u2u_trust(i, j);
	} } mx = mx / (users.size() * users.size());
	my = my / (users.size() * users.size());
	for (int i = 0; i < users.size(); i++) {
	    for (int j = 0; j < users.size(); j++) {
		 cov +=
		    ((1 - userDissimilarity(i, j)) -
		     mx) * (u2u_trust(i, j) - my);
		sy += (u2u_trust(i, j) - my) * (u2u_trust(i, j) - my);
		sx +=
		    ((1 - userDissimilarity(i, j)) -
		     mx) * ((1 - userDissimilarity(i, j)) - mx);
	}  } cov = cov / (users.size() * users.size());
	sx = Math.sqrt(sx / (users.size() * users.size()));
	sy = Math.sqrt(sy / (users.size() * users.size()));
	return (cov / (sx * sy));
    }
   }

 class Profile {
    private double[] interests;
    private int access;	//0=OPEN;1=CLOSED;2=SECRET
    private boolean[] behaviours;
    public Profile() {
    } 

    public Profile(double[]in, int a, boolean[]b) {
	interests = new double[SN.numCategory];
	for (int i = 0; i < SN.numCategory; i++) {
	    interests[i] = in[i];
	} access = a;
	behaviours = new boolean[SN.numBehaviours];
	for (int i = 0; i < SN.numBehaviours; i++) {
	    behaviours[i] = b[i];
    }  } 

    public double[] getInterests() {
	return interests;
    }
    public int getAccess() {
	return access;
    }
    public boolean[]getBehaviours() {
	return behaviours;
    }
    public void createRandom() {
	Random random = new Random();
	interests = new double[SN.numCategory];
	behaviours = new boolean[SN.numBehaviours];
	for (int i = 0; i < SN.numCategory; i++)
	    interests[i] = random.nextDouble();
	access = random.nextInt(2);
	for (int i = 0; i < SN.numBehaviours; i++)
	    behaviours[i] = random.nextBoolean();
     }


    //crea un profilo per l'utente k ricavandolo dal file dei rating
    public void createFromFile(int k) {
	 int numRating = 0, sumRating = 0, sumHelp = 0;
	interests = new double[SN.numCategory + 1];
	behaviours = new boolean[SN.numBehaviours];
	access = SN.ACCESS[k];
	
	//Compute Interest for user k
	for (int i = 0; i < SN.numCategory; i++)
	    interests[i] = 0;

	for (int i = 0; i < SN.numRowsRating; i++)
	    if (SN.RATING[i][0] == k) {
		interests[SN.RATING[i][2]]++;
		numRating++;
		sumRating += SN.RATING[i][3];
		sumHelp += SN.RATING[i][4];
	     }
	
	for (int i = 0; i < SN.numCategory; i++)
	    if (numRating != 0) {
		interests[i] = interests[i] / numRating;
	     }
	  
	    //behaviour 1: dare ai prodotti un ranking medio superiore a 3
	    //behaviour 2: avere una helpfulness dei propri rating superiore a 3
	if (numRating > 0 && sumRating / numRating > 3)
	    behaviours[0] = true;
	if (numRating > 0 && sumHelp / numRating > 3)
	    behaviours[1] = true;
	
	/*if(k==10){ // random k 
	  System.out.println("User "+k);
	  //stampa(new PrintStream(System.out));
	}*/
    }

     public void stampa(PrintStream p) {
	for (int i = 0; i < SN.numCategory; i++) {
	    p.println("int. cat. " + i + "=" + interests[i]);
	} p.println("mod. accesso=" + access);
	for (int i = 0; i < SN.numBehaviours; i++) {
	    p.println("behav. cat. " + i + "=" + behaviours[i]);
} }  }  

    class User {

    private Profile profile;
    
	//il campo rep rappresenta la reputation dello user nella SN
	//puo' essere modificato solo attraverso il metodo setRep()
    private int rep;
    
	//private int WI;
	//private int WA;
	//private int WB;
	//private int WD;
	//private int alfa;
	public User() {
    } public User(Profile p, int wi, int wa, int wb, int wd, int a) {
	profile = p;
	
	    //WI=wi; WA=wa;WB=wb; WD=wd; alfa=a;
    } public Profile getProfile() {
	return profile;
    }
    
	//public double getWI(){
	//      return WI/100;
	//}
	
	//public double getWA(){
	//      return WA/100;
	//}
	
	//public double getWB(){
	//      return WB/100;
	//}
	
	//public double getWD(){
	//      return WD/100;
	//}
	
	//public double getAlfa(){
	//      return alfa/100;
	//}
    public int getRep() {
	return rep;
    }
    public void setRep(int r) {
	rep = r;
    } public void createRandom() {
	profile = new Profile();
	profile.createRandom();
	
	    //Random rand=new Random();
	    //assegna a WI un valore random compreso tra 40 e 60
	    //WI=40+rand.nextInt(20);
	    //assegna a WA e WB il valore (1-WI)/2;
	    //WA=(100-WI)/2;
	    //WB=100-WI-WA;
	    //assegna a WD un valore compreso tra 0 e 100
	    //WD=rand.nextInt(100);
	    //assegna ad alfa un valore compreso tra 0 e 100
	    //alfa=rand.nextInt(100);
    }  public void createFromFile(int k) {
	profile = new Profile();
	profile.createFromFile(k);
     }   public void stampa(PrintStream p) {
	profile.stampa(p);
}  }  

//modella la membership. 
//il campo pos rappresenta la posizione del membro nell'array degli user
    class Member {
    private int pos;
    public Member() {
    } public Member(int p) {
	pos = p;
	
	    //rel=r;
    } public int getPos() {
	return pos;
    }
}

 class Group {
    private Profile profile;
    private ArrayList < Integer > requests;
    private ArrayList < Member > members;
    public Group() {
	requests = new ArrayList < Integer > ();
	members = new ArrayList < Member > ();
    }
    public Group(Profile p, ArrayList < Integer > r,
		  ArrayList < Member > m) {
	profile = p;
	requests = new ArrayList < Integer > (r);
	members = new ArrayList <> (m);
     }
    public Profile getProfile() {
	return profile;
    }
    public ArrayList < Integer > getRequests() {
	return requests;
    }
    public ArrayList < Member > getMembers() {
	return members;
    }
    public void createRandom(int epoca) {
	members = new ArrayList < Member > ();
	Random rand = new Random();
	int numMembers = SN.KMAX / 2 + rand.nextInt(SN.KMAX / 2);
	for (int i = 0; i < numMembers; i++)
	    members.add(new Member(rand.nextInt(SN.numUsers - 1)));
	 profile = new Profile();
	profile.createRandom();
	requests = new ArrayList < Integer > ();
     } public void createSingletonRandom(Member m) {
	members = new ArrayList < Member > ();
	members.add(m);
	profile = new Profile();
	profile.createRandom();
	requests = new ArrayList < Integer > ();
    }   public double Pearson(SN s) {
	if (members.size() == 0)
	    return 0;
	double mx = 0, my = 0;
	double cov = 0, sx = 0, sy = 0;
	for (int i = 0; i < members.size(); i++) {
	    for (int j = 0; j < members.size(); j++) {
		mx +=
		    (1 -
		     s.userDissimilarity(members.get(i).getPos(),
					 members.get(j).getPos()));
		my +=
		    s.u2u_trust(members.get(i).getPos(),
				members.get(j).getPos());
	} } mx = mx / (members.size() * members.size());
	my = my / (members.size() * members.size());
	for (int i = 0; i < members.size(); i++) {
	    for (int j = 0; j < members.size(); j++) {
		 cov +=
		    (s.
		     userDissimilarity(members.get(i).getPos(),
				       members.get(j).getPos()) -
		     mx) * (s.u2u_trust(members.get(i).getPos(),
					members.get(j).getPos()) - my);
		sy +=
		    (s.
		     u2u_trust(members.get(i).getPos(),
			       members.get(j).getPos()) -
		     my) * (s.u2u_trust(members.get(i).getPos(),
					members.get(j).getPos()) - my);
		sx +=
		    (s.
		     userDissimilarity(members.get(i).getPos(),
				       members.get(j).getPos()) -
		     mx) * (s.userDissimilarity(members.get(i).getPos(),
						members.get(j).getPos()) -
			    mx);
	}  } cov = cov / (members.size() * members.size());
	sx = Math.sqrt(sx / (members.size() * members.size()));
	sy = Math.sqrt(sy / (members.size() * members.size()));
	return (cov / (sx * sy));
    }
      public void stampa(PrintStream p) {
	profile.stampa(p);
      } 
    }    

    public class Sim {

    private static String dataset = System.getProperty("dataset");
//ciao";
    private final static String fileRating = dataset + "-rating-sorted.txt";
    private final static String fileTrust = dataset + "-trust.txt";
    private final static String fileConfig = dataset + "-config.txt";
     
	//genera una SN caricandola da dataset reale per l'epoca zero
	//e lasciando vuoti gruppi e users
    public static SN InitFile() throws FileNotFoundException {
	
	    //inizializza i parametri statici della classe SN
	    // ed i valori di trust
	    //con i valori letti da file
	Scanner reader = new Scanner(new FileInputStream(fileConfig));
	
	SN.sWindows = Integer.parseInt(System.getProperty("sw"));
	    //Lettura del file di configurazione
	SN.numBehaviours = (int) Double.parseDouble(reader.next());
	SN.numGroups = (int) Double.parseDouble(reader.next());
	SN.KMAX = (int) Double.parseDouble(reader.next());	//numero massimo di membri di un gruppo
	SN.NMAX = (int) Double.parseDouble(reader.next());	//numero massimo di gruppi a cui un utente puo' associarsi
	SN.NREQ = (int) Double.parseDouble(reader.next());	//numero massimo di richieste che un utente puo' fare ad altri gruppi
	SN.coldStartRel = (int) Double.parseDouble(reader.next());
	SN.coldStartRep = (int) Double.parseDouble(reader.next());
	SN.delta = Double.parseDouble(reader.next());
	SN.alfa = Double.parseDouble(reader.next());
	SN.psi = (int) Double.parseDouble(reader.next());
	SN.tau = Double.parseDouble(reader.next());
	SN.eta = (int) Double.parseDouble(reader.next());
	SN.pi = Double.parseDouble(reader.next());
	SN.WI = Double.parseDouble(reader.next());
	SN.WA = Double.parseDouble(reader.next());
	SN.WB = Double.parseDouble(reader.next());
	SN.WD = Double.parseDouble(reader.next());
	SN.KMIN = (int) Double.parseDouble(reader.next());	//numero minimo di membri di un gruppo
	SN.NMIN = (int) Double.parseDouble(reader.next());	//numero minimo di gruppi a cui un utente puo' associarsi
	SN.coldStartSim = (int) Double.parseDouble(reader.next());
	
	//Fine lettura file di configurazione
	reader.close();
	System.out.println("Dataset: "+ dataset+"file: "+ fileRating+ ", sw: "+ SN.sWindows);
	reader = new Scanner(new FileInputStream(fileRating));
	int numUsers, numProducts = 0, numCategory = 0;
	
	//legge il file dei ratings, determina numUsers, numCategories, maxRowsRating
	int a = 0, b, c = 0, d, e, f, cont = 0;
	 while (reader.hasNext()) {
	    a = (int) Double.parseDouble(reader.next()) - 1;
	    b = (int) Double.parseDouble(reader.next());
	    if (b > numProducts)
		numProducts = b;
	    c = (int) Double.parseDouble(reader.next()) - 1;
	    if (c > numCategory)
		numCategory = c;
	    d = (int) Double.parseDouble(reader.next());
	    e = (int) Double.parseDouble(reader.next());
	    f = (int) Double.parseDouble(reader.next());
	    cont++;
	} reader.close();
	reader = new Scanner(new FileInputStream(fileRating));
	SN.numUsers = a + 1;
	SN.numRowsRating = SN.maxRowsRating = cont;
	SN.numCategory = numCategory + 1;
	SN.RATING = new int[SN.numRowsRating][5];
	Random random = new Random();
	SN.ACCESS = new int[SN.numUsers + 1];
	for (int i = 1; i <= SN.numUsers; i++)
	    SN.ACCESS[i] = random.nextInt(2);
	reader.close();
	
	//caricamento del file delle reliability
	char[][] r = new char[SN.numUsers + 1][SN.numUsers + 1];
	for (int i = 0; i <= SN.numUsers; i++)
	    for (int j = 0; j <= SN.numUsers; j++)
		r[i][j] = 0;
	
	    //System.out.println(SN.numUsers);
	    reader = new Scanner(new FileInputStream(fileTrust));
	int x, y;
	while (reader.hasNext()) {
	    x = (int) Double.parseDouble(reader.next());
	    y = (int) Double.parseDouble(reader.next());
	    
		//System.out.println("x="+x+" y="+y);
		if ((x <= SN.numUsers) && (y <= SN.numUsers))
		r[x][y] = 255;
	}
	 reader.close();
	  
	    //lascia vuoti gli array degli users e dei groups       
	    ArrayList < User > users = new ArrayList < User > ();
	ArrayList < Group > groups = new ArrayList < Group > ();
	 return (new SN(users, groups, r));
    }
     public static SN InitTraining(int end) throws FileNotFoundException{
	    //end rappresenta l'ultima riga da leggere del training set
	    //la struttura dei gruppi sara' random
	Scanner reader = new Scanner(new FileInputStream(fileRating));
	SN.numRowsRating = end;
	SN.RATING = new int[SN.numRowsRating][5];
	 int cont = 0;
	 int a, b, c, d, e, f;
	 while (reader.hasNext() && (cont < end)) {
	    a = (int) Double.parseDouble(reader.next()) - 1;
	    SN.RATING[cont][0] = a;
	    b = (int) Double.parseDouble(reader.next());
	    SN.RATING[cont][1] = b;
	    c = (int) Double.parseDouble(reader.next()) - 1;
	    SN.RATING[cont][2] = c;
	    d = (int) Double.parseDouble(reader.next());
	    SN.RATING[cont][3] = d;
	    e = (int) Double.parseDouble(reader.next());
	    SN.RATING[cont][4] = e;
	    f = (int) Double.parseDouble(reader.next());
	    cont++;
	} reader.close();
	
	    //caricamento del file delle reliability
	char[][] r = new char[SN.numUsers + 1][SN.numUsers + 1];
	for (int i = 0; i <= SN.numUsers; i++)
	    for (int j = 0; j <= SN.numUsers; j++)
		r[i][j] = 0;
	
	    //System.out.println(SN.numUsers);
	    reader = new Scanner(new FileInputStream(fileTrust));
	int x, y;
	while (reader.hasNext()) {
	    x = (int) Double.parseDouble(reader.next());
	    y = (int) Double.parseDouble(reader.next());
	    
		//System.out.println("x="+x+" y="+y);
		if ((x <= SN.numUsers) && (y <= SN.numUsers))
		r[x][y] = 255;
	}
	reader.close();

	ArrayList < User > users = new ArrayList < User > ();
	ArrayList < Group > groups = new ArrayList < Group > ();

	for (int i = 0; i < SN.numUsers; i++) {
	    User u = new User();
	    u.createFromFile(i);
	    users.add(u);
	}  for (int i = 0; i < SN.numGroups; i++) {
	    Group g = new Group();
	    g.createRandom(0);
	    groups.add(g);
	}    return (new SN(users, groups, r));
      }

      public static SN InitTraining(int end,
				       ArrayList < Group >
				       groups) throws FileNotFoundException
    {
	
	    //end rappresenta l'ultima riga da leggere del training set
	    //la struttura dei gruppi e' passata in groups
	Scanner reader = new Scanner(new FileInputStream(fileRating));
	SN.RATING = new int[SN.numRowsRating][5];
	 int cont = 0;
	 int a, b, c, d, e, f;
	 while (reader.hasNext() && (cont <= end)) {
	    a = (int) Double.parseDouble(reader.next()) - 1;
	    SN.RATING[cont][0] = a;
	    b = (int) Double.parseDouble(reader.next());
	    SN.RATING[cont][1] = b;
	    c = (int) Double.parseDouble(reader.next()) - 1;
	    SN.RATING[cont][2] = c;
	    d = (int) Double.parseDouble(reader.next());
	    SN.RATING[cont][3] = d;
	    e = (int) Double.parseDouble(reader.next());
	    SN.RATING[cont][4] = e;
	    f = (int) Double.parseDouble(reader.next());
	    cont++;
	} reader.close();
	
	    //caricamento del file delle reliability
	char[][] r = new char[SN.numUsers + 1][SN.numUsers + 1];
	for (int i = 0; i <= SN.numUsers; i++)
	    for (int j = 0; j <= SN.numUsers; j++)
		r[i][j] = 0;
	
	    //System.out.println(SN.numUsers);
	    reader = new Scanner(new FileInputStream(fileTrust));
	int x, y;
	while (reader.hasNext()) {
	    x = (int) Double.parseDouble(reader.next());
	    y = (int) Double.parseDouble(reader.next());
	    
		//System.out.println("x="+x+" y="+y);
		if ((x <= SN.numUsers) && (y <= SN.numUsers))
		r[x][y] = 255;
	}
	 reader.close();
	  ArrayList < User > users = new ArrayList < User > ();
	for (int i = 0; i < SN.numUsers; i++) {
	    User u = new User();
	    u.createFromFile(i);
	    users.add(u);
	}    return (new SN(users, groups, r));
    }
      public static SN InitTest(
	int start, 
	int end, 
	ArrayList < Group > groups,
	char[][]r) throws FileNotFoundException
    {
	//end-start rappresenta la finestar da leggere del test set
	if(SN.maxRowsRating<0)
	  InitFile();

	SN.numRowsRating = end - start + 1;
	int toRead = SN.numRowsRating;
	SN.RATING = new int[SN.numRowsRating][5];
	Scanner reader = new Scanner(new FileInputStream(fileRating));
	int cont = 1;

	//skip start lines	
	int a, b, c, d, e, f;
	while(cont<start && reader.hasNext()){
	    a = (int) Double.parseDouble(reader.next()) - 1;
	    b = (int) Double.parseDouble(reader.next());
	    c = (int) Double.parseDouble(reader.next()) - 1;
	    d = (int) Double.parseDouble(reader.next());
	    e = (int) Double.parseDouble(reader.next());
	    f = (int) Double.parseDouble(reader.next());
	    cont++;
	}	
  

	cont=0;
	//read end-start lines from start
	while (reader.hasNext() && cont<toRead) {
	    a = (int) Double.parseDouble(reader.next()) - 1;
	    b = (int) Double.parseDouble(reader.next());
	    c = (int) Double.parseDouble(reader.next()) - 1;
	    d = (int) Double.parseDouble(reader.next());
	    e = (int) Double.parseDouble(reader.next());
	    f = (int) Double.parseDouble(reader.next());
	    SN.RATING[cont][0] = a;
	    SN.RATING[cont][1] = b;
	    SN.RATING[cont][2] = c;
	    SN.RATING[cont][3] = d;
	    cont++;
	}

	reader.close();
	ArrayList < User > users = new ArrayList < User > ();
	for (int i = 0; i < SN.numUsers; i++) {
	    User u = new User();
	    u.createFromFile(i);
	    users.add(u);
	}   return (new SN(users, groups, r));
    }
     public static SN InitTestRandom(int start,
				       char[][]r) throws
	FileNotFoundException {
	
	    //start rappresenta la prima riga da leggere del test set
	Scanner reader = new Scanner(new FileInputStream(fileRating));
	
	    //legge il file dei ratings e determina numUsers e numCategories
	int a = 0, b, c = 0, d, e, f, cont = 0;
	   while (reader.hasNext()) {
	    a = (int) Double.parseDouble(reader.next()) - 1;
	    b = (int) Double.parseDouble(reader.next());
	    c = (int) Double.parseDouble(reader.next()) - 1;
	    d = (int) Double.parseDouble(reader.next());
	    e = (int) Double.parseDouble(reader.next());
	    f = (int) Double.parseDouble(reader.next());
	    if (cont >= start) {
		SN.RATING[cont - start][0] = a;
		SN.RATING[cont - start][1] = b;
		SN.RATING[cont - start][2] = c;
		SN.RATING[cont - start][3] = d;
		SN.RATING[cont - start][4] = e;
	    }
	    cont++;
	}
	reader.close();
	   ArrayList < User > users = new ArrayList < User > ();
	for (int i = 0; i < SN.numUsers; i++) {
	    User u = new User();
	    u.createFromFile(i);
	    users.add(u);
	}  ArrayList < Group > groups = new ArrayList < Group > ();
	for (int i = 0; i < SN.numGroups; i++) {
	    Group g = new Group();
	    g.createRandom(0);
	    groups.add(g);
	} return (new SN(users, groups, r));
    }
     public static void user_agent_task_diss(SN s, int epoca, int u) {
	int m = SN.NREQ;	//numero di gruppi che ad ogni epoca possono essere contattati dallo user agent
	int ng, cont = 0;
	
	    //l'agente u determina i gruppi (al massimo NMAX) a cui chiedere di associarsi
	    //prima genera una lista contenente inizialmente tutti i vecchi gruppi
	    //ordinati per dissimilarita' crescente
	    ArrayList < Member > originalGroups = s.groups(u);
	ArrayList < Integer > group2contact =
	    new ArrayList < Integer > ();
	Integer gi;
	if (!originalGroups.isEmpty()) {
	    gi = new Integer(originalGroups.get(0).getPos());
	    group2contact.add(gi);
	    for (int i = 1; i < originalGroups.size(); i++) {
		gi = new Integer(originalGroups.get(i).getPos());
		boolean trovato = false;
		for (int j = 0; j < group2contact.size(); j++) {
		    if (s.groupDissimilarity(u, gi.intValue()) <
			 s.groupDissimilarity(u,
					      group2contact.get(j).
					      intValue())) {
			group2contact.add(j, gi);
			trovato = true;
			break;
		    }
		}
		if (!trovato)
		    group2contact.add(gi);
	    }
	}
	
	    //poi aggiunge alla lista m nuovi gruppi, sempre ordinati per dissimilarita' crescente  
	    
	do {
	    Random random = new Random();
	    ng = random.nextInt(s.groups.size());
	    Integer ngi = new Integer(ng);
	    if (!originalGroups.contains(ngi)) {
		cont++;
		boolean trovato = false;
		for (int j = 0; j < group2contact.size(); j++)
		    if (s.groupDissimilarity(u, ng) <
			 s.groupDissimilarity(u,
					      group2contact.get(j).
					      intValue())) {
			group2contact.add(j, ngi);
			trovato = true;
			break;
		    }
		if (!trovato)
		    group2contact.add(ngi);
	    }
	} while (cont < m);
	
	    //poi elimina i gruppi aventi dissimilarita' con u maggiore di tau
	    //tenendo conto che bisogna soddisfare il vincolo di NMIN
	    //for(int i=0;i<group2contact.size();i++){
	    //      Integer g=group2contact.get(i);
	    //      if((s.groupDissimilarity(u, g.intValue())>SN.tau)&&(group2contact.size()>=SN.NMIN)){
	    //              group2contact.remove(i);
	    //              //dualmente, u viene eliminato dalla lista dei membri del gruppo
	    //              Group group=s.groups.get(g);
	    //              for(int z=0;z<group.getMembers().size();z++)
	    //                      if(group.getMembers().get(z).getPos()==u) group.getMembers().remove(z); 
	    //      }
	    //}     
	    //infine lascia in group2contact solo i primi NMAX gruppi
	    if (group2contact.size() > SN.NMAX) {
	    for (int i = SN.NMAX; i < group2contact.size(); i++) {
		Integer g = group2contact.get(i);
		group2contact.remove(i);
		s.groups.get(g).getMembers().remove(g);
		
		    //dualmente, u viene eliminato dalla lista dei membri del gruppo
		    Group group = s.groups.get(g);
		for (int z = 0; z < group.getMembers().size(); z++)
		    if (group.getMembers().get(z).getPos() == u)
			group.getMembers().remove(z);
	    }
	}
	for (int i = 0; i < group2contact.size(); i++)
	    s.groups.get(group2contact.get(i).intValue()).getRequests().add(u);
	
	    //adesso invia la richiesta di associazione a tutti i gruppi
	    //presenti in group2contact
    }  

      public static void group_agent_task_diss(SN s, int epoca, int k) {
	Group g = s.groups.get(k);
	
	    //prima genera una lista contenente inizialmente tutti i vecchi membri
	    //ordinati per dissimilarita' crescente
	    ArrayList < Member > originalMembers = g.getMembers();
	ArrayList < Integer > update = new ArrayList < Integer > ();
	Integer ui;
	if (!originalMembers.isEmpty()) {
	    ui = new Integer(originalMembers.get(0).getPos());
	    update.add(ui);
	    for (int i = 1; i < originalMembers.size(); i++) {
		int u = originalMembers.get(i).getPos();
		ui = new Integer(u);
		boolean trovato = false;
		for (int j = 0; j < update.size(); j++)
		    if (s.groupDissimilarity(u, k) <
			 s.groupDissimilarity(update.get(j).intValue(),
					      k)) {
			update.add(j, ui);
			trovato = true;
			break;
		    }
		if (!trovato)
		    update.add(ui);
	    }
	}
	
	    //poi aggiunge ad update tutti gli users che hanno fatto richiesta
	    //sempre in modo ordinato per dissimilarita'
	    for (int i = 0; i < g.getRequests().size(); i++) {
	    ui = g.getRequests().get(i);
	    int u = ui.intValue();
	    boolean trovato = false;
	    for (int j = 0; j < update.size(); j++)
		if (s.groupDissimilarity(u, k) <
		     s.groupDissimilarity(update.get(j).intValue(), k)) {
		    update.add(j, ui);
		    trovato = true;
		    break;
		}
	    if (!trovato)
		update.add(ui);
	}
	
	    //poi elimina i membri aventi dissimilarita' con k maggiore di tau
	    //tenendo conto di rispettare il vincolo di KMIN
	    for (int i = 0; i < update.size(); i++) {
	    ui = update.get(i);
	    if ((s.groupDissimilarity(ui.intValue(), k) > SN.tau)
		 && (update.size() >= SN.KMIN)) {
		
		    //System.out.println("soglia="+SN.tau*s.MAD);
		    update.remove(i);
	    }
	}
	
	    //infine lascia in update solo i primi KMAX members
	    if (update.size() > SN.KMAX) {
	    for (int i = SN.KMAX; i < update.size(); i++) {
		update.remove(i);
	} }
	
	    //adesso aggiorna la lista dei membri con tutti i membri
	    //presenti in update
	    g.getMembers().clear();
	for (int i = 0; i < update.size(); i++) {
	    Member member = new Member(update.get(i));
	    g.getMembers().add(member);
	}  
	    //infine, se il gruppo e' rimasto vuoto lo elimina dalla lista dei gruppi
	    if (g.getMembers().isEmpty())
	    s.groups.remove(g);
	
	    //inoltre ripulisce la lista delle richieste
	    g.getRequests().clear();
     }

      //Da usare per formare i gruppi in base alla sola similarità
     public static void u2g_diss(SN s, int epoca) {
	
	    //esegue l'algoritmo U2G nella versione IDC, basato sulla sola dissimilarita'
	    
	    //inizia il task degli user agents
	    //gli agenti degli users inviano le richieste di join ai gruppi
	    for (int u = 0; u < s.users.size(); u++)
	    user_agent_task_diss(s, epoca, u);
	 
	    //inizia il task dei group agents
	    for (int k = 0; k < s.groups.size(); k++)
	    group_agent_task_diss(s, epoca, k);
       }  

     public static void group_agent_task(SN s, int epoca, int k) {
	Group g = s.groups.get(k);
	 
	    //prima genera una lista contenente inizialmente tutti i vecchi membri
	    //ordinati per coesione decrescente
	    ArrayList < Member > originalMembers = g.getMembers();
	ArrayList < Integer > update = new ArrayList < Integer > ();
	Integer ui;
	if (!originalMembers.isEmpty()) {
	    ui = new Integer(originalMembers.get(0).getPos());
	    update.add(ui);
	    for (int i = 1; i < originalMembers.size(); i++) {
		int u = originalMembers.get(i).getPos();
		ui = new Integer(u);
		boolean trovato = false;
		for (int j = 0; j < update.size(); j++)
		    if (s.g2u_cohesion(k, u) >
			 s.g2u_cohesion(k, update.get(j).intValue())) {
			update.add(j, ui);
			trovato = true;
			break;
		    }
		if (!trovato)
		    update.add(ui);
	    }
	}
	 
	    //poi aggiunge ad update tutti gli users che hanno fatto richiesta
	    //sempre in modo ordinato per cohesion
	    //l'aggiunta viene fatta solo se la cohesion con k e' minore
	    //della cohesion media
	double pi = s.cohesion(k);
	for (int i = 0; i < g.getRequests().size(); i++) {
	    ui = g.getRequests().get(i);
	    int u = ui.intValue();
	    if ((s.g2u_cohesion(k, u) >= pi)) {
		boolean trovato = false;
		for (int j = 0; j < update.size(); j++)
		    if (s.g2u_cohesion(k, u) >
			 s.g2u_cohesion(k, update.get(j).intValue())) {
			update.add(j, ui);
			trovato = true;
			break;
		    }
		if (!trovato)
		    update.add(ui);
	    }
	}
	
	    //poi elimina i membri aventi cohesion con k minore 
	    //della cohesion media di gruppo
	    
	    //double pi=s.cohesion(k);
	    //for(int i=0;i<update.size();i++){
	    //      ui=update.get(i);
	    //      if((s.g2u_cohesion(k,ui.intValue())<pi)&&(update.size()>=SN.KMIN)){
	    //              update.remove(i);
	    //      }
	    //}
	    //infine lascia in update solo i primi KMAX members
	    if (update.size() > SN.KMAX) {
	      for (int i = SN.KMAX; i < update.size(); i++) {
		update.remove(i);
	} }
	
	    //adesso aggiorna la lista dei membri con tutti i membri
	    //presenti in update
	    g.getMembers().clear();
	for (int i = 0; i < update.size(); i++) {
	    Member member = new Member(update.get(i));
	    g.getMembers().add(member);
	 }  
	    //infine, se il gruppo e' rimasto vuoto lo elimina dalla lista dei gruppi
	    if (g.getMembers().isEmpty())
	    s.groups.remove(g);
	
	    //inoltre ripulisce la lista delle richieste
	    g.getRequests().clear();
	 
	    //System.out.println("agente "+k+" ha finito");         
    }
     
	/////////////////////////////////////////////////////////////////////////////
	///////// metodi ultima versione per COSN ///////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////
    public static void user_agent_task(SN s, int epoca, int u) {
	int m = SN.NREQ;	//numero di nuovi gruppi che ad ogni epoca possono essere contattati dallo user agent
	int ng, cont = 0;
	
	    //l'agente u determina i nuovi gruppi (al massimo NRQ) a cui chiedere di associarsi
	    //prima genera una lista contenente inizialmente tutti i vecchi gruppi
	    //ordinati per compactness decrescente
	    ArrayList < Member > originalGroups = s.groups(u);
	    ArrayList < Integer > group2contact = new ArrayList < Integer > ();
	Integer gi;
	 if (!originalGroups.isEmpty()) {
	    gi = new Integer(originalGroups.get(0).getPos());
	    group2contact.add(gi);
	    for (int i = 1; i < originalGroups.size(); i++) {
		gi = new Integer(originalGroups.get(i).getPos());
		boolean trovato = false;
		for (int j = 0; j < group2contact.size(); j++) {
		    if (s.u2g_cohesion(u, gi.intValue()) >
			 s.u2g_cohesion(u,group2contact.get(j).intValue())) {
			group2contact.add(j, gi);
			trovato = true;
			break;
		    }
		}
		if (!trovato)
		    group2contact.add(gi);
	    }
	}
	  
	    //poi aggiunge alla lista m nuovi gruppi, sempre ordinati per compacteness crescente
	    // e con compactness superiore a quelle con i gruppi originali
	int controllo = 0;
	
	do {
	    controllo++;
	    Random random = new Random();
	    ng = random.nextInt(s.groups.size());
	    Integer ngi = new Integer(ng);
	    if (((!group2contact.isEmpty())
		  && !originalGroups.contains(ngi))
		 && (s.u2g_cohesion(u, ng) >
		     s.u2g_cohesion(u,
				    group2contact.get(group2contact.
						      size() -
						      1).intValue()))) {
		boolean trovato = false;
		for (int j = 0; j < group2contact.size(); j++)
		    if (s.u2g_cohesion(u, ng) >
			 s.u2g_cohesion(u,
					group2contact.get(j).intValue())) {
			group2contact.add(j, ngi);
			trovato = true;
			cont++;
			break;
		    }
		if (!trovato)
		    group2contact.add(ngi);
	    }	    
		//System.out.println("agente "+u+" contatto:"+cont+" controllo:"+controllo);
	} while ((cont < s.groups.size()) && (controllo < s.groups.size()));
	 if (group2contact.size() > SN.NMAX) {
	    for (int i = SN.NMAX; i < group2contact.size(); i++) {
		Integer g = group2contact.get(i);
		group2contact.remove(i);
		s.groups.get(g).getMembers().remove(g);
		
		    //dualmente, u viene eliminato dalla lista dei membri del gruppo
		    Group group = s.groups.get(g);
		for (int z = 0; z < group.getMembers().size(); z++)
		    if (group.getMembers().get(z).getPos() == u)
			group.getMembers().remove(z);
	    }
	}
	
	    //mette i gruppi da contattare (solo quelli non presenti nella lista originale) nella lista delle richieste
	    for (int i = 0; i < group2contact.size(); i++) {
	    Integer ngi = new Integer(group2contact.get(i));
	    if (!originalGroups.contains(ngi))
		s.groups.get(group2contact.get(i).intValue()).
		    getRequests().add(u);
	}
      }

     public static void u2g(SN s, int epoca) {
	 for (int u = 0; u < s.users.size(); u++)
	    user_agent_task(s, epoca, u);
	 
	  //inizia il task dei group agents
	  for (int k = 0; k < s.groups.size(); k++)
	    group_agent_task(s, epoca, k);
       }

      //Compactness vs Similarity to form groups - Transactions on Cybernetics 
      public static void experiment2() throws Exception{  

	//inizializza i parametri statici della SN con i valori 
	//letti da file
	SN s = InitFile();

	//total number of lines of the rating file
	int nTest = Integer.parseInt(System.getProperty("nt"));
	int swTraining = Integer.parseInt(System.getProperty("sw"));
	int overlap = Integer.parseInt(System.getProperty("overlap"));
	int swTest;
	double MAC, MAS;
    
	int start, end;

	System.out.println("Loading data, sWindows=" + SN.sWindows);	

	//int dimTraining = SN.sWindows;
//	System.out.println("Starting training, sw="+SN.sWindows);
	
	//INIZIALIZZA LA RETE S1 PER LA FASE DI TRAINING - COMPACTNESS
	SN s1 = InitTraining(SN.sWindows);
	System.out.println("Generation of network for training (COMP), done (window-size=" + 
	SN.sWindows + "/" + SN.maxRowsRating + ")");
	s1.computeReputation();
	System.out.println("Reputation for training calculated..");

	int epoca = 0;
	MAC = s1.MAC();
	MAS = s1.MAS(epoca, "INIT-Training-COMP");
	System.out.println("Starting training with U2G");
	System.out.println("Training epoch: " + epoca + " MAC=" + MAC + ", MAS="+MAS);

	//FORMAZIONE GRUPPI CON COMPACTNESS
	double e = 0.5;
	double prevMac, prevMas;
	prevMac = prevMas = 0;
	while(e>=0.05){
//	 for (epoca = 1; epoca < 4; epoca++) {
	    //modifica i gruppi applicando U2G
	    //attenzione! i valori di reliability user2user non cambiano
	    //u2g_diss(s1,epoca); //versione di U2G presentata ad IDC, basata solo su dissimilarita'
	    epoca++;
	    u2g(s1, epoca);	//versione di U2G per COSN, basata su coesione
	    MAC = s1.MAC();
	    MAS = s1.MAS(epoca, "Training-COMP");
	
	    if(epoca==0)
	      e = 1.0;	      
	    
	    else e = Math.max(Math.abs(MAC-prevMac), Math.abs(MAS-prevMas));

	    prevMac = MAC;
	    prevMas = MAS;
	    
	    //DAC=s1.DAC();
	    System.out.println("Epoch= " + epoca + " MAC=" + MAC + ", MAS="+MAS);
	  }
	  System.out.println("Training for U2G-Comp ended");
	
	  //crea la SN di test con i gruppi trovati nella fase di training

	  System.out.println("Starting test for U2G, nt="+nTest);
	  swTest = (int) java.lang.Math.floor(((double) SN.maxRowsRating - (double) swTraining) / (double) nTest);

	  if(overlap==1)
	    start=1;
	  else
	    start=swTraining+1;

	  end=swTraining+swTest;

	  int nt=0;
	  System.err.println("%COMP - 1-nt 2-MAC 3-MAS");
	  System.err.println(nt+"\t"+MAC+"\t"+MAS);
	  nt++;

	  while(end<SN.maxRowsRating){
	    SN s3 = InitTest(start, end, s1.groups, s1.rel);
	    assert SN.numRowsRating == end-start+1; 
	    s3.computeReputation();
	    MAC = s3.MAC();
	    MAS = s3.MAS(epoca, "Test-COMP");
	    System.err.println(nt+"\t"+MAC+"\t"+MAS);
  
	    //go ahead 
	    if(overlap==0)
	      start=end+1;
	    end+=swTest;
	    nt++;	    
	}
	System.out.println("finito");

	//RE-INIZIALIZZA LA RETE S1 PER LA FASE DI TRAINING -- SIMILARITY
	s1 = InitTraining(SN.sWindows);
	System.out.println("Generation of network for training, done (window-size=" + SN.sWindows + "/" + SN.maxRowsRating + ")");
	s1.computeReputation();
	//System.out.println("Calcolo dei valori di reputazione di training completata");
	MAC = s1.MAC();
	MAS = s1.MAS(epoca, "INIT-Training-SIM");
	epoca = 0;
	System.out.println("Starting simulation for U2G-Sim");
	System.out.println("Epoch= " + epoca + " MAS = "+MAS);
	  e=0.5;
	  //FOrmazione gruppi con similarità
	  while(e>=0.5){
//	 for (epoca = 1; epoca < 4; epoca++) {
	    //modifica i gruppi applicando U2G
	    //attenzione! i valori di reliability user2user non cambiano
	    //u2g_diss(s1,epoca); //versione di U2G presentata ad IDC, basata solo su dissimilarita'
	    epoca++;
	    u2g_diss(s1, epoca);	//versione di U2G per COSN, basata su coesione
	    MAS = s1.MAS(epoca, "Training-SIM");
//	    MAC=s1.MAC();

	    if(epoca==0)
	      e = 1.0;	      

	    else e = Math.max(Math.abs(MAC-prevMac), Math.abs(MAS-prevMas));

	    prevMac = MAC;
	    prevMas = MAS;
  
	    epoca++;
	    
	    //DAC=s1.DAC();
	    System.out.println("Epoch= " + epoca + " MAC=" + MAC + ", MAS="+MAS);
	  }
	  System.out.println("finito");

	  //CREA LA SN DI TEST CON I GRUPPI TROVATI NELLA FASE DI TRAINING
	  System.out.println("Starting test, nt="+nTest);

	  //init first windows
	  if(overlap==1)
	    start=1;
	  else
	    start=swTraining+1;

	  nt=0;
	  System.err.println("%SIM - 1-nt 2-MAC 3-MAS");
	  System.err.println(nt+"\t"+MAC+"\t"+MAS);
	  nt++;

	  end=swTraining+swTest;
	  while(end<SN.maxRowsRating){
	    SN s3 = InitTest(start, end, s1.groups, s1.rel);
	    assert SN.numRowsRating == end-start+1;
	    s3.computeReputation();
	    MAC = s3.MAC();
	    MAS = s3.MAS(epoca, "Test-SIM");
	    System.err.println(nt+"\t"+MAC+"\t"+MAS);
  
	    //go ahead
	    if(overlap==0)
	      start=end+1;
	    end+=swTest;
	    nt++;
	}
	  System.out.println("Test for U2G-Sim ended");
      }

     public static void main(String[]args) throws Exception {
	    experiment2();
    }     
}
