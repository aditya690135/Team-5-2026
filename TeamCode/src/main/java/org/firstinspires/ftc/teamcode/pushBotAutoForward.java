package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous (name="Ringmaster Forward Auto Forward")
public class pushBotAutoForward extends LinearOpMode {

    // Define motor variables
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables
        frontLeftMotor  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightMotor   = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeft");
        backRightMotor  = hardwareMap.get(DcMotor.class, "backRight");

        // Reverse the right-side motors so positive power drives the robot forward
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Tell the motors to just use raw power, bypassing encoders
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Ready to start");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match
        if (opModeIsActive()) {

            // Set all 4 motors to move forward
            // (Adjust the power from 0.0 to 1.0 to change speed)
            frontLeftMotor.setPower(0.5);
            backLeftMotor.setPower(0.5);
            frontRightMotor.setPower(0.5);
            backRightMotor.setPower(0.5);

            // Infinite loop to keep the motors spinning as long as the op mode is active
            while (opModeIsActive()) {
                telemetry.addData("Status", "Driving forward infinitely...");
                telemetry.update();
            }

            // Stop the motors when the Stop button is pressed
            frontLeftMotor.setPower(0.5);
            backLeftMotor.setPower(0.5);
            frontRightMotor.setPower(0.5);
            backRightMotor.setPower(0.5);
        }
    }
}