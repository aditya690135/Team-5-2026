package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private DcMotor shooterMotor;
    //private CRServo shooterServo1;
    private boolean reverseMotor = false;

//    public int testMethod(int x, int y) {
//        return x+y;
//    }
//
//    testMethod(1, 2);



    public Shooter(HardwareMap map) {
        shooterMotor = map.get(DcMotor.class, "shooterMotor");
        shooterMotor.setDirection(reverseMotor ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        //shooterServo1 = map.get(CRServo.class, "spin");
    }



    public void runShooter(double power) {
        shooterMotor.setPower(reverseMotor ? -power : power);
        //shooterServo1.setPower(reverseMotor ? -power : power);
    }
}