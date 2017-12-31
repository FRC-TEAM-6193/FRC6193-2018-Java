package org.usfirst.frc.team6193.robot.subsystems;


import org.usfirst.frc.team6193.robot.Cals;
import org.usfirst.frc.team6193.robot.Enums;
import org.usfirst.frc.team6193.robot.OI;
import org.usfirst.frc.team6193.robot.RobotMap;
import org.usfirst.frc.team6193.robot.commands.DrivelineDefaultCommand;
import org.usfirst.frc.team6193.robot.lib.TalonSRX_CAN;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/** <b>Driveline Subsystem</b><br>
 * Functionality:<br>
 * 1. Controls 4 CANTalons in Arcade style driving.<br>
 * 2. Uses dual speed gearboxs with automatic shifting or manual shifting<br>
 * 3. Inherits from PIDSubsystems and allows for PID control in Angle and Drive modes<br>
 */
public class DrivelineSubsystem extends PIDSubsystem {


	/**<b>Driveline Requested Gear from manual or auto shift modes</b><br>
	 * 1 = LOW Gear<br>
	 * 2 = HIGH Gear 
	 */
	public int drivelineRequestedGear = Enums.GEAR_HIGH;
	/** <b>Shift Mode as an Integer</b><br>
	 * 0 = Manual shift mode with controller buttons for low and high<br>
	 * 1 = Automatic shift mode
	 */
	public int drivelineShiftMode = Enums.SHIFT_MODE_MANUAL; // 0 = Manual, 1 = Automatic
	/** <b>PID Mode as an Integer</b><br>
	 * 0 = Drive mode<br>
	 * 1 = Rotate mode
	 */
	public int drivelinePIDMode = Enums.PID_MODE_DRIVE;
	public double drivelineAutoCommandRampInitTime = 0.0;
	
	/**<b>Driveline Currently Active Gear as an integer</b><br>
	 * 1 = LOW Gear<br>'
	 * 2 = HIGH Gear 
	 */
	private int m_drivelineCurrentGear = Enums.GEAR_HIGH;
	private double m_drivelineShiftTime = 0.0;
	private double m_drivelineAutoInitAngle = 0.0;
	private double m_drivelineAutoInitPosition = 0;
	
	private TalonSRX_CAN m_leftMotCtrl_1;
	private TalonSRX_CAN m_leftMotCtrl_2;
	private TalonSRX_CAN m_rightMotCtrl_1;
	private TalonSRX_CAN m_rightMotCtrl_2;

	private DoubleSolenoid m_drivelineShiftSolenoid;
	private ADXRS450_Gyro m_gyro;
	
	private DifferentialDrive m_robotDrive;

	
    /**<b>DrivelineSubsystem Constructor</b><br>moving the robot.
     */
    public DrivelineSubsystem() {
    	super(1,0,0);
    	m_leftMotCtrl_1 = new TalonSRX_CAN(RobotMap.k_DrivelineLeftMotCtrl_1_CANID);
    	m_leftMotCtrl_2 = new TalonSRX_CAN(RobotMap.k_DrivelineLeftMotCtrl_2_CANID);
    	m_rightMotCtrl_1 = new TalonSRX_CAN(RobotMap.k_DrivelineRightMotCtrl_1_CANID);
    	m_rightMotCtrl_2 = new TalonSRX_CAN(RobotMap.k_DrivelineRightMotCtrl_2_CANID);
    	m_leftMotCtrl_1.configEncoderCodesPerRev(250);
    	SpeedControllerGroup leftSpeedControllerGroup = new SpeedControllerGroup(m_leftMotCtrl_1, m_leftMotCtrl_2);
    	SpeedControllerGroup rightSpeedControllerGroup = new SpeedControllerGroup(m_rightMotCtrl_1, m_rightMotCtrl_2);
    	m_drivelineShiftSolenoid = new DoubleSolenoid(RobotMap.k_DrivelineShiftSolenoidForwardPort, RobotMap.k_DrivelineShiftSolenoidForwardPort + 2);
    	m_gyro = new ADXRS450_Gyro();
    	
    	m_robotDrive = new DifferentialDrive(leftSpeedControllerGroup, rightSpeedControllerGroup);

    }
    /** <b>Select the new gear and command the solenoid</b><br>
     * 
     * kForward = 1st gear or Low gear<br>
     * kReverse = 2nd gear or High gear
     */
    public void selectGear(double move, double rotate) {
    	if(drivelineShiftMode == Enums.SHIFT_MODE_AUTO && Timer.getFPGATimestamp() > m_drivelineShiftTime + Cals.k_DrivelineShiftDelay) {
    		drivelineRequestedGear = getNewAutoShiftGear(move, rotate);
    	}
    	setGear();

    }
    /**
     * Set the gear to the drivelineRequestedGear variable
     */
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

    public int getCurrentGear() {
    	return m_drivelineCurrentGear;
    }
    /**<b>getNewAutoShiftGear(double move, double rotate)</b><br>
     * 
     * @param move -1 to 1 command to move
     * @param rotate -1 to 1 command to rotate
     * @return A new gear for requested gear
     */
    private int getNewAutoShiftGear(double move, double rotate) {
    	
    	double speed = getDrivelineVelocity();
    	
    	if(speed < Cals.k_DrivelineAutoShiftDownSpeed) {
    		return 1;
    	}else if(speed > Cals.k_DrivelineAutoShiftUpSpeed && Math.abs(rotate) < Cals.k_DrivelineAutoShiftRotateLimit) {
    		return 2;
    	}
    	return drivelineRequestedGear;
    }
    /** <b>double getDrivelineSpeed()</b><br>
     * getSpeed() returns ticks in 100ms
     * We want this to return our ft/sec
     * 7680 ticks per wheel rotation
     * 12.57 inches/wheel rotation
     * if speed returned 7680 then I would have gone one revolution in 100ms or 10 revolutions /sec
     * 10 rev/sec = 125.7 inch/sec or 10.475 ft/sec
     * TODO: This needs calibration and scale factor or set TalonSRX cnts per rev
     * @return The speed of the driveline in ft/sec
     */
    public double getDrivelineVelocity() {
    	double leftVel = m_leftMotCtrl_1.getEncVelocity();
    	double rightVel = m_rightMotCtrl_1.getEncVelocity();
    	double vel = (leftVel - rightVel)/2.0;
    	vel = vel * Cals.k_DrivelineVeltoFtPerSec_Scale;
    	//vel = vel * 10/ Cals.k_DrivelineEncoder_CntPerWheelRev * Cals.k_DrivelineWheelCircumference_Inch/12.0;
    	return vel;
    }
    /** <b>double getDrivelinePosition()</b><br>
     * This will return the current position of the robot in inches.<br>
     * Use {@link DrivelineSubsystem#initDrivelinePosition() initDrivelinePosition()} and {@link DrivelineSubsystem#getDrivelinePositionFromInitialization() getDrivelinePositionFromInitialization()} 
     * 
     * @return The position of the driveline in inch
     */
    public double getDrivelinePosition() {
    	double leftPosition = m_leftMotCtrl_1.getEncPosition() * Cals.k_DrivelineEncoder_InchPerCnt;
    	double rightPosition = m_rightMotCtrl_1.getEncPosition() * Cals.k_DrivelineEncoder_InchPerCnt;
    	return (leftPosition - rightPosition)/2.0;
   		//return m_leftMotCtrl_1.getEncPosition() * Cals.k_DrivelineEncoder_InchPerCnt;

    }
    /**<b>initDrivelinePosition()</b><br>
     * Set a local vaiable to the current Driveline Position
     * 
     */
    public void initDrivelinePosition() {
    	m_drivelineAutoInitPosition = getDrivelinePosition();
    }
    /**
     * Get the current Driveline position minus the initialized position.
     * @return
     */
    public double getDrivelinePositionFromInitialization() {
    	return getDrivelinePosition() - m_drivelineAutoInitPosition;
    }
    /**
     * shows the angle at which the robot is facing
     * @return
     */
    public double getDrivelineAngle() {
    	return m_gyro.getAngle();
    }
    /**
     * Sets the offset angle for further calls of getDrivelineAngleFromInitialization()
     */
    public void initDrivelineAngle() {
    	m_drivelineAutoInitAngle = m_gyro.getAngle();
    }
    /**
     * receive angle of which the robot is facing
     * @return
     */
    public double getDrivelineAngleFromInitialization() {
    	return m_gyro.getAngle() - m_drivelineAutoInitAngle;
    }
    public double getDrivelineCurrent() {
    	double leftAmps = m_leftMotCtrl_1.getOutputCurrent() + m_leftMotCtrl_2.getOutputCurrent();
    	double rightAmps = m_rightMotCtrl_1.getOutputCurrent() + m_rightMotCtrl_2.getOutputCurrent();
    	return (leftAmps + rightAmps)/2.0;
    }
    /**
     * This is used by autonomous/PID commands. The gear is set in the initialize of the command.
     * TODO: A gradual ramp needs to be implemented. The default start gear is High gear and the shock of 
     * the PID will be to large. Every PID will take more that 0.5 seconds, therefore we can do a ramp from
     * 0 to max for 0.5 seconds.
     * 
     * @param move
     * @param rotate
     */
    public void Drive(double move, double rotate) {
    	// A linear ramp for 0.5 seconds to reduce the initial shock of the PID or SD max command
    	if((Math.abs(move) > 0.0  || Math.abs(rotate) > 0.0) && Timer.getFPGATimestamp() < drivelineAutoCommandRampInitTime + Cals.k_DrivelineAutoRampTime_Sec) {
    		move = move * (Timer.getFPGATimestamp()-drivelineAutoCommandRampInitTime) * 1/Cals.k_DrivelineAutoRampTime_Sec;
    		rotate = rotate * (Timer.getFPGATimestamp()-drivelineAutoCommandRampInitTime) * 1/Cals.k_DrivelineAutoRampTime_Sec;
    	}
    	// A compensation for mechanical drift during autonomous drive commands
    	// Amazingly this actually works fairly well without oscillation
    	if(Math.abs(move) > 0.0) {
    		rotate = getDrivelineAngleFromInitialization() * Cals.k_DrivelineAutoAngleCompensationFactor;
    	}
    	m_robotDrive.arcadeDrive(move, rotate);
    }
    /**moves the robot and connects the driveline to the joystick
     * 
     */
    public void Drive() {
    	double move = OI.joystickXBOX.getRawAxis(2) - OI.joystickXBOX.getRawAxis(3);
    	double rotate = OI.joystickXBOX.getX();
    	selectGear(move,rotate);
    	m_robotDrive.arcadeDrive(move, rotate);
   		//robotDrive.curvatureDrive(move, rotate, true);
    }
    /**
     * positions the robot based on the joystick placement
     */

    public void setRotatePID() {
    	getPIDController().setPID(Cals.k_Driveline_PID_ROTATE_P, Cals.k_Driveline_PID_ROTATE_I, Cals.k_Driveline_PID_ROTATE_D);
    }
    /**
     * Moves the robot according to the position of the controller trigger
     */
    public void setDrivePID() {
    	getPIDController().setPID(Cals.k_Driveline_PID_DRIVE_P, Cals.k_Driveline_PID_DRIVE_I, Cals.k_Driveline_PID_DRIVE_D);
    }
    /**
     * sets the numbers for the PID to move forward or backward
     * @param p
     * @param i
     * @param d
     */
    public void setDrivePID(double p, double i, double d) {
    	getPIDController().setPID(p, i, d);
    }
    /**
     * sets the numbers for the PID for rotate
     * @param p
     * @param i
     * @param d
     */
    public void setRotatePID(double p, double i, double d) {
    	getPIDController().setPID(p, i, d);
    }
    /**
     * Initialize the default command for this subsystem.
     * When no other command requires this subsystem, this command will run.
     */
    public void initDefaultCommand() {
        setDefaultCommand(new DrivelineDefaultCommand());
    }
    /**
     * The PIDController will call this method to get the sensor value used in the PID calculations.
     * The values used are initialized at the start of the command.
     * This is done to help with onTagert Min/Max values.
     * 
     */
    protected double returnPIDInput() {
    	if(drivelinePIDMode == Enums.PID_MODE_DRIVE) {
    		return getDrivelinePositionFromInitialization();
    	}else {
    		return getDrivelineAngleFromInitialization();
    	}
    }
    /**
     * @param output 
     * The output of the PID controller is done through this method.
     * The PIDController will call this method after the calculations for the user to
     * set a motor value.
     */
    protected void usePIDOutput(double output) {
    	if(drivelinePIDMode == Enums.PID_MODE_DRIVE) {
    		Drive(output,0.0);
    	}else {
    		Drive(0.0,output);
    	}
    }
}
