package org.usfirst.frc.team6193.robot.commands;

import org.usfirst.frc.team6193.robot.Cals;
import org.usfirst.frc.team6193.robot.Enums;
import org.usfirst.frc.team6193.robot.Robot;
import org.usfirst.frc.team6193.robot.lib.Interpolation;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Stopping Distance Command for autonomous mode
 * Issues with Timed autonomous:
 * 1. Battery voltage is always different.
 * 2. A variable battery voltage makes the distance traveled variable
 * Why this may be better?
 * 1. Distance traveled will always be reached.
 * 2. Battery voltage does not matter
 * 3. The momentum of the robot at a percent voltage command is used.
 * 4. When the distance is reached the command is set to zero. 
 * 5. A calibration table is used to determine how far the robot travels after command is set to zero with a percent voltage.
 * 6. All motors need to be set to brake mode to have a minimum stopping distance.
 * Issues with this:
 * 1. The command will finish while the robot is still moving or sliding to a stop.
 * 2. Usually the next command is a rotate command 
 * 3. Possible fix is to set a small delay between the commands if this is really an issue.
 * 3. 
 */
public class DrivelineDriveSDCommand extends Command {

	private double m_distance;
	private double m_percentVoltage;
	private int m_gear;
	/**
	 * 
	 * @param distance Distance in inches (Positive only, use a negative percentVoltage to go in reverse)
	 * @param percentVoltage -1.0 to 1.0 to output to motor
	 * @param gear The gear to drive in.
	 */
    public DrivelineDriveSDCommand(double distance, double percentVoltage, int gear) {
        m_distance = distance;
        m_percentVoltage = percentVoltage;
        m_gear = gear;
        requires(Robot.driveline);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.driveline.initDrivelinePosition();
    	Robot.driveline.drivelineRequestedGear = m_gear;
    	Robot.driveline.drivelineShiftMode = Enums.SHIFT_MODE_MANUAL;
    	Robot.driveline.setGear();
    	this.setInterruptible(true);
    	Robot.driveline.drivelineAutoCommandRampInitTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveline.driveAutonomous(m_percentVoltage, 0.0);
    }

    /**
     * @param return Return true if (distance needed - stopping distance) is reached.
     * 
     */
    protected boolean isFinished() {
    	double stoppingDistance = 0.0;
    	if(m_gear == 1) {
    		stoppingDistance = Interpolation.LnrIntrpn(Cals.k_StoppingPercentVoltage_X_Uls, Cals.k_StoppingDistance1stGear_Y_Inch, Math.abs(m_percentVoltage));
    	}else {
    		stoppingDistance = Interpolation.LnrIntrpn(Cals.k_StoppingPercentVoltage_X_Uls, Cals.k_StoppingDistance2ndGear_Y_Inch, Math.abs(m_percentVoltage));
    	}
    	double currentPosition = Robot.driveline.getDrivelinePositionFromInitialization();
    	if(Math.abs(currentPosition) >= m_distance - stoppingDistance) {
    		SmartDashboard.putNumber("RobotDistanceBegin", currentPosition);
    		m_percentVoltage = 0.0;
    		Robot.driveline.driveAutonomous(0.0, 0.0);
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveline.driveAutonomous(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	this.cancel();
    
    }
}
