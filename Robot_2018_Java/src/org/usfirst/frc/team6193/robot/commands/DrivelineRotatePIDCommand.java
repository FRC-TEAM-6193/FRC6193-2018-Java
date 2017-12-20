package org.usfirst.frc.team6193.robot.commands;

import org.usfirst.frc.team6193.robot.Robot;
import org.usfirst.frc.team6193.robot.subsystems.DrivelineSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DrivelineRotatePIDCommand extends Command {

	private double m_angle;
	private double m_percentTolerance;
	
	/**
	 * 
	 * @param angle The angle to rotate
	 * @param percentTolerance Percent of angle for onTarget()
	 */
    public DrivelineRotatePIDCommand(double angle, double percentTolerance) {

    	m_angle = angle;
    	m_percentTolerance = percentTolerance;
        requires(Robot.driveline);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.driveline.initDrivelineAngle();
    	Robot.driveline.setRotatePID();
    	Robot.driveline.drivelinePIDMode = 1;
    	
    	Robot.driveline.setSetpoint(m_angle);
    	Robot.driveline.getPIDController().setToleranceBuffer(16); // 16 x 0.02 = 0.32 seconds 
    	Robot.driveline.setInputRange(0, m_angle);
    	Robot.driveline.setPercentTolerance(m_percentTolerance);
    	
    	Robot.driveline.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    }
}
