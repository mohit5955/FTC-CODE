package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import static com.pedropathing.api.Paths.*; // gives us line(), curve(), path()

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Test X Y Heading", group = "Test")
public class Pedro24InchTest extends OpMode {

    private Follower follower;

    // Use degrees for heading input
    private final PoseFactory p = PoseFactory.degrees();

    // Where you physically place the robot before pressing Play
    private final Pose startPose = p.of(0, 0, 0);

    // Target pose to test — change these numbers to whatever you want
    private final Pose targetPose = p.of(24, 24, 90);

    private Path testPath() {
        return line(startPose, targetPose).linear(startPose, targetPose);
    }

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    @Override
    public void start() {
        follower.follow(testPath());
    }

    @Override
    public void loop() {
        follower.update();

        Pose current = follower.pose();

        telemetry.addData("Target X", targetPose.x());
        telemetry.addData("Target Y", targetPose.y());
        telemetry.addData("Target Heading (deg)", Math.toDegrees(targetPose.heading()));
        telemetry.addLine("---");
        telemetry.addData("Current X", current.x());
        telemetry.addData("Current Y", current.y());
        telemetry.addData("Current Heading (deg)", Math.toDegrees(current.heading()));
        telemetry.addData("Follower Mode", follower.mode());
        telemetry.addData("Busy?", follower.isBusy());
        telemetry.update();
    }
}