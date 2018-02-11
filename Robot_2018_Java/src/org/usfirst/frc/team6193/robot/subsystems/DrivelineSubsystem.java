package org.usfirst.frc.team6193.robot.subsystems;


import org.usfirst.frc.team6193.robot.Cals;
import org.usfirst.frc.team6193.robot.Enums;
import org.usfirst.frc.team6193.robot.OI;
import org.usfirst.frc.team6193.robot.RobotMap;
import org.usfirst.frc.team6193.robot.commands.DrivelineDefaultCommand;
import org.usfirst.frc.team6193.robot.lib.DifferentialDrive6193;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class DrivelineSubsystem extends Subsystem {


	public int drivelineRequestedGear = Enums.GEAR_HIGH;
	public int drivelineShiftMode = Enums.SHIFT_MODE_MANUAL;
	public double drivelineAutoCommandRampInitTime = 0.0;
	
	private int m_drivelineCurrentGear = Enums.GEAR_HIGH;
	private double m_drivelineShiftTime = 0.0;
	private double m_drivelineAutoInitAngle = 0.0;
	private double m_drivelineAutoInitPosition = 0;

	private WPI_TalonSRX m_leftMotCtrl_1;
	private WPI_TalonSRX m_leftMotCtrl_2;
	private WPI_TalonSRX m_rightMotCtrl_1;
	private WPI_TalonSRX m_rightMotCtrl_2;

	private DoubleSolenoid m_drivelineShiftSolenoid;
	private ADXRS450_Gyro m_gyro;
	
	private DifferentialDrive6193 m_robotDrive;

    public DrivelineSubsystem() {
    	m_leftMotCtrl_1 = new WPI_TalonSRX(RobotMap.k_DrivelineLeftMotCtrl_1_CANID);
    	m_leftMotCtrl_2 = new WPI_TalonSRX(RobotMap.k_DrivelineLeftMotCtrl_2_CANID);
    	m_rightMotCtrl_1 = new WPI_TalonSRX(RobotMap.k_DrivelineRightMotCtrl_1_CANID);
    	m_rightMotCtrl_2 = new WPI_TalonSRX(RobotMap.k_DrivelineRightMotCtrl_2_CANID);

    	m_leftMotCtrl_1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100);
    	m_leftMotCtrl_1.setNeutralMode(NeutralMode.Brake);
    	m_leftMotCtrl_1.configOpenloopRamp(1, 0);
    	
    	m_leftMotCtrl_2.set(ControlMode.Follower, RobotMap.k_DrivelineLeftMotCtrl_1_CANID);
    	m_leftMotCtrl_2.setNeutralMode(NeutralMode.Brake);
    	m_leftMotCtrl_2.configOpenloopRamp(0, 0);
    	
    	m_rightMotCtrl_1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100);
    	m_rightMotCtrl_1.setNeutralMode(NeutralMode.Brake);
    	m_rightMotCtrl_1.configOpenloopRamp(1, 0);
    	
    	m_rightMotCtrl_2.set(ControlMode.Follower, RobotMap.k_DrivelineRightMotCtrl_1_CANID);
    	m_rightMotCtrl_2.setNeutralMode(NeutralMode.Brake);
    	m_rightMotCtrl_2.configOpenloopRamp(0, 0);
    	
    	SpeedControllerGroup leftSpeedControllerGroup = new SpeedControllerGroup(m_leftMotCtrl_1, m_leftMotCtrl_2);
    	SpeedControllerGroup rightSpeedControllerGroup = new SpeedControllerGroup(m_rightMotCtrl_1, m_rightMotCtrl_2);

    	m_drivelineShiftSolenoid = new DoubleSolenoid(15,RobotMap.k_DrivelineShiftSolenoidForwardPort, RobotMap.k_DrivelineShiftSolenoidForwardPort + 1);

    	m_gyro = new ADXRS450_Gyro();
    	
    	m_robotDrive = new DifferentialDrive6193(leftSpeedControllerGroup, rightSpeedControllerGroup);
    	m_robotDrive.setDeadband(Cals.k_DrivelineDeadband);
    	
    }
    
    public void driveDefault() {
    	double move = OI.joystickXBOX.getRawAxis(3) - OI.joystickXBOX.getRawAxis(2);
    	double rotate = OI.joystickXBOX.getX();
    	selectGear(move,rotate);
    	//if(RobotState.isOperatorControl() || RobotState.isTest()) {
    		m_robotDrive.arcadeDrive(move, rotate);
    		//m_robotDrive.curvatureDrive(move, rotate, true);
    	//}
    }
    
    public void driveAutonomous(double move, double rotate) {
    	// A linear ramp for 0.5 seconds to reduce the initial shock of the PID or SD max command
    	if((Math.abs(move) > 0.0  || Math.abs(rotate) > 0.0) && Timer.getFPGATimestamp() < drivelineAutoCommandRampInitTime + Cals.k_DrivelineAutoRampTime_Sec) {
    		move = move * (Timer.getFPGATimestamp()-drivelineAutoCommandRampInitTime) * 1/Cals.k_DrivelineAutoRampTime_Sec;
    		rotate = rotate * (Timer.getFPGATimestamp()-drivelineAutoCommandRampInitTime) * 1/Cals.k_DrivelineAutoRampTime_Sec;
    	}
    	// A compensation for mechanical drift during autonomous drive commands. Amazingly this actually works fairly well without oscillation
    	if(Math.abs(move) > 0.0) {
    		rotate = getDrivelineAngleFromInitialization() * Cals.k_DrivelineAutoAngleCompensationFactor;
    	}
    	m_robotDrive.arcadeDrive(move, rotate);
    }

    public void selectGear(double move, double rotate) {
    	if(drivelineShiftMode == Enums.SHIFT_MODE_AUTO && Timer.getFPGATimestamp() > m_drivelineShiftTime + Cals.k_DrivelineShiftDelay) {
    		drivelineRequestedGear = getNewAutoShiftGear(move, rotate);
    	}
    	setGear();
    }

    public void setGear() {
    	if(m_drivelineCurrentGear != drivelineRequestedGear) {
			if(drivelineRequestedGear == 1) {
				m_drivelineShiftSolenoid.set(Value.kForward);
				m_drivelineCurrentGear = 1;
			}else {
				m_drivelineShiftSolenoid.set(Value.kReverse);
				m_drivelineCurrentGear = 2;
			}
			m_drivelineShiftTime = Timer.getFPGATimestamp();
    	}
    }

    private int getNewAutoShiftGear(double move, double rotate) {
    	double speed = getDrivelineVelocity();
    	
    	if(speed < Cals.k_DrivelineAutoShiftDownSpeed) {
    		return 1;
    	}else if(speed > Cals.k_DrivelineAutoShiftUpSpeed && Math.abs(rotate) < Cals.k_DrivelineAutoShiftRotateLimit) {
    		return 2;
    	}
    	return drivelineRequestedGear;
    }

    public int getCurrentGear() {
    	return m_drivelineCurrentGear;
    }
    
    /** 
     * TalonSRX.getSpeed() returns ticks in 100ms
     * We want this to return our ft/sec
     * 7680 ticks per wheel rotation
     * 12.57 inches/wheel rotation
     * if speed returned 7680 then I would have gone one revolution in 100ms or 10 revolutions /sec
     * 10 rev/sec = 125.7 inch/sec or 10.475 ft/sec
     * @return The speed of the driveline in ft/sec
     */
    public double getDrivelineVelocity() {
    	double leftVel = m_leftMotCtrl_1.getSelectedSensorVelocity(0);
    	double rightVel = m_rightMotCtrl_1.getSelectedSensorVelocity(0);
    	double vel = (leftVel - rightVel)/2.0;
    	vel = vel * Cals.k_DrivelineVeltoFtPerSec_Scale;
//    	//vel = vel * 10/ Cals.k_DrivelineEncoder_CntPerWheelRev * Cals.k_DrivelineWheelCircumference_Inch/12.0;
    	return vel;
//    	return 0.0;
    }

    public void initDrivelinePosition() {
    	m_drivelineAutoInitPosition = getDrivelinePosition();
    }
    
    public double getDrivelinePositionFromInitialization() {
    	return getDrivelinePosition() - m_drivelineAutoInitPosition;
    }
    public double getDrivelineLeftPosition() {
    	return m_leftMotCtrl_1.getSelectedSensorPosition(0);
    }
    public double getDrivelineRightPosition() {
    	return m_rightMotCtrl_1.getSelectedSensorPosition(0);
    }
    public double getDrivelinePosition() {
    	double leftPosition = m_leftMotCtrl_1.getSelectedSensorPosition(0) * Cals.k_DrivelineEncoder_InchPerCnt;
    	double rightPosition = m_rightMotCtrl_1.getSelectedSensorPosition(0) * Cals.k_DrivelineEncoder_InchPerCnt;
    	return (leftPosition - rightPosition)/2.0;
    }
    
    public void initDrivelineAngle() {
    	m_drivelineAutoInitAngle = m_gyro.getAngle();
    }
    
    public double getDrivelineAngleFromInitialization() {
    	return m_gyro.getAngle() - m_drivelineAutoInitAngle;
    }
    
    public double getDrivelineAngle() {
    	return m_gyro.getAngle();
    }

    public double getDrivelineCurrent() {
    	double leftAmps = m_leftMotCtrl_1.getOutputCurrent() + m_leftMotCtrl_2.getOutputCurrent();
    	double rightAmps = m_rightMotCtrl_1.getOutputCurrent() + m_rightMotCtrl_2.getOutputCurrent();
    	return (leftAmps + rightAmps)/2.0;
    }

    /**
     * Initialize the default command for this subsystem.
     * When no other command requires this subsystem, this command will run.
     */
    public void initDefaultCommand() {
        setDefaultCommand(new DrivelineDefaultCommand());
    }

}
