/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6193.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6193.robot.subsystems.LiftSubsystem;
import org.usfirst.frc.team6193.robot.commands.AutonomousCommandGroup;
import org.usfirst.frc.team6193.robot.subsystems.DrivelineSubsystem;

<<<<<<< HEAD

=======
>>>>>>> branch 'master' of https://github.com/FRC-TEAM-6193/FRC6193-2018-Java.git

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	
	public static DigitalInput liftDownLimitSwitch_DIO;
	public static DigitalInput liftUpLimitSwitch_DIO;
	public static DigitalInput autonomousFieldPlacementSide;  // Left = 0, Right = 1
	
	public static DigitalInput autonomousSwitchOverride; //
	public static DigitalInput autonomousScaleOverride; // 
	
	public static LiftSubsystem liftSubsystem;
	public static DrivelineSubsystem driveline;

	public static OI oi;

	private AutonomousCommandGroup m_acg;
	private double timeStart;
	public void robotInit() {
		liftDownLimitSwitch_DIO = new DigitalInput(RobotMap.k_liftDownLimitSwitch_DIO_Port);
		liftUpLimitSwitch_DIO = new DigitalInput(RobotMap.k_liftUpLimitSwitch_DIO_Port);
		autonomousFieldPlacementSide = new DigitalInput(RobotMap.k_AutonomousFieldPlacementSide_Port);
		autonomousSwitchOverride = new DigitalInput(RobotMap.k_AutonomousSwitch_DIO_Port);
		autonomousScaleOverride = new DigitalInput(RobotMap.k_AutonomousScale_DIO_Port);
		liftSubsystem = new LiftSubsystem();
		driveline = new DrivelineSubsystem();
<<<<<<< HEAD
=======

		m_acg = new AutonomousCommandGroup();
		oi = new OI();
>>>>>>> branch 'master' of https://github.com/FRC-TEAM-6193/FRC6193-2018-Java.git

		oi = new OI();
		
		
	}
	/*
	 * This method runs all the time. It does NOT depend on the state the robot is in.
	 * 
	 */
	@Override
	public void robotPeriodic() {

		SmartDashboard.putBoolean("liftDownLimitSwitch_DIO", liftDownLimitSwitch_DIO.get());
		SmartDashboard.putBoolean("liftUpLimitSwitch_DIO",liftUpLimitSwitch_DIO.get());	
		SmartDashboard.putBoolean("autonomousFieldPlacementSide",autonomousFieldPlacementSide.get());
		SmartDashboard.putBoolean("DIO_4", autonomousSwitchOverride.get());
		SmartDashboard.putNumber("RobotPosition", driveline.getDrivelinePosition());
		SmartDashboard.putNumber("DrivelineLeftPosition", driveline.getDrivelineLeftPosition());
		SmartDashboard.putNumber("DrivelineRightPosition", driveline.getDrivelineRightPosition());
		SmartDashboard.putNumber("RobotAngle", driveline.getDrivelineAngle());
		SmartDashboard.putNumber("RobotVelocity", driveline.getDrivelineVelocity());
		SmartDashboard.putNumber("DrivelineTotalCurrent", driveline.getDrivelineCurrent());
		SmartDashboard.putNumber("RightTrigger", OI.joystickXBOX.getRawAxis(3));
		SmartDashboard.putNumber("LeftTrigger", OI.joystickXBOX.getRawAxis(2));
		//Flight Stick
		SmartDashboard.putBoolean("FlightTrigger",OI.joystickFlight.getRawButton(1));
		SmartDashboard.putBoolean("Button7",OI.joystickFlight.getRawButton(7));
		SmartDashboard.putBoolean("Button8",OI.joystickFlight.getRawButton(8));
		SmartDashboard.putBoolean("Button11",OI.joystickFlight.getRawButton(11));
		SmartDashboard.putBoolean("Button12",OI.joystickFlight.getRawButton(12));
		SmartDashboard.putNumber("XAxis",OI.joystickFlight.getRawAxis(0));
		SmartDashboard.putNumber("YAxis",OI.joystickFlight.getRawAxis(1));
		
	}
	@Override
	public void disabledInit() {
		
	}

	
	/*
	 * LEDS
	 * During disable the LEDS should indicate the crowd noise level.  
	 */
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	@Override
	public void autonomousInit() {
		//setPeriod(0.01);
		m_acg = new AutonomousCommandGroup();
		timeStart = Timer.getFPGATimestamp();
		
	}
	/*
	 * During Autonomous the LEDs should display the color of the alliance on the bottom 2/3rds of the LED strip.
	 * The top 1/3rd of the LED strip should indicate the gear the robot is in. Green = 1st gear and Alliance color = 2nd gear
	 * 
	 */
	@Override
	public void autonomousPeriodic() {
		String gameData = "";
		// while loops are not used very often in embedded systems.
		// The Timer will exit this while statement if 10 seconds have gone by. 
		// We are assuming the getGameSpecificMessage will return a 0 length string if it does not have data.
		while(gameData.length() > 2) {
			// If we waited 10 seconds in the while loop looking for good data, then exit with our default to cross the line
			if(Timer.getFPGATimestamp() - timeStart > 10.0) {
				gameData = "xxx";
				break;
			}
			gameData = DriverStation.getInstance().getGameSpecificMessage();
		}
		
		m_acg.selectAutonomousPlay(gameData, autonomousFieldPlacementSide.get(), autonomousSwitchOverride.get(), autonomousScaleOverride.get());
		m_acg.start();
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		
	}
	/*
	 * During Autonomous the LEDs should display the color of the alliance on the bottom 2/3rds of the LED strip.
	 * The top 1/3rd of the LED strip should indicate the gear the robot is in. Green = 1st gear and Alliance color = 2nd gear
	 * 
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}
