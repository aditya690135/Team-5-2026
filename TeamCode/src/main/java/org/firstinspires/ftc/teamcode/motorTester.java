package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class motorTester extends OpMode {
//introducing variables
    private DcMotor frontLeft, frontRight, backLeft, backRight;


    public void init() {


        //Mapping motor variables to the motor names on the Driver Hub.
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
    }

    public void loop() {
        /*Setting all motors to a certain button */
        if (gamepad1.a) {
            frontRight.setPower(0.2);
        } else {
            frontRight.setPower(0);
        }


        if (gamepad1.b) {
            frontLeft.setPower(0.2);
        } else {
            frontLeft.setPower(0);
        }


        if (gamepad1.x) {
            backRight.setPower(0.2);
        } else {
            backRight.setPower(0);
        }


        if (gamepad1.y) {
            backLeft.setPower(0.2);
        } else {
            backLeft.setPower(0);
        }


    }

}