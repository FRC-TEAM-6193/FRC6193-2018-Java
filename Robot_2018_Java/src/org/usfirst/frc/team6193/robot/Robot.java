/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6193.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6193.robot.commands.AutonomousCommandGroup;
import org.usfirst.frc.team6193.robot.subsystems.DrivelineSubsystem;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	public static DigitalInput DIO_1;
	public static DigitalInput DIO_2;
	public static DigitalInput DIO_3;
	public static DigitalInput DIO_4;
	
	public static DrivelineSubsystem driveline;

	public static OI oi;

	private AutonomousCommandGroup m_acg;

	@Override
	public void robotInit() {
		DIO_1 = new DigitalInput(RobotMap.k_AutonomousDIO_1_Port);
		DIO_2 = new DigitalInput(RobotMap.k_AutonomousDIO_2_Port);
		DIO_3 = new DigitalInput(RobotMap.k_AutonomousDIO_3_Port);
		DIO_4 = new DigitalInput(RobotMap.k_AutonomousDIO_4_Port);
		
		driveline = new DrivelineSubsystem();

		m_acg = new AutonomousCommandGroup();
		oi = new OI();

		
	}
	@Override
	public void robotPeriodic() {
//		double Drive_P = 1.0; 
//		double Drive_I = 0.0; 
//		double Drive_D = 0.0; 
//		double Rotate_P = 1.0; 
//		double Rotate_I = 0.0; 
//		double Rotate_D = 0.0; 
//		Drive_P = SmartDashboard.getNumber("Drive_P", 1.0);
//		Drive_I = SmartDashboard.getNumber("Drive_I", 0.0);
//		Drive_D = SmartDashboard.getNumber("Drive_D", 0.0);
//		Rotate_P = SmartDashboard.getNumber("Rotate_P", 1.0);
//		Rotate_I = SmartDashboard.getNumber("Rotate_I", 0.0);
//		Rotate_D = SmartDashboard.getNumber("Rotate_D", 0.0);
//		if(!RobotState.isAutonomous()) {
//			driveline.setDrivePID(Drive_P, Drive_I, Drive_D);
//			driveline.setRotatePID(Rotate_P,Rotate_I,Rotate_D);
//		}
		SmartDashboard.putNumber("RobotPosition", driveline.getDrivelinePosition());
		SmartDashboard.putNumber("RobotAngle", driveline.getDrivelineAngle());
		SmartDashboard.putNumber("RobotVelocity", driveline.getDrivelineVelocity());
		SmartDashboard.putNumber("DrivelineTotalCurrent", driveline.getDrivelineCurrent());
		SmartDashboard.putNumber("AutoIndex", m_acg.getAutoIndex());
	}
	@Override
	public void disabledInit() {
		m_acg.setAutonomousIndex();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	@Override
	public void autonomousInit() {
		setPeriod(0.01);
		m_acg.selectAutonomousCommandGroup();
		m_acg.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		m_acg.cancel();
		setPeriod(0.02);
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}
