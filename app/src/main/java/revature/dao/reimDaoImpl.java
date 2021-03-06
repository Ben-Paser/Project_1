package revature.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import revature.Models.Reimbursement;

import static revature.util.Log.logger;
import static revature.util.ConnectionUtil.con;
import static revature.dao.reimDaoImpl.PARAMS.*;

public class reimDaoImpl implements reimDao {

    public enum PARAMS{  
        RID("reimid"),      
        AMOUNT("amount"),
        SUBM("submitted"),
        RESOLV("resolved"),
        DESCR("description"),
        RECEIPT("receipt"),
        AUTHOR("author"),
        RESOLVER("resolver"),
        STATUS("statusid"),
        TYPE("typeid"),
        FNAME("fname"),
        LNAME("lname"),
        EMAIL("email"),
        UNAME("username");

  
        private final String text;

        PARAMS(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }

    

    @Override
    public boolean createReim(Reimbursement reim) throws SQLException {
        String sql = "insert into project01.reimbursement (amount, submitted, description, author, statusid, typeid) values (?,?,?,?,?,?)";
        PreparedStatement ps;
        try{
            ps = con.prepareStatement(sql);
        }catch(SQLException e){
            logger.warn(e);
            return false;
        }
        
        ps.setDouble(1, reim.getAmount());
        ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        ps.setString(3, reim.getDescription());
        // ps.setTimestamp(8, reim.getResolved());
        ps.setInt(4, reim.getAuthor());
        // ps.setInt(5, reim.getResolver());
        ps.setInt(5, 1);
        ps.setInt(6, reim.getType_ID());
        
        int rowsAffected = ps.executeUpdate();
        if(rowsAffected==1){
            return true;
        }

        return false;
    }

    @Override
    public List<Reimbursement> getAllReim() throws SQLException {
        String sql = "select * from project01.reimbursement as s inner join project01.users as u on s.author=u.userid";
        List<Reimbursement> reims = new ArrayList<>();
        Statement  s;
        try{
            s= con.createStatement();
        }catch(SQLException e){
            logger.warn(e);
            return reims;
        }
        ResultSet rs = s.executeQuery(sql);
        
        
        int id=-1;
        double amount=-1;
        Timestamp subm=new Timestamp(0);
        Timestamp resolved=new Timestamp(0);
        int resolver=-1;
        int author=-1;
        int type=-1;
        int status=-1;
        String descr="";
        String authorFname="";
        String authorLname="";
        String authorUname="";
        String authoremail="";
        //String receipt="";
        while(rs.next()) {
            id=-1;
            amount=-1;
            subm=new Timestamp(0);
            resolved=new Timestamp(0);
            resolver=-1;
            author=-1;
            type=-1;
            status=-1;
            descr="";
            authorFname="";
            authorLname="";
            authorUname="";
            authoremail="";

            try{
                id = rs.getInt(RID.toString());
                amount=rs.getDouble(AMOUNT.toString());
                subm=rs.getTimestamp(SUBM.toString());
                resolved=rs.getTimestamp(RESOLV.toString());
                descr=rs.getString(DESCR.toString());
                //receipt=rs.getString(RECEIPT.toString());
                author=rs.getInt(AUTHOR.toString());
                resolver=rs.getInt(RESOLVER.toString());
                status=rs.getInt(STATUS.toString());
                type=rs.getInt(TYPE.toString());
                authorFname=rs.getString(FNAME.toString());
                authorLname=rs.getString(LNAME.toString());
                authorUname=rs.getString(UNAME.toString());
                authoremail=rs.getString(EMAIL.toString());
            }catch(SQLException e){
                logger.trace(e);
                // throw new SQLException("No found reimbursement matching those parameters.");
            }

            reims.add(new Reimbursement(id, amount,subm,resolved,descr,authorFname,authorLname,authorUname,authoremail,author,resolver,status,type));

        }


        return reims;
    }

    @Override
    public Reimbursement getReimById(int id) throws SQLException {
        String sql = "select * from project01.reimbursement as s inner join project01.users as u on s.author=u.userid WHERE reimid = ? LIMIT 1";
        
        PreparedStatement s;

        try{
            s = con.prepareStatement(sql);
        }catch(SQLException e){
            logger.warn(e);
            return new Reimbursement();
        }

        s.setInt(1, id);
        
        ResultSet rs = s.executeQuery();
        
        double amount=-1;
        Timestamp subm=new Timestamp(0);
        Timestamp resolved=new Timestamp(0);
        int resolver=-1;
        int author=-1;
        int type=-1;
        int status=-1;
        String descr="";
        String authorFname="";
        String authorLname="";
        String authorUname="";
        String authoremail="";
        //String receipt="";
        rs.next();

        try{
            // id = rs.getInt(RID.toString());
            amount=rs.getDouble(AMOUNT.toString());
            subm=rs.getTimestamp(SUBM.toString());
            resolved=rs.getTimestamp(RESOLV.toString());
            descr=rs.getString(DESCR.toString());
            //receipt=rs.getString(RECEIPT.toString());
            author=rs.getInt(AUTHOR.toString());
            resolver=rs.getInt(RESOLVER.toString());
            status=rs.getInt(STATUS.toString());
            type=rs.getInt(TYPE.toString());
            authorFname=rs.getString(FNAME.toString());
            authorLname=rs.getString(LNAME.toString());
            authorUname=rs.getString(UNAME.toString());
            authoremail=rs.getString(EMAIL.toString());
        }catch(SQLException e){
            logger.trace(e);
            throw new SQLException("No found reimbursement matching those parameters.");
        }

        Reimbursement reim = new Reimbursement(id, amount,subm,resolved,descr,authorFname,authorLname,authorUname,authoremail,author,resolver,status,type);


        return reim;
    }

    @Override
    public List<Reimbursement> getReimByStatus(int status,int author) throws SQLException {
        String sql;
        PreparedStatement s;
        List<Reimbursement> reims = new ArrayList<>();

        if(author!=-1&&status==-1){
            sql = "select * from project01.reimbursement as s inner join project01.users as u on s.author=u.userid WHERE author = ?";
            try{
                s = con.prepareStatement(sql);
            }catch(SQLException e){
                logger.warn(e);
                return reims;
            }
            s.setInt(1, author);
        }
        else if (author==-1&&status!=-1){
            sql = "select * from project01.reimbursement as s inner join project01.users as u on s.author=u.userid WHERE statusid = ?";
            try{
                s = con.prepareStatement(sql);
            }catch(SQLException e){
                logger.warn(e);
                return reims;
            }
            s.setInt(1, status);
        }
        else {
            sql = "select * from project01.reimbursement as s inner join project01.users as u on s.author=u.userid WHERE statusid = ? AND  author = ?";
            try{
                s = con.prepareStatement(sql);
            }catch(SQLException e){
                logger.warn(e);
                return reims;
            }
            s.setInt(1, status);
            s.setInt(2, author);
        }
        
        ResultSet rs = s.executeQuery();

        

        int id=-1;
        double amount=-1;
        Timestamp subm=new Timestamp(0);
        Timestamp resolved=new Timestamp(0);
        int resolver=-1;
        int type=-1;
        String descr="";
        String authorFname="";
        String authorLname="";
        String authorUname="";
        String authoremail="";
        //String receipt="";
        while(rs.next()) {
            id=-1;
            amount=-1;
            subm=new Timestamp(0);
            resolved=new Timestamp(0);
            resolver=-1;
            author=-1;
            type=-1;
            status=-1;
            descr="";
            authorFname="";
            authorLname="";
            authorUname="";
            authoremail="";


            try{
                id = rs.getInt(RID.toString());
                amount=rs.getDouble(AMOUNT.toString());
                subm=rs.getTimestamp(SUBM.toString());
                resolved=rs.getTimestamp(RESOLV.toString());
                descr=rs.getString(DESCR.toString());
                //receipt=rs.getString(RECEIPT.toString());
                author=rs.getInt(AUTHOR.toString());
                resolver=rs.getInt(RESOLVER.toString());
                type=rs.getInt(TYPE.toString());
                status=rs.getInt(STATUS.toString());
                authorFname=rs.getString(FNAME.toString());
                authorLname=rs.getString(LNAME.toString());
                authorUname=rs.getString(UNAME.toString());
                authoremail=rs.getString(EMAIL.toString());

            }catch(SQLException e){
                logger.trace(e);
                // throw new SQLException("No found reimbursement matching those parameters.");
            }
            reims.add(new Reimbursement(id, amount,subm,resolved,descr,authorFname,authorLname,authorUname,authoremail,author,resolver,status,type));

        }


        return reims;
    }

    @Override
    public boolean updateReim(Reimbursement reim) throws SQLException {
        String sql = "UPDATE project01.reimbursement SET amount=?,submitted=?,resolved = ?,description=?,author = ?,resolver=?,statusid=?,typeid=? WHERE reimid = ?";

        PreparedStatement ps;

        try{
            ps = con.prepareStatement(sql);
        }catch(SQLException e){
            logger.warn(e);
            return false;
        }


        ps.setDouble(1, reim.getAmount());
        ps.setTimestamp(2, reim.getSubmitted());
        ps.setTimestamp(3, reim.getResolved());
        ps.setString(4, reim.getDescription());
        // ps.setString(5, reim.getr());
        ps.setInt(5, reim.getAuthor());
        ps.setInt(6, reim.getResolver());
        ps.setInt(7, reim.getStatus_ID());
        ps.setInt(8, reim.getType_ID());

        int rowsAffected = ps.executeUpdate();
        if(rowsAffected==1){
            return true;
        }

        return false;
    }

    @Override
    public boolean validate(int id, int status,int resolver) throws SQLException {
        String sql = "UPDATE project01.reimbursement SET statusid = ? ,resolved = ? , resolver = ? WHERE reimid = ?";


        PreparedStatement ps;

        try{
            ps = con.prepareStatement(sql);
        }catch(SQLException e){
            logger.warn(e);
            return false;
        }

        ps.setInt(1,status);
        ps.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
        ps.setInt(3, resolver);
        ps.setInt(4,id);


        int rowsAffected = ps.executeUpdate();
        if(rowsAffected==1){
            return true;
        }

        return false;
    }

    
    
    
}
