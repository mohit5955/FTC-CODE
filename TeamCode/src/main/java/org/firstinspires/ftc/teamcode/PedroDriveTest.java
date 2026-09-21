package org.firstinspires.ftc.teamcode;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.drivetrain.Drivetrain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name = "Pedro Drive Test", group = "Pedro Test")
public class PedroDriveTest extends LinearOpMode {

    private Drivetrain drivetrain;

    @Override
    public void runOpMode() {

        // Create Pedro drivetrain
        drivetrain = Constants.createDrivetrain(hardwareMap);

        telemetry.addLine("==============================");
        telemetry.addLine("       PEDRO DRIVE TEST");
        telemetry.addLine("==============================");
        telemetry.addLine("");
        telemetry.addLine("Drivetrain initialized");
        telemetry.addLine("");
        telemetry.addLine("Press START");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) {
            return;
        }

        while (opModeIsActive()) {

            // Driver controls
            double forward = -gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double turn = -gamepad1.right_stick_x;

            // Slow mode
            double speed =  0.4;

            forward *= speed;
            strafe *= speed;
            turn *= speed;

            // Pedro drivetrain
            drivetrain.drive(
                    new DrivePowers(
                            forward,
                            strafe,
                            turn
                    ),
                    true
            );

            telemetry.clear();

            telemetry.addLine("==============================");
            telemetry.addLine("       PEDRO DRIVE TEST");
            telemetry.addLine("==============================");

            telemetry.addData("Status", "DRIVETRAIN WORKING");

            telemetry.addLine("");

            telemetry.addData("Forward", "%.2f", forward);
            telemetry.addData("Strafe", "%.2f", strafe);
            telemetry.addData("Turn", "%.2f", turn);

            telemetry.addLine("");
            telemetry.addLine("Left Stick Y : Forward");
            telemetry.addLine("Left Stick X : Strafe");
            telemetry.addLine("Right Stick X: Turn");
            telemetry.addLine("Right Bumper : Slow Mode");

            telemetry.update();
        }

        // Stop motors
        drivetrain.drive(
                new DrivePowers(0, 0, 0),
                true
        );
    }
}