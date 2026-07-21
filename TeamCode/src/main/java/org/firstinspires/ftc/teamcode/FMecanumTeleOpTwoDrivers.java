package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.lib.Shooter;
import org.firstinspires.ftc.teamcode.lib.Intake;

@TeleOp (name = "Mecanum TeleOp Two Drivers", group = "LinearOpMode")
public class FMecanumTeleOpTwoDrivers extends LinearOpMode {

    private CRServo ArmAxonCR;

    @Override
    public void runOpMode() throws InterruptedException {

        final double DeadBand = 0.05;
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        ArmAxonCR = hardwareMap.get(CRServo.class, "intakeServo");
        DcMotor shooterMotor = hardwareMap.dcMotor.get("shooterMotor");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        ArmAxonCR.setDirection(CRServo.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Shooter shooter = new Shooter(hardwareMap);
        Intake intake = new Intake(hardwareMap);


        // Create an object to receive the IMU angles
       /* YawPitchRollAngles robotOrientation;
        robotOrientation = imu.getRobotYawPitchRollAngles();

        // Create angular velocity array variable
        AngularVelocity myRobotAngularVelocity;
*/
        // Retrieve the IMU from the hardware map
        GoBildaPinpointDriver imu = hardwareMap.get(GoBildaPinpointDriver.class, "pinpointimu");

        imu.recalibrateIMU();

        imu.resetPosAndIMU();

        waitForStart();

        boolean driveType = true;

        boolean hasUpdated = false;

        if (isStopRequested()) return;
        while (opModeIsActive()) {
            double gamepad1LeftY = -gamepad1.left_stick_y;
            double gamepad1LeftX = -gamepad1.left_stick_x;
            double gamepad1RightX = gamepad1.right_stick_x;
            double gamepad2LeftY = -gamepad2.left_stick_y;
            double gamepad2LeftX = -gamepad2.left_stick_x;
            double gamepad2RightX = gamepad2.right_stick_x;
            if (Math.abs(gamepad1LeftY) < DeadBand) {
                gamepad1LeftY = 0;
            }
            if (Math.abs(gamepad1LeftX) < DeadBand) {
                gamepad1LeftX = 0;
            }
            if (Math.abs(gamepad1RightX) < DeadBand) {
                gamepad1RightX = 0;
            }
            if (Math.abs(gamepad2LeftY) < DeadBand) {
                gamepad1RightX = 0;
            }
            if (Math.abs(gamepad2LeftX) < DeadBand) {
                gamepad1RightX = 0;
            }
            if (Math.abs(gamepad2RightX) < DeadBand) {
                gamepad1RightX = 0;
            }
            double y = gamepad1LeftY + (-gamepad2.left_stick_y / 5);
            double x = gamepad1LeftX + (-gamepad2.left_stick_x / 5);
            double rx = gamepad1RightX + (gamepad2.right_stick_x / 5);

            double CPR = 8192;

            if (gamepad1.back && !hasUpdated) {
                driveType = !driveType;
                hasUpdated = true;
            }

            if (!gamepad1.back) {
                hasUpdated = false;
            }

            // Get the current position of the motor
            //int position = motor.getCurrentPosition();
            //double revolutions = position / CPR;

            //double angle = revolutions * 360;

            //double Arm_Pos = -angle;

            double LTrigger = gamepad2.left_trigger;
            intake.runIntake(LTrigger);
            double RTrigger = gamepad2.right_trigger;
            shooter.outtakeShoot(RTrigger);

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on some controllers.
            if (gamepad1.start) {
                imu.resetPosAndIMU();
            }

            shooterMotor.setPower(0);


            if (gamepad2.b) {
                shooterMotor.setPower(0.5);
            } else if (gamepad2.a) {
                shooterMotor.setPower(-1);
            }

            ArmAxonCR.setPower(0);

            if (LTrigger > 0.01) {
                ArmAxonCR.setPower(-1);
            }

            if (RTrigger > 0.01) {
                ArmAxonCR.setPower(1);
            }

            double botHeading = imu.getHeading(AngleUnit.RADIANS);
            double botHeadingMeasure = imu.getHeading(AngleUnit.DEGREES);
            telemetry.addLine("Field orientation is: " + botHeadingMeasure);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            if (driveType) {
                double frontLeftPower = -y + rx + x;
                double backLeftPower = -y + rx - x;
                double frontRightPower = -y - rx - x;
                double backRightPower = -y - rx + x;

                double max1 = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
                double max2 = Math.max(Math.abs(frontRightPower), Math.abs(backRightPower));
                double max3 = Math.max(max1, max2);

                if (max3 < 1) {
                    max3 = 1;
                }

                frontLeftMotor.setPower(frontLeftPower / max3);
                backLeftMotor.setPower(backLeftPower / max3);
                frontRightMotor.setPower(frontRightPower / max3);
                backRightMotor.setPower(backRightPower / max3);
            } else {
                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
                double frontLeftPower = (rotY - rotX + rx) / denominator;
                double backLeftPower = (rotY + rotX + rx) / denominator;
                double frontRightPower = (rotY + rotX - rx) / denominator;
                double backRightPower = (rotY - rotX - rx) / denominator;

                frontLeftMotor.setPower(frontLeftPower);
                backLeftMotor.setPower(backLeftPower);
                frontRightMotor.setPower(frontRightPower);
                backRightMotor.setPower(backRightPower);
            }

            telemetry.update();
            imu.update();
        }

    }
    }