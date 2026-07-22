package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Intake {
    private DcMotor intakeMotor;
    //private CRServo intakeServo;
    private final boolean reverseServo = false;
    private final boolean reverseMotor = false;

//    public int testMethod(int x, int y) {
//        return x+y;
//    }
//
//    testMethod(1, 2);



    public Intake (HardwareMap map) {
        intakeMotor = map.get(DcMotor.class, "intakeMotor");
        intakeMotor.setDirection(reverseMotor ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        //intakeServo = map.get(CRServo.class, "intakeServo");
        intakeMotor.getCurrentPosition();
    }



    public void runIntake(double power) {
        intakeMotor.setPower(reverseMotor ? -power : power);
    }
}