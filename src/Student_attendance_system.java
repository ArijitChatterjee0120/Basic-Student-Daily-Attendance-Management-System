import java.sql.*;
import java.util.*;
public class Student_attendance_system
{
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) throws Exception
	{	Connection con = null ;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/arijit?useSSL=false","root","Arijit@0120");
		   int choice=1;
			while(choice==1)
			{
				studentdetails(con);	
				System.out.println("If you want to add more students press 1 otherwise any other keys=");
				choice=input.nextInt();
			}
			attendenceofstudent(con);
			System.out.println("If you want to check whether a student is present on a particular date first press 5 then enter the date otherwise any other number=");
			int particulardatepresent=input.nextInt();
			if(particulardatepresent==5)
			{
			System.out.println("Enter the data to check=");
			String date=input.next();
			int present=getattendenceofstudent(con,date);
			System.out.println("No. of students present on "+date+"="+present); 
			}
			System.out.println("If you want to display all the female candidates name then press 10 otherwise any other number=");
			int female=input.nextInt();
			if(female==10)
			{
			allfemalestudents(con);
			}
			System.out.println("If you want to display all the male candidates name then press 11 otherwise any other number=");
			int male=input.nextInt();
			if(male==11)
			{
				allmalestudents(con);
			}
			System.out.println("If you want to display all the name of the students present press 8 otherwise any other number= ");
			int pres=input.nextInt();
			if(pres==8)
			{
				allstudentspresent(con);
			}
			System.out.println("If you want to display all the name of the students who are absent press 15 otherwise any other number= ");
			int abse=input.nextInt();
			if(abse==15)
			{
				allstudentsabsent(con);
			}
			
		}	
		catch(Exception e) {
			System.out.println(e);
		}
		
		con.close();
		
	}
	static void studentdetails(Connection con) throws SQLException
	{
		Statement stmt = con.createStatement();
		System.out.println("Enter first name of the student=");
		String Firstname = input.next();
		System.out.println("Enter last name of the student=");
		String Lastname = input.next();
		System.out.println("Male/Female/Other?=");
		String Sex= input.next();
		System.out.println("Enter the maid-id=");
		String Mailid=input.next();
		System.out.println("Enter the state where student live=");
		String State=input.next();
		System.out.println("Enter the district of the student =");
		String District=input.next();
		System.out.println("Enter the course of the student=");
		String Course=input.next();
		System.out.println("Enter the department of the student=");
		String Department=input.next();
		System.out.println("Enter the date in form of DD-MMM-YYYY=");
		String Date_Of_Birth=input.next();
		System.out.println("Enter the address of the student=");
		String Address=input.next();
		String query = "INSERT INTO student_details(Firstname,Lastname,Sex,Mailid,State,District,Course,Department,Date_Of_Birth,Address) VALUES"
				+ " ('"+Firstname+"','"+Lastname+"','"+Sex+"','"+Mailid+"','"+State+"','"+District+"','"+Course+"','"+Department+"','"+Date_Of_Birth+"','"+Address+"')";
		
		stmt.execute(query);
	}
	static void attendenceofstudent(Connection con) throws SQLException
	{
		Statement stmt = con.createStatement();
		Statement stmt2 = con.createStatement();
		String query ="SELECT * from student_details";
		boolean status = stmt.execute(query);
        if(status){
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                String Roll_num = rs.getString(1);
                String name = rs.getString("Firstname");
                System.out.println(" IS "+name+" Present ?If Present Reply True otherwise False");
                boolean present = input.nextBoolean();
                String q;
                if(present) {
                	q = "INSERT into student_attendance(STUDENT_ID,PRESENT) VALUES("+Roll_num+",true) ";
                }else {
                	q = "INSERT into student_attendance(STUDENT_ID) VALUES("+Roll_num+") ";
                }
                
                stmt2.execute(q);
                
            }
           
        } else {
            int cnt = stmt.getUpdateCount();
            System.out.println("The total number of records updated="+cnt);
        }	
	}
	static int getattendenceofstudent(Connection con,String date) throws SQLException
	{
		Statement stmt = con.createStatement();
		int count =0;
		String query ="SELECT count(*) from student_attendance where PRESENT=true and date(created_at) ='"+date+"'" ;
		boolean status = stmt.execute(query);
		 ResultSet rs = stmt.getResultSet();
         while(rs.next()){
             count = rs.getInt(1);
         }
         int temp=count;
         count=0;
		return temp;
	}
	static void allfemalestudents(Connection con)throws SQLException
	{
		Statement stmt = con.createStatement();
		String query = "Select *From student_details where Sex='Female' ";
		boolean status = stmt.execute(query);
        if(status){
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                String fname = rs.getString("Firstname");
                String sname = rs.getString("Lastname");
                System.out.println(fname+"\t"+sname);
            	 
                }
        }
	}
	static void allmalestudents(Connection con)throws SQLException
	{
		Statement stmt = con.createStatement();
		String query = "Select *From student_details where GENDER='Male' ";
		boolean status = stmt.execute(query);
        if(status){
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                String fname = rs.getString("Firstname");
                String sname = rs.getString("Lastname");
                System.out.println(fname+"\t"+sname);
            	 
                }
        }
	}
        static void allstudentspresent(Connection con)throws SQLException
	{
		Statement stmt = con.createStatement();
		String query = "select * from student_details inner join student_attendance on student_details.Roll_number=student_attendance.STUDENT_ID where PRESENT='1'";
		boolean status = stmt.execute(query);
        if(status){
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
            	 String fname = rs.getString("Firstname");
                 String sname = rs.getString("Lastname");
                 System.out.println(fname+"\t"+sname);
                }
        }
	}
        static void allstudentsabsent(Connection con)throws SQLException
    	{
    		Statement stmt = con.createStatement();
    		String query = "select * from student_details inner join student_attendance on student_details.Roll_number=student_attendance.STUDENT_ID where PRESENT='0'";
    		boolean status = stmt.execute(query);
            if(status){
                ResultSet rs = stmt.getResultSet();
                while(rs.next()){
                	 String fname = rs.getString("Firstname");
                     String sname = rs.getString("Lastname");
                     System.out.println(fname+"\t"+sname);
                    }
            }
    		
    	}

	
}