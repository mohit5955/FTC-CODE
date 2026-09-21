    package org.firstinspires.ftc.teamcode.pedro;
    
    import com.pedropathing.algorithm.Foresight;
    import com.pedropathing.algorithm.ForesightConfig;
    import com.pedropathing.controllers.Controller;
    import com.pedropathing.drivetrain.Drivetrain;
    import com.pedropathing.follower.Follower;
    import com.pedropathing.math.Matrix;
    import com.pedropathing.math.Vector2D;
    import com.pedropathing.revhub.drivetrains.Mecanum;
    import com.pedropathing.revhub.drivetrains.MecanumConfig;
    import com.pedropathing.revhub.localizers.PinpointConfig;
    import com.pedropathing.revhub.localizers.PinpointLocalizer;
    
    import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    import com.qualcomm.robotcore.hardware.HardwareMap;
    
    import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
    
    public class Constants {
    
        // ==============================
        // PINPOINT LOCALIZER
        // ==============================

        public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
            c.name.set("pinpoint");
            c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
            c.xPodOffset.set(2.237431083138534);
            c.yPodOffset.set(-3.217569786732591);
            c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
            c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
            c.globalDistanceUnit.set(DistanceUnit.INCH);
            c.offsetUnits.set(DistanceUnit.INCH);
        });
//        public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
//
//            c.name.set("pinpoint");
//
//            c.podType.set(
//                    GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD
//            );
//
//            // Temporary offsets
//            // Replace these after running Pinpoint Tuner
//            c.xPodOffset.set(0.0);
//            c.yPodOffset.set(0.0);
//
//            // Your tested directions
//            c.xPodDirection.set(
//                    GoBildaPinpointDriver.EncoderDirection.FORWARD
//            );
//
//            c.yPodDirection.set(
//                    GoBildaPinpointDriver.EncoderDirection.REVERSED
//            );
//
//            c.globalDistanceUnit.set(DistanceUnit.INCH);
//            c.offsetUnits.set(DistanceUnit.INCH);
//        });
    
    
        // ==============================
        // MECANUM DRIVETRAIN
        // ==============================
    
        public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
    
            c.frontLeftName.set("FL");
            c.frontRightName.set("FR");
            c.backLeftName.set("BL");
            c.backRightName.set("BR");
    
    
            /*
             * Starting motor directions.
             *
             * We will verify these with the
             * Mecanum Tuner.
             */
    
            c.frontLeftDirection.set(
                    DcMotorSimple.Direction.FORWARD
            );
    
            c.frontRightDirection.set(
                    DcMotorSimple.Direction.REVERSE
            );
    
            c.backLeftDirection.set(
                    DcMotorSimple.Direction.FORWARD
            );
    
            c.backRightDirection.set(
                    DcMotorSimple.Direction.REVERSE
            );
        });
    
    
        // ==============================
        // FORESIGHT ALGORITHM
        // ==============================
    
//        public static ForesightConfig foresightConfig =
//                new ForesightConfig(c -> {
//
//                    /*
//                     * Basic starting values.
//                     *
//                     * These are NOT final tuning values.
//                     */
//
//
//
//                    c.headingDriveRatio.set(0.5);
//
//                    c.brakeAtEnd.set(true);
//
//                    c.translationalDeviationTolerance.set(2.0);
//
//                    c.headingDeviationTolerance.set(
//                            Math.toRadians(5)
//                    );
//                });

        public static ForesightConfig foresightConfig = new ForesightConfig(
                c -> {
                    Controller primaryTranslationalForward = Controller.proportional(0.25404245293683547);
                    Controller secondaryTranslationalForward = Controller.proportional(0.0938618982207993);
                    Controller primaryTranslationalLateral = Controller.proportional(102.15694303350831);
                    Controller secondaryTranslationalLateral = Controller.proportional(37.74426076709017);

                    c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                    c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                    c.coast.set(Controller.proportionalFeedforward(0.013008279202095624));
                    c.brake.set(Controller.proportionalFeedforward(0.011057037321781281));

                    c.headingFeedback.set(Controller.proportional(3.9874317260002337));
                    c.headingBrakeCoefficients.set(Vector2D.cartesian(0.05372834159338006, 0.007083737173153856));

                    c.linearBrakeCoefficients.set(Matrix.diag(0.07042010430654493, 0.04143784749526655));
                    c.quadraticBrakeCoefficients.set(Matrix.diag(0.0023756871687752035, 0.0026080516800165263));

                    c.maxAchievableForwardVelocity.set(63.37276175837819);
                    c.maxAchievableStrafeVelocity.set(48.4211277303249);
                    c.naturalForwardDeceleration.set(25.926630962063868);
                    c.naturalStrafeDeceleration.set(65.60973459267453);

                    // Your movement-feel settings from before — add these back in
                    c.headingDriveRatio.set(0.5);
                    c.brakeAtEnd.set(true);
                    c.translationalDeviationTolerance.set(2.0);
                    c.headingDeviationTolerance.set(Math.toRadians(5));
                }
        );
    
    
        // ==============================
        // CREATE FOLLOWER
        // ==============================
    
        public static Follower create(HardwareMap hardwareMap) {
    
            PinpointLocalizer localizer =
                    new PinpointLocalizer(
                            hardwareMap,
                            localizerConfig
                    );
    
            Mecanum drivetrain =
                    new Mecanum(
                            hardwareMap,
                            drivetrainConfig
                    );
    
            Foresight foresight =
                    new Foresight(
                            foresightConfig
                    );
    
            return new Follower(
                    localizer,
                    drivetrain,
                    foresight
            );
        }
        public static Drivetrain createDrivetrain(HardwareMap hardwareMap) {
    
            return new Mecanum(
                    hardwareMap,
                    drivetrainConfig
            );
        }
    }