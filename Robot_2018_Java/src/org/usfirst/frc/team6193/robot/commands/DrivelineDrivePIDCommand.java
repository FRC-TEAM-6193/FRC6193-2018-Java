package org.usfirst.frc.team6193.robot.commands;

import org.usfirst.frc.team6193.robot.Enums;
import org.usfirst.frc.team6193.robot.Robot;
import org.usfirst.frc.team6193.robot.subsystems.DrivelineSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DrivelineDrivePIDCommand extends Command {

	private double m_distanceSetpoint;
	private double m_percentTolerance;
	private int m_gear;
	/**
	 * 
	 * @param distance Distance in inches to travel
	 * @param percentTolerance Percent tolerance of Distance for onTarget()
	 * @param Gear to be set for this command
	 */
    public DrivelineDrivePIDCommand(double distance, double percentTolerance, int gear) {
        m_distanceSetpoint = distance;
        m_percentTolerance = percentTolerance;
        m_gear = gear;
        requires(Robot.driveline);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveline.initDrivelinePosition();
    	Robot.driveline.setDrivePID();
    	Robot.driveline.drivelinePIDMode = Enums.PID_MODE_DRIVE;
    	
    	Robot.driveline.drivelineRequestedGear = m_gear;
    	Robot.driveline.drivelineShiftMode = Enums.SHIFT_MODE_MANUAL;
    	Robot.driveline.setGear(m_gear);
    	
    	Robot.driveline.setSetpoint(m_distanceSetpoint);
    	Robot.driveline.getPIDController().setToleranceBuffer(16); // 16 x 0.02 = 0.32 seconds 
    	Robot.driveline.setInputRange(0, m_distanceSetpoint);
    	Robot.driveline.setPercentTolerance(m_percentTolerance);
    	Robot.driveline.drivelineAutoCommandRampInitTime = Timer.getFPGATimestamp();
    	Robot.driveline.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Nothing needed to be done here since Robot.driveline.enable(); in initialize()
    	// kicked off the PID loop. The isFinished() method will determine when this is done
    	// or the timeout that was set when the command was added in AutonomousCommandGroup
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.driveline.onTarget()) {
    		return true;
    	}else {
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveline.disable();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	// Shouldn't have to do anything here. All autonomous commands were added and the 
    	// default subsystems commands should not run. 
    }
}
