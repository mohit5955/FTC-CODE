package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "AutoPath", group = "Autonomous")
public class AutoPath extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    // Starting position
    private final Pose start =
            poseFactory.of(56, 8, 90);

    // Target position
    private final Pose target =
            poseFactory.of(56, 36, 90);


    @Override
    public void runOpMode() {

        // Create Pedro Follower
        follower = Constants.create(hardwareMap);

        // Set starting pose
        follower.setPose(start);

        // Create path
        Path path = path1();

        // Initial update
        follower.update();


        // ==============================
        // INIT TELEMETRY
        // ==============================

        telemetry.addLine("==============================");
        telemetry.addLine("       PEDRO PATH TEST");
        telemetry.addLine("==============================");

        telemetry.addData("Start X", "%.2f", start.x());
        telemetry.addData("Start Y", "%.2f", start.y());
        telemetry.addData(
                "Start Heading",
                "%.2f°",
                Math.toDegrees(start.heading())
        );

        telemetry.addLine("");

        telemetry.addData("Target X", "%.2f", target.x());
        telemetry.addData("Target Y", "%.2f", target.y());
        telemetry.addData(
                "Target Heading",
                "%.2f°",
                Math.toDegrees(target.heading())
        );

        telemetry.addLine("");
        telemetry.addLine("Distance: 28 inches");
        telemetry.addLine("");
        telemetry.addLine("Press START");

        telemetry.update();


        waitForStart();

        if (isStopRequested()) {
            return;
        }


        // ==============================
        // START PATH
        // ==============================

        follower.follow(path);


        // ==============================
        // MAIN LOOP
        // ==============================

        while (opModeIsActive()) {

            // Update Pedro
            follower.update();

            Pose pose = follower.pose();


            // ==============================
            // TELEMETRY
            // ==============================

            telemetry.clear();

            telemetry.addLine("==============================");
            telemetry.addLine("       PEDRO PATH TEST");
            telemetry.addLine("==============================");

            telemetry.addData(
                    "X",
                    "%.2f",
                    pose.x()
            );

            telemetry.addData(
                    "Y",
                    "%.2f",
                    pose.y()
            );

            telemetry.addData(
                    "Heading",
                    "%.2f°",
                    Math.toDegrees(pose.heading())
            );

            telemetry.addLine("");

            if (follower.currentPath() != null) {

                telemetry.addData(
                        "Status",
                        "FOLLOWING"
                );

                telemetry.addData(
                        "Distance Remaining",
                        "%.2f",
                        follower.distanceToEndpoint()
                );

                telemetry.addData(
                        "Path Number",
                        follower.pathIndex()
                );

            } else {

                telemetry.addData(
                        "Status",
                        "NO ACTIVE PATH"
                );
            }

            telemetry.update();


            // ==============================
            // PATH COMPLETE
            // ==============================

            if (follower.atParametricEnd()) {

                telemetry.addLine("");
                telemetry.addLine("TARGET REACHED");
                telemetry.update();

                sleep(500);

                break;
            }
        }
    }


    // ==============================
    // PATH
    // ==============================

    public Path path1() {

        return line(start, target)
                .linear(start, target);
    }
}