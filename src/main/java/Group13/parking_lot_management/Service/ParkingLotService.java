package Service;

import dao.ParkingHistoryDAO;
import dao.ParkingLotDAO;
import dao.StudentDAO;
import model.ParkingHistory;
import model.ParkingLot;
import model.Student;
import model.VehicleType;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.Calendar;

public class ParkingLotService {
    private ParkingLotDAO parkingLotDAO=new ParkingLotDAO();
    private ParkingHistoryDAO parkingHistoryDAO=new ParkingHistoryDAO();
    private StudentDAO studentDAO=new StudentDAO();


    public int checkslot(int id){
        return parkingLotDAO.getByKey(id).getCapacity()-
                parkingLotDAO.getByKey(id).getCurrent_count();
    }

    public int checkin(Student student, VehicleType vehicleType,int id){
        StudentService studentService=new StudentService();
        if(vehicleType.getName().equals("Car")&&checkslot(id)>=3) {
            if(studentService.checkbalance(student,vehicleType)==1) return 3;
            else return 0;
        }else if(!vehicleType.getName().equals("Car")&&checkslot(id)>0) {
            if(studentService.checkbalance(student,vehicleType)==1) return 1;
            else return 0;
        }
        return 0;
    }

    public void insertin(Student student,VehicleType vehicleType,int id){
        if(checkin(student,vehicleType,id)>0) {
            ParkingLot parkingLot=parkingLotDAO.getByKey(id);
            parkingLot.setCurrent_count(parkingLot.getCurrent_count()
                    +checkin(student,vehicleType,id));
            if(parkingLotDAO.saveOrUpdate(parkingLot)) {
                Timestamp entry_time=new Timestamp(Calendar.getInstance().getTime().getTime());
                ParkingHistory parkingHistory = new ParkingHistory(parkingLotDAO.getByKey(id),
                        student, vehicleType,new String(),entry_time);
                parkingHistoryDAO.insert(parkingHistory);
            }
        }else if(student==null){
            Timestamp entry_time=new Timestamp(Calendar.getInstance().getTime().getTime());
            ParkingHistory parkingHistory=new ParkingHistory(parkingLotDAO.getByKey(id),
                    student, vehicleType,new String(),entry_time);
            parkingHistoryDAO.insert(parkingHistory);
        }
    }

    public void updateout(ParkingHistory parkingHistory){
        Timestamp exit_time=new Timestamp(Calendar.getInstance().getTime().getTime());
        parkingHistory.setExit_time(exit_time);
        parkingHistory.setFee((exit_time.getHours()>18?parkingHistory.getVehicle_type().getNight_fee():
                parkingHistory.getVehicle_type().getSession_fee()));
        if(parkingHistoryDAO.saveOrUpdate(parkingHistory)) {
            ParkingLot parkingLot=parkingLotDAO.getByKey(parkingHistory.getParking_lot().getId());
            parkingLot.setCurrent_count(parkingLot.getCurrent_count()
                    -parkingHistory.getVehicle_type().getSize());
            if(parkingLotDAO.saveOrUpdate(parkingLot)){
                if(parkingHistory.getStudent()!=null){
                    parkingHistory.getStudent().setBalance(parkingHistory.getStudent().getBalance()
                            -(exit_time.getHours()>18?parkingHistory.getVehicle_type().getNight_fee()
                            :parkingHistory.getVehicle_type().getSession_fee()));
                    if (studentDAO.saveOrUpdate(parkingHistory.getStudent()))
                        System.out.println(parkingHistory.getStudent().getBalance());
                }else System.out.println("Khach vang lai da thanh toan tien mat");

            }

        }
    }





}
