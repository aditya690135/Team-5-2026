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



    public Shooter (HardwareMap map) {
        shooterMotor = map.get(DcMotor.class, "shooterMotor");
        shooterMotor.setDirection(reverseMotor ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        //shooterServo1 = map.get(CRServo.class, "spin");
    }



    public void outtakeShoot (double RTrigger) {
        if (Math.abs(RTrigger) > 0.1) {
            shooterMotor.setPower(1);
            //shooterServo1.setPower(reverseMotor ? -1 : 1);
        } else {
            shooterMotor.setPower(0);
            //shooterServo1.setPower(0);
        }
    }
}
