package Service;

import dao.StaffDAO;
import model.Staff;

public class StaffService {
    private StaffDAO staffDAO=new StaffDAO();

    public int check(int username,String password){
        if(staffDAO.getByKey(username).getId()!=0
                &&staffDAO.getByKey(username).getPassword().equals(password)) return 1;
        return 0;
    }

    public void changepass(int id,String password){
        Staff staff=staffDAO.getByKey(id);
        staff.setPassword(password);
        if(staffDAO.saveOrUpdate(staff)) System.out.println("Successfully " +
                "changed password");
        else System.out.println("Failed to change password");
    }

}
