package org.usfirst.frc.team6193.robot.subsystems;


import org.usfirst.frc.team6193.robot.Enums;
import org.usfirst.frc.team6193.robot.OI;
import org.usfirst.frc.team6193.robot.RobotMap;
import org.usfirst.frc.team6193.robot.commands.DrivelineDefaultCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DrivelineSubsystem extends PIDSubsystem {

	/**<b>Driveline Currently Active Gear as an integer</b><br>
	 * 1 = LOW Gear<br>'
	 * 2 = HIGH Gear 
	 */
	public static int drivelineCurrentGear = Enums.GEAR_HIGH;
	/**<b>Driveline Requested Gear from manual or auto shift modes</b><br>
	 * 1 = LOW Gear<br>
	 * 2 = HIGH Gear 
	 */
	public static int drivelineRequestedGear = Enums.GEAR_LOW;
	/** <b>Shift Mode as an Integer</b><br>
	 * 0 = Manual shift mode with controller buttons for low and high<br>
	 * 1 = Automatic shift mode
	 */
	public static int drivelineShiftMode = Enums.SHIFT_MODE_MANUAL; // 0 = Manual, 1 = Automatic
	/** <b>PID Mode as an Integer</b><br>
	 * 0 = Drive mode<br>
	 * 1 = Rotate mode
	 */
	public static int drivelinePIDMode = Enums.PID_MODE_DRIVE;
	public double drivelineAutoCommandRampInitTime = 0.0;
	private double drivelineShiftTime = 0.0;
	private double drivelineAutoInitAngle = 0.0;
	private double drivelineAutoInitPosition = 0;
	private CANTalon m_leftMotCtrl_1;
	private CANTalon m_leftMotCtrl_2;
	private CANTalon m_rightMotCtrl_1;
	private CANTalon m_rightMotCtrl_2;
	private DifferentialDrive robotDrive;
	private DoubleSolenoid m_drivelineShiftSolenoid;
	private ADXRS450_Gyro m_gyro;
	
	private double PID_DRIVE_P = 1;
	private double PID_DRIVE_I = 0;
	private double PID_DRIVE_D = 0;
	
	private double PID_ROTATE_P = 1;
	private double PID_ROTATE_I = 0;
	private double PID_ROTATE_D = 0;
	
	
    /**<b>DrivelineSubsystem Constuctor</b><br>
     * The DrivelineSubsystem is responsible for containing all objects and methods for moving the robot.
     */
    public DrivelineSubsystem() {
    	super(1,0,0);
    	m_leftMotCtrl_1 = new CANTalon(RobotMap.k_DrivelineLeftMotCtrl_1_CANID);
    	m_leftMotCtrl_2 = new CANTalon(RobotMap.k_DrivelineLeftMotCtrl_2_CANID);
    	m_rightMotCtrl_1 = new CANTalon(RobotMap.k_DrivelineRightMotCtrl_1_CANID);
    	m_rightMotCtrl_2 = new CANTalon(RobotMap.k_DrivelineRightMotCtrl_2_CANID);
    	SpeedControllerGroup leftSpeedControllerGroup = new SpeedControllerGroup(m_leftMotCtrl_1, m_leftMotCtrl_2);
    	SpeedControllerGroup rightSpeedControllerGroup = new SpeedControllerGroup(m_rightMotCtrl_1, m_rightMotCtrl_2);
    	m_drivelineShiftSolenoid = new DoubleSolenoid(RobotMap.k_DrivelineShiftSolenoidForwardPort, RobotMap.k_DrivelineShiftSolenoidForwardPort + 2);
    	m_gyro = new ADXRS450_Gyro();
    	
    	robotDrive = new DifferentialDrive(leftSpeedControllerGroup, rightSpeedControllerGroup);

    }
    /** <b>Select the new gear and command the solenoid</b><br>
     * 
     * kForward = 1st gear or Low gear<br>
     * kReverse = 2nd gear or High gear
     */
    public void selectGear(double move, double rotate) {
    	if(drivelineShiftMode == 1 && Timer.getFPGATimestamp() > drivelineShiftTime + RobotMap.k_DrivelineShiftDelay) {
    		drivelineRequestedGear = getNewAutoShiftGear(move, rotate);
    	}
    	
    	if(drivelineCurrentGear != drivelineRequestedGear) {
			if(drivelineRequestedGear == 1) {
				m_drivelineShiftSolenoid.set(Value.kForward);
				drivelineCurrentGear = 1;
				drivelineShiftTime = Timer.getFPGATimestamp();
			}else {
				m_drivelineShiftSolenoid.set(Value.kReverse);
				drivelineCurrentGear = 2;
				drivelineShiftTime = Timer.getFPGATimestamp();
			}
    	}
    }
    /** <b>setGear(int gear) </b><br>
     * 
     * @param gear The gear that needs to be set immediately
     */
    public void setGear(int gear) {
    	if(drivelineCurrentGear != drivelineRequestedGear) {
			if(drivelineRequestedGear == 1) {
				m_drivelineShiftSolenoid.set(Value.kForward);
				drivelineCurrentGear = 1;
				drivelineShiftTime = Timer.getFPGATimestamp();
			}else {
				m_drivelineShiftSolenoid.set(Value.kReverse);
				drivelineCurrentGear = 2;
				drivelineShiftTime = Timer.getFPGATimestamp();
			}
    	}
    }
    /**<b>getNewAutoShiftGear(double move, double rotate)</b><br>
     * 
     * @param move -1 to 1 command to move
     * @param rotate -1 to 1 command to rotate
     * @return A new gear for requested gear
     */
    private int getNewAutoShiftGear(double move, double rotate) {
    	
    	double speed = getDrivelineSpeed();
    	
    	if(speed < RobotMap.k_DrivelineAutoShiftDownSpeed) {
    		return 1;
    	}else if(speed > RobotMap.k_DrivelineAutoShiftUpSpeed && Math.abs(rotate) < RobotMap.k_DrivelineAutoShiftRotateLimit) {
    		return 2;
    	}
    	return drivelineRequestedGear;
    }
    /** <b>double getDrivelineSpeed()</b><br>
     * 
     * @return The speed of the driveline in inch/sec
     */
    private double getDrivelineSpeed() {
    	return (m_leftMotCtrl_1.getEncVelocity() - m_rightMotCtrl_1.getEncVelocity())/2.0;
    }
    /** <b>double getDrivelinePosition()</b><br>
     * This will return the current position of the robot in inches.<br>
     * Use {@link DrivelineSubsystem#initDrivelinePosition() initDrivelinePosition()} and {@link DrivelineSubsystem#getDrivelinePositionFromInitialization() getDrivelinePositionFromInitialization()} 
     * 
     * @return The position of the driveline in inch
     */
    private double getDrivelinePosition() {
    	return m_leftMotCtrl_1.getEncPosition() * RobotMap.k_Driveline_InchPerCnt;
    }
    /**<b>initDrivelinePosition()</b><br>
     * shows format of driveline position on robot
     * 
     */
    public void initDrivelinePosition() {
    	drivelineAutoInitPosition = getDrivelinePosition();
    }
    /**
     * receives position of driveline on robot
     * @return
     */
    public double getDrivelinePositionFromInitialization() {
    	return getDrivelinePosition() - drivelineAutoInitPosition;
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
    	drivelineAutoInitAngle = m_gyro.getAngle();
    }
    /**
     * receive angle of which the robot is facing
     * @return
     */
    public double getDrivelineAngleFromInitialization() {
    	return m_gyro.getAngle() - drivelineAutoInitAngle;
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
    	// A linear ramp for 0.5 seconds to reduce the initial shock of the PID max command
    	if(Math.abs(move) > 0.0 && Timer.getFPGATimestamp() < drivelineAutoCommandRampInitTime + 0.5) {
    		move = move * (Timer.getFPGATimestamp()-drivelineAutoCommandRampInitTime) *2.0;
    	}
    	// A compensation for mechanical drift during autonomous drive commands
    	if(Math.abs(move) > 0.0) {
    		rotate = getDrivelineAngleFromInitialization() * RobotMap.k_Driveline_AutoAngleCompensationFactor;
    	}
    	robotDrive.arcadeDrive(move, rotate);
    }
    /**moves the robot and connects the driveline to the joystick
     * 
     */
    public void Drive() {
    	double move = OI.joystickXBOX.getRawAxis(2) - OI.joystickXBOX.getRawAxis(3);
    	double rotate = OI.joystickXBOX.getX();
    	selectGear(move,rotate);
    	robotDrive.arcadeDrive(move, rotate);
   		//robotDrive.curvatureDrive(move, rotate, true);
    }
    /**
     * positions the robot based on the joystick placement
     */

    public void setRotatePID() {
    	getPIDController().setPID(PID_ROTATE_P, PID_ROTATE_I, PID_ROTATE_D);
    }
    /**
     * Moves the robot according to the position of the controller trigger
     */
    public void setDrivePID() {
    	getPIDController().setPID(PID_DRIVE_P, PID_DRIVE_I, PID_DRIVE_D);
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
