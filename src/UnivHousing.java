

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;
import java.util.Date;

public class UnivHousing {
        public static String auth,id,appId;
        public static int unity,password;
      
        
//        public static int id,appId;
	static final String jdbcURL 
		= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
	
	static Connection conn;
	///////////////////////////////////////////////////////////////////////////
	//////////////// Connection //////////////////////////////////////////////
    //sb begin
	public static Connection connectToOracle()
	{
		Connection conn = null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String user = "sbhatpr";	// For example, "jsmith"
		    String passwd = "200014871";	// Your 9 digit student ID number
		   
            conn = DriverManager.getConnection(jdbcURL, user, passwd);	 

		} 
		catch(Throwable oops) {
            oops.printStackTrace();
		}
		return conn;
	}
	
	static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } catch(Throwable whatever) {}
        }
    }
	
	static void close(Statement st) {
        if(st != null) {
            try { st.close(); } catch(Throwable whatever) {}
        }
    }

	 static void close(ResultSet rs) {
	        if(rs != null) {
	            try { rs.close(); } catch(Throwable whatever) {}
	        }
	    }
	 
	static void executeUpdate(Connection conn, String command)
	{
		Statement stmt = null;
		try{
			try{
				stmt = conn.createStatement();
				stmt.executeUpdate(command);
			}finally{
				close(stmt);
			}
		}
		catch(Throwable oops) {
            oops.printStackTrace();
		}	
	}
	
	//sb begin
	static ResultSet executeQuery(Connection conn, String command)
	{
		ResultSet rs = null;
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(command);
		}
		catch(Throwable oops){
			close(stmt);
		}
		
		// print rs //
		
		//close(rs);
		return rs;
	}
	//sb end
	
	///////////////////////////////////////////////////////////////////////////
	//////////////// Connection End//////////////////////////////////////////////
	//3B
	
	public static void login() throws IOException{
      int choice;
      
      Scanner in = new Scanner(System.in);
      BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
      
      while(true){    
          System.out.println("NC State University Housing\n");
          System.out.println("1. Login");
          System.out.println("2. Guest Login");
          System.out.println("3. Exit");
          choice = readInputOption(1,3);
      
          switch(choice){
          case 1:
              System.out.println("Login as Student or Staff?\n");
              auth = in.nextLine();
              if (auth.equalsIgnoreCase("Student")){
                 System.out.println("Login as Student\n");
                 if (Student_login()){
                     System.out.println("Logged in as " + unity);
                     student();
                 }       
              }
              else
              {
                    System.out.println("Login as Staff");
                    showAdminOptions();
              }       
              break;
          case 2:
              System.out.println("Logged in as Guest");
              guestLogin();
              break;
          case 3:
              System.exit(0);
              break;
          default: 
              return;     
        }
      }
    }
    
    public static Boolean Student_login(){
        Scanner in = new Scanner(System.in);
      
        while (true)
        {
	        System.out.println("1. Enter STUDENT ID followed by password");
	        
	        unity = in.nextInt();
	        password = in.nextInt();
        
	        //sb begin
       
            while(unity!=password){
                System.out.println("Login Incorrect!");
                Student_login();
            }
            
            String command = "select S.SID from students S where S.SID='" + unity + "'";
            
            try{
            	ResultSet rs = executeQuery(conn, command);
                if (rs.next())
                {
                	int u_id = rs.getInt("SID");
                	
                }
                else
                {
                	System.out.println("Login incorrect");
            		continue;
                }
            	
                
            }catch (SQLException e ) {}
            //sb end
            return true;
        }
        
    }
    
    public static void student() throws IOException{
        int ch;
    Scanner in = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while(true){
        System.out.println("Logged in as Student\n");
        System.out.println("MAIN MENU");
        System.out.println("1. Housing option");
        System.out.println("2. Parking option");
        System.out.println("3. Maintenance");
        System.out.println("4. Profile");
        System.out.println("5. Back");
        ch = readInputOption(1,5);
        switch(ch){
                case 1:
                    System.out.println("Housing options for Students\n");
                    Housing_options(1);
                    break;
                case 2:
                    System.out.println("Parking options\n");
                    showParkingOption(1);
                    break;
                case 3:
                    System.out.println("Maintenance\n");
                    showMaintainance(1);
                    break;
                case 4:
                    System.out.println("Profile\n");
                    showProfileOptions(1);
                    break;
                default: 
                    return;   
          }
        }
    
  }
    
    public static void Housing_options(int sel) throws IOException{
        int ch;
    Scanner in = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while(true){
        System.out.println("Housing Options\n");
        System.out.println("1. View Invoices");
        System.out.println("2. View leases");
        System.out.println("3. New request");
        System.out.println("4. View/Cancel requests");
        System.out.println("5. View vacancy");
        System.out.println("6. Back");
//        System.out.println("7. Back to Main");
        ch = readInputOption(1,6);
        switch(ch){
                case 1:
                    System.out.println("View Invoices\n");
                    invoice();
                    break;
                case 2:
                    System.out.println("View leases\n");
                    lease();
                    break;
                case 3:
                    System.out.println("New request\n");
                    request();
                    break;
                case 4:
                    System.out.println("View/Cancel requests\n");
                    view();
                    break;
                case 5:
                    System.out.println("View vacancy\n");
                    if (sel==1)
                        vacancy_s();
                    else if (sel==2)
                        vacancy_g();
                    break;
                default: 
                    return;   
          }
        } 
   }
    
    public static void invoice() throws IOException{
        int ch;
    Scanner in = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while(true){
        System.out.println("1. View current invoice");
        System.out.println("2. View former invoices");
        System.out.println("3. Back");

        ch = readInputOption(1,3);
        switch(ch){
                case 1:
                    System.out.println("View current invoice\n");
                    display_current_invoice();
                    break;
                case 2:
                    System.out.println("View former invoices\n");
                    display_former_invoice();
                    break;
                default: 
                    return;   
          }    
        } 
    }
    
  public static void display_current_invoice() throws IOException{
      int ch;
      Scanner in = new Scanner(System.in);
      System.out.println("1. Display invoices");
      System.out.println("2. Back");
      ch = readInputOption(1,2);
        switch(ch){
                case 1:
                    // Enter the information to issue a new lease and save the data into the tables when it is submitted.
                    System.out.println("Displaying invoice1,2,........");
                    break;
                default: 
                    return;
       }
      
  }
  
  public static void display_former_invoice() throws IOException{
      int ch;
    Scanner in = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while(true){
        System.out.println("1. Invoice 1 (Period)");
        System.out.println("2. Invoice 2 (Period)");
        System.out.println("3. Back");

        ch = readInputOption(1,3);
        switch(ch){
                case 1:
                    System.out.println("Displaying invoice 1");
                    break;
                case 2:
                    System.out.println("Displaying invoice 2");
                    break;
                default: 
                    return; 
        }
       }

  }
  
  public static void lease() throws IOException{
      int ch;
    Scanner in = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while(true){
        System.out.println("View Leases\n");
        System.out.println("1. View current lease");
        System.out.println("2. View former lease");
        System.out.println("3. Back");
        ch = readInputOption(1,3);
        switch(ch){
                case 1:
                    System.out.println("View current lease\n");
                    display_current_lease();
                    break;
                case 2:
                    System.out.println("View former leases\n");
                    display_former_lease();
                    break;
                default: 
                    return;   
          }    
    }
      
  }
  
  public static void display_current_lease() throws IOException{
      int ch;
      Scanner in = new Scanner(System.in);
      System.out.println("1. Display leases");
      System.out.println("2. Back");
      ch = readInputOption(1,2);
        switch(ch){
                case 1:
                    // Enter the information to issue a new lease and save the data into the tables when it is submitted.
                    System.out.println("Displaying lease1,2,........");
                    break;
                default: 
                    return;
       }
      
  
  }
  
  public static void display_former_lease() throws IOException{
      int ch;
    Scanner in = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while(true){
        System.out.println("1. Lease 1 (Period)");
        System.out.println("2. Lease 2 (Period)");
        System.out.println("3. Back");
        ch = readInputOption(1,3);
        switch(ch){
                case 1:
                    System.out.println("Displaying lease 1");
                    break;
                case 2:
                    System.out.println("Displaying lease 2");
                    break;
                default: 
                    return; 
        }
    }

   }
  

  
  public static void request() throws IOException{
      int ch;
    Scanner in = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while(true){
        System.out.println("1. New Lease Request");
        System.out.println("2. Terminate Lease Request");
        System.out.println("3. Back");
        ch = readInputOption(1,3);
        switch(ch){
                case 1:
                    System.out.println("New Lease Request\n");
                    new_request_l();
                    break;
                case 2:
                    System.out.println("Terminate Lease Request\n");
                    terminate_request();
                    break;
                default: 
                    return; 
    }
  }
  }
  
  public static void new_request_l() throws IOException{
       int ch;
       String details;
    Scanner in = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    // NO DO_WHILE LOOP ADDED BECAUSE IT WILL RETURN TO NEW REQUEST 
        System.out.println("1. Submit");
        System.out.println("2. Back");
        ch = readInputOption(1,2);
        switch(ch){
                case 1:
                    // Enter the information to issue a new lease and save the data into the tables when it is submitted.
                    System.out.println("\nEnter details");
                    details = in.nextLine();
                    break;
                default: 
                    return;
       }
 
  }
  
  public static void terminate_request() throws IOException{
       int ch;
       String details;
    Scanner in = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    // NO DO_WHILE LOOP ADDED BECAUSE IT WILL RETURN TO NEW REQUEST 
        System.out.println("1. Submit");
        System.out.println("2. Back");
        ch = readInputOption(1,2);
        switch(ch){
                case 1:
                    // Enter the information to issue a terminate lease and save the data into the tables when it is submitted.
                    System.out.println("\nEnter details");
                    details = in.nextLine();
                    break;
                default: 
                    return;
        }
  }
  
  public static void view() throws IOException{
        int ch;
    Scanner in = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while(true){
        System.out.println("1. View request");
        System.out.println("2. Cancel request");
        System.out.println("3. Back");

        ch = readInputOption(1,3);
        switch(ch){
                case 1:
                    System.out.println("View request\n");
                    display_request();
                    break;
                case 2:
                    System.out.println("Cancel request\n");
                    cancel_request();
                    break;
                default: 
                    return;    
          }    
    }
    }
  
  public static void display_request() throws IOException{
      int ch;
      Scanner in = new Scanner(System.in);
      System.out.println("1. Display requests");
      System.out.println("2. Back");
      ch = readInputOption(1,2);
        switch(ch){
                case 1:
                    // Enter the information to issue a new lease and save the data into the tables when it is submitted.
                    System.out.println("Displaying request1,2,........");
                    break;
                default: 
                    return;
       }
      
  }
  
  public static void cancel_request() throws IOException{
      int ch;
      String number;
    Scanner in = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while(true){
        System.out.println("1. Enter request number to cancel");
        System.out.println("2. Back");

        ch = readInputOption(1,2);;
        switch(ch){
                case 1:
                    System.out.println("\nEnter request number");
                    number = in.nextLine();
                    //PROCESS FURTHER
                    break;
                default: 
                    return;   
          }    
    }  
    }
  
  public static void vacancy_s() throws IOException{
      int ch;
      Scanner in = new Scanner(System.in);
      while (true){
          System.out.println("1. Display all available vacancies");
            System.out.println("2. Back");
            ch = readInputOption(1,2);
            switch(ch){
                case 1:
                    //System.out.println("Displaying Vacancies:");
                    String command = "select family from users where USER_NO='" + unity + "'";
                    try{
                    ResultSet rs2 = executeQuery(conn, command);
                    if(rs2.next()){
                        String family = rs2.getString("FAMILY");
                        family = family.trim();
                        
                        if (!family.equalsIgnoreCase("Y")){
                            System.out.println("Displaying vacancies for non-family students:");
                            String command1 = "(SELECT R.RH_PLACE AS PLACE_NO, R.RH_HO_ID AS HOUSE_ID FROM RESHALL R WHERE RH_LEASE_NO=0) UNION (SELECT G.GA_PLACE_NO AS PLACE_NO, G.GA_HO_ID AS HOUSE_ID FROM GAAPT G WHERE G.GA_LEASE_NO=0)";
                            ResultSet rs = executeQuery(conn, command1);
                             System.out.println("PLACE NUMBER" + "       " + "HOUSING TYPE");
//                            System.out.println(command1);
                            while(rs.next()){
                                int rhp = rs.getInt("PLACE_NO");
//                                int ghp = rs.getInt("PLACE_NO");
                                String rsh = rs.getString("HOUSE_ID");
//                                String gap = rs.getString("HOUSE_ID");
                               
                                System.out.println(rhp + "                  " + rsh);
//                                System.out.println(ghp + "      " + gap);
                            }
                            
                        }
                        else{
                            System.out.println("Displaying vacancies for family students:");
                            String command2 = "SELECT F.FAM_BED,F.FAM_HO_ID FROM FAMAPT F WHERE FAM_LEASE_NO=0";
                            ResultSet rs3 = executeQuery(conn, command2);
                            System.out.println("PLACE NUMBER" + "       " + "HOUSING TYPE");
                            while(rs3.next()){
                                int fam = rs3.getInt("FAM_BED");
                                String fam_ho = rs3.getString("FAM_HO_ID");
                                
                                System.out.println(fam + "                  " + fam_ho);
                                }
                           }
                        }
                    else
                        System.out.println("Error!!!");
                    }
                    catch (SQLException e ) {}
                    break;
                default: 
                    return;
            }
      }
  }
  
  public static void vacancy_g() throws IOException{
      int ch;
      Scanner in = new Scanner(System.in);
      while (true){
            System.out.println("1. Display all available vacancies");
            System.out.println("2. Back");
            ch = readInputOption(1,2);
            switch(ch){
                case 1:
                    int approval = Integer.parseInt(appId);
                    String command = "select family from users where USER_NO='" + approval + "'";
                    try{
                    ResultSet rs2 = executeQuery(conn, command);
                    if(rs2.next()){
                        String family = rs2.getString("FAMILY");
                        family = family.trim();
                        
                        if (!family.equalsIgnoreCase("Y")){
                            System.out.println("Displaying vacancies for non-family guests:");
                            String command1 = "(SELECT R.RH_PLACE AS PLACE_NO, R.RH_HO_ID AS HOUSE_ID FROM RESHALL R WHERE RH_LEASE_NO=0) UNION (SELECT G.GA_PLACE_NO AS PLACE_NO, G.GA_HO_ID AS HOUSE_ID FROM GAAPT G WHERE G.GA_LEASE_NO=0)";
                            ResultSet rs = executeQuery(conn, command1);
                            System.out.println("PLACE NUMBER" + "       " + "HOUSING TYPE");
                            while(rs.next()){
                                int rhp = rs.getInt("PLACE_NO");
//                                int ghp = rs.getInt("GA_PLACE_NO");
                                String rsh = rs.getString("HOUSE_ID");
//                                String gap = rs.getString("GA_HO_ID");
                                
                                System.out.println(rhp + "                  " + rsh);
//                                System.out.println(ghp + "      " + gap);
                            }
                        }
                        else{
                            System.out.println("Displaying vacancies for family guests:");
                            String command2 = "SELECT F.FAM_BED,F.FAM_HO_ID FROM FAMAPT F WHERE FAM_LEASE_NO=0";
                            ResultSet rs3 = executeQuery(conn, command2);
                            System.out.println("PLACE NUMBER" + "       " + "HOUSING TYPE");
                            while(rs3.next()){
                                int fam = rs3.getInt("FAM_BED");
                                String fam_ho = rs3.getString("FAM_HO_ID");
                                
                                System.out.println(fam + "                  " + fam_ho);
                                }
                           }
                        }
                    else
                        System.out.println("Error!!!");
                    }
                    catch (SQLException e ) {}
                    break;
                default: 
                    return;
            }
      }
  }
  
  public static void showParkingOption(int sel) throws IOException{
		int selection;
		Scanner in = new Scanner(System.in);
		while(true)
		{
			//System.out.println("\nParking options:");
			System.out.println("1. Request new parking spot");
			System.out.println("2. View parking lot information");
			System.out.println("3. View current parking spot");
			System.out.println("4. Renew parking spot");
			System.out.println("5. Return parking spot");
			System.out.println("6. View request status");
			System.out.println("7. Back");
					
			selection = readInputOption(1,7);
			
			switch(selection)
			{
				case 1:
					showRequestNewParkingSpot();
					break;
				case 2:
					showAvailableParkingSpot();
					break;
				case 3:if (sel==1)
                                           showCurrentParkingSpot_s();
                                        else if (sel==2)
                                            showCurrentParkingSpot_g();					
					break;
				case 4: 
					renewParkingSpot();
					break;
				case 5:if (sel==1)
                                           returnParkingSpot_s();
                                        else if (sel==2)
                                            returnParkingSpot_g();					
					break;
				case 6:
					viewRequestStatus();
					break;
				default:
					return;
			}
		}		
	}
  
  //3B.1
	public static void showRequestNewParkingSpot()
	{
		int vehicleType, handicap;
		
		System.out.println("Enter the following details");
		System.out.println("Vehicle type. Choose any of the below options");
		System.out.println("1. Bike");
		System.out.println("2. Compact cars");
		System.out.println("3. Standard cars");
		System.out.println("4. Large cars");
		
		vehicleType = readInputOption(1,4);
		
		System.out.println("Handicapped ?");
		System.out.println("1. Yes 		2. No");
		
		handicap = readInputOption(1,2);
		
		//process
		System.out.println("Request accepted");
		
	}
  
//3B.2
	public static void showAvailableParkingSpot()
	{
		System.out.println("All the available parking spots are:");
                String commnd = "select h.HTS_HOUSING_ID, h.HTS_LOT_ID, count(s.spot_id) as No_of_Spots from housing_to_lot h, parking_spot s where s.lot_id = h.HTS_LOT_ID and s.spot_avail = 'Y' group by h.HTS_HOUSING_ID, h.HTS_LOT_ID";

                        try{
                            ResultSet rs = executeQuery(conn, commnd);
                            System.out.println("HOUSING_TYPE" + "       " + "LOT_ID" + "        " + "NO_OF_SPOTS");
                            while(rs.next()){
                                String type = rs.getString("HTS_HOUSING_ID");
                                int lot_id = rs.getInt("HTS_LOT_ID");
                                int spot_id = rs.getInt("No_of_Spots");
                                
                                System.out.println(type + "                  " + lot_id + "                 " + spot_id);
                            }
                        }
                   catch (SQLException e ) {}         
	}
	
	//3B.3
	public static void showCurrentParkingSpot_s()
	{
		System.out.println("Current Parking Spot:");
                String command = "select L.L_PSPOT, L.PARKING_PERMIT FROM LEASE L WHERE L.USER_NUM='" + unity + "' and L.L_PSPOT IS NOT NULL";
                        try{
                            ResultSet rs = executeQuery(conn, command);
                            System.out.println("PARKING_SPOT" + "       " + "PARKING_PERMIT");
                            if(rs.next()){
                                int spot = rs.getInt("L_PSPOT");
                                int permit = rs.getInt("PARKING_PERMIT");
                                System.out.println(spot + "                  " + permit);
                            }
                             else
                                System.out.println("ERROR!!!");
                        } 
                        catch (SQLException e ) {}
                
	}    
        public static void showCurrentParkingSpot_g()
	{
		System.out.println("Current Parking Spot:");
                int appproval = Integer.parseInt(appId);
                String command = "select L.L_PSPOT, L.PARKING_PERMIT FROM LEASE L WHERE L.USER_NUM='" + appproval + "' and L.L_PSPOT IS NOT NULL";
                        try{
                            ResultSet rs = executeQuery(conn, command);
                            System.out.println("PARKING_SPOT" + "       " + "PARKING_PERMIT");
                            if(rs.next()){
                                int spot = rs.getInt("L_PSPOT");
                                int permit = rs.getInt("PARKING_PERMIT");
                                System.out.println(spot + "                  " + permit);
                            }
                             else
                                System.out.println("ERROR!!!");
                        } 
                        catch (SQLException e ) {}
	}  
 
 //3B.4
	public static void renewParkingSpot()
	{
		String sId;
	
		Scanner in = new Scanner(System.in);
		System.out.println("Enter parking spot id");
		sId = in.nextLine();
		
		//validate
		System.out.println("Renewed");
	}
	
	//3B.5
	public static void returnParkingSpot_s()
        {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter parking permit");
		int pid = in.nextInt();
                String command = "update lease set L_PSPOT = null, parking_permit = null where user_num='" + unity + "' and parking_permit =" + pid;
                String com = "update parking_spot set spot_avail = ‘Y’, permit_no = null where permit_no =" + pid;
                       System.out.println(command);
                       System.out.println(com);
                            executeUpdate(conn, command);
                            executeUpdate(conn, com);
                       
                        
		System.out.println("Returned");
	}
     public static void returnParkingSpot_g()
	{
		Scanner in = new Scanner(System.in);
                int approval = Integer.parseInt(appId);
		System.out.println("Enter parking permit");
		String pid = in.nextLine();
                String command = "update lease set L_PSPOT = null, parking_permit = null where user_num='" + approval + "' and parking_permit ='" + pid + "'";
                String com = "update parking_spot set spot_avail = ‘Y’, permit_no = null where permit_no ='" + pid + "'";
                       
                            executeUpdate(conn, command);
                            executeUpdate(conn, com);
	}
        //3B.6
	public static void viewRequestStatus()
	{
		System.out.println("Current spot requests :");
	}
   
        //3C
	public  static void showMaintainance(int type) throws IOException{
		String s;
		int selection;
		Scanner in = new Scanner(System.in);
		
		while (true)
		{
			System.out.println("\nSelect one of the options");
			System.out.println("1. New ticket");
			System.out.println("2. View ticket status");
			System.out.println("3. Back");
			
			selection = readInputOption(1,3);
			
			if (selection == 1)
                        { if (type==1)
				showNewTicket_student();
                            else if (type==2)
                                showNewTicket_guest();
                        }
			else if (selection == 2)
                        { if (type==1)
				showTicketList_student();
                            else if (type==2)
                                showTicketList_guest();
                        }
                        else 
                            return;
		}
		
	}
    
//3C.1
	public  static void showNewTicket_student() throws IOException
        {
		
                Scanner in = new Scanner(System.in);
		System.out.println("\nSelect type for the ticket");
		System.out.println("1=Low, 2=Medium, 3=High");
		String iType = in.nextLine();
                
                System.out.println("\nEnter your location");
                String loct = in.nextLine();
                
                System.out.println("\nEnter date in DD-MM-YYYY");
		String date = in.nextLine();
                
                System.out.println("\nEnter your description");
                String desc = in.nextLine();
               
                   String command = "insert into tickets values(ticket_seq.nextval," + iType + ",'" + date + "',NULL,'" + loct + "','PENDING'," + unity + ",'" + desc + "')"; 
//                   System.out.println(command);
                   executeUpdate(conn, command);
                            
		//add the ticket
		System.out.println("Adding ticket to the database");
                
	}
        
	public  static void showNewTicket_guest() throws IOException
	{
		Scanner in = new Scanner(System.in);
		System.out.println("\nSelect type for the ticket");
		System.out.println("1=Low, 2=Medium, 3=High");
		String iType = in.nextLine();
                
                System.out.println("\nEnter your location");
                String loct = in.nextLine();
                
                System.out.println("\nEnter date in DD-MM-YYYY");
		String date = in.nextLine();
                
                System.out.println("\nEnter your description");
                String desc = in.nextLine();
               int approval = Integer.parseInt(appId);
                   String command = "insert into tickets values(ticket_seq.nextval," + iType + ",'" + date + "',NULL,'" + loct + "','PENDING'," + approval + ",'" + desc + "')"; 
                  // System.out.println(command);
                   executeUpdate(conn, command);
                            
		//add the ticket
		System.out.println("Adding ticket to the database");
                
	}
        //3C.2
	public static void showTicketList_student() throws IOException
	{
		System.out.println("Ticket List:");
		String commnd = "SELECT * FROM TICKETS WHERE T_USER='" + unity + "' ORDER BY T_TYPE ASC";
//                System.out.println(commnd);
                        try{
                            ResultSet rs = executeQuery(conn, commnd);
                            while (rs.next()){
                                int t_no = rs.getInt("T_NO");
                                int type = rs.getInt("T_TYPE");
                                Date sdate = rs.getDate("T_SDATE");
                                Date edate = rs.getDate("T_EDATE");
                                String loc = rs.getString("T_LOCATION");
                                String status = rs.getString("T_STATUS");
                                String desc = rs.getString("T_DESC");
                                System.out.println("Ticket No: " + t_no + "\nTicket Type: " + type + "\nStart Date: " + sdate + "\nEnd Date: " + edate + "\nLocation: " + loc + "\nStatus: "+status + "\nDescription: "+desc);
                            }
                        }
                            catch (SQLException e ) {}
	}
    
        public static void showTicketList_guest() throws IOException
	{
		System.out.println("Ticket List:");
                int approval = Integer.parseInt(appId);
		String commnd = "SELECT * FROM TICKETS WHERE T_USER='" + approval + "' ORDER BY T_TYPE ASC";
                        try{
                            ResultSet rs = executeQuery(conn, commnd);
                            while (rs.next()){
                                int t_no = rs.getInt("T_NO");
                                int type = rs.getInt("T_TYPE");
                                Date sdate = rs.getDate("T_SDATE");
                                Date edate = rs.getDate("T_EDATE");
                                String loc = rs.getString("T_LOCATION");
                                String status = rs.getString("T_STATUS");
                                String desc = rs.getString("T_DESC");
                                System.out.println("Ticket No: " + t_no + "\nTicket Type: " + type + "\nStart Date: " + sdate + "\nEnd Date: " + edate + "\nLocation: " + loc + "\nStatus: "+status + "\nDescription: "+desc);
                            }
                        }
                            catch (SQLException e ) {}
	}
    //3D
	public static void showProfileOptions(int type) throws IOException{
		
		int iType;
		Scanner in = new Scanner(System.in);
			
		while (true)
		{
			System.out.println("\nProfile options:");
			System.out.println("1. View Profile");
			System.out.println("2. Update profile");
			System.out.println("3. Back");
//     
			iType = readInputOption(1,3);
			
			if (iType == 1)
                        {
                            if (type == 1)
                                viewProfile_student();
                            else if (type == 2)
                                viewProfile_admin();
                            else if (type == 3)
                                viewProfile_guest();
                        }
				
			else if (iType == 2)
				{
                            if (type == 1)
                                updateProfile_student();
                            else if (type == 2)
                                updateProfile_admin();
                            else if (type == 3)
                                updateProfile_guest();
                        }
			else 
                            return;
		}	
	}
    //3D.1
        public static void updateProfile_student(){
      
            int selection;
            Scanner in = new Scanner(System.in);
            System.out.println("** update profile **");
            while(true){
//            String command = "select * from students,users where sid=user_no and UNITY_ID='" + unity + "'";
            System.out.println("\nUPDATE");
            System.out.println("1. Change Name");
            System.out.println("2. Change Nationality");
            System.out.println("3. Change Address");
            System.out.println("4. Change Sex (M/F)");
            System.out.println("5. Change Course");
            System.out.println("6. Change Category");
            System.out.println("7. Change Hobbies");
            System.out.println("8. Smoking Status (Y/N)");
            System.out.println("9. Special Status (Y/N)");
            System.out.println("10. Change DOB");
            System.out.println("11. Change Phone numbers");
            System.out.println("12. Change Kin's Name");
            System.out.println("13. Change Kin's Relation");
            System.out.println("14. Change Kin's Address");
            System.out.println("15. Change Kin's Phone Number");
            System.out.println("16. Family Status (Y/N)");
            System.out.println("17. Back");
            selection = readInputOption(1,17);
            switch(selection){
                    case 1:
                        System.out.println("Enter First Name");
                        String f = in.nextLine();
                        System.out.println("Enter Last Name");
                        String l = in.nextLine();
                        String commnd = "update users u set u.fname ='" + f + "'," + "u.lname ='" + l + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, commnd);
                            if (rs.next()){
                                String s_fname = rs.getString("fname");
                                String s_lname = rs.getString("lname");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                    case 2:
                    System.out.println("Enter Nationality");
                        String nation = in.nextLine();
                        String comma6 = "update users u set u.nationality ='" + nation + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, comma6);
                            if (rs.next()){
                                String s_nationality = rs.getString("NATIONALITY");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                    case 3:
                        System.out.println("Enter Street:");
                        String street = in.nextLine();
                        System.out.println("\nEnter City:");
                        String city = in.nextLine();
                        System.out.println("\nEnter Zipcode:");
                        String zip = in.nextLine();
                        String command1 = "update users u set u.street ='" + street + "'," + "u.city ='" + city + "'," + "u.zipcode ='" + zip + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, command1);
                            if (rs.next()){
                                String s_street = rs.getString("STREET");
                                String s_city = rs.getString("CITY");
                                String s_zip = rs.getString("ZIPCODE");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                    case 4:
                        System.out.println("Sex (F/M)");
                        String sex = in.nextLine();
                        String cmd = "update users u set u.sex ='" + sex + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, cmd);
                            if (rs.next()){
                                String sx = rs.getString("SEX");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                    case 5:
                        System.out.println("Enter Course details");
                        String course = in.nextLine();
                        String command2 = "update students s set s.S_COURSES ='" + course + "'" + "where s.SID='" + unity + "'";
                        try{
                            ResultSet rs = executeQuery(conn, command2);
                            if (rs.next()){
                                String s_course = rs.getString("S_COURSES");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                   case 6:
                        System.out.println("Enter Category");
                        String category = in.nextLine();
                        String command3 = "update students s set s.S_CATEGORY ='" + category + "'" + "where s.SID='" + unity + "'";
                        try{
                            ResultSet rs = executeQuery(conn, command3);
                            if (rs.next()){
                                String s_category = rs.getString("S_CATEGORY");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                    case 7:
                        System.out.println("Enter Hobbies");
                        String hobbies = in.nextLine();
                        String command4 = "update students s set s.INTERESTS ='" + hobbies + "'" + "where s.SID='" + unity + "'";
                        try{
                            ResultSet rs = executeQuery(conn, command4);
                            if (rs.next()){
                                String interest = rs.getString("INTERESTS");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                     case 8:
                        System.out.println("Smoking Status (Y/N)");
                        String smoke = in.nextLine();
                        String command5 = "update users u set u.smoker ='" + smoke + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, command5);
                            if (rs.next()){
                                String smoking = rs.getString("SMOKER");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                     case 9:
                        System.out.println("Special Status (Y/N)");
                        String special = in.nextLine();
                        String command6 = "update users u set u.special ='" + special + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, command6);
                            if (rs.next()){
                                String s_special = rs.getString("SPECIAL");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                     case 10:
                         System.out.println("Enter DOB in DD-MMM-YYYY");
                        String db = in.nextLine();
                        String command10 = "update users u set u.dob ='" + db + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, command10);
                            if (rs.next()){
                                String dob = rs.getString("DOB");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                     case 11:
                        System.out.println("Enter Phone Numbers:");
                        String p1 = in.nextLine();
                        String p2 = in.nextLine();
                        String command7 = "update users u set u.phone='" + p1 + "'," + "u.a_phone='" + p2 + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, command7);
                            if (rs.next()){
                                String s_phone = rs.getString("PHONE");
                                String s_aphone = rs.getString("A_PHONE");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                     case 12:
                     System.out.println("Enter Kin's name");
                        String name = in.nextLine();
                        String co = "update users u set u.kin_name ='" + name + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, co);
                            if (rs.next()){
                                String dob = rs.getString("kin_name");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                      case 13:
                     System.out.println("Enter Kin's relation");
                        String rel = in.nextLine();
                        String co1 = "update users u set u.KIN_RELATION ='" + rel + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, co1);
                            if (rs.next()){
                                String dob = rs.getString("KIN_RELATION");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                    case 14:
                     System.out.println("Enter Kin's address");
                        String add = in.nextLine();
                        String co2 = "update users u set u.KIN_ADDR ='" + add + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, co2);
                            if (rs.next()){
                                String dob = rs.getString("KIN_ADDR");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                     case 15:
                     System.out.println("Enter Kin's phone number");
                        String no = in.nextLine();
                        String coo2 = "update users u set u.KIN_PHONE ='" + no + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, coo2);
                            if (rs.next()){
                                String dob = rs.getString("KIN_PHONE");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                      case 16:
                     System.out.println("Enter family status (Y/N)");
                        String st = in.nextLine();
                        String cooo2 = "update users u set u.FAMILY ='" + st + "'" + "where u.user_no=(select s.sid from students s where s.SID='" + unity + "')";
                        try{
                            ResultSet rs = executeQuery(conn, cooo2);
                            if (rs.next()){
                                String dob = rs.getString("FAMILY");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                     default:
                         return;
                }
			
            } 
        }
        public static void updateProfile_admin(){
            
            int selection;
            Scanner in = new Scanner(System.in);
            System.out.println("** update profile **");
            while(true){
            int staff = Integer.parseInt(id);
//            String command = "select * from staff where s_no='" + staff + "'";
            System.out.println("\nUPDATE");
            System.out.println("1. Change Name");
            System.out.println("2. Change Sex (F/M)");
            System.out.println("3. Change Address");
            System.out.println("4. Change Position");
            System.out.println("5. Change Location");
            System.out.println("6. Change DOB");
            System.out.println("7. Back");
            selection = readInputOption(1,7);
                switch(selection){
                    case 1:
                    System.out.println("Enter first name");
                        String f = in.nextLine();
                        System.out.println("\nEnter last name");
                        String l = in.nextLine();
                        
                        String commnd = "update staff s set s.S_FNAME ='" + f + "'," + "s.S_LNAME ='" + l + "'" + "where s.s_no='" + staff + "'";
                        try{
                            ResultSet rs = executeQuery(conn, commnd);
                            if (rs.next()){
                                String s_fname = rs.getString("S_FNAME");
                                String s_lname = rs.getString("S_LNAME");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                        
                    case 2:
                        System.out.println("Enter Sex (F/M)");
                        String s = in.nextLine();
                        String commnd3 = "update staff s set s.S_SEX ='" + s + "'" + "where s.s_no='" + staff + "'";
                        try{
                            ResultSet rs = executeQuery(conn, commnd3);
                            if (rs.next()){
                                String sex = rs.getString("S_SEX");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                    case 3:
                        System.out.println("Enter Street:");
                        String street = in.nextLine();
                        System.out.println("\nEnter City:");
                        String city = in.nextLine();
                        System.out.println("\nEnter Zipcode:");
                        String zip = in.nextLine();
                        System.out.println("\nEnter Country:");
                        String country = in.nextLine();
                        String command1 = "update staff s set s.S_STREET ='" + street + "'," + "s.S_COUNTRY ='" + country + "'," + "s.S_CITY ='" + city + "'," + "s.S_POSTCODE ='" + zip + "'" + "where s.s_no='" + staff + "'";
                        try{
                            ResultSet rs = executeQuery(conn, command1);
                            if (rs.next()){
                                String s_street = rs.getString("S_STREET");
                                String s_city = rs.getString("S_CITY");
                                String s_pcode = rs.getString("S_POSTCODE");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                        
                     case 4:
                        System.out.println("Enter Position:");
                        String position = in.nextLine();
                        String command4 = "update staff s set s.S_POSITION ='" + position + "'" + "where s.s_no='" + staff + "'";
                        try{
                            ResultSet rs = executeQuery(conn, command4);
                            if (rs.next()){
                                String s_position = rs.getString("S_POSITION");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                         
                     case 5:
                        System.out.println("Enter Location:");
                        String location = in.nextLine();
                        String command5 = "update staff s set s.S_LOCATION ='" + location + "'" + "where s.s_no='" + staff + "'";
                        try{
                            ResultSet rs = executeQuery(conn, command5);
                            if (rs.next()){
                                String s_location = rs.getString("S_LOCATION");
                               // System.out.println("Updated!!!");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                     
                     case 6:
                         System.out.println("Enter DOB in DD-MMM-YYYY");
                        String db = in.nextLine();
                        String commn3 = "update staff s set s.S_DOB ='" + db + "'" + "where s.s_no='" + staff + "'";
                        try{
                            ResultSet rs = executeQuery(conn, commn3);
                            if (rs.next()){
                                String dob = rs.getString("S_DOB");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                     default:
                         return;
                    }
                
            } 
        }
        public static void updateProfile_guest(){
            
            int selection;
            Scanner in = new Scanner(System.in);
            System.out.println("** update profile **");
            while(true){
            int approval = Integer.parseInt(appId);
            //String command = "select * from guests,users where USER_NO=" + approval + ";";
            System.out.println("\nUPDATE");
            System.out.println("1. Change Name");
            System.out.println("2. Change Nationality");
            System.out.println("3. Change Address");
            System.out.println("4. Change Sex");
            System.out.println("5. Change Phone Numbers");
            System.out.println("6. Special Status (Y/N)");
            System.out.println("7. Smoking Status (Y/N)");
            System.out.println("8. Change DOB");
            System.out.println("9. Change Kin's Name");
            System.out.println("10. Change Kin's Relation");
            System.out.println("11. Change Kin's Address");
            System.out.println("12. Change Kin's Phone Number");
            System.out.println("13. Family Status (Y/N)");
            System.out.println("14. Change Course");
            System.out.println("15. Change Category");
            System.out.println("16. Change Hobbies");
            System.out.println("17. Back");
            selection = readInputOption(1,17);
            switch (selection){
                case 1:
                        System.out.println("Enter First Name");
                        String f = in.nextLine();
                        System.out.println("Enter Last Name");
                        String l = in.nextLine();
                        String commnd = "update users u set u.fname ='" + f + "'," + "u.lname ='" + l + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, commnd);
                            if (rs.next()){
                                String s_fname = rs.getString("fname");
                                String s_lname = rs.getString("lname");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                    case 2:
                    System.out.println("Enter Nationality");
                        String nation = in.nextLine();
                        String comma6 = "update users u set u.nationality ='" + nation + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, comma6);
                            if (rs.next()){
                                String s_nationality = rs.getString("NATIONALITY");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                 case 3:
                        System.out.println("Enter Street:");
                        String street = in.nextLine();
                        System.out.println("\nEnter City:");
                        String city = in.nextLine();
                        System.out.println("\nEnter Zipcode:");
                        String zip = in.nextLine();
                        System.out.println("\nEnter Country:");
                        String country = in.nextLine();
                        String command1 = "update users u set u.street ='" + street + "'," + "u.city ='" + city + "'," + "u.u_country ='" + country + "'," + "u.zipcode ='" + zip + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, command1);
                            if (rs.next()){
                                String s_street = rs.getString("STREET");
                                String s_city = rs.getString("CITY");
                                String s_zip = rs.getString("ZIPCODE");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                 case 4:
                     System.out.println("Sex (F/M)");
                        String sex = in.nextLine();
                        String cmd = "update users u set u.sex ='" + sex + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, cmd);
                            if (rs.next()){
                                String s = rs.getString("SEX");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                 case 5:
                     System.out.println("Enter Phone Numbers:");
                        String p1 = in.nextLine();
                        String p2 = in.nextLine();
                        String command7 = "update users u set u.phone='" + p1 + "'," + "u.a_phone='" + p2 + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, command7);
                            if (rs.next()){
                                String s_phone = rs.getString("PHONE");
                                String s_aphone = rs.getString("A_PHONE");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                 case 6:
                     System.out.println("Special Status (Y/N)");
                        String special = in.nextLine();
                        String command6 = "update users u set u.special ='" + special + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, command6);
                            if (rs.next()){
                                String s_special = rs.getString("SPECIAL");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                 case 7:
                     System.out.println("Smoking Status (Y/N)");
                        String smoke = in.nextLine();
                        String command5 = "update users u set u.smoker ='" + smoke + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, command5);
                            if (rs.next()){
                                String smoking = rs.getString("SMOKER");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                 case 8:
                     System.out.println("Enter DOB in DD-MMM-YYYY");
                        String db = in.nextLine();
                        String comman5 = "update users u set u.dob ='" + db + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, comman5);
                            if (rs.next()){
                                String dob = rs.getString("DOB");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                  case 9:
                     System.out.println("Enter Kin's name");
                        String name = in.nextLine();
                        String co = "update users u set u.kin_name ='" + name + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, co);
                            if (rs.next()){
                                String dob = rs.getString("kin_name");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                   case 10:
                     System.out.println("Enter Kin's relation");
                        String rel = in.nextLine();
                        String co1 = "update users u set u.KIN_RELATION ='" + rel + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, co1);
                            if (rs.next()){
                                String dob = rs.getString("KIN_RELATION");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                    case 11:
                     System.out.println("Enter Kin's address");
                        String add = in.nextLine();
                        String co2 = "update users u set u.KIN_ADDR ='" + add + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, co2);
                            if (rs.next()){
                                String dob = rs.getString("KIN_ADDR");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                     case 12:
                     System.out.println("Enter Kin's phone number");
                        String no = in.nextLine();
                        String coo2 = "update users u set u.KIN_PHONE ='" + no + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, coo2);
                            if (rs.next()){
                                String dob = rs.getString("KIN_PHONE");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                      case 13:
                     System.out.println("Enter family status (Y/N)");
                        String st = in.nextLine();
                        String cooo2 = "update users u set u.FAMILY ='" + st + "'" + "where u.user_no=(select g.app_id from guests g where g.app_id='" + approval + "')";
                        try{
                            ResultSet rs = executeQuery(conn, cooo2);
                            if (rs.next()){
                                String dob = rs.getString("FAMILY");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                 default:
                     return;
                    case 14:
                        System.out.println("Enter Course details");
                        String course = in.nextLine();
                        String command2 = "update guests s set s.G_COURSES ='" + course + "'" + "where s.APP_ID='" + approval + "'";
                        try{
                            ResultSet rs = executeQuery(conn, command2);
                            if (rs.next()){
                                String s_course = rs.getString("G_COURSES");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                   case 15:
                        System.out.println("Enter Category");
                        String category = in.nextLine();
                        String command3 = "update guests s set s.G_CAT ='" + category + "'" + "where s.APP_ID='" + approval + "'";
                        try{
                            ResultSet rs = executeQuery(conn, command3);
                            if (rs.next()){
                                String s_category = rs.getString("G_CAT");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                    case 16:
                        System.out.println("Enter Hobbies");
                        String hobbies = in.nextLine();
                        String command4 = "update guests s set s.G_INTERESTS ='" + hobbies + "'" + "where s.APP_ID='" + approval + "'";
                        try{
                            ResultSet rs = executeQuery(conn, command4);
                            if (rs.next()){
                                String interest = rs.getString("G_INTERESTS");
                            }
                        
                            else
                                System.out.println("ERROR!!!");
                        }
                        catch (SQLException e ) {}
                        System.out.println("Updated!!!");
                        break;
                }
			
            } 
        }
        public static void viewProfile_student(){
            System.out.println("** profile info **");
                 String fam_fname;
                 String fam_lname;
                 Date fam_dob;
               //  String family;
                String command = "select * from users u,students s where u.user_no=s.SID and u.user_no ='" + unity + "'";
              //  System.out.println(command);
            try{
                    ResultSet rs2 = executeQuery(conn, command);
                    if (rs2.next()){
                        int s_id = rs2.getInt("SID");
                        String s_fname = rs2.getString("FNAME");
                        String s_lname = rs2.getString("LNAME");
                        String s_street = rs2.getString("STREET");
                        String course = rs2.getString("S_COURSES");
                        String category = rs2.getString("S_CATEGORY");
                        String hobbies = rs2.getString("INTERESTS");
                        String country = rs2.getString("U_COUNTRY");
                        String phone = rs2.getString("PHONE");
                        String a_phone = rs2.getString("A_PHONE");
                        String special = rs2.getString("SPECIAL");
                        String s_city = rs2.getString("CITY");
                        int s_zcode = rs2.getInt("ZIPCODE");
                        Date s_dob = rs2.getDate("DOB");
                        String s_sex = rs2.getString("SEX");
                        String nation = rs2.getString("NATIONALITY");
                        String smoker = rs2.getString("SMOKER");
                        String status = rs2.getString("STATUS");
                        String kin_name = rs2.getString("KIN_NAME");
                        String family = rs2.getString("FAMILY");
                        String kin_relation = rs2.getString("KIN_RELATION");
                        String kin_add = rs2.getString("KIN_ADDR");
                        String kin_ph = rs2.getString("KIN_PHONE");
                        System.out.println("S_ID: " + s_id + "\nFirst: " + s_fname + "\nLast: " + s_lname + "\nStreet: "+ s_street + "\nCity: " + s_city + "\nCountry: " + country + "\nZip: " + s_zcode + "\nDOB: " + s_dob+ "\nSex: " + s_sex + "\nCourse: "+course +"\nCategory: "+category+"\nPhone: "+phone+"\nAlternate Phone: "+a_phone+"\nHobbies: "+ hobbies +"\nNationality: "+ nation+"\nSmoking: "+ smoker+"\nSpecial: "+special + "\nStatus: " + status + "\nFamily Status: " + family);
                        System.out.println("\nNext of kin:");
                        System.out.println("Kin Name: " + kin_name +"\nKin Relationship:" + kin_relation + "\nKin Address: " + kin_add + "\nKin Phone: " + kin_ph );
                        family = family.trim();
                        
                        if (!family.equalsIgnoreCase("Y"))
                         System.out.println("No family details");
                        else{
                            String command1 = "select fs.F_FN,fs.F_LN,fs.F_DOB from students s, fam_stud  fs  where s.SID = fs.SID and s.SID ='" + unity + "'";
                            ResultSet rs1 = executeQuery(conn, command1);  
                            while (rs1.next()){
                                fam_fname = rs1.getString("F_FN");
                                fam_lname = rs1.getString("F_LN");
                                fam_dob = rs1.getDate("F_DOB");
                                System.out.println("Family Details:");
                                System.out.println("First Name: " + fam_fname +"\nLast Name: " + fam_lname + "\nDate of Birth: " + fam_dob);
                            }
//                            else
//                                System.out.println("ERROR!!!");
                        }
                          
                    }
                    else
                        System.out.println("ERROR!!!");
  
            }        
                catch (SQLException e ) {}
                
        }
        public static void viewProfile_guest(){
            
            System.out.println("** profile info **");
                  String fam_fname;
                 String fam_lname;
                 Date fam_dob;
                int approval = Integer.parseInt(appId);
                String command = "select * from users u,guests s where u.user_no=s.APP_ID and u.user_no ='" + approval + "'";
              //  System.out.println(command);
            try{
            	ResultSet rs = executeQuery(conn, command);
                if (rs.next())
                {
                	int s_id = rs.getInt("APP_ID");
                	String s_fname = rs.getString("FNAME");
                        String s_lname = rs.getString("LNAME");
                        String s_street = rs.getString("STREET");
                        String country = rs.getString("U_COUNTRY");
                        String phone = rs.getString("PHONE");
                        String a_phone = rs.getString("A_PHONE");
                        String special = rs.getString("SPECIAL");
                        String s_city = rs.getString("CITY");
                        int s_zcode = rs.getInt("ZIPCODE");
                        Date s_dob = rs.getDate("DOB");
                        String s_sex = rs.getString("SEX");
                        String nation = rs.getString("NATIONALITY");
                        String smoker = rs.getString("SMOKER");
                        String interests = rs.getString("G_INTERESTS");
                        String courses = rs.getString("G_COURSES");
                        String cat = rs.getString("G_CAT");
                        String status = rs.getString("STATUS");
                        String kin_name = rs.getString("KIN_NAME");
                        String kin_relation = rs.getString("KIN_RELATION");
                        String kin_add = rs.getString("KIN_ADDR");
                        String kin_ph = rs.getString("KIN_PHONE");
                        String family = rs.getString("FAMILY");
                        System.out.println("Approval_id: " + s_id + "\nFirst: " + s_fname + "\nLast: " + s_lname + "\nStreet: "+ s_street + "\nCity: " + s_city + "\nCountry: " + country + "\nZip: " + s_zcode + "\nDOB: " + s_dob+ "\nSex: " + s_sex + "\nPhone: "+phone+"\nAlternate Phone: "+a_phone+ "\nNationality: "+ nation+"\nSmoking: "+ smoker+"\nSpecial: "+special + "\nInterests: "+interests +"\nCategory: "+cat+"\nCourses: " + courses + "\nStatus: " + status + "\nFamily Status: " + family);
                        System.out.println("\nNext of kin:");
                        System.out.println("Kin Name: " + kin_name +"\nKin Relationship:" + kin_relation + "\nKin Address: " + kin_add + "\nKin Phone: " + kin_ph );
                        family = family.trim();
                        
                        if (!family.equalsIgnoreCase("Y"))
                         System.out.println("No family details");
                        else{
                        String command1 = "select fs.F_FN,fs.F_LN,fs.F_DOB from guests s, fam_guest  fs  where s.APP_ID = fs.APP_ID and s.APP_ID ='" + approval + "'";
                            ResultSet rs1 = executeQuery(conn, command1);  
                            while(rs1.next()){
                                fam_fname = rs1.getString("F_FN");
                                fam_lname = rs1.getString("F_LN");
                                fam_dob = rs1.getDate("F_DOB");
                                System.out.println("Family Details:");
                                System.out.println("First Name: " + fam_fname +"\nLast Name: " + fam_lname + "\nDate of Birth: " + fam_dob);
                            }
                           
                        }
                          
                    }
                    else
                        System.out.println("ERROR!!!");
  
            }        
                catch (SQLException e ) {}
        }
	public static void viewProfile_admin()
        {
		System.out.println("** profile info **");
             
                int staff = Integer.parseInt(id);
                String command = "select * from staff s where s.S_NO=" + staff + "";
               // System.out.println(command);
            try{
            	ResultSet rs = executeQuery(conn, command);
                if (rs.next())
                {
                	int s_id = rs.getInt("S_NO");
                	String s_fname = rs.getString("S_FNAME");
                        String s_lname = rs.getString("S_LNAME");
                        String s_street = rs.getString("S_STREET");
                        String s_city = rs.getString("S_CITY");
                        String s_country = rs.getString("S_COUNTRY");
                        int s_zcode = rs.getInt("S_POSTCODE");
                        Date s_dob = rs.getDate("S_DOB");
                        String s_sex = rs.getString("S_SEX");
                        String s_position = rs.getString("S_POSITION");
                        String s_location = rs.getString("S_LOCATION");
                        
                        System.out.println("Staff_id: " + s_id + "\nFirst Name: " + s_fname + "\nLast Name: " + s_lname + "\nAddress: "+ s_street + s_city + s_country + s_zcode + "\nDOB: " + s_dob+ "\nSex: " + s_sex + "\nPosition: " + s_position + "\nWork Location: " + s_location);
                }
                else
                    System.out.println("ERROR!!!");
            }
                catch (SQLException e ) {}
                
        }
        
        //4A
	public static Boolean authAdmin(){
            Scanner in = new Scanner(System.in);
            
            while(true){
                
                System.out.println("Enter your STAFF ID or go back");
                id = in.nextLine();
		
                if (id.equals("back"))
                    return false;
                
                int staff = Integer.parseInt(id);
                String command = "select s_no from staff where s_no='" + staff + "'";
              //  System.out.println(command);
            try{
            	ResultSet rs = executeQuery(conn, command);
                if (rs.next())
                {
                	String u_id = rs.getString("S_NO");
	            	u_id = u_id.trim();
	            	if (!u_id.equals(id))
	            	{
	            		System.out.println("Login incorrect");
	            		continue;
	            	}	
                }
                else
                {
                	System.out.println("Login incorrect");
            		continue;
                }
            	   
            }catch (SQLException e ) {}
            
         return true;
	        
		}
	}
                public static void showAdminOptions() throws IOException
	{
//		String id;
		int iType;
		Scanner in = new Scanner(System.in);
                
		if (!authAdmin())
			return;
                while(true){
			System.out.println("\nAdmin options:");
			System.out.println("1. View new lease requests");
			System.out.println("2. View terminate lease requests");
			System.out.println("3. View maintenance tickets");
			System.out.println("4. View parking requests");
			System.out.println("5. Profile");
			System.out.println("6. Back");
			
			iType = readInputOption(1,6);
			
			if (iType == 1)
				showNewLeaseRequests();
			else if (iType == 2)
				showTerminateLeaseRequests();
			else if (iType == 3)
				showMaintainanceTickets();
			else if (iType == 4)
				showParkingRequests();
			else if (iType == 5)
				showProfileOptions(2);
                        else 
                            return;
		}
                }
			
	
      //4A.1
	public static void showNewLeaseRequests()
	{
		//fetch all lease request
		//display them numerically.
		//take input from user to modify
		//process
		System.out.println("All lease requests:");
	}
	
	//4A.2
	public static void showTerminateLeaseRequests()
	{
		//fetch and terminate
		System.out.println("Termination requests:");
	}
	
	//4A.3
	public static void showMaintainanceTickets()
	{       
                    int staff = Integer.parseInt(id);

		System.out.println("Pending Maintainance Tickets List:");
                String command = "select t.t_no,t.t_type,t.T_SDATE, t.T_EDATE,t.T_STATUS,t.T_DESC from tickets t,staff s where s.LOC_ID=t.T_LOCATION and s.S_NO='" + staff + "'";
                try{
	        	
	        	ResultSet rs = executeQuery(conn, command);
	            while(rs.next()){
                        int t_no = rs.getInt("t_no");
                        int t_type = rs.getInt("t_type");
                        Date sdate = rs.getDate("T_SDATE");
                        Date edate = rs.getDate("T_EDATE");
                        String st = rs.getString("T_STATUS");
                        String de = rs.getString("T_DESC");
                        System.out.println("Ticket No: " + t_no + "\nTicket Type: " + t_type + "\nStart Date: " + sdate + "\nEnd Date: " + edate + "\nStatus: "+st + "\nDescription: "+de);
                        
                        String command1 = "update tickets t set t.T_EDATE=SYSDATE,t.T_STATUS='COMPLETED' where t.T_STATUS='PENDING' and t.T_LOCATION=(select loc_id from staff s where s.S_NO='" + staff + "')";
                                ResultSet rs1 = executeQuery(conn, command1);
                                if (rs1.next()){
                                    String status = rs1.getString("T_STATUS");
                                    Date date = rs1.getDate("T_EDATE");
                                }
                    }
                }
                    catch (SQLException e ) {}
        }
	
	//4A.4
	public static void showParkingRequests()
	{
		//fetch all requests
		System.out.println("Pending parking spot requests:");
	}
	

	public static Boolean authGuest() 
	{
		Scanner in = new Scanner(System.in);
		
		while (true)
		{
			System.out.println("Enter APPROVAL ID or return back");
			appId = in.nextLine();
                        if (appId.equals("back"))
                            return false;
                        int approval = Integer.parseInt(appId);
			String command = "select g.APP_ID from guests g where g.APP_ID='" + approval + "'";
	       // System.out.println(command);
	        
	        try{
	        	
	        	ResultSet rs = executeQuery(conn, command);
	            if (rs.next())
	            {
	            	String u_id = rs.getString("APP_ID");
                        u_id = u_id.trim();
	            	if (!u_id.equals(appId))
	            	{
	            		System.out.println("Login incorrect");
	            		continue;
	            	}
	            }
	            else
	            {
	            	System.out.println("Login incorrect");
	        		continue;
	            }
	        	
	            
	        }catch (SQLException e ) {}
	        
	        return true;
	        
		}
	}
	public static void guestLogin() throws IOException
	{
		//String type;
		int iType;
		Scanner in = new Scanner(System.in);
		
                if (!authGuest())
			return;
		
		while(true)
		{
			System.out.println("Choose one of the following options");
			System.out.println("1. Housing");
			System.out.println("2. Parking");
			System.out.println("3. Maintainance");
			System.out.println("4. Profile");
			System.out.println("5. Back");
			
			iType = readInputOption(1,5);
			
			switch (iType)
			{
				case 1:
					Housing_options(2);
                                        //3A
					break;
				case 2:
                                        showParkingOption(2);
                                        //3B
					break;
				case 3:
					showMaintainance(2);
					break;
				case 4:
					showProfileOptions(3);
				default:
					return;
			}
		}
		
	}
	
	public static int readInputOption(int min, int max)
	{
		String type;
		int iType;
		Scanner in = new Scanner(System.in);
		
		while (true)
		{
			type = in.nextLine();
			if (type.equals(""))
				continue;
			iType = Integer.parseInt(type);
			if (iType < min || iType > max)
			{
				System.out.println("Invalid choice");
			}
			else
				break;
		}
		return iType;
		
	}
	public static void main(String[] args) throws IOException {
		String command = "CREATE TABLE COFFEES " +
				   "(COF_NAME VARCHAR(32), SUP_ID INTEGER, " +
				   "PRICE FLOAT, SALES INTEGER, TOTAL INTEGER)";
		
		//sb
		conn = connectToOracle();
		
		//executeUpdate(conn, command);
		//close(conn);
		//showMaintainance();
		//showProfileOptions();
		//showAdminOptions();
		//showGuestOptions();
		login();
		System.out.println("Exit main function");
	}
}
