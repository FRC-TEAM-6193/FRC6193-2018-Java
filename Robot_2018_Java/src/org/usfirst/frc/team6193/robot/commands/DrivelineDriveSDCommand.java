package org.usfirst.frc.team6193.robot.commands;

import org.usfirst.frc.team6193.robot.Cals;
import org.usfirst.frc.team6193.robot.Enums;
import org.usfirst.frc.team6193.robot.Robot;
import org.usfirst.frc.team6193.robot.lib.Interpolation;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DrivelineDriveSDCommand extends Command {

	private double m_distance;
	private double m_power;
	private int m_gear;
	
    public DrivelineDriveSDCommand(double distance, double power, int gear) {
        m_distance = distance;
        m_power = power;
        m_gear = gear;
        requires(Robot.driveline);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveline.initDrivelinePosition();
    	Robot.driveline.drivelineRequestedGear = m_gear;
    	Robot.driveline.drivelineShiftMode = Enums.SHIFT_MODE_MANUAL;
    	Robot.driveline.setGear(m_gear);
    	this.setInterruptible(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveline.Drive(m_power, 0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double stoppingDistance = Interpolation.LnrIntrpn(Cals.k_StoppingPower_X_Uls, Cals.k_StoppingDistance_Y_Inch, m_power);
    	if(m_distance > 0) {
	    	if(Robot.driveline.getDrivelinePositionFromInitialization() >= m_distance - stoppingDistance) {
	    		Robot.driveline.Drive(0.0, 0.0);
	    		return true;
	    	}
    	}else {
	    	if(Robot.driveline.getDrivelinePositionFromInitialization() <= m_distance + stoppingDistance) {
	    		Robot.driveline.Drive(0.0, 0.0);
	    		return true;
	    	}
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveline.Drive(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.cancel();
    
    }
}
