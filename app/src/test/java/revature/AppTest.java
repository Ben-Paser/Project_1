/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package revature;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.junit.Before;
import org.junit.Test;

import revature.Models.Reimbursement;
import revature.Models.Users;
import revature.dao.reimDaoImpl;
import revature.dao.userDaoImpl;
import revature.services.ReimService;
import revature.services.UserService;

import static revature.util.ConnectionUtil.con;

public class AppTest {
    private static userDaoImpl ud=new userDaoImpl();
    private static reimDaoImpl rd= new reimDaoImpl();

    @Test
    public void TestGetAllByRole() throws SQLException {
        List<Users> u=UserService.getAllByRole(1);
        List<Users>u2=ud.getAllByRole(1);
        assertEquals(u.size(), u2.size());
        assertEquals(true,checkAllUser(u, u2));

    }
    @Test
    public void TestGetByUserId() throws SQLException{
        Users u=UserService.getById(1);
        Users u2=ud.getUserById(1);
        assertEquals(true,u.equals(u2));

    }

    @Test
    public void TestLogin() throws JsonMappingException, JsonProcessingException, SQLException{
        Users u=UserService.login("Zent","12345",1);
        Users u2=ud.login("Zent","12345",1);
        assertEquals(true,u.equals(u2));
        u=UserService.login("ZentAdmin","12345",2);
        u2=ud.login("ZentAdmin","12345",2);
        assertEquals(true,u.equals(u2));
    }

    @Test
    public void TestMapToUser(){
        String users_ID = "1";
        String userName = "Zent";
        String password = "12345";
        String fName = "David";
        String lName = "Guijosa";
        String email = "Zent@email.com";
        String role_ID = "1";
        
        Map<String,List<String>> map=new HashMap<>();

        map.put("userid", Arrays.asList(users_ID));
        map.put("username", Arrays.asList(userName));
        map.put("password", Arrays.asList(password));
        map.put("fname", Arrays.asList(fName));
        map.put("lname", Arrays.asList(lName));
        map.put("email", Arrays.asList(email));
        map.put("roleid", Arrays.asList(role_ID));

        Users u=new Users(1,userName,password,fName,lName,email,1);

        assertEquals(true,u.equals(Users.fillUsers(map).get(0)));
    }

    @Test
    public void TestMapToReim(){
        String reimid = "1";
        String amount = "2";
        String description = "This is the description";
        String author = "3";
        String resolver = "4";
        String statusid = "5";
        String typeid = "6";
        
        Map<String,List<String>> map=new HashMap<>();

        map.put("reimid", Arrays.asList(reimid));
        map.put("amount", Arrays.asList(amount));
        map.put("description", Arrays.asList(description));
        map.put("author", Arrays.asList(author));
        map.put("resolver", Arrays.asList(resolver));
        map.put("statusid", Arrays.asList(statusid));
        map.put("typeid", Arrays.asList(typeid));

        Reimbursement u=new Reimbursement(1, 2, new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()), description, "authorfName", "authorLName", "authorUName", "authorEmail", 3, 4, 5, 6);
        assertEquals(true,u.equals(Reimbursement.fillReimbursments(map).get(0)));

    }


    // @Test
    // public void TestCreateUser() throws SQLException{
    //     String username="TestingCreate10@testing.org";
    //     Users u=new Users(500, username, "Testting", "Testing", "Testing", username, 1);
    //     UserService us = new UserService();

    //     boolean test=us.create(u);
 
    //     assertEquals(true, test);
    // }


    @Test
    public void TestReimGetAll() throws SQLException {
        List<Reimbursement> u=ReimService.get();
        List<Reimbursement>u2=rd.getAllReim();
        assertEquals(u.size(), u2.size());
        assertEquals(true,checkAllReim(u, u2));
    }
    @Test
    public void TestGetReimById() throws SQLException {
        Reimbursement u=ReimService.getById(4);
        Reimbursement u2=rd.getReimById(4);
        assertEquals(true,u.equals(u2));
    }

    @Test
    public void TestGetReimByStatus() throws SQLException {
        List<Reimbursement> u=ReimService.getByStatus(4,1);
        List<Reimbursement> u2=rd.getReimByStatus(4,1);
        assertEquals(u.size(), u2.size());
        assertEquals(true,checkAllReim(u, u2));
        u=ReimService.getByStatus(4,-1);
        u2=rd.getReimByStatus(4,-1);
        assertEquals(u.size(), u2.size());
        assertEquals(true,checkAllReim(u, u2));
        u=ReimService.getByStatus(-1,4);
        u2=rd.getReimByStatus(-1,4);
        assertEquals(u.size(), u2.size());
        assertEquals(true,checkAllReim(u, u2));

    }

    @Test
    public void TestCreateReim() throws SQLException{
        Reimbursement u=new Reimbursement(-1, 456.99, new Timestamp(0), new Timestamp(0), "From Test Create Reim", "authorfName", "authorfName", "authorfName", "authorfName", 2, -1, -1, 3);

        boolean test=ReimService.create(u);
 
        assertEquals(true, test);
    }

    @Test
    public void TestValidateReim() throws SQLException{
        List<String> ids = Arrays.asList("92","91");
        List<String> status = Arrays.asList("2","3");

        String test=ReimService.validate(ids,status,2);
 
        assertEquals("", test);
    }

    public boolean checkAllReim(List<Reimbursement> u,List<Reimbursement>u2){
        for(int i=0;i<u.size();i++){
            if(!u.get(i).equals(u2.get(i)))return false;
        }
        return true;
    }
    public boolean checkAllUser(List<Users> u,List<Users>u2){
        for(int i=0;i<u.size();i++){
            if(!u.get(i).equals(u2.get(i)))return false;
        }
        return true;
    }
}
