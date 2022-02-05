package revature.services;

import java.sql.SQLException;
import java.util.List;

import revature.dao.reimDaoImpl;
import revature.Models.Reimbursement;

public class ReimService {
    final static reimDaoImpl reim = new reimDaoImpl();

    public static List<Reimbursement> get() throws SQLException{
        return reim.getAllReim();
    }

    public static Reimbursement getById(int id) throws SQLException{
        return reim.getReimById(id);
    }

    public static boolean create(Reimbursement r) throws SQLException{
        return reim.createReim(r);
    }

    public static int update(List<Reimbursement> r) throws SQLException{
        int i=0;
        for(Reimbursement s:r){
            i+=reim.updateReim(s)?1:0;
        }
        
        return i;
    }

    public static String validate(List<String> id, List<String> status) throws SQLException{
        StringBuilder errors = new StringBuilder("");
        for(int i=0;i<id.size();i++){
            int x=Integer.parseInt(id.get(i));
            int y=Integer.parseInt(status.get(i));
            if(!reim.validate(x,y)){
                errors.append("Error on reimbursement with id: "+x);
            }
        }
        return errors.toString();
        
    }
}
